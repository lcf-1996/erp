package com.lcf.erp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcf.erp.entity.StoreAlert;
import com.lcf.erp.mapper.StoreAlertMapper;
import com.lcf.erp.service.IStoreAlertService;

import tk.mybatis.mapper.common.Mapper;

@Service
public class StoreAlertServiceImpl extends BaseServiceImpl<StoreAlert> implements IStoreAlertService {
	
	@Autowired
	private StoreAlertMapper mapper;

	@Override
	public Mapper<StoreAlert> getMapper() {
		return this.mapper;
	}

}
