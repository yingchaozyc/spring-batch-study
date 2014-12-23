package com.alibaba.batch.listener;

import java.util.List;

import org.springframework.batch.core.ItemWriteListener;

import com.alibaba.batch.vo.MessageVO;

public class ItemBatchWriterListener implements ItemWriteListener<MessageVO>{

	@Override
	public void beforeWrite(List<? extends MessageVO> items) {
		System.out.println("=================>我是ItemBatchWriter， 我的beforeWrite方法开始启动<==================");
	}

	@Override
	public void afterWrite(List<? extends MessageVO> items) {
		System.out.println("=================>我是ItemBatchWriter， 我的afterWrite执行完成<==================");
	}

	@Override
	public void onWriteError(Exception exception,
		List<? extends MessageVO> items) {
		
	} 
}
