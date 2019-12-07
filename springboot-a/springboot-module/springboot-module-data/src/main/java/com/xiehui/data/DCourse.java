package com.xiehui.data;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 课程类
 * 
 * @author xiehui
 *
 */
@Getter
@Setter
@ToString
public class DCourse implements Serializable {

    /** 版本标识 */
    private static final long serialVersionUID = 1L;

    /** 课程标识 */
    private Long id;
    /** 课程名称 */
    private String name;
    /**课程版本名称**/
    private String versionNikeName;
    /** 课程小图 */
    private String smallLogo;
    /** 课程大图 */
    private String bigLogo;
    /** banner图 */
    private String banner;
    /** 课前须知图 */
    private String beforeNoticePic;
    /** 课程概况 */
    private String summary;
    /** 类型(课程分类,1:训练营,2:进阶,3: 高阶) */
    private Short type;
    /** 专题(课程专题,1: 股票,2: 基金) */
    private Short special;
    /** 课程标签 */
    private String tags;
    /** 是否热推 */
    private Boolean isHot;
    /** 状态:0:启用,1:禁用 */
    private Short status;
    /** 更新状态0:更新中1:已完结 */
    private Short updateStatus;
    /** 课程相对失效时间 */
    private Integer invalidDate;
    /** 划线价 */
    private Long markedPrice;
    /** 课程价格 */
    private Long price;
    /** 课程优惠价格 */
    private Long discountPrice;
    /** 适合人群 */
    private String peopleGroup;
    /** 课程特色 */
    private String characters;
    /** 详情图片 */
    private String detailPic;
    /** 是否可以试听 */
    private Boolean isAudition;
    /** 是否开放购买 */
    private Boolean isOpenPay;
    /** 结业作业标识 */
    private Long examId;
    /** 是否引导加群 */
    private Boolean isJoinGroup;
    /** 引导标题 */
    private String guideTitle;
    /** 引导描述 */
    private String guideDesc;
    /** 备注 */
    private String remark;
    /** 排序编号 */
    private Integer orderNumber;
    /** 媒体类型 1:音频 2:视频 */
    private Short mediaType;

    /** 是否删除 */
    private Boolean isDeleted;
    /** 创建时间 */
    private Date createdTime;
    /** 修改时间 */
    private Date modifiedTime;

}
