package com.alibaba.batch.listener; 
 
import org.springframework.batch.core.JobExecution; 
import org.springframework.batch.core.listener.JobExecutionListenerSupport; 
public class JobBatchListener extends JobExecutionListenerSupport {

	@Override
	public void afterJob(JobExecution jobExecution) {
		System.out.println("===========I am running over.===========");
	}
	
	@Override
	public void beforeJob(JobExecution jobExecution) {
		System.out.println("===========I am start running!===========");
	} 
}