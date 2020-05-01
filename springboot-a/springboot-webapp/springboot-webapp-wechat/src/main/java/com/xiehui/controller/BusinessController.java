package com.xiehui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xiehui.common.core.annotation.Access;
import com.xiehui.common.core.exception.KnowledgeException;
import com.xiehui.feign.api.BusinessService;
import com.xiehui.feign.api.MongdbService;

@RestController
@RequestMapping("/api/v1/business")
public class BusinessController {
	@Autowired
	private BusinessService businessService;
	@Autowired
	private MongdbService mongdbService;

	@GetMapping("/test")
	@Access(isLogin = false, isSign = false, isTimestamp = false)
	public void test() {
		businessService.queryAllCourse();
	}

	@GetMapping("/testIdGenerator")
	@Access(isLogin = false, isSign = false, isTimestamp = false)
	public void testIdGenerator() {
		businessService.testIdGenerator();
	}

	@GetMapping("/testMongdb")
	@Access(isLogin = false, isSign = false, isTimestamp = false)
	public void testMongdb() throws KnowledgeException {
		mongdbService.findAll();
	}
	
	@GetMapping("/testMQ")
	@Access(isLogin = false, isSign = false, isTimestamp = false)
	public void testMQ() throws KnowledgeException {
		businessService.testMQ();
	}
	
	@GetMapping("/testRedisssionSync")
	@Access(isLogin = false, isSign = false, isTimestamp = false)
	public void testRedisssionSync() throws KnowledgeException {
		businessService.testRedisssionSync();
	}

}
