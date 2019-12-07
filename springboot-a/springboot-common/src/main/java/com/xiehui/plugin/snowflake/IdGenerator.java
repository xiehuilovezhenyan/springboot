package com.xiehui.plugin.snowflake;

/**
 * 标识生成器接口
 *
 * @author xiehui
 */
public interface IdGenerator {

    /**
     * 下一个标识
     *
     * @return 下一个标识
     */
    public long nextId();

    /**
     * 获取时间戳
     *
     * @param id 雪花标识
     * @return 时间戳
     */
    public long getTime(long id);

}
