package com.xiehui.plugin.shard;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 分表集注解
 *
 * @author cychen
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ShardTables {

    /**
     * 取值
     *
     * @return 取值
     */
    ShardTable[] value();

}
