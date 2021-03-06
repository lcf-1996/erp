package com.lcf.erp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lcf.erp.entity.Goods;
import com.lcf.erp.service.IGoodsService;

@Controller
@RequestMapping("/goods")
public class GoodsController extends BaseController {
	@Autowired
	private IGoodsService goodsService;
	
	//加载订单表格的数据
	@RequestMapping(path="/getComboData.do", produces={"application/json;charset=utf-8"})
	@ResponseBody
	public List<Goods> getComboData() {
		return goodsService.findAll();
	}
	
}
