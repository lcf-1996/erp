package com.lcf.erp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.lcf.erp.entity.Goods;
import com.lcf.erp.mapper.GoodsMapper;
import com.lcf.erp.service.IGoodsService;

@Service
public class GoodsServiceImpl extends BaseServiceImpl<Goods> implements IGoodsService {
	@Autowired
	private GoodsMapper mapper;

	@Override
	public Mapper<Goods> getMapper() {
		return this.mapper;
	}

}
