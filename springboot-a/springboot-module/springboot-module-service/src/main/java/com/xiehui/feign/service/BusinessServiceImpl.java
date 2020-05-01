package com.xiehui.feign.service;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.xiehui.common.core.exception.KnowledgeException;
import com.xiehui.constant.DataSourceName;
import com.xiehui.customer.api.AccountService;
import com.xiehui.customer.api.CustomerService;
import com.xiehui.customer.module.CustomerAccount;
import com.xiehui.customer.module.CustomerData;
import com.xiehui.data.DCourse;
import com.xiehui.data.DCourseDAO;
import com.xiehui.data.DCustomerAccountTransaction;
import com.xiehui.data.DCustomerAccountTransactionDAO;
import com.xiehui.data.DCustomerClickLog;
import com.xiehui.data.DCustomerClickLogDAO;
import com.xiehui.feign.api.BusinessService;
import com.xiehui.plugin.snowflake.IdGenerator;
import com.xiehui.redission.DelayJobService;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BusinessServiceImpl implements BusinessService {
	@Autowired
	private DCourseDAO dCourseDAO;
	@Autowired
	private DCustomerAccountTransactionDAO dCustomerAccountTransactionDAO;
	@Autowired
	private DCustomerClickLogDAO dCustomerClickLogDAO;

	@Autowired
	private IdGenerator idGenerator;

	@Autowired
	private RocketMQService rocketMQService;

	@Autowired
	private TaskExecutor taskExecutor;

	@Autowired
	private DelayJobService delayJobService;

	@Autowired
	private CustomerService customerService;
	@Autowired
	private AccountService accountService;

	/**
	 * 测试一主,两从
	 */
	@Override
	@DS(DataSourceName.MASTER)
	public void queryAllCourse() throws KnowledgeException {
		List<DCourse> dCourses = dCourseDAO.listHotCourse();
		log.info("课程数据...:" + JSON.toJSONString(dCourses));

		// 查询用户交易
		DCustomerAccountTransaction dCustomerAccountTransaction = dCustomerAccountTransactionDAO
				.get(235070127472910400L, 219491708261638310L);
		log.info("账户数据: " + JSON.toJSONString(dCustomerAccountTransaction));

		// 添加记录
		DCustomerClickLog dCustomerClickLog = new DCustomerClickLog();
		dCustomerClickLog.setId(idGenerator.nextId());
		dCustomerClickLog.setChannel(1);
		dCustomerClickLog.setSubChannel(2);
		dCustomerClickLog.setCustomerId(123L);
		dCustomerClickLog.setPlateFrom("123");
		dCustomerClickLog.setIp("127.0.0.1");
		dCustomerClickLog.setHrefId(1L);
		dCustomerClickLogDAO.create(dCustomerClickLog);
		log.info("创建日志成功.................................");

		// 查询
		DCustomerClickLog logData = dCustomerClickLogDAO.get(dCustomerClickLog.getId());
		log.info("查询日志: " + JSON.toJSONString(logData));

		// 测试rabbitMQ
		// sender.sendMsg("测试啊");

		// 测试调用feign
		CustomerData customerData = customerService.getCustomerData(idGenerator.nextId() + "");
		log.info("CustomerData: " + JSON.toJSONString(customerData));
		CustomerAccount customerAccount = accountService.getCustomerAccount();
		log.info("customerAccount: " + JSON.toJSONString(customerAccount));
	}

	/**
	 * 测试雪花生成器
	 */
	@Override
	public void testIdGenerator() {
		for (int i = 0; i < 1000000; i++) {
			System.out.println(idGenerator.nextId());
		}
	}

	@Override
	public void testMQ() {
		try {
			// 测试发送广播消息
			rocketMQService.sendMsg("broad-cast", "你好");

			// 发送顺序消息
			for (int i = 0; i < 20; i++) {
				rocketMQService.sendOrderlyMsg("order-message-1", "这里是顺序消息:" + i);
			}

			// 发送异步消息
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (Exception e) {
			}

			// 发送异步消息
			rocketMQService.sendSyncMsg("sync-message-1", "这里异步消息");

			// 发送延迟消息
			rocketMQService.sendDelayMsg("sync-message-1", "这里是延迟消息", 1);

		} catch (KnowledgeException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void testRedisssionSync() {
		for (int i = 0; i < 10000; i++) {
			taskExecutor.execute(new Runnable() {

				@Override
				public void run() {
					Test test = new Test(idGenerator.nextId(), "Test2222222222");
					delayJobService.submitJob("test", test, 10L, TimeUnit.SECONDS);
				}
			});
		}

		for (int i = 0; i < 10000; i++) {
			taskExecutor.execute(new Runnable() {

				@Override
				public void run() {
					Test test = new Test(idGenerator.nextId(), "Test1111111111");
					delayJobService.submitJob("test", test, 6L, TimeUnit.SECONDS);
				}
			});
		}
	}

	@Data
	public static class Test implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 8810108867237394687L;
		private Long id;
		private String name;

		public Test(Long i, String name) {
			this.id = i;
			this.name = name;
		}
	}

}
