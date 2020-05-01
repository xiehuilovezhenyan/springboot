package com.xiehui.customer.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xiehui.common.core.exception.KnowledgeException;
import com.xiehui.customer.module.CustomerAccount;
/**
 * 账户服务接口
 * 
 * @author xiehui
 *
 */
@FeignClient(name="springboot-customer")
@RequestMapping("/api/vi/account")
public interface AccountService {

	@PostMapping(value = "/getCustomerAccount")
	public CustomerAccount getCustomerAccount() throws KnowledgeException;

}
