package com.alibaba.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.alibaba.batch.vo.MessageVO;
import com.alibaba.batch.vo.UserVO;

public class SecondStepBatchProcessor implements ItemProcessor<UserVO, MessageVO> {

	public MessageVO process(UserVO user) throws Exception {
		MessageVO m = new MessageVO();
		m.setContent("(已经过第二步骤包装处理)" + user.getName());
		return m;
	}

}
