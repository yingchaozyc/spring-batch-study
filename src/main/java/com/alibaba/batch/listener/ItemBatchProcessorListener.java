package com.alibaba.batch.listener;

import org.springframework.batch.core.ItemProcessListener;

import com.alibaba.batch.vo.MessageVO;
import com.alibaba.batch.vo.UserVO;

public class ItemBatchProcessorListener implements ItemProcessListener<UserVO, MessageVO> {

	@Override
	public void beforeProcess(UserVO item) {
		System.out.println("=================>����ItemBatchProcessor�� �ҵ�beforeProcess������ʼ����, name=" + item.getName()+ "<==================");
	}

	@Override
	public void afterProcess(UserVO item, MessageVO result) {
		System.out.println("=================>����ItemBatchProcessor�� �ҵ�afterProcessִ�����, name=" + item.getName() + ","
				+ " content=" + result.getContent() + "<==================");
	}

	@Override
	public void onProcessError(UserVO item, Exception e) {
		// TODO Auto-generated method stub
		
	}

}
