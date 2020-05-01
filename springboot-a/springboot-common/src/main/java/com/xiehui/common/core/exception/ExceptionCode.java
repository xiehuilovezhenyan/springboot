package com.xiehui.common.core.exception;

/**
 * 异常编码枚举
 *
 * @author cychen
 */
public enum ExceptionCode {

    /** 字段相关 */
    /**
     * 时间戳错误
     */
    TIMESTAMP_ERROR(1, "时间戳错误"),
    /**
     * 签名错误
     */
    SIGNATURE_ERROR(2, "签名错误"),
    /**
     * 令牌无效
     */
    TOKEN_INVALID(3, "登录无效"),
    /**
     * 权限不足
     */
    RIGHT_NOT_ENOUGH(4, "权限不足"),
    /**
     * 参数错误
     */
    PARAMETER_ERROR(5, "参数错误"),
    /**
     * 参数错误
     */

    NEED_IMAGE_CAPTCHA(8, "需要图形验证码"),

    NEED_SUBSCRIBE(6, "没有关注公众号"),

    NEED_BIND_WECHAT(7, "该账户没有绑定微信"),

    NEED_BINDBANKCARD(10, "没有绑定银行卡"),
    LIVE_BUY_FAILED(13, "直播间购课失败"),
    /**
     * 进度查询错误
     */
    PROGRESS_QUERY_ERROR(602, "进度查询错误"),

    USER_DELETE(9997, "该账号异常操作，已被删除"),

    USER_DISABLE(9998, "该账号异常操作，已被禁用"),
    /**
     * 未知错误
     */
    COMMON_CODE(9999, "未知错误"),
    /**
     * 任务已经执行
     */
    TASK_HAVEUSED(1001, "任务已执行"),
    /**
     * 任务已经执行
     */
    REWARD_HAVEUSED(1002, "奖励已领取"),
    /**
     * 任务已经执行
     */
    TASK_NOTCOMPLETED(1003, "任务未完成"),
    /**
     * 任务已经执行
     */
    REWARD_CONDITION_NOTENOUGH(1004, "任务没有达到领取条件"),

    TASK_IS_FINISHED(1005, "活动已过期"),
    /**
     * 重复购买
     */
    ORDER_HAD_BUY(1006, "您已购买该课程"),
    /**
     * 重复报名
     */
    COURSE_HAD_JOIN(1007, "重复报名"),
    SYSTEM_ERROR_CODE(1234, "系统错误"),
    /**
     * 不可继续支付(用户小白营/直播课互斥)
     */
    CAN_NOT_2_PAY(1008, "不可继续支付"),

    ;

    /**
     * 异常编码值
     */
    private int value = 0;
    /**
     * 异常编码描述
     */
    private String description;

    /**
     * 构造函数
     *
     * @param value 异常编码值
     * @param description 异常编码描述
     */
    private ExceptionCode(int value, String description) {
        this.value = value;
        this.description = description;
    }

    /**
     * 获取异常编码值
     *
     * @return 异常编码值
     */
    public int getValue() {
        return value;
    }

    /**
     * 获取异常编码描述
     *
     * @return 异常编码描述
     */
    public String getDescription() {
        return description;
    }

}