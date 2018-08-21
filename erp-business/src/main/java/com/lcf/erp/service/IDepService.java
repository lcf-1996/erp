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
	
	/**
	 * 统计查询结果
	 * @param dep 该对象封装了查询条件
	 * @return
	 */
	int getTotal(Dep dep);
	
	/**
	 * 添加部门
	 * @param dep
	 */
	void addDep(Dep dep);

	/**
	 * 删除部门
	 * @param uuid
	 */
	void delDep(int uuid);

	/**
	 * 修改部门
	 * @param dep
	 */
	void updateDep(Dep dep);

}
