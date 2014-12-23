package com.alibaba.batch.decider;
 
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

public class BatchDecider implements JobExecutionDecider {

	public FlowExecutionStatus decide(JobExecution jobExecution,
			StepExecution stepExecution) {
		if(stepExecution.getReadCount() > 3){
			return FlowExecutionStatus.COMPLETED;
		}
		return FlowExecutionStatus.FAILED;
	}

}