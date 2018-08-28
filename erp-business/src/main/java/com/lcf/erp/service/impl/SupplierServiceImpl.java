package com.lcf.erp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.lcf.erp.entity.Supplier;
import com.lcf.erp.mapper.SupplierMapper;
import com.lcf.erp.service.ISupplierService;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
@Transactional(readOnly = true)
public class SupplierServiceImpl extends BaseServiceImpl<Supplier> implements ISupplierService {
	@Autowired
	private SupplierMapper mapper;
	
	@Override
	public Mapper<Supplier> getMapper() {
		return this.mapper;
	}

	@Override
	public List<Supplier> findSuppliers(Supplier supplier) {
		Example example = new Example(Supplier.class);
		Criteria c = example.createCriteria();
		if (!StringUtils.isEmpty(supplier.getName())) {
			c.andLike("name", "%" + supplier.getName() + "%");
		}
		c.andEqualTo("type", supplier.getType());
		return mapper.selectByExample(example);
	}

}