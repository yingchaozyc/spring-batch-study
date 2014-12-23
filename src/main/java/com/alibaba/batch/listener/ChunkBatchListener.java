package com.alibaba.batch.listener;

import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;

public class ChunkBatchListener implements ChunkListener{

	@Override
	public void beforeChunk(ChunkContext context) {
		System.out.println("-------->我是chunkListener，我启动了<--------");
	}

	@Override
	public void afterChunk(ChunkContext context) {
		System.out.println("-------->我是chunkListener，我停止了<--------");
	}

	/**
	 * 这个方法的触发以及spring batch中异常的判断还没弄明白 
	 */
	@Override
	public void afterChunkError(ChunkContext context) {
		System.out.println("-------->我是chunkListener，当chunk出现错误的时候我发现了<--------");
	}

}
