package com.alibaba.batch.writer;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.springframework.batch.item.ItemWriter;

import com.alibaba.batch.vo.MessageVO;

public class BatchWriter implements ItemWriter<MessageVO> {
	
	public void write(List<? extends MessageVO> messages) throws Exception {
		System.out.println("write results");
		
		FileOutputStream os = new FileOutputStream(new File("D:/m.txt"));
		for (MessageVO m : messages) {
			os.write(m.getContent().getBytes());
			os.write("\r\n".getBytes());
			System.out.println(m.getContent());
		}
		os.close();
	}

}
