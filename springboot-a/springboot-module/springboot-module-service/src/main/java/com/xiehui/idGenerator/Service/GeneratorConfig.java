package com.xiehui.idGenerator.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xiehui.plugin.snowflake.WorkerIdProvider;

/**
 * 雪花生成器注入参数
 * @author xiehui
 *
 */
@Configuration
public class GeneratorConfig {
	
	/**
	 * 工作者标识提供者接口
	 */
	@Autowired
	private WorkerIdProvider workerIdProvider;
	
	/**
	 * 注入工作者标识
	 * @return
	 */
	@Bean
	public SnowflakeIdGenerator snowflakeIdGenerator() {
		return new SnowflakeIdGenerator(workerIdProvider);
	}

}
