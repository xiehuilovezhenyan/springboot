package com.xiehui.data;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 雪花工作者类
 *
 * @author cychen
 */
@Getter
@Setter
@ToString
public class DSnowflakeWorker implements Serializable {

    /**
     * 版本标识
     */
    private static final long serialVersionUID = 1L;

    /**
     * 雪花标识
     */
    private Long id;
    /**
     * 模块名称
     */
    private String moduleName;
    /**
     * 主机地址
     */
    private String hostAddress;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 修改时间
     */
    private Date modifiedTime;

}
