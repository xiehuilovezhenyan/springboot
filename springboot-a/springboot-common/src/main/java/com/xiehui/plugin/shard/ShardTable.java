package com.xiehui.plugin.shard;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 分表注解
 *
 * @author xiehui
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ShardTables.class)
@Target({ElementType.METHOD})
public @interface ShardTable {

    /**
     * 取值
     *
     * @return 取值
     */
    String value();

    /**
     * 分表
     *
     * @return 分表
     */
    String shard();

}
