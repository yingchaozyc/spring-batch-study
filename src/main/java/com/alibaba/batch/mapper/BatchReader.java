package com.alibaba.batch.mapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.alibaba.batch.vo.UserVO;

public class BatchReader implements FieldSetMapper<UserVO>{

	@Override
	public UserVO mapFieldSet(FieldSet fieldSet) throws BindException {
		UserVO user = new UserVO();
		user.setName(fieldSet.readString(0));
		user.setAge(fieldSet.readInt(1));
		
		System.out.println("¶Á²Ù×÷Ö´ÐÐ£¬name=" + user.getName());
		return user;
	}

}
