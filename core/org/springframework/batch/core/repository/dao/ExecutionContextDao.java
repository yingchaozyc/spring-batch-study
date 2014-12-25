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
import org.springframework.batch.item.ExecutionContext;

/**
 * 2014-12-25 ExecutionContext��������DAO�ӿ�
 * 
 * DAO interface for persisting and retrieving {@link ExecutionContext}s.
 * 
 * @author Robert Kasanicky
 * @author David Turanski
 */
public interface ExecutionContextDao {

	/**
	 * 2014-12-25 ����job����ʱ��ϢjobExecution��ȡ��������ϢExecutionContext
	 * 
	 * @param jobExecution
	 * @return execution context associated with the given jobExecution
	 */
	ExecutionContext getExecutionContext(JobExecution jobExecution);

	/**
	 * 2014-12-25 ����step����ʱ��ϢstepExecution��ȡ��������ϢExecutionContext��
	 * 
	 * @param stepExecution
	 * @return execution context associated with the given stepExecution
	 */
	ExecutionContext getExecutionContext(StepExecution stepExecution);

	/**
	 * 2014-12-25 ����job����ʱ��ϢjobExecution������������ϢExecutionContext��
	 * 
	 * Persist the execution context associated with the given jobExecution,
	 * persistent entry for the context should not exist yet.
	 * @param jobExecution
	 */
	void saveExecutionContext(final JobExecution jobExecution);

	/**
	 * 2014-12-25 ����step����ʱ��ϢstepExecution������������ϢExecutionContext��
	 * 
	 * Persist the execution context associated with the given stepExecution,
	 * persistent entry for the context should not exist yet.
	 * @param stepExecution
	 */
	void saveExecutionContext(final StepExecution stepExecution);

	/**
	 * 2014-12-25 �־û�һ��StepExecution��
	 * 
	 * Persist the execution context associated with each stepExecution in a given collection,
	 * persistent entry for the context should not exist yet.
	 * @param stepExecutions
	 */
	void saveExecutionContexts(final Collection<StepExecution> stepExecutions);

	/**
	 * 2014-12-25 ����jobExecution����ָ����ExecutionContext��Ϣ��
	 * 
	 * Persist the updates of execution context associated with the given
	 * jobExecution. Persistent entry should already exist for this context.
	 * @param jobExecution
	 */
	void updateExecutionContext(final JobExecution jobExecution);

	/**
	 * 2014-12-25 ����stepExecution����ָ����ExecutionContext��Ϣ�� 
	 * 
	 * Persist the updates of execution context associated with the given
	 * stepExecution. Persistent entry should already exist for this context.
	 * @param stepExecution
	 */
	void updateExecutionContext(final StepExecution stepExecution);
}
