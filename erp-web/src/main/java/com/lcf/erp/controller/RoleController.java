package com.lcf.erp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lcf.erp.entity.Role;
import com.lcf.erp.service.IRoleService;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {
	@Autowired
	private IRoleService roleService;

	//跳转到角色查询页
	@RequestMapping("/list.do")
	public void list() {}
	
	//加载部门表格的数据
	@RequestMapping("/getData.do")
	@ResponseBody
	public List<Role> getData() {
		return roleService.findAll();
	}
	
	@RequestMapping(path="/saveRoleMenu.do", produces={"application/json;charset=utf-8"})
	@ResponseBody
	public Map<String, Object> saveRoleMenu(String roleId, String menuIds) {
		String[] ids = menuIds.split(",");
		try {
			roleService.saveRoleMenu(roleId, ids);
			return ajaxReturn(true, "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxReturn(false, "保存失败");
		}
			
	}
}
