package com.lcf.erp.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lcf.erp.service.IBaseService;

import tk.mybatis.mapper.common.Mapper;

public abstract class BaseServiceImpl<T> implements IBaseService<T> {
	/*
		获取Mapper对象
	*/
	public abstract Mapper<T> getMapper();
	
	@Override
	public List<T> findAll() {
		return getMapper().selectAll();
	}

	@Override
	public T findById(int uuid) {
		return getMapper().selectByPrimaryKey(uuid);
	}

	@Override
	public List<T> find(T entity) {
		return getMapper().select(entity);
	}

	@Override
	public int count(T entity) {
		return getMapper().selectCount(entity);
	}

	@Override
	@Transactional(readOnly = false)
	public void add(T entity) {
		getMapper().insertSelective(entity);
	}

	@Override
	@Transactional(readOnly = false)
	public void update(T entity) {
		getMapper().updateByPrimaryKeySelective(entity);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(T entity) {
		getMapper().delete(entity);
	}

}
