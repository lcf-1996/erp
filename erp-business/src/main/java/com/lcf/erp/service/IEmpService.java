package com.lcf.erp.service;

import java.util.List;

import com.lcf.erp.entity.Emp;
import com.lcf.erp.entity.Tree;

public interface IEmpService extends IBaseService<Emp> {

	/**
	 * 根据员工编号查询角色
	 * @param empuuid 员工编号
	 * @return 返回Tree类型的集合
	 */
	List<Tree> getRoleTree(long empuuid);

	/**
	 * 保存员工角色
	 * @param empId
	 * @param ids
	 */
	void saveEmpRoles(String empId, String[] ids);

}
