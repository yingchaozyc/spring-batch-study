package com.alibaba.batch.writer;
 
import java.util.List;

import org.springframework.batch.item.ItemWriter;

import com.alibaba.batch.vo.MessageVO;

public class SecondStepBatchWriter implements ItemWriter<MessageVO> {
	
	public void write(List<? extends MessageVO> messages) throws Exception {
		System.out.println("�ڶ��ν����ʼд��"); 
		for (MessageVO m : messages) { 
			System.out.println("�ڶ�����������������:" + m.getContent());
		}  
	} 
}