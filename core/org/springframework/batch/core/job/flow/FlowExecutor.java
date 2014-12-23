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
package org.springframework.batch.core.job.flow;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInterruptedException;
import org.springframework.batch.core.StartLimitExceededException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.repository.JobRestartException;

/**
 * 2014-12-21 我的理解是FlowExecutor其实就是一层代理，帮助FlowJob做执行这一层的事情。
 * 
 * Context and execution strategy for {@link FlowJob} to allow it to delegate
 * its execution step by step.
 * 
 * @author Dave Syer
 * @since 2.0
 */
public interface FlowExecutor {

	/**
	 * 2014-12-21 执行step。
	 * 返回的结果是Flow的exit status? 后一句不确定。		TODO
	 * 
	 * @param step a {@link Step} to execute
	 * @return the exit status that drives the surrounding {@link Flow}
	 * @throws StartLimitExceededException
	 * @throws JobRestartException
	 * @throws JobInterruptedException
	 */
	String executeStep(Step step) throws JobInterruptedException, JobRestartException, StartLimitExceededException;

	/**
	 * 2014-12-21 返回当前的JobExecution(当前的job运行时数据)。
	 * 
	 * @return the current {@link JobExecution}
	 */
	JobExecution getJobExecution();

	/**
	 * 2014-12-21 返回最新的StepExecution。当然如果没有返回为空。
	 * 
	 * @return the latest {@link StepExecution} or null if there is none
	 */
	StepExecution getStepExecution();

	/**
	 * 2014-12-21 给一个机会去在flow结束的时候去清理资源(无论流程是否成功结束)
	 * 
	 * Chance to clean up resources at the end of a flow (whether it completed
	 * successfully or not).
	 * 
	 * @param result the final {@link FlowExecution}
	 */
	void close(FlowExecution result);

	/**
	 * 2014-12-21 处理状态的改动？这个不是太明白 TODO
	 * 
	 * Handle any status changes that might be needed at the start of a state.
	 */
	void abandonStepExecution();

	/**
	 * 2014-12-21 更新JobExecution(job运行时数据)的状态。
	 * 
	 * Handle any status changes that might be needed in the
	 * {@link JobExecution}.
	 */
	void updateJobExecutionStatus(FlowExecutionStatus status);

	/**
	 * 2014-12-21 不懂。TODO
	 * 
	 * @return true if the flow is at the beginning of a restart
	 */
	boolean isRestart();

	/**
	 * 2014-12-21 没有太懂 TODO.
	 * 
	 * @param code the label for the exit status when a flow or sub-flow ends
	 */
	void addExitStatus(String code);

}
