package com.alibaba.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.alibaba.batch.vo.MessageVO;
import com.alibaba.batch.vo.UserVO;

public class BatchProcessor implements ItemProcessor<UserVO, MessageVO> {

	public MessageVO process(UserVO user) throws Exception { 
		MessageVO m = new MessageVO();
		m.setContent("Hello " + user.getName()
				+ ",please pay promptly at the end of this month.");
		return m;
	}

}