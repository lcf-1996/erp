package com.lcf.erp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.lcf.erp.entity.Orders;
import com.lcf.erp.mapper.OrdersMapper;
import com.lcf.erp.service.IOrdersService;

@Service
public class OrdersServiceImpl extends BaseServiceImpl<Orders> implements IOrdersService {
	@Autowired
	private OrdersMapper mapper;

	@Override
	public Mapper<Orders> getMapper() {
		return this.mapper;
	}

}
