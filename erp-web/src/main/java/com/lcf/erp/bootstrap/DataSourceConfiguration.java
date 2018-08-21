package com.lcf.erp.bootstrap;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration // 定义配置信息类
public class DataSourceConfiguration {

	/** 定义创建数据源方法 */
	@Bean // 定义Bean
	@Primary // 主要的候选者
	@ConfigurationProperties(prefix="spring.datasource.c3p0") // 配置属性
	public DataSource getDataSource(){
		return DataSourceBuilder.create() // 创建数据源构建对象
				.type(ComboPooledDataSource.class) // 设置数据源类型
				.build(); // 构建数据源对象
	}
	
}
