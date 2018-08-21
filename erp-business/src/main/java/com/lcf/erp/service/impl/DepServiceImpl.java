package com.lcf.erp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcf.erp.entity.Dep;
import com.lcf.erp.mapper.DepMapper;
import com.lcf.erp.service.IDepService;

@Service
public class DepServiceImpl implements IDepService {
	@Autowired
	private DepMapper mapper;
	
	@Override
	public List<Dep> findDeps(Dep dep) {
		
		return mapper.select(dep);
	}

}
