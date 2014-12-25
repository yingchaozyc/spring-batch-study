/*
 * Copyright 2006-2013 the original author or authors.
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

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.NoSuchJobException;

/**
 * 2014-12-24 JOB实例对应的DAO层。目前分为两种:
 * A: 内存相关的dao层MapJobInstanceDao。 只供测试数据用。
 * B: 关系型数据库相关的dao层JdbcJobInstanceDao。
 * 
 * Data Access Object for job instances.
 *
 * @author Lucas Ward
 * @author Robert Kasanicky
 * @author Michael Minella
 *
 */
public interface JobInstanceDao {

	/**
	 * 2014-12-24 根据给定的名字和参数创建JobInstance。前提条件是给定条件下的JobInstance不存在。
	 * 一个有效的实例将会返回并持久化(TODO)并包含一个唯一的ID.
	 * 
	 * Create a JobInstance with given name and parameters.
	 *
	 * PreConditions: JobInstance for given name and parameters must not already
	 * exist
	 *
	 * PostConditions: A valid job instance will be returned which has been
	 * persisted and contains an unique Id.
	 *
	 * @param jobName
	 * @param jobParameters
	 * @return JobInstance
	 */
	JobInstance createJobInstance(String jobName, JobParameters jobParameters);

	/**
	 * 2014-12-24 根据指定的名字和参数创建JobInstance。如果存在返回对应的JobInstance，如果不存在
	 * 则返回为null。
	 * 
	 * Find the job instance that matches the given name and parameters. If no
	 * matching job instances are found, then returns null.
	 *
	 * @param jobName the name of the job
	 * @param jobParameters the parameters with which the job was executed
	 * @return {@link JobInstance} object matching the job name and
	 * {@link JobParameters} or null
	 */
	JobInstance getJobInstance(String jobName, JobParameters jobParameters);

	/**
	 * 2014-12-24 根据指定的实例ID返回JobInstance对象.
	 * 
	 * Fetch the job instance with the provided identifier.
	 *
	 * @param instanceId the job identifier
	 * @return the job instance with this identifier or null if it doesn't exist
	 */
	JobInstance getJobInstance(Long instanceId);

	/**
	 * 2014-12-24 根据指定的job运行时数据JobException获取Job实例JobInstance
	 * 
	 * Fetch the JobInstance for the provided JobExecution.
	 *
	 * @param jobExecution the JobExecution
	 * @return the JobInstance for the provided execution or null if it doesn't exist.
	 */
	JobInstance getJobInstance(JobExecution jobExecution);

	/**
	 * 2014-12-24 截取
	 * 
	 * Fetch the last job instances with the provided name, sorted backwards by
	 * primary key.
	 *
	 * if using the JdbcJobInstance, you can provide the jobName with a wildcard
	 * (e.g. *Job) to return 'like' job names. (e.g. *Job will return 'someJob' 
	 * and 'otherJob')
	 *
	 * @param jobName the job name
	 * @param start the start index of the instances to return
	 * @param count the maximum number of objects to return
	 * @return the job instances with this name or empty if none
	 */
	List<JobInstance> getJobInstances(String jobName, int start, int count);

	/**
	 * 2014-12-24 获取所有的job实例名称列表
	 * 
	 * Retrieve the names of all job instances sorted alphabetically - i.e. jobs
	 * that have ever been executed.
	 * @return the names of all job instances
	 */
	List<String> getJobNames();
	
	/**
	 * 2014-12-24 用like关键字来搜索匹配的job实例。
	 * 
	 * Fetch the last job instances with the provided name, sorted backwards by
	 * primary key, using a 'like' criteria
	 * 
	 * @param jobName
	 * @param start
	 * @param count
	 * @return
	 */
	List<JobInstance> findJobInstancesByName(String jobName, int start, int count);


	/**
	 * 2014-12-25 根据当前job的name查询
	 * 
	 * Query the repository for the number of unique {@link JobInstance}s
	 * associated with the supplied job name.
	 *
	 * @param jobName the name of the job to query for
	 * @return the number of {@link JobInstance}s that exist within the
	 * associated job repository
	 * @throws NoSuchJobException
	 */
	int getJobInstanceCount(String jobName) throws NoSuchJobException;

}
