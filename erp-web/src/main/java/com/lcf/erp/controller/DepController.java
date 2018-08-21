package com.lcf.erp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.lcf.erp.entity.Dep;
import com.lcf.erp.service.IDepService;

@Controller
@RequestMapping("/dep")
public class DepController extends BaseController {
	@Autowired
	private IDepService depService;
	
	@RequestMapping("/list.do")
	public void list() {}
	
	@RequestMapping(path="/getData.do", produces={"application/json;charset=utf-8"})
	@ResponseBody
	public Map<String, Object> getData(Dep dep, Integer page, Integer rows) {
		
		if (page == null) {
			page = 1;
		}
		if (rows == null) {
			rows = 10;
		}
		
		PageHelper.startPage(page, rows);
		List<Dep> depList = depService.findDeps(dep);
		int total = depService.getTotal(dep);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", depList);
		map.put("total", total);
		return map;
	}
	
	@RequestMapping(path="/add.do", produces="application/json;charset=utf-8")
	@ResponseBody
	public Map<String, Object> add(Dep dep) {
		try {
			depService.addDep(dep);
			return ajaxReturn(true, "添加成功！");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ajaxReturn(false, "添加失败！");
	}

	@RequestMapping(path="/del.do", produces="application/json;charset=utf-8")
	@ResponseBody
	public Map delDep(int uuid) {
		try {
			depService.delDep(uuid);
			return ajaxReturn(true, "删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ajaxReturn(false, "删除失败！");
	}

	@RequestMapping(path="/get.do", produces="application/json;charset=utf-8")
	@ResponseBody
	public Dep get(Dep dep) {
		List<Dep> list = depService.findDeps(dep);
		return list.get(0);
	}

	@RequestMapping(path="/update.do", produces="application/json;charset=utf-8")
	@ResponseBody
	public Map updateDep(Dep dep) {
		try {
			depService.updateDep(dep);
			return ajaxReturn(true, "修改成功！");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ajaxReturn(false, "修改失败！");
	}

}