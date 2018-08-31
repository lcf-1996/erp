package com.lcf.erp.bootstrap;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.mgt.SecurityManager;

import com.lcf.erp.auth.EmpRealm;

@Configuration
public class ShiroConfiguration {

	// shiro核心过滤器
	@Bean
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		// 未认证界面
		shiroFilterFactoryBean.setLoginUrl("/login.html");
		// 未授权界面
		shiroFilterFactoryBean.setUnauthorizedUrl("/403.html");
		// 拦截器
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		// 其他授权页面
		//filterChainDefinitionMap.put("/dep/list.do", "perms[部门]");
		shiroFilterFactoryBean
				.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

	// shiro安全管理器
	@Bean
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(empRealm());
		return securityManager;
	}

	@Bean
	public EmpRealm empRealm() {
		return new EmpRealm();
	}

	// 启用Shiro注解的支持
	@Bean
	public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		advisorAutoProxyCreator.setProxyTargetClass(true);
		return advisorAutoProxyCreator;
	}

	/**
	 * 开启aop注解支持
	 * 
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
			SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

}
