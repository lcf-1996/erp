package com.lcf.erp.controller;

import java.util.HashMap;
import java.util.Map;

public class BaseController {
	protected Map<String, Object> ajaxReturn(boolean success, String message) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", success);
		map.put("message", message);
		return map;
	}

}
