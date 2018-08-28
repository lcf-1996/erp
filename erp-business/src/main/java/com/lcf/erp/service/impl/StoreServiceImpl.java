package com.lcf.erp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcf.erp.entity.Store;
import com.lcf.erp.mapper.StoreMapper;
import com.lcf.erp.service.IStoreService;

import tk.mybatis.mapper.common.Mapper;

@Service
public class StoreServiceImpl extends BaseServiceImpl<Store> implements IStoreService {
	@Autowired
	private StoreMapper mapper;

	@Override
	public Mapper<Store> getMapper() {
		return this.mapper;
	}

}
