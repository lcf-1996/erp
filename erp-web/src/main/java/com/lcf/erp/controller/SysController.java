package com.lcf.erp.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lcf.erp.entity.Emp;
import com.lcf.erp.service.IEmpService;
import com.lcf.erp.utils.EncryptUtil;

@Controller
@RequestMapping("/sys")
public class SysController extends BaseController {
	@Autowired
	private IEmpService empService;
//	@Autowired
//	private ISoftctrBiz softctrBiz;
	
	@RequestMapping(path="/login.do", produces="application/json;charset=utf-8")
	@ResponseBody
	public Map<String, Object> login(HttpSession session, Emp emp) {
		/*//动态获取机器码(硬盘序列号)
		String machineid = chenmin.io.DiskID.DiskID();
		System.out.println("机器码：" + machineid);
		//软件认证操作
		String info = softctrBiz.doLogin(machineid, "蓝云ERP", "商贸1.0");
		if ("过期".equals(info)) {
			return ajaxReturn(false, info);
		}
		List<Emp> emps = empService.find(emp);
		if (emps.size() > 0) {
			session.setAttribute("emp", emps.get(0)); //把当前登录用户保存在Session中
			return ajaxReturn(true, "登录成功");
		} else {
			return ajaxReturn(false, "用户名或密码不正确");
		}*/
		
		//使用Shiro框架执行认证
		String encodedPwd = EncryptUtil.encode(emp.getPwd(), emp.getUsername());
		UsernamePasswordToken token = new UsernamePasswordToken(emp.getUsername(), encodedPwd); //创建令牌
		Subject subject = SecurityUtils.getSubject(); //创建主体
		try {
			subject.login(token); //执行认证
			return ajaxReturn(true, "登录成功");
		} catch (AuthenticationException e) {
			return ajaxReturn(false, "用户名或密码不正确");
		}
	}
	
	//获取当前登录的用户名
	@RequestMapping(path="/getName.do", produces="application/json;charset=utf-8")
	@ResponseBody
	public Map<String, Object> getName() {
		//获取主体对象
		Subject subject = SecurityUtils.getSubject();
		Object o = subject.getPrincipal();
		if (o == null) {
			return ajaxReturn(false, "请先登录");
		} else {
			Emp e = (Emp) o;
			return ajaxReturn(true, e.getName());
		}
	}
	
	@RequestMapping("/logout.do")
	public String logout(HttpSession session) {
		//session.invalidate();
		SecurityUtils.getSubject().logout();
		return "redirect:/login.html";
	}
	
	//修改密码
	@RequestMapping(path="/updatePwd.do", produces="application/json;charset=utf-8")
	@ResponseBody
	public Map<String, Object> updatePwd(String oldPwd, String newPwd) {
		//获取当前登录用户，然后再比较密码是否与oldPwd相同
		Emp emp = (Emp) SecurityUtils.getSubject().getPrincipal();
		oldPwd = EncryptUtil.encode(oldPwd, emp.getUsername());
		if (!emp.getPwd().equals(oldPwd)) {
			return ajaxReturn(false, "密码有误");
		} else {
			//执行修改操作
			newPwd = EncryptUtil.encode(newPwd, emp.getUsername());
			emp.setPwd(newPwd);
			empService.update(emp);
			return ajaxReturn(true, "修改成功");
		}
	}
	
}
