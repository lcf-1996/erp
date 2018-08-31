package com.lcf.erp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcf.erp.entity.Menu;
import com.lcf.erp.entity.Tree;
import com.lcf.erp.mapper.MenuMapper;
import com.lcf.erp.service.IMenuService;

import tk.mybatis.mapper.common.Mapper;

@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu> implements IMenuService {
	@Autowired
	private MenuMapper mapper;
	
	@Override
	public Mapper<Menu> getMapper() {
		return this.mapper;
	}

	@Override
	public List<Tree> getMenuTree(long roleuuid) {
		List<Tree> trees = new ArrayList<Tree>();
		//把角色所拥有的菜单查询
		List<Menu> roleMenus = mapper.getRoleMenu(roleuuid);
		//查询所有的一级菜单
		Menu menu = new Menu();
		menu.setPid("0");
		List<Menu> menus_1 = mapper.select(menu);
		//遍历所有的一级菜单，把该遍历出来的菜单封装成Tree对象
		for (Menu menu_1 : menus_1) {
			Tree tree = new Tree();
			tree.setId(menu_1.getMenuid());
			tree.setText(menu_1.getMenuname());
			//根据一级菜单获取二级菜单
			menu.setPid(menu_1.getMenuid()); //修改查询条件
			List<Menu> menus_2 = mapper.select(menu);
			//遍历二级菜单，然后把遍历出来的二级菜单封装到Tree集合中，并且添加一级菜单的children属性中。
			for (Menu menu_2 : menus_2) {
				//把二级菜单封装成Tree对象，然后保存在上一级Tree对象的children属性中
				Tree tree_2 = new Tree();
				tree_2.setId(menu_2.getMenuid());
				tree_2.setText(menu_2.getMenuname());
				//判断当前用户角色是否拥有该二级菜单，如果有就设置选中状态为true
				if (roleMenus.contains(menu_2)) {
					tree_2.setChecked(true);
				}
				tree.getChildren().add(tree_2);
			}
			trees.add(tree);
		}
		return trees;
	}

	@Override
	public List<Menu> getEmpMenus(long empuuid) {
		return mapper.getEmpMenu(empuuid);
	}

}
