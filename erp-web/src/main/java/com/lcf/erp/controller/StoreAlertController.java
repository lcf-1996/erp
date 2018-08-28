package com.lcf.erp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lcf.erp.entity.StoreAlert;
import com.lcf.erp.service.IStoreAlertService;

@Controller
@RequestMapping("/storealert")
public class StoreAlertController extends BaseController {
	@Autowired
	private IStoreAlertService storeAlertService;
	
	@RequestMapping("/list.do")
	public void list() {}

	@RequestMapping(path="/getData.do", produces="application/json;charset=utf-8")
	@ResponseBody
	public List<StoreAlert> getData() {
		return storeAlertService.findAll();
	}
}

