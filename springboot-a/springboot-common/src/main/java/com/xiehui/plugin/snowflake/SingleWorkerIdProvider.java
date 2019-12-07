package com.xiehui.plugin.snowflake;

/**
 * 单机工作者标识提供者类
 *
 * @author xiehui
 */
public class SingleWorkerIdProvider implements WorkerIdProvider {

    /**
     * 工作者标识
     */
    private long workerId;

    /**
     * 构造函数
     *
     * @param workerId 工作者标识
     */
    public SingleWorkerIdProvider(long workerId) {
        this.workerId = workerId;
    }

    /**
     * 获取工作者标识
     *
     * @return 工作者标识
     */
    @Override
    public long getWorkerId() {
        return workerId;
    }

}
