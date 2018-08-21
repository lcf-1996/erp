package com.lcf.erp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lcf.erp.entity.Dep;
import com.lcf.erp.service.IDepService;

@Controller
@RequestMapping("/dep")
public class DepController {
	@Autowired
	private IDepService depService;
	
	@RequestMapping("/list.do")
	public void list() {}
	
	@RequestMapping(path="/getData.do", produces={"application/json;charset=utf-8"})
	@ResponseBody
	public List<Dep> getData(Dep dep) {
		System.out.println("dep = " + dep);
		return depService.findDeps(dep);
	}
}