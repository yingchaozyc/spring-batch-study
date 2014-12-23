package com.alibaba.batch.listener;

import java.util.List;

import org.springframework.batch.core.ItemWriteListener;

import com.alibaba.batch.vo.MessageVO;

public class ItemBatchWriterListener implements ItemWriteListener<MessageVO>{

	@Override
	public void beforeWrite(List<? extends MessageVO> items) {
		System.out.println("=================>����ItemBatchWriter�� �ҵ�beforeWrite������ʼ����<==================");
	}

	@Override
	public void afterWrite(List<? extends MessageVO> items) {
		System.out.println("=================>����ItemBatchWriter�� �ҵ�afterWriteִ�����<==================");
	}

	@Override
	public void onWriteError(Exception exception,
		List<? extends MessageVO> items) {
		
	} 
}
