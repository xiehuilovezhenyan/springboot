package com.xiehui.shard.service;

import org.springframework.util.Assert;

import com.xiehui.plugin.shard.ShardTableStrategy;


/**
 * 简单分表策略类
 *
 * @author xiehui
 */
public class SimpleShardTableStrategy implements ShardTableStrategy {

    /**
     * 获取目标表格名称
     *
     * @param tableName 表格名称
     * @param parameterValue 参数取值
     * @return 目标表格名称
     */
    @Override
    public String getTargetTableName(String tableName, Object parameterValue) {
        // 检查参数取值
        Assert.notNull(parameterValue, "分表参数取值为空");

        // 组装表格名称
        StringBuilder sb = new StringBuilder(64);
        sb.append(tableName);
        sb.append(UNDER_LINE);
        sb.append(String.valueOf(parameterValue));

        // 返回表格名称
        return sb.toString();
    }

}
