package com.lcf.erp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lcf.erp.entity.Menu;
import com.lcf.erp.service.IMenuService;

@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController {
	@Autowired
	private IMenuService menuService;

	//加载部门表格的数据
	@RequestMapping(path="/getData.do", produces={"application/json;charset=utf-8"})
	@ResponseBody
	public Map getData() {
		Menu menu  = new Menu();
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
		return map;
	}
	
}
