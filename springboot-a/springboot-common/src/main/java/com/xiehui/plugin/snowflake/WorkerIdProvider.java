package com.xiehui.plugin.snowflake;

/**
 * 工作者标识提供者接口
 *
 * @author xiehui
 */
public interface WorkerIdProvider {

    /**
     * 获取工作者标识
     *
     * @return 工作者标识
     */
    public long getWorkerId();

}
