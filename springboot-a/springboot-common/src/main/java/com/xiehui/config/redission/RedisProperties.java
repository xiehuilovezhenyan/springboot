package com.xiehui.config.redission;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import lombok.ToString;

/**
 * 读取配置文件
 * @author xiehui
 *
 */
@ConfigurationProperties(prefix="redission.spring.redis", ignoreUnknownFields = false)   //可以把配置自动封装成实体类  -->具有@Component功能
@Data
@ToString
public class RedisProperties {
	
    private int database;

    /**
     * 等待节点回复命令的时间。该时间从命令发送成功时开始计时
     */
    private int timeout;

    private String password;

    private String mode;

    /**
     * 池配置
     */
    private RedisPoolProperties pool;

    /**
     * 单机信息配置
     */
    private RedisSingleProperties single;

    /**
     * 集群 信息配置
     */
    private RedisClusterProperties cluster;

    /**
     * 哨兵配置
     */
    private RedisSentinelProperties sentinel;

}
