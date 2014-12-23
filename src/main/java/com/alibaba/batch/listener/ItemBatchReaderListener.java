package com.alibaba.batch.listener;

import org.springframework.batch.core.ItemReadListener;

import com.alibaba.batch.vo.UserVO;
 
public class ItemBatchReaderListener implements ItemReadListener<UserVO>{

	@Override
	public void beforeRead() {
		System.out.println("=================>����ItemBatchReader�� �ҵ�beforeRead������ʼ����<==================");
	}

	@Override
	public void afterRead(UserVO item) {
		System.out.println("=================>����ItemBatchReader�� �ҵ�afterRead����ִ�����, name=" + item.getName() + "<==================");
	}

	@Override
	public void onReadError(Exception ex) {
	 
	} 
}
