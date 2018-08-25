package com.lcf.erp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lcf.erp.entity.Supplier;
import com.lcf.erp.mapper.SupplierMapper;
import com.lcf.erp.service.ISupplierService;

import tk.mybatis.mapper.common.Mapper;

@Service
@Transactional(readOnly = true)
public class SupplierServiceImpl extends BaseServiceImpl<Supplier> implements ISupplierService {
	@Autowired
	private SupplierMapper mapper;
	
	@Override
	public Mapper<Supplier> getMapper() {
		return this.mapper;
	}

}