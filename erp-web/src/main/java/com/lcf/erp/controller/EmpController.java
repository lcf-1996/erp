package com.lcf.erp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lcf.erp.entity.Emp;
import com.lcf.erp.entity.Tree;
import com.lcf.erp.service.IEmpService;

@Controller
@RequestMapping("/emp")
public class EmpController extends BaseController {
	@Autowired
	private IEmpService empService;

	//根据员工ID获取该员工的名字
	@RequestMapping(path = "/getName.do", produces="application/json;charset=utf-8")
	@ResponseBody
	public Map<String, Object> getEmpName(Emp emp) {
		List<Emp> emps = empService.find(emp);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", emps.get(0).getName());
		return map;
	}
	
	@RequestMapping("/list.do")
	public void list() {}
	
	@RequestMapping(path = "/getData.do", produces="application/json;charset=utf-8")
	@ResponseBody
	public List<Emp> getData() {
		return empService.findAll();
	}
	
	//加载员工角色树的数据
	@RequestMapping(path="/tree.do", produces="application/json;charset=utf-8")
	@ResponseBody
	public List<Tree> tree(Long empuuid) {
		return empService.getRoleTree(empuuid);
	}
	
	//保存用户角色
	@RequestMapping(path="/saveEmpRole.do", produces={"application/json;charset=utf-8"})
	@ResponseBody
	public Map<String, Object> saveEmpRole(String empId, String roleIds) {
		String[] ids = roleIds.split(",");
		try {
			empService.saveEmpRoles(empId, ids);
			return ajaxReturn(true, "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxReturn(false, "保存失败");
		}
			
	}
	
}
