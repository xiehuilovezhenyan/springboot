package com.xiehui.plugin.shard;

import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;

import lombok.Setter;

/**
 * 分表转化器类
 *
 * @author xiehui
 */
public class ShardTableConverter implements ApplicationContextAware {

    /**
     * 应用上下文
     */
    private static ApplicationContext applicationContext;
    /**
     * 分表策略映射
     */
    @Setter
    private Map<String, ShardTableStrategy> strategyMap;

    /**
     * 设置静态应用上下文
     *
     * @param applicationContext 应用上下文
     */
    private static void setStaticApplicationContext(ApplicationContext applicationContext) {
        ShardTableConverter.applicationContext = applicationContext;
    }

    /**
     * 获取分表转化器实例
     *
     * @return 分表转化器实例
     */
    public static ShardTableConverter getInstance() {
        if (Objects.isNull(applicationContext)) {
            return null;
        }
        return applicationContext.getBean(ShardTableConverter.class);
    }

    /**
     * 设置应用上下文
     *
     * @param applicationContext 应用上下文
     * @throws BeansException Beans异常
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 不建议直接设置静态变量
        setStaticApplicationContext(applicationContext);
    }

    /**
     * 转化SQL语句
     *
     * @param metaObject 元数据对象
     * @param shardMap 分表映射
     * @param originSql 原始SQL
     * @return 目标SQL
     */
    public String convertSql(MetaObject metaObject, Map<String, String> shardMap, String originSql) {
        // 初始构建器
        StringBuilder sb = new StringBuilder(originSql.length() + 100);

        // 转化SQL语句
        int index = 0;
        String regex = "(from|join|into|update)(\\s+)(\\w+)";
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(originSql);
        while (matcher.find()) {
            // 获取相关数据
            String keyword = matcher.group(1);
            String blank = matcher.group(2);
            String tableName = matcher.group(3);

            // 组装SQL语句
            sb.append(originSql.substring(index, matcher.start()));
            sb.append(keyword);
            sb.append(blank);
            sb.append(convertTableName(metaObject, shardMap, tableName));

            // 设置起始位置
            index = matcher.end();
        }
        sb.append(originSql.substring(index));

        // 返回SQL语句
        return sb.toString();
    }

    /**
     * 转化表格名称
     *
     * @param metaObject 元数据对象
     * @param shardMap 分表映射
     * @param tableName 表格名称
     * @return 新表格名称
     */
    private String convertTableName(MetaObject metaObject, Map<String, String> shardMap, String tableName) {
        // 获取参数名称
        String parameterName = shardMap.get(tableName);
        Assert.isTrue(StringUtils.isNoneBlank(parameterName), "表格(" + tableName + ")的分表参数名称为空");

        // 获取参数取值
        Object parameterValue = metaObject.getValue(parameterName);
        Assert.isTrue(StringUtils.isNoneBlank(parameterName), "表格(" + tableName + ")的分表参数取值为空");

        // 获取分表策略
        ShardTableStrategy strategy = null;
        if (MapUtils.isNotEmpty(shardMap)) {
            strategy = strategyMap.get(tableName);
        }
        Assert.notNull(strategy, "表格(" + tableName + ")没有配置分表策略");

        // 获取表格名称
        return strategy.getTargetTableName(tableName, parameterValue);
    }

}
