package com.xiehui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xiehui.feign.api.RedisService;

@RestController
@RequestMapping("/api/v1/redis")
public class RedisController {
	@Autowired
	private RedisService redisService;

	@GetMapping("/test")
	public void test() {
		redisService.test();
	}
	
	@GetMapping("/test1")
	public void test1() {
		redisService.test1();
	}


}
