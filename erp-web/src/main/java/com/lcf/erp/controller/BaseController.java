package com.lcf.erp.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

public class BaseController {

	/**
	 * 把操作结果的状态信息封装到Map对象中
	 * @param success 结果状态，true代表成功，false代表失败
	 * @param message 结果信息
	 * @return
	 */
	public Map<String, Object> ajaxReturn(boolean success, String message) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", success);
		map.put("message", message);
		return map;
	}
	
	@InitBinder
	public void init(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}
	
}
