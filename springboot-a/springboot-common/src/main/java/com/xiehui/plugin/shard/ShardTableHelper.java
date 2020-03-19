package com.xiehui.plugin.shard;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

import org.apache.commons.lang3.ClassUtils;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

/**
 * 分表辅助类
 *
 * @author xiehui
 */
public class ShardTableHelper {

    /**
     * 构造函数
     */
    private ShardTableHelper() {}

    /**
     * 设置字段值
     *
     * @param object 对象实例
     * @param fieldName 字段名称
     * @param value 字段值
     */
    public static void setFieldValue(Object object, String fieldName, Object value) {
        // 获取字段
        Field field = ReflectionUtils.findField(object.getClass(), fieldName);
        Assert.notNull(object, "找不到字段(" + fieldName + ")");

        // 设置可访问
        ReflectionUtils.makeAccessible(field);

        // 设置字段值
        ReflectionUtils.setField(field, object, value);
    }

    /**
     * 获取字段值
     *
     * @param object 对象实例
     * @param fieldName 字段名称
     * @return 字段值
     */
    public static Object getFieldValue(Object object, String fieldName) {
        // 获取字段
        Field field = ReflectionUtils.findField(object.getClass(), fieldName);
        Assert.notNull(object, "找不到字段(" + fieldName + ")");

        // 设置可访问
        ReflectionUtils.makeAccessible(field);

        // 获取字段值
        return ReflectionUtils.getField(field, object);
    }

    /**
     * 获取标识方法
     *
     * @param mapperId 映射标识
     * @return 操作方法
     */
    public static Method getMethod(String mapperId) {
        // 获取类和方法
        int index = mapperId.lastIndexOf(".");
        if (index == -1) {
            return null;
        }
        String className = mapperId.substring(0, index);
        String methodName = mapperId.substring(index + 1);

        // 获取指定类
        Class<?> clazz = null;
        try {
            clazz = ClassUtils.getClass(className);
        } catch (ClassNotFoundException e) {
            return null;
        }
        if (Objects.isNull(clazz)) {
            return null;
        }

        // 获取指定方法
        return ReflectionUtils.findMethod(clazz, methodName, (Class<?>[])null);
    }
}
