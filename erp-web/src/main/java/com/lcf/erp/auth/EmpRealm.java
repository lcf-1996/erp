package com.lcf.erp.auth;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.lcf.erp.entity.Emp;
import com.lcf.erp.entity.Menu;
import com.lcf.erp.service.IEmpService;
import com.lcf.erp.service.IMenuService;

public class EmpRealm extends AuthorizingRealm {
	@Autowired
	private IEmpService empService;
	@Autowired
	private IMenuService menuService;

	//进行授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection arg0) {
		//获取当前登录用户
		Emp emp = (Emp) arg0.getPrimaryPrincipal();
		//获取用户所拥有的菜单权限
		List<Menu> menus = menuService.getEmpMenus(emp.getUuid());
		//该对象保存了当前登录用户的权限信息
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		for (Menu menu : menus) {
			//设置用户所具有的权限信息
			info.addStringPermission(menu.getMenuname());
		}
		return info;
	}

	//进行认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken arg0) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) arg0;
		String userName = token.getUsername();
		String password = new String(token.getPassword());
		
		Emp emp = new Emp();
		emp.setUsername(userName);
		emp.setPwd(password);
		List<Emp> emps = empService.find(emp);
		if (emps.size() > 0) {
			/*
			 	参数1：主体（用户对象）
			 	参数2：密码
			 	参数3：realm的类名
			 */
			emp = emps.get(0);
			return new SimpleAuthenticationInfo(emp, emp.getPwd(), emp.getName());
		}
		return null;
	}

}
