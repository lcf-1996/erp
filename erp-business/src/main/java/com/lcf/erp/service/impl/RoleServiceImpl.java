package com.lcf.erp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lcf.erp.entity.Role;
import com.lcf.erp.entity.RoleMenu;
import com.lcf.erp.mapper.RoleMapper;
import com.lcf.erp.mapper.RoleMenuMapper;
import com.lcf.erp.service.IRoleService;

import tk.mybatis.mapper.common.Mapper;



@Service
@Transactional(readOnly = true)
public class RoleServiceImpl extends BaseServiceImpl<Role> implements IRoleService {
	@Autowired
	private RoleMapper mapper;
	@Autowired
	private RoleMenuMapper roleMenuMapper;
	
	@Override
	public Mapper<Role> getMapper() {
		return this.mapper;
	}

	@Override
	public void saveRoleMenu(String roleId, String[] ids) {
		//先把原来角色所拥有的菜单删除掉
		RoleMenu roleMenu = new RoleMenu();
		roleMenu.setRoleuuid(roleId);
		roleMenuMapper.delete(roleMenu);
		//重新把新的角色菜单添加进来
		for (String menuId : ids) {
			roleMenu.setMenuuuid(menuId);
			roleMenuMapper.insertSelective(roleMenu);
		}
	}
	
}