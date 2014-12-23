package com.alibaba.batch.listener;

import org.springframework.batch.core.JobExecution;

public class JobBatchPojoListener {
 
	public void afterJob(JobExecution jobExecution) {
		System.out.println("===========I am running over with pojo.===========");
	}
	 
	public void beforeJob(JobExecution jobExecution) {
		System.out.println("===========I am start running! with pojo.===========");
	} 
}
