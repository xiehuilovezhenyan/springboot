package com.xiehui.common.core.autowire;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 基础自身自绑定类
 *
 * @author xiehui
 */

public class BasicSelfAware<T> implements SelfAware<T> {

    /**
     * 自身对象
     */

    protected T self;

    /**
     * 设置自身对象
     *
     * @param self 自身对象
     */

    @Override
    @Autowired
    public void setSelf(T self) {
        this.self = self;
    }

}
