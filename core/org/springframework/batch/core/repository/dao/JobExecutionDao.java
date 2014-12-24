/*
 * Copyright 2006-2007 the original author or authors.
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

package org.springframework.batch.core.repository.dao;

import java.util.List;
import java.util.Set;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;

/**
 * 2014-12-24 JOB执行对应的DAO层。目前分为两种:
 * A: 内存相关的dao层MapJobExecutionDao。 只供测试数据用。
 * B: 关系型数据库相关的dao层JdbcJobExecutionDao。
 * 
 * Data Access Object for job executions.
 * 
 * @author Lucas Ward
 * @author Robert Kasanicky
 */
public interface JobExecutionDao {

	/**
	 * 2014-12-24 保存job运行时信息JobExecution。
	 * 
	 * Save a new JobExecution.
	 * 
	 * Preconditions: jobInstance the jobExecution belongs to must have a
	 * jobInstanceId.
	 * 
	 * @param jobExecution
	 */
	void saveJobExecution(JobExecution jobExecution);

	/**
	 * 2014-12-24 更新job运行时信息JobExecution。不返回更新影响的行数信息。
	 * 
	 * Update and existing JobExecution.
	 * 
	 * Preconditions: jobExecution must have an Id (which can be obtained by the
	 * save method) and a jobInstanceId.
	 * 
	 * @param jobExecution
	 */
	void updateJobExecution(JobExecution jobExecution);

	/**
	 * 2014-12-24 根据job实例获取job运行信息列表。倒序排序，所以最先执行的排在最后。
	 * 
	 * Return all {@link JobExecution} for given {@link JobInstance}, sorted
	 * backwards by creation order (so the first element is the most recent).
	 */
	List<JobExecution> findJobExecutions(JobInstance jobInstance);

	/**
	 * 2014-12-24 根据job实例返回最后一次执行的job运行信息。
	 * 
	 * Find the last {@link JobExecution} to have been created for a given
	 * {@link JobInstance}.
	 * @param jobInstance the {@link JobInstance}
	 * @return the last {@link JobExecution} to execute for this instance
	 */
	JobExecution getLastJobExecution(JobInstance jobInstance);

	/**
	 * 2014-12-24 根据job名称返回所有正在跑(或者不确定状态)的job运行时信息。
	 * 
	 * @return all {@link JobExecution} that are still running (or indeterminate
	 * state), i.e. having null end date, for the specified job name.
	 */
	Set<JobExecution> findRunningJobExecutions(String jobName);

	/**
	 * 2014-12-24 根据唯一的Long类型的job运行时id获取到本次运行信息JobExecution。
	 * 
	 * @return the {@link JobExecution} for given identifier.
	 */
	JobExecution getJobExecution(Long executionId);

	/**
	 * 2014-12-24 状态同步。(是为了解决并发问题还是什么的？TODO)
	 * 
	 * Because it may be possible that the status of a JobExecution is updated
	 * while running, the following method will synchronize only the status and
	 * version fields.
	 * 
	 * @param jobExecution to be updated.
	 */
	void synchronizeStatus(JobExecution jobExecution);

}
