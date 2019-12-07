package com.xiehui;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 主要定义外界接口
 * @author xiehui
 *
 */
@FeignClient("springboot-B")
@RequestMapping("/api/v1/test")
public interface TestInterface {
	
	@GetMapping("/hello")
	public String hello(@RequestParam(name = "name") String name);

}
