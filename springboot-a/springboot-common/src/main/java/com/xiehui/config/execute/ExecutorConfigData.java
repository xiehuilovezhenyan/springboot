package com.xiehui.config.execute;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import lombok.ToString;

@ConfigurationProperties(prefix = "spring.execute", ignoreUnknownFields = false) 																					
@Data
@ToString
public class ExecutorConfigData {

	// 线程池维护线程的最少数量
	private int corePoolSize;
	// 线程池维护线程的最大数量
	private int maxPoolSize;
	// 缓存队列
	private int queueCapacity;
	// 允许的空闲时间
	private int keepAlive;

}
