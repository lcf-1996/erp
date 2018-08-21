package com.lcf.erp.service;

import java.util.List;

public interface IBaseService<T> {

	List<T> findAll();
	
	T findById(int uuid);
	
	List<T> find(T entity);
	
	int count(T entity);
	
	void add(T entity);
	
	void update(T entity);
	
	void delete(T entity);
	
}
