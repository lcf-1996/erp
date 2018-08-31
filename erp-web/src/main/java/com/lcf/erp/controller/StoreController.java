package com.lcf.erp.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lcf.erp.entity.Emp;
import com.lcf.erp.entity.Store;
import com.lcf.erp.service.IStoreService;

@Controller
@RequestMapping("/store")
public class StoreController extends BaseController {
	@Autowired
	private IStoreService storeService;
	
	//获取仓库下拉框的数据
	@RequestMapping(path="/getComboData.do", produces="application/json;charset=utf-8")
	@ResponseBody
	public List<Store> getComboData(HttpSession session) {
		Subject subject = SecurityUtils.getSubject();
		Emp emp = (Emp) subject.getPrincipal();
		Store store = new Store();
		store.setEmpuuid(emp.getUuid());
		return storeService.find(store);
	}

}
