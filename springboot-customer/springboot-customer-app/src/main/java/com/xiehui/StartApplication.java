package com.xiehui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;

/**
 * springboot启动类
 * 
 * @author xiehui
 *
 */
@EnableDiscoveryClient // springboot alibaba 注册扫描
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class) // springboot 启动类
@EnableFeignClients(basePackages = {"com.xiehui.customer.api.*"}) // 指定对外发布的API包, 否则不会初始化bean
// @EnableScheduling	     // 开启定时任务扫描  (springboot quarter)
public class StartApplication {

	public static void main(String[] args) {
		SpringApplication.run(StartApplication.class, args);
	}

}
