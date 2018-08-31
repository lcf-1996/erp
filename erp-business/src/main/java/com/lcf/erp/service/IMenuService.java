package com.lcf.erp.service;

import java.util.List;

import com.lcf.erp.entity.Menu;
import com.lcf.erp.entity.Tree;

public interface IMenuService extends IBaseService<Menu> {

	/**
	 * 获取菜单树的数据
	 * @roleuuid 角色ID
	 * @return
	 */
	List<Tree> getMenuTree(long roleuuid);
	
	/**
	 * 根据员工ID查询员工拥有的菜单
	 * @param empuuid
	 * @return
	 */
	List<Menu> getEmpMenus(long empuuid);
	
}
