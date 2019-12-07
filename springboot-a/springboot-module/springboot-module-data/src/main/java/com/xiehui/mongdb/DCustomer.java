package com.xiehui.mongdb;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 客户类
 * 
 * @author xiehui
 *
 */
@Getter
@Setter
@ToString
@Document(collection = "m_customer")
public class DCustomer implements Serializable {

    /** 版本标识 */
    private static final long serialVersionUID = 1L;

    /** 客户标识 */
    @Id
    private Long id;
    /** 手机号码 */
    private String mobile;
    /** 客户名称 */
    private String realName;
    /** 身份证 */
    private String idNumber;
    /** 客户学号 */
    private String learnNumber;
    /** 微信账号 */
    private String wechatAccount;
    /** 微信昵称 */
    private String wechatName;
    /** 微信性别(0:未知;1:男性;2:女性) */
    private Short wechatSex;
    /** 微信头像 */
    private String wechatAvatar;
    /** 微信国家 */
    private String wechatCountry;
    /** 微信省份 */
    private String wechatProvince;
    /** 微信城市 */
    private String wechatCity;
    /** 微信开放平台唯一标识 */
    private String unionId;
    /** 开放标识 {appid:{openid:',status:'},appid:{openid:',status:'}} */
    private String openIds;
    /** 父客户标识 */
    private Long parentId;
    /** 父客户标识列表(逗号隔开) */
    private String parentIds;
    /** 客户状态(0:正常,1:禁用) */
    private Short status;
    /** 是否删除 */
    private Boolean isDeleted;
    /** 用户层级 */
    private Integer layerNumber;
    /** 用户身份(???????????????????????????) */
    private Short identity;
    /** 绑定上级时间 */
    private Date bindedTime;
    /** 最后登录时间 */
    private Date lastLoginTime;
    /** 数据来源:1、安卓,2: IOS 3: 微信 4: 其它-渠道1 5: 其它-渠道2 等等 */
    private Integer dataFrom;
    /** 创建时间 */
    private Date createdTime;
    /** 修改时间 */
    private Date modifiedTime;

}
