package com.xiehui.customer.service;

import org.springframework.web.bind.annotation.RestController;

import com.xiehui.common.core.exception.KnowledgeException;
import com.xiehui.customer.api.AccountService;
import com.xiehui.customer.module.CustomerAccount;

import lombok.extern.slf4j.Slf4j;

/**
 * 账户服务接口实现类
 * 
 * @author xiehui
 *
 */
@Slf4j
@RestController
public class AccountServiceImp implements AccountService {

	@Override
	public CustomerAccount getCustomerAccount() throws KnowledgeException{
		log.info("调用起账户啦============================================================");
		return new CustomerAccount(1L, 123123L);
	}

}
