package com.xiehui.customer.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.xiehui.common.core.exception.KnowledgeException;
import com.xiehui.customer.module.CustomerData;

/**
 * 客户服务接口
 * 
 * @author xiehui
 *
 */
@FeignClient(name="springboot-customer")
@RequestMapping("/api/vi/customer")
public interface CustomerService {

	@PostMapping(value = "/getCustomerData")
	public CustomerData getCustomerData(@RequestParam(name = "name") String name) throws KnowledgeException;

}
