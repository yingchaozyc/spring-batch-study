/*
 * Copyright 2006-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.batch.item.database;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapSession;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <p>
 * {@link org.springframework.batch.item.ItemReader} for reading database
 * records using iBATIS in a paging fashion.
 * </p>
 *
 * <p>
 * It executes the query specified as the {@link #setQueryId(String)} to
 * retrieve requested data. The query is executed using paged requests of a size
 * specified in {@link #setPageSize(int)}. Additional pages are requested when
 * needed as {@link #read()} method is called, returning an object corresponding
 * to current position. Some standard query parameters are provided by the
 * reader and the SQL in the named query must use some or all of these parameters
 * (depending on the SQL variant) to construct a result set of the required
 * size. The parameters are:</p>
 * <ul>
 * <li><code>_page</code>: the page number to be read (starting at 0)</li>
 * <li><code>_pagesize</code>: the size of the pages, i.e. the number of rows to
 * return</li>
 * <li><code>_skiprows</code>: the product of <code>_page</code> and
 * <code>_pagesize</code></li>
 * </ul>
 * <p>
 * Failure to write the correct platform-specific SQL often results in an
 * infinite loop in the reader because it keeps asking for the next page and
 * gets the same result set over and over.
 * </p>
 *
 * <p>
 * The performance of the paging depends on the iBATIS implementation.
 * Setting a fairly large page size and using a commit interval that matches the
 * page size should provide better performance.
 * </p>
 *
 * <p>
 * The implementation is thread-safe in between calls to
 * {@link #open(ExecutionContext)}, but remember to use
 * <code>saveState=false</code> if used in a multi-threaded client (no restart
 * available).
 * </p>
 *
 * <p><em>Note:</em> This reader was refactored as part of Spring Batch 3.0 to use the iBatis
 * APIs directly instead of using Spring's SqlMapClientTemplate as part of the upgrade to
 * support Spring 4.</p>

 * @author Thomas Risberg
 * @author Dave Syer
 * @author Michael Minella
 * @since 2.0
 * @deprecated as of Spring Batch 3.0, in favor of the native Spring Batch support
 * in the MyBatis follow-up project (http://mybatis.github.io/spring/)
 */
@Deprecated
public class IbatisPagingItemReader<T> extends AbstractPagingItemReader<T> {

	private SqlMapClient sqlMapClient;

	private String queryId;

	private Map<String, Object> parameterValues;

	private DataSource dataSource;

	public IbatisPagingItemReader() {
		setName(ClassUtils.getShortName(IbatisPagingItemReader.class));
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setSqlMapClient(SqlMapClient sqlMapClient) {
		this.sqlMapClient = sqlMapClient;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	/**
	 * The parameter values to be used for the query execution.
	 *
	 * @param parameterValues the values keyed by the parameter named used in
	 * the query string.
	 */
	public void setParameterValues(Map<String, Object> parameterValues) {
		this.parameterValues = parameterValues;
	}

	/**
	 * Check mandatory properties.
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		Assert.notNull(sqlMapClient);
		Assert.notNull(queryId);
	}

	@Override
	protected void doReadPage() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		if (parameterValues != null) {
			parameters.putAll(parameterValues);
		}
		parameters.put("_page", getPage());
		parameters.put("_pagesize", getPageSize());
		parameters.put("_skiprows", getPage() * getPageSize());
		if (results == null) {
			results = new CopyOnWriteArrayList<T>();
		}
		else {
			results.clear();
		}
		results.addAll(execute(parameters));
	}

	@SuppressWarnings("unchecked")
	private List<T> execute(Map<String, Object> parameters) {
		// We always need to use a SqlMapSession, as we need to pass a Spring-managed
		// Connection (potentially transactional) in. This shouldn't be necessary if
		// we run against a TransactionAwareDataSourceProxy underneath, but unfortunately
		// we still need it to make iBATIS batch execution work properly: If iBATIS
		// doesn't recognize an existing transaction, it automatically executes the
		// batch for every single statement...

		SqlMapSession session = this.sqlMapClient.openSession();
		if (logger.isDebugEnabled()) {
			logger.debug("Opened SqlMapSession [" + session + "] for iBATIS operation");
		}
		Connection ibatisCon = null;

		try {
			Connection springCon = null;
			boolean transactionAware = (dataSource instanceof TransactionAwareDataSourceProxy);

			// Obtain JDBC Connection to operate on...
			try {
				ibatisCon = session.getCurrentConnection();
				if (ibatisCon == null) {
					springCon = (transactionAware ?
							dataSource.getConnection() : DataSourceUtils.doGetConnection(dataSource));
					session.setUserConnection(springCon);
					if (logger.isDebugEnabled()) {
						logger.debug("Obtained JDBC Connection [" + springCon + "] for iBATIS operation");
					}
				}
				else {
					if (logger.isDebugEnabled()) {
						logger.debug("Reusing JDBC Connection [" + ibatisCon + "] for iBATIS operation");
					}
				}
			}
			catch (SQLException ex) {
				throw new CannotGetJdbcConnectionException("Could not get JDBC Connection", ex);
			}

			// Execute given callback...
			try {
				return session.queryForList(queryId, parameters);
			}
			catch (SQLException ex) {
				SQLExceptionTranslator sqlStateSQLExceptionTranslator;

				if(dataSource != null) {
					sqlStateSQLExceptionTranslator = new SQLStateSQLExceptionTranslator();
				} else {
					sqlStateSQLExceptionTranslator = new SQLErrorCodeSQLExceptionTranslator(dataSource);
				}

				throw sqlStateSQLExceptionTranslator.translate("SqlMapClient operation", null, ex);
			}
			finally {
				try {
					if (springCon != null) {
						if (transactionAware) {
							springCon.close();
						}
						else {
							DataSourceUtils.doReleaseConnection(springCon, dataSource);
						}
					}
				}
				catch (Throwable ex) {
					logger.debug("Could not close JDBC Connection", ex);
				}
			}

			// Processing finished - potentially session still to be closed.
		}
		finally {
			// Only close SqlMapSession if we know we've actually opened it
			// at the present level.
			if (ibatisCon == null) {
				session.close();
			}
		}
	}

	@Override
	protected void doJumpToPage(int itemIndex) {
	}

}
