package com.lcf.erp.service;

import java.util.List;

import com.lcf.erp.entity.Dep;

public interface IDepService extends IBaseService<Dep> {
	
	/**
	 * 按条件执行查询
	 * @param dep 封装查询条件
	 * @return
	 */
	List<Dep> findDeps(Dep dep);
	
	/**
	 * 查询总的记录数
	 * @param dep 封装查询条件
	 * @return
	 */
	int getTotal(Dep dep);
	
}
