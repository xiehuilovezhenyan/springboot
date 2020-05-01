package com.xiehui.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.xiehui.common.core.exception.KnowledgeException;
import com.xiehui.customer.api.AccountService;
import com.xiehui.customer.api.CustomerService;
import com.xiehui.customer.module.CustomerAccount;
import com.xiehui.customer.module.CustomerData;

import lombok.extern.slf4j.Slf4j;

/**
 * 客户服务接口实现类
 * 
 * @author xiehui
 *
 */
@Slf4j
@RestController
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private AccountService accountService;

	@Override
	public CustomerData getCustomerData(@RequestParam(name = "name") String name) throws KnowledgeException {
		CustomerData customerData = new CustomerData(1L, name, "17310619286");
		CustomerAccount customerAccount = accountService.getCustomerAccount();
		log.info("客户模块调用本地账户模块: " + JSON.toJSONString(customerAccount));
		return customerData;
	}

}
