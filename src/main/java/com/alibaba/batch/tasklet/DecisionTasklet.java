package com.alibaba.batch.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class DecisionTasklet implements Tasklet{

	private String message;
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		System.out.println("decision message is:" + message);
		return RepeatStatus.FINISHED;
	}
	
}
