package com.xiehui.feign.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiehui.TestInterface;
import com.xiehui.feign.api.FeignService;

/**
 * 测试外部接口调用  spring clod  alibaba
 * @author 82796
 *
 */
@Service("feignService")
public class FeignServiceImpl implements FeignService{
	@Autowired
	private TestInterface testInterface;

	@Override
	public String test(String name) {
		return testInterface.hello(name);
	}

}
