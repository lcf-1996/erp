package com.lcf.erp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lcf.erp.entity.Emp;
import com.lcf.erp.entity.Menu;
import com.lcf.erp.entity.Tree;
import com.lcf.erp.service.IMenuService;

@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController {
	@Autowired
	private IMenuService menuService;

	//加载首页左边菜单的数据
	@RequestMapping(path="/getData.do", produces={"application/json;charset=utf-8"})
	@ResponseBody
	public Map<String, Object> getData(HttpSession session) {
		/*Menu menu  = new Menu();
		menu.setPid("0");
		//查询所有的一级菜单
		List<Menu> menus_1 = menuService.find(menu);
		for (Menu menu1 : menus_1) {
			//查询当前一级菜单下的二级菜单
			menu = new Menu();
			menu.setPid(menu1.getMenuid());
			List<Menu> menus_2 = menuService.find(menu);
			//把二级菜单设置到一级菜单的menu对象中
			menu1.setMenus(menus_2);
		}
		Map map = new HashMap();
		map.put("menus", menus_1);
		return map;*/
		
		//保存一级菜单的集合
		List<Menu> menus_1 = new ArrayList<Menu>();
		
//		Object o = session.getAttribute("emp");
		//获取主体对象
		Subject subject = SecurityUtils.getSubject();
		Object o = subject.getPrincipal();
		
		if (o != null) {
			Emp emp = (Emp) o;
			//查询员工所拥有的菜单
			List<Menu> menus = menuService.getEmpMenus(emp.getUuid());
			//System.out.println("menus = " + menus);
			//对查询出来的菜单进行处理（在一级菜单包含二级菜单）
			for (Menu menu : menus) {
				if ("0".equals(menu.getPid())) {
					menus_1.add(menu);
				}
			}
			//查找二级菜单
			for (Menu menu : menus) {
				if (!"0".equals(menu.getPid()) && !"-1".equals(menu.getPid())) {
					//把二级菜单添加到对应的一级菜单的menus集合中
					for (Menu menu_1 : menus_1) {
						if (menu_1.getMenuid().equals(menu.getPid())) {
							menu_1.getMenus().add(menu);
							break;
						}
					}
				}
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("menus", menus_1);
		return map;
	}
	
	//跳转到菜单树页面
	@RequestMapping("/tree.do")
	public void tree() {}

	//加载授权管理的菜单树的数据
	@RequestMapping(path="/getRoleMenus.do", produces="application/json;charset=utf-8")
	@ResponseBody
	public List<Tree> getRoleMenus(Long roleuuid) {
		return menuService.getMenuTree(roleuuid);
	}

	
}
