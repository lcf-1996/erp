package com.lcf.erp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.lcf.erp.entity.Menu;
import com.lcf.erp.mapper.MenuMapper;
import com.lcf.erp.service.IMenuService;

@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu> implements IMenuService {
	@Autowired
	private MenuMapper mapper;
	
	@Override
	public Mapper<Menu> getMapper() {
		return this.mapper;
	}
}
