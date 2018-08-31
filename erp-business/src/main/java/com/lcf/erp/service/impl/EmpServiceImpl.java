package com.lcf.erp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcf.erp.entity.Emp;
import com.lcf.erp.entity.EmpRole;
import com.lcf.erp.entity.Role;
import com.lcf.erp.entity.Tree;
import com.lcf.erp.mapper.EmpMapper;
import com.lcf.erp.mapper.EmpRoleMapper;
import com.lcf.erp.mapper.RoleMapper;
import com.lcf.erp.service.IEmpService;

import tk.mybatis.mapper.common.Mapper;

@Service
public class EmpServiceImpl extends BaseServiceImpl<Emp> implements IEmpService {
	@Autowired
	private EmpMapper mapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private EmpRoleMapper empRoleMapper;

	@Override
	public Mapper<Emp> getMapper() {
		return this.mapper;
	}

	@Override
	public List<Tree> getRoleTree(long empuuid) {
		List<Tree> trees = new ArrayList<Tree>();
		//把员工所拥有的角色查询出来
		List<Role> empRoles = mapper.getEmpRoles(empuuid);
		//查询所有的一级菜单
		List<Role> roles = roleMapper.selectAll();
		//遍历所有角色，把该遍历出来的角色封装成Tree对象
		for (Role role : roles) {
			Tree tree = new Tree();
			tree.setId(role.getUuid());
			tree.setText(role.getName());
			//判断当前用户是否拥有该角色，如果有就设置选中状态为true
			if (empRoles.contains(role)) {
				tree.setChecked(true);
			}
			trees.add(tree);
		}
		return trees;
	}

	@Override
	public void saveEmpRoles(String empId, String[] ids) {
		//把员工原有的角色删除
		EmpRole empRole = new EmpRole();
		empRole.setEmpuuid(empId);
		empRoleMapper.delete(empRole);
		//把员工新的角色添加到emp_role表中
		for (String roleId : ids) {
			empRole.setRoleuuid(roleId);
			empRoleMapper.insertSelective(empRole);
		}
	}

}
