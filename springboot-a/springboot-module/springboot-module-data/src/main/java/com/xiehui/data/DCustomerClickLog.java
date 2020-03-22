package com.xiehui.data;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 客户点击日志类
 * 
 * @author xiehui
 *
 */
@Getter
@Setter
@ToString
public class DCustomerClickLog implements Serializable {

    /** 版本标识 */
    private static final long serialVersionUID = 1L;

    /** 日志标识 */
    private Long id;
    /** ip地址 */
    private String ip;
    /** 来源 */
    private String plateFrom;
    /** 主渠道 */
    private Integer channel;
    /** 子渠道 */
    private Integer subChannel;
    /** 落地页标识 */
    private Long hrefId;
    /** 创建时间 */
    private Date createdTime;
    /** 用户标识 */
    private Long customerId;

}
