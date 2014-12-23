package com.alibaba.batch.listener;

import org.springframework.batch.core.ItemReadListener;

import com.alibaba.batch.vo.UserVO;
 
public class ItemBatchReaderListener implements ItemReadListener<UserVO>{

	@Override
	public void beforeRead() {
		System.out.println("=================>我是ItemBatchReader， 我的beforeRead方法开始启动<==================");
	}

	@Override
	public void afterRead(UserVO item) {
		System.out.println("=================>我是ItemBatchReader， 我的afterRead方法执行完毕, name=" + item.getName() + "<==================");
	}

	@Override
	public void onReadError(Exception ex) {
	 
	} 
}
