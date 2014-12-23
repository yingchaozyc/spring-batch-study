package com.alibaba.batch.listener;

import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;

public class ChunkBatchListener implements ChunkListener{

	@Override
	public void beforeChunk(ChunkContext context) {
		System.out.println("-------->����chunkListener����������<--------");
	}

	@Override
	public void afterChunk(ChunkContext context) {
		System.out.println("-------->����chunkListener����ֹͣ��<--------");
	}

	/**
	 * ��������Ĵ����Լ�spring batch���쳣���жϻ�ûŪ���� 
	 */
	@Override
	public void afterChunkError(ChunkContext context) {
		System.out.println("-------->����chunkListener����chunk���ִ����ʱ���ҷ�����<--------");
	}

}
