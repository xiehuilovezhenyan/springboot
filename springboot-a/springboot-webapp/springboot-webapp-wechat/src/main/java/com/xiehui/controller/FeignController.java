package com.xiehui.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xiehui.common.core.annotation.Access;
import com.xiehui.feign.api.FeignService;

@RestController
@RequestMapping("/api/v1/feign")
public class FeignController {
	@Resource(name = "feignService")
	private FeignService feignService;

	@GetMapping("/test")
	@Access(isLogin = false, isSign = false, isTimestamp = false)
	public String test(@RequestParam String name) {
		String result = feignService.test(name);
		return "Return : " + result;
	}

}
