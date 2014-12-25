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

import java.util.Collection;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;

/**
 * 2014-12-25 Stepִ�ж�Ӧ��DAO�㡣Ŀǰ��Ϊ����:
 * A: �ڴ���ص�dao��MapStepExecutionDao�� ֻ�����������á�
 * B: ��ϵ�����ݿ���ص�dao��JdbcStepExecutionDao��
 * 
 * @author yingchao.zyc
 *
 */
public interface StepExecutionDao {

	/**
	 * 2014-12-25 ����ָ����step����ʱ��ϢStepExecution��
	 * 
	 * Save the given StepExecution.
	 * 
	 * Preconditions: Id must be null.
	 * 
	 * Postconditions: Id will be set to a unique Long.
	 * 
	 * @param stepExecution
	 */
	void saveStepExecution(StepExecution stepExecution);

	/**
	 * 2014-12-25 ����һ�鼯�ϵ�StepExecution����Ҫע����������:
	 * 		A: �����StepExecution������Id����Ϊ�ա�
	 * 		B: StepExecution��������Ψһ��id��
	 * 
	 * Save the given collection of StepExecution as a batch.
	 * 
	 * Preconditions: StepExecution Id must be null.
	 * 
	 * Postconditions: StepExecution Id will be set to a unique Long.
	 * 
	 * @param stepExecutions
	 */
	void saveStepExecutions(Collection<StepExecution> stepExecutions);

	/**
	 * 2014-12-25 ����ָ����StepExecution��
	 * 
	 * Update the given StepExecution
	 * 
	 * Preconditions: Id must not be null.
	 * 
	 * @param stepExecution
	 */
	void updateStepExecution(StepExecution stepExecution);

	/**
	 * 2014-12-25 ����ָ����id��ȡStepExecution��
	 * 
	 * Retrieve a {@link StepExecution} from its id.
	 * 
	 * @param jobExecution the parent {@link JobExecution}
	 * @param stepExecutionId the step execution id
	 * @return a {@link StepExecution}
	 */
	StepExecution getStepExecution(JobExecution jobExecution, Long stepExecutionId);

	/**
	 * 2014-12-25 û���������Ӣ�ķ���ͷ������֡����� --��  TODO
	 * 
	 * Retrieve all the {@link StepExecution} for the parent {@link JobExecution}.
	 * 
	 * @param jobExecution the parent job execution
	 */
	void addStepExecutions(JobExecution jobExecution);

}
