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

	//跳转到部门查询页
	@RequestMapping("/list.do")
	public void list() {}
	
	//加载部门表格的数据
	@RequestMapping(path="/getData.do", produces={"application/json;charset=utf-8"})
	@ResponseBody
	public Map<String, Object> getData(Dep dep, Integer page, Integer rows) {
//		System.out.println("dep = " + dep + ", page = " + page + ", rows = " + rows);
		if (page == null) {
			page = 1;
		}
		if (rows == null) {
			rows = 10;
		}
		PageHelper.startPage(page, rows); //设置分页的信息
		List<Dep> deps = depService.findDeps(dep);
		int total = depService.getTotal(dep);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", deps);
		map.put("total", total);
		return map;
	}
	
	//添加部门
	@RequestMapping(path="/add.do", produces={"application/json;charset=utf-8"})
	@ResponseBody
	public Map<String, Object> addDep(Dep dep) {
		try {
			depService.add(dep);
			return ajaxReturn(true, "添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxReturn(false, "添加失败");
		}
	}
	
	//添加部门
	@RequestMapping(path="/del.do", produces={"application/json;charset=utf-8"})
	@ResponseBody
	public Map<String, Object> delDep(Dep dep) {
		try {
			depService.delete(dep);
			return ajaxReturn(true, "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxReturn(false, "删除失败");
		}
	}
	
	//查询修改的部门信息
	@RequestMapping(path="/get.do", produces="application/json;charset=utf-8")
	@ResponseBody
	public Dep get(int uuid) {
		System.out.println("uuid" + uuid);
		return depService.findById(uuid);
	}
	
	//修改部门
	@RequestMapping(path="/update.do", produces={"application/json;charset=utf-8"})
	@ResponseBody
	public Map<String, Object> updateDep(Dep dep) {
		try {
			depService.update(dep);
			return ajaxReturn(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxReturn(false, "修改失败");
		}
	}

}
