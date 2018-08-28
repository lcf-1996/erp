package com.lcf.erp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.common.Mapper;

import com.lcf.erp.entity.StoreAlert;
import com.lcf.erp.mapper.StoreAlertMapper;
import com.lcf.erp.service.IStoreAlertService;

@Service
@Transactional(readOnly = true)
public class StoreAlertServiceImpl extends BaseServiceImpl<StoreAlert> implements IStoreAlertService {
	@Autowired
	private StoreAlertMapper mapper;
	
	@Override
	public Mapper<StoreAlert> getMapper() {
		return this.mapper;
	}
	
}
