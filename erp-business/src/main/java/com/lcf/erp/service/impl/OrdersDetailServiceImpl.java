package com.lcf.erp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcf.erp.entity.OrdersDetail;
import com.lcf.erp.mapper.OrdersDetailMapper;
import com.lcf.erp.service.IOrdersDetailService;

import tk.mybatis.mapper.common.Mapper;

@Service
public class OrdersDetailServiceImpl extends BaseServiceImpl<OrdersDetail> implements IOrdersDetailService {
	@Autowired
	private OrdersDetailMapper mapper;

	@Override
	public Mapper<OrdersDetail> getMapper() {
		return this.mapper;
	}

}
