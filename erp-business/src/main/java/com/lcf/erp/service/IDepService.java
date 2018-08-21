package com.lcf.erp.service;

import java.util.List;

import com.lcf.erp.entity.Dep;

public interface IDepService {

	/**
	 * 按条件执行查询
	 * @param dep 封装查询条件
	 * @return
	 */
	public List<Dep> findDeps(Dep dep);
	
}
