package com.xiehui.config.execute;

import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

/**
 * 初始化线程池
 * 
 * @author xiehui
 *
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(ExecutorConfigData.class)
public class TaskExecutorConfig {

	@Autowired
	private ExecutorConfigData executorConfigData;

	@Bean
	public TaskExecutor taskExecutor() {
		log.info("初始化ThreadPoolTaskExecutor......" + JSON.toJSONString(executorConfigData));

		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(executorConfigData.getCorePoolSize());
		executor.setMaxPoolSize(executorConfigData.getMaxPoolSize());
		executor.setQueueCapacity(executorConfigData.getQueueCapacity());
		executor.setThreadNamePrefix("taskExecutor-");
		// 对拒绝task的处理策略
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		executor.setKeepAliveSeconds(executorConfigData.getKeepAlive());
		executor.initialize();
		return executor;
	}

}
