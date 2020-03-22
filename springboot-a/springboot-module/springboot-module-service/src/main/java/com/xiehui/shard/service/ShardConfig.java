package com.xiehui.shard.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.xiehui.plugin.shard.ShardTableConverter;
import com.xiehui.plugin.shard.ShardTableStrategy;
import com.xiehui.plugin.snowflake.IdGenerator;

import lombok.extern.slf4j.Slf4j;

/**
 * 初始化分表配置
 * 
 * @author xiehui
 *
 */
@Slf4j
@Configuration
public class ShardConfig {
	/**雪花生成器*/
	@Autowired
	private IdGenerator idGenerator;

	@Bean
	public ShardTableConverter initShardBean() {
		// 初始化几种分表策略
		log.info("初始化分表策略........................");

		// 分10个表
		ModulusShardTableStrategy mod10ShardTableStrategy = new ModulusShardTableStrategy(10, false);

		// 分21个表
		ModulusShardTableStrategy mod21ShardTableStrategy = new ModulusShardTableStrategy(21, false);
		
		// 按照时间分表(基于雪花生成器)
		SnowflakeShardTableStrategy snowflakeShardTableStrategy = new SnowflakeShardTableStrategy(idGenerator, "yyyyMM");
		

		// 初始化分表转化器
		Map<String, ShardTableStrategy> strategyMap = new HashMap<String, ShardTableStrategy>();
		strategyMap.put("t_customer_account_transaction", mod10ShardTableStrategy);
		strategyMap.put("t_push_message", mod21ShardTableStrategy);
		strategyMap.put("t_customer_click_log", snowflakeShardTableStrategy);

		// 日期 雪花???

		// 返回
		return new ShardTableConverter(strategyMap);

	}

}
