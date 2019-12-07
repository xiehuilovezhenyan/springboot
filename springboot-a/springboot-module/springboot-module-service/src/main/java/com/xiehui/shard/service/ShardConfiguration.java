package com.xiehui.shard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

/**
 * 分表注入参数
 * 
 * @author xiehui
 *
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(ShardConfig.class)
public class ShardConfiguration {

	@Autowired
	private ShardConfig shardConfig;

}
