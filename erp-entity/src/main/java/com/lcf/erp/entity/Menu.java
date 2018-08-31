package com.lcf.erp.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

/*
	菜单实体类
*/
@Data
public class Menu {
	@Id
	private String menuid; //菜单ID
	private String menuname; //菜单名称
	private String icon; //菜单图标
	private String url; //菜单URL
	private String pid; //上级菜单ID
	@Transient
	private List<Menu> menus = new ArrayList<Menu>(); //下级菜单
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof Menu) {
			Menu menu = (Menu) obj;
			return this.menuid.equals(menu.getMenuid());
		}
		return false;
	}
	
}
