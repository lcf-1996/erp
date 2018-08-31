package com.lcf.erp.bootstrap;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 SpringBoot启动类
 */
@SpringBootApplication(scanBasePackages = { "com.lcf" })
@MapperScan(basePackages = { "com.lcf.erp.mapper" })
public class Application {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(Application.class);
		springApplication.run(args);
	}

}
