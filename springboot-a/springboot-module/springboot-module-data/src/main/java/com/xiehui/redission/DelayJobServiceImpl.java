package com.xiehui.redission;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DelayJobServiceImpl implements DelayJobService {
	@Autowired
	private RedissonClient redissonClient;

	/**
	 * 提交延迟任务
	 * 
	 * @param queueName
	 * @param msg
	 * @param delay
	 * @param timeUnit
	 */
	@Override
	public void submitJob(String queueName, Object msg, Long delay, TimeUnit timeUnit) {
		try {
			RBlockingQueue blockingQueue = redissonClient.getBlockingQueue(queueName);
			RDelayedQueue delayedQueue = redissonClient.getDelayedQueue(blockingQueue);
			delayedQueue.offer(msg, delay, timeUnit);
			log.info("提交延迟任务成功:" + JSON.toJSONString(msg));
		} catch (Exception e) {
			log.info("提交延迟任务异常", e);
		}
	}

}
