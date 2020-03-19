package com.xiehui.data;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 客户账户交易类
 * 
 * @author xiehui
 *
 */
@Getter
@Setter
@ToString
public class DCustomerAccountTransaction implements Serializable {

    /** 版本标识 */
    private static final long serialVersionUID = 1L;

    /** 交易标识 */
    private Long id;
    /** 客户标识 */
    private Long customerId;
    /** 客户标识 */
    private Long sourceCustomerId;
    /** 交易金额(有正负) */
    private Long amount;
    /** 发生交易后的实时余额(分) */
    private Long balance;
    /** 交易类型(1.收入 2,提现) */
    private Integer type;
    /** 子类型（201: 提现） */
    private Integer subType;
    /** 交易描述信息 */
    private String description;
    /** 交易状态(0: 成功, 1:失败) */
    private Short status;
    /** 结算状态(1, 未结算 2, 已结算) */
    private Short settleStatus;
    /** 来源业务标识 @since 2020-1-19 */
    private Long sourceBizId;
    /** 创建时间 */
    private Date createdTime;
    /** 修改时间 */
    private Date modifiedTime;

}
