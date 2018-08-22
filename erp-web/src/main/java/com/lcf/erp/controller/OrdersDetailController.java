package com.lcf.erp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lcf.erp.entity.OrdersDetail;
import com.lcf.erp.service.IOrdersDetailService;

@Controller
@RequestMapping("/ordersdetail")
public class OrdersDetailController extends BaseController {
	@Autowired
	private IOrdersDetailService ordersDetailService;
	
	//加载订单明细表格的数据
	@RequestMapping(path="/getData.do", produces={"application/json;charset=utf-8"})
	@ResponseBody
	public List<OrdersDetail> getData(OrdersDetail ordersDetail) {
		return ordersDetailService.find(ordersDetail);
	}
	
}
