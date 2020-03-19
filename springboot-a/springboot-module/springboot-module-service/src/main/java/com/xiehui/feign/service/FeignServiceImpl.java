package com.xiehui.feign.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiehui.BodyTest;
import com.xiehui.TestInterface;
import com.xiehui.feign.api.FeignService;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试外部接口调用  spring clod  alibaba
 * @author 82796
 *
 */
@Slf4j
@Service("feignService")
public class FeignServiceImpl implements FeignService{
	@Autowired
	private TestInterface testInterface;

	@Override
	public String test(String name) {
		BodyTest bodyTest = new BodyTest();
		bodyTest.setId(1L);
		bodyTest.setName("test");
		log.info("bodyTest=" + testInterface.testBody(bodyTest));
		return testInterface.hello(name);
	}

}
