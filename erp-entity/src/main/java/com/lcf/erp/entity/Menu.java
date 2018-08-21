package com.lcf.erp.entity;

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
	private List<Menu> menus; //下级菜单
}
