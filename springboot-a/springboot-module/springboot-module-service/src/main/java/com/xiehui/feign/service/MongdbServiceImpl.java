package com.xiehui.feign.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.xiehui.common.core.exception.KnowledgeException;
import com.xiehui.feign.api.MongdbService;
import com.xiehui.mongdb.DCustomer;
import com.xiehui.mongdb.MCustomermDAO;

import lombok.extern.slf4j.Slf4j;

/**
 * mongdb 测试服务接口实现类
 * 
 * @author xiehui
 *
 */
@Slf4j
@Service("mongdbService")
public class MongdbServiceImpl implements MongdbService {
	@Autowired
	private MCustomermDAO mCustomerDao;

	@Autowired
	private TaskExecutor taskExecutor;

	@Override
	public void findAll() throws KnowledgeException {
		// 查找客户
		/*
		 * List<DCustomer> dCustomerList = mCustomerDao.findAll(); log.info("所有客户信息: " +
		 * JSON.toJSONString(dCustomerList));
		 */
		/**
		 * 测试mongdb条件查询
		 */
		DCustomer dCustomer = new DCustomer();
		dCustomer.setIsDeleted(Boolean.FALSE);
		// dCustomer.setId(206859508634628104L);
		List<DCustomer> dCustomerList = mCustomerDao.queryByCondition(dCustomer);
		System.out.println(JSON.toJSONString(dCustomerList));

		// 测试分页查询
		DCustomer dCustomer1 = new DCustomer();
		dCustomer1.setStatus((short) 0);
		dCustomer1.setWechatName("POS");
		dCustomerList = mCustomerDao.queryByPage(dCustomer1, 1, 10);
		System.out.println(JSON.toJSON(dCustomerList));

		// 聚合函数测试
		// 统计状态为0有多少个
		Integer count = mCustomerDao.countByStatus();
		System.out.println("状态为0的有:" + count);
		// 多条件查询统计
		List<MCustomermDAO.Test> testCount = mCustomerDao.testAggregationCount(dCustomer);
		System.out.println("测试聚合函数count" + JSON.toJSONString(testCount));
	}

}
