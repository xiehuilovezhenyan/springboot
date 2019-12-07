package com.xiehui.feign.service;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.xiehui.feign.api.RedisService;
import com.xiehui.redis.RedisHelper;
import com.xiehui.redission.DistributedLocker;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RedisServiceImpl implements RedisService {
	@Autowired
	private RedisHelper redisHelper;
	@Autowired
	private DistributedLocker distributedLocker;
	
	@Value("${common.lock.expireTime}")
	private Long expireTime;
	@Value("${common.lock.tryTimeout}")
	private Long tryTimeout;

	/**
	 * 测试redis
	 */
	@Override
	public void test() {

		try {
			// 取值
			Test test = redisHelper.getObjectHash("test:object:Test:%s", 1, Test.class);
			System.out.println("获取对象:" + JSON.toJSONString(test));

			// 设置值
			Test test1 = new Test();
			test1.setId(2L);
			test1.setName("哈哈哈");
			redisHelper.saveObjectHash("test:object:Test:%s", 2, test1);

			test1 = new Test();
			test1.setId(3L);
			test1.setName("嘿嘿嘿");
			redisHelper.saveObjectHash("test:object:Test:%s", 3, test1);

			test = redisHelper.getObjectHash("test:object:Test:%s", 2, Test.class);
			System.out.println("获取对象:" + JSON.toJSONString(test));

		} catch (Exception e) {
			log.info("异常", e);
		}

	}

	/**
	 * 测试Redisson分布式锁
	 */
	@Override
	public void test1() {
		String key = "redisson_key";
		for (int i = 0; i < 100; i++) {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						System.err.println("=============线程开启============" + Thread.currentThread().getName());
						// 尝试获取锁，等待5秒，自己获得锁后一直不解锁则10秒后自动解锁
						boolean isGetLock = distributedLocker.tryLock(key, TimeUnit.SECONDS, tryTimeout, expireTime);
						if (!isGetLock) {
							System.out.println("线程:" + Thread.currentThread().getName() + ",没有获取到锁");
							return;
						}
						System.out.println("线程:" + Thread.currentThread().getName() + ",获取到了锁");
						Thread.sleep(100); // 获得锁之后可以进行相应的处理
						System.err.println("======获得锁后进行相应的操作======" + Thread.currentThread().getName());
						distributedLocker.unlock(key);
						System.err.println("=============================" + Thread.currentThread().getName());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			t.start();
		}
	}

	@Data
	static class Test {
		private Long Id;
		private String name;
	}

}
