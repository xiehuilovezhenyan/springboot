package com.xiehui.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.xiehui.common.core.exception.CustomException;
import com.xiehui.feign.api.BusinessService;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class BusinessControllerTest {
	
	@Autowired
	private BusinessService businessService;
	
	
	@Test
	public void testRedisssionSync() throws CustomException {
		businessService.testRedisssionSync();
	}

}
