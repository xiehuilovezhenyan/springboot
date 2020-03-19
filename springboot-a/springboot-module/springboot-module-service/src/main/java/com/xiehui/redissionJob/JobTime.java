package com.xiehui.redissionJob;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.redisson.api.RBlockingQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JobTime {

	@Autowired
	private RedissonClient redissonClient;
	@Autowired
	private ApplicationContext context;
	@Autowired
	private TaskExecutor taskExecutor;

	@PostConstruct
	public void startJobTime() {
		RBlockingQueue blockingQueue = redissonClient.getBlockingQueue("test");
		redissonClient.getDelayedQueue(blockingQueue);
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					log.info("检测JobTime中.........................");
					try {
						Object obj = blockingQueue.take();
						taskExecutor.execute(new Runnable() {
							
							@Override
							public void run() {
								log.info("JobTime:" + JSON.toJSONString(obj));
							}
						});
					} catch (Exception e) {
						log.error("JobTime异常:",e);
                        try {
                            TimeUnit.SECONDS.sleep(60);
                        } catch (Exception ex) {
                        }
					}
				}
			}
		}).start();
	}

}
