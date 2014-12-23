package com.alibaba.batch.main;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

public class Main {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		ClassPathXmlApplicationContext c = new ClassPathXmlApplicationContext("batch.xml");
		SimpleJobLauncher launcher = new SimpleJobLauncher();
		launcher.setJobRepository((JobRepository) c.getBean("jobRepository"));
		launcher.setTaskExecutor(new SimpleAsyncTaskExecutor());
		try {
			Job job = (Job) c.getBean("messageJob");
			launcher.run(job, new JobParameters());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
