package com.alibaba.batch.mapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.alibaba.batch.vo.UserVO;

public class SecondStepBatchReader implements FieldSetMapper<UserVO>{

	@Override
	public UserVO mapFieldSet(FieldSet fieldSet) throws BindException {
		UserVO user = new UserVO();
		user.setName(fieldSet.readString(0)); 
		
		System.out.println("第二步骤读操作执行，name=" + user.getName());
		return user;
	}

}