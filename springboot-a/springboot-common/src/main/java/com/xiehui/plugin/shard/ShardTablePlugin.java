package com.xiehui.plugin.shard;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;

/**
 * 分表插件类
 *
 * @author xiehui
 */
@Slf4j
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class ShardTablePlugin implements Interceptor {

    /**
     * SQL
     */
    private static final String SQL = "sql";
    /**
     * 对象代理
     */
    private static final String DELEGATE = "delegate";

    /** 静态常量 */
    /**
     * 映射声明
     */
    private static final String MAPPED_STATEMENT = "mappedStatement";
    /**
     * 统计后缀
     */
    private static final String COUNT_SUFFIX = "_COUNT";
    /**
     * 方法映射
     */
    private final Map<String, Map<String, String>> methodMap = new ConcurrentHashMap<>();
    /**
     * 分表转化器
     */
    private ShardTableConverter converter;

    /**
     * 进行拦截
     *
     * @param invocation 调用
     * @return 结果
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 获取声明处理器
        StatementHandler handler = (StatementHandler)invocation.getTarget();
        Assert.notNull(handler, "获取声明处理器为空");

        // 获取映射声明
        MappedStatement statement = null;
        if (handler instanceof RoutingStatementHandler) {
            StatementHandler delegate = (StatementHandler)ShardTableHelper.getFieldValue(handler, DELEGATE);
            statement = (MappedStatement)ShardTableHelper.getFieldValue(delegate, MAPPED_STATEMENT);
        } else {
            statement = (MappedStatement)ShardTableHelper.getFieldValue(handler, MAPPED_STATEMENT);
        }
        Assert.notNull(statement, "获取映射声明为空");

        // 获取方法标识
        String methodId = statement.getId();
        Assert.notNull(methodId, "获取方法标识为空");
        if (methodId.endsWith(COUNT_SUFFIX)) {
            methodId = methodId.substring(0, methodId.length() - COUNT_SUFFIX.length());
        }

        // 获取分表映射
        Map<String, String> shardMap = methodMap.get(methodId);
        if (Objects.isNull(shardMap)) {
            shardMap = getShardMap(methodId);
            methodMap.put(methodId, shardMap);
        }

        // 更改SQL语句
        if (MapUtils.isNotEmpty(shardMap)) {
            changeSql(handler, statement, methodId, shardMap);
        }

        // 调用函数处理
        return invocation.proceed();
    }

    /**
     * 获取分表映射
     *
     * @param methodId 方法标识
     * @return 分表映射
     */
    private Map<String, String> getShardMap(String methodId) {
        // 获取调用方法
        Method method = ShardTableHelper.getMethod(methodId);
        Assert.notNull(method, "获取调用方法为空");

        // 获取分表注解
        Map<String, String> shardMap = new HashMap<String, String>(10);
        // 获取分表注解: 多个注解
        ShardTables shardTables = AnnotationUtils.findAnnotation(method, ShardTables.class);
        if (Objects.nonNull(shardTables)) {
            if (ArrayUtils.isNotEmpty(shardTables.value())) {
                for (ShardTable shardTable : shardTables.value()) {
                    shardMap.put(shardTable.value(), shardTable.shard());
                }
            }
        }
        // 获取分表注解: 单个注解
        else {
            ShardTable shardTable = AnnotationUtils.findAnnotation(method, ShardTable.class);
            if (Objects.nonNull(shardTable)) {
                shardMap.put(shardTable.value(), shardTable.shard());
            }
        }

        // 返回参数映射
        return shardMap;
    }

    /**
     * 更改SQL语句
     *
     * @param handler 声明处理器
     * @param statement 映射声明
     * @param methodId 方法标识
     * @param shardMap 分表映射
     */
    private void changeSql(StatementHandler handler, MappedStatement statement, String methodId,
        Map<String, String> shardMap) {
        // 获取转化器
        if (Objects.isNull(converter)) {
            converter = ShardTableConverter.getInstance();
        }
        Assert.notNull(converter, "获取分表转化器为空");

        // 获取SQL语句
        String sql = handler.getBoundSql().getSql();
        Map parameterMap = (Map)handler.getBoundSql().getParameterObject();
        if (log.isDebugEnabled()) {
            log.debug("原始SQL语句[{}]:{}", methodId, sql);
        }

        // 转化SQL语句
        Configuration configuration = statement.getConfiguration();
        MetaObject metaObject = configuration.newMetaObject(parameterMap);
        sql = converter.convertSql(metaObject, shardMap, sql);
        if (log.isDebugEnabled()) {
            log.debug("目标SQL语句[{}]:{}", methodId, sql);
        }

        // 设置SQL语句
        ShardTableHelper.setFieldValue(handler.getBoundSql(), SQL, sql);
    }

    /**
     * 获取插件
     *
     * @param target 对象
     * @return 插件
     */
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /**
     * 设置属性
     *
     * @param properties 属性列表
     */
    @Override
    public void setProperties(Properties properties) {}

}
