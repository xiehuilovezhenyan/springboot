package com.xiehui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

/**
 * springboot启动类
 * @author xiehui
 *
 */
@EnableDiscoveryClient   //	springboot alibaba 注册扫描
@SpringBootApplication	 //	springboot 启动类
@EnableFeignClients		 //	解来指定这个接口所要调用的服务名称，接口中定义的各个函数使用Spring MVC的注解就可以来绑定服务提供方的REST接口
public class StartApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(StartApplication.class, args);
	}
	
	
	@Slf4j
	@RestController
	@RequestMapping("/api/v1/test")
	static class TestController{
		@GetMapping("/hello")
		public  String Hello(@RequestParam String name){
			log.info("invoked name = " + name);
			return "hello " + name;
		}
		
		@PostMapping("/testBody")
		public  String testBody(@RequestBody BodyTest bodyTest){
			log.info("invoked body = " + bodyTest);
			return "bodyTest " + JSON.toJSONString(bodyTest);
		}
	}

}
