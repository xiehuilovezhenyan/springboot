package com.xiehui.shard.service;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.xiehui.plugin.shard.ShardTableStrategy;

import lombok.Setter;

/**
 * 日期分表策略类
 *
 * @author xiehui
 */
public class DateShardTableStrategy implements ShardTableStrategy, InitializingBean {

    /**
     * 日期模式
     */
    @Setter
    private String dateFormat;

    /**
     * 在属性设置后调用
     *
     * @throws Exception 异常信息
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        // 检查属性取值
        Assert.isTrue(StringUtils.isNoneBlank(dateFormat), "属性dateFormat(日期格式)未设置");
    }

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
        Assert.isInstanceOf(Date.class, parameterValue, "分表参数取值类型错误");

        // 获取参数取值
        Date shardDate = (Date)parameterValue;
        Assert.notNull(shardDate, "获取分表日期取值为空");

        // 获取分表后缀
        String tableSuffix = DateFormatUtils.format(shardDate, dateFormat);
        Assert.isTrue(StringUtils.isNotBlank(tableSuffix), "获取分表后缀为空");

        // 组装表格名称
        StringBuilder sb = new StringBuilder(64);
        sb.append(tableName);
        sb.append(UNDER_LINE);
        sb.append(tableSuffix);

        // 返回表格名称
        return sb.toString();
    }

}
