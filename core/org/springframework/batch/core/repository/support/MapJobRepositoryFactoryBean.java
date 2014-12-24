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

package org.springframework.batch.core.repository.support;

import org.springframework.batch.core.repository.dao.ExecutionContextDao;
import org.springframework.batch.core.repository.dao.JobExecutionDao;
import org.springframework.batch.core.repository.dao.JobInstanceDao;
import org.springframework.batch.core.repository.dao.MapExecutionContextDao;
import org.springframework.batch.core.repository.dao.MapJobExecutionDao;
import org.springframework.batch.core.repository.dao.MapJobInstanceDao;
import org.springframework.batch.core.repository.dao.MapStepExecutionDao;
import org.springframework.batch.core.repository.dao.StepExecutionDao;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 2014-12-24. MapJobRepositoryFactoryBean是为了去创建SimpleJobRepository的。他使用了非持久的DAO实现。
 * 所以这个repository一般是用在测试环境或者快速原型阶段。在这样的设置下你会发现ResourcelessTransactionManager
 * 是非常有用的，因为他不涉及到关系型数据库。当前类不适合使用在多线程，即使他在多线程的step下是线程安全的。  
 * 
 * A {@link FactoryBean} that automates the creation of a
 * {@link SimpleJobRepository} using non-persistent in-memory DAO
 * implementations. This repository is only really intended for use in testing
 * and rapid prototyping. In such settings you might find that
 * {@link ResourcelessTransactionManager} is useful (as long as your business
 * logic does not use a relational database). Not suited for use in
 * multi-threaded jobs with splits, although it should be safe to use in a
 * multi-threaded step.
 * 
 * @author Robert Kasanicky
 */
public class MapJobRepositoryFactoryBean extends AbstractJobRepositoryFactoryBean {

	private MapJobExecutionDao jobExecutionDao;

	private MapJobInstanceDao jobInstanceDao;

	private MapStepExecutionDao stepExecutionDao;

	private MapExecutionContextDao executionContextDao;

	/**
	 * Create a new instance with a {@link ResourcelessTransactionManager}.
	 */
	public MapJobRepositoryFactoryBean() {
		this(new ResourcelessTransactionManager());
	}

	/**
	 * Create a new instance with the provided transaction manager.
	 * 
	 * @param transactionManager {@link org.springframework.transaction.PlatformTransactionManager}
	 */
	public MapJobRepositoryFactoryBean(PlatformTransactionManager transactionManager) {
		setTransactionManager(transactionManager);
	}

	public JobExecutionDao getJobExecutionDao() {
		return jobExecutionDao;
	}

	public JobInstanceDao getJobInstanceDao() {
		return jobInstanceDao;
	}

	public StepExecutionDao getStepExecutionDao() {
		return stepExecutionDao;
	}

	public ExecutionContextDao getExecutionContextDao() {
		return executionContextDao;
	}

	/**
	 * Convenience method to clear all the map DAOs globally, removing all
	 * entities.
	 */
	public void clear() {
		jobInstanceDao.clear();
		jobExecutionDao.clear();
		stepExecutionDao.clear();
		executionContextDao.clear();
	}

	@Override
	protected JobExecutionDao createJobExecutionDao() throws Exception {
		jobExecutionDao = new MapJobExecutionDao();
		return jobExecutionDao;
	}

	@Override
	protected JobInstanceDao createJobInstanceDao() throws Exception {
		jobInstanceDao = new MapJobInstanceDao();
		return jobInstanceDao;
	}

	@Override
	protected StepExecutionDao createStepExecutionDao() throws Exception {
		stepExecutionDao = new MapStepExecutionDao();
		return stepExecutionDao;
	}

	@Override
	protected ExecutionContextDao createExecutionContextDao() throws Exception {
		executionContextDao = new MapExecutionContextDao();
		return executionContextDao;
	}

}
