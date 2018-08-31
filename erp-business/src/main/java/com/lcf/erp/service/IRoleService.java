package com.lcf.erp.service;

import com.lcf.erp.entity.Role;

public interface IRoleService extends IBaseService<Role> {

	/**
	 * 保存角色菜单
	 * @param roleId
	 * @param ids
	 */
	void saveRoleMenu(String roleId, String[] ids);

}
