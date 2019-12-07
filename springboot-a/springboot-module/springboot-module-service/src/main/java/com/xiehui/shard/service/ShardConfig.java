package com.xiehui.shard.service;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import lombok.ToString;

@ConfigurationProperties(prefix = "shard", ignoreUnknownFields = false) // 可以把配置自动封装成实体类																					// -->具有@Component功能
@Data
@ToString
public class ShardConfig {
	
	private String module;
	private String tabDes;

}
