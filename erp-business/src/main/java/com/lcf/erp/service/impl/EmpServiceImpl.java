package com.lcf.erp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.lcf.erp.entity.Emp;
import com.lcf.erp.mapper.EmpMapper;
import com.lcf.erp.service.IEmpService;

@Service
public class EmpServiceImpl extends BaseServiceImpl<Emp> implements IEmpService {
	@Autowired
	private EmpMapper mapper;

	@Override
	public Mapper<Emp> getMapper() {
		return this.mapper;
	}

}