package com.xiehui.common.core.autowire;

/**
 * 自身自绑定接口
 *
 * @author xiehui
 */
public interface SelfAware<T> {

    /**
     * 设置自身对象
     *
     * @param self 自身对象
     */
    public void setSelf(T self);

}
