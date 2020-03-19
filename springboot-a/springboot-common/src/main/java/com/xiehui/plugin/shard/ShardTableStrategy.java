package com.xiehui.plugin.shard;

/**
 * 分表策略接口
 *
 * @author xiehui
 */
public interface ShardTableStrategy {

    /**
     * 下划线
     */
    public static final String UNDER_LINE = "_";

    /**
     * 获取目标表格名称
     *
     * @param tableName 表格名称
     * @param parameterValue 参数取值
     * @return 目标表格名称
     */
    public String getTargetTableName(String tableName, Object parameterValue);

}
