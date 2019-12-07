package com.xiehui.feign.service;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.xiehui.common.core.exception.CustomException;
import com.xiehui.constant.DataSourceName;
import com.xiehui.data.DCourse;
import com.xiehui.data.DCourseDAO;
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
	private IdGenerator idGenerator;

	@Autowired
	private RocketMQService rocketMQService;
	
	@Autowired
	private TaskExecutor taskExecutor;
	
	@Autowired
	private DelayJobService delayJobService;

	/**
	 * 测试一主,两从
	 */
	@Override
	@DS(DataSourceName.SLAVE)
	public void queryAllCourse() {
		List<DCourse> dCourses = dCourseDAO.listHotCourse();
		log.info("课程数据:" + JSON.toJSONString(dCourses));
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
			
			//发送异步消息
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (Exception e) {
			}
			
			//发送异步消息
			rocketMQService.sendSyncMsg("sync-message-1", "这里异步消息");
			
			//发送延迟消息
			rocketMQService.sendDelayMsg("sync-message-1", "这里是延迟消息", 1);

		} catch (CustomException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void testRedisssionSync() {
		for(int i=0;i<1000;i++) {
			taskExecutor.execute(new Runnable() {
				
				@Override
				public void run() {
					Test test = new Test(idGenerator.nextId(), "Test2222222222");
					delayJobService.submitJob("test", test, 10L, TimeUnit.SECONDS);
				}
			});
		}
		
		for(int i=0;i<1000;i++) {
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
	public static class Test implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 8810108867237394687L;
		private Long id;
		private String name;
		
		public Test(Long i,String name) {
			this.id = i;
			this.name = name;
		}
	}

}
