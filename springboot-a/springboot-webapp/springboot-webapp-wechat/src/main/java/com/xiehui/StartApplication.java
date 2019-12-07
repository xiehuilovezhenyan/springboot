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
@EnableFeignClients // 解来指定这个接口所要调用的服务名称，接口中定义的各个函数使用Spring MVC的注解就可以来绑定服务提供方的REST接口
// @EnableScheduling	     // 开启定时任务扫描  (springboot quarter)
public class StartApplication {

	public static void main(String[] args) {
		SpringApplication.run(StartApplication.class, args);
	}

}
