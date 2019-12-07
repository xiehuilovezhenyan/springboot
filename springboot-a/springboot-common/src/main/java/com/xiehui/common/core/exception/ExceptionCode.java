package com.xiehui.common.core.exception;

/**
 * 异常编码枚举
 *
 * @author xiehui
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
    /**
     * 进度查询错误
     */
    PROGRESS_QUERY_ERROR(602, "进度查询错误"),
    /**
     * 未知错误
     */
    COMMON_CODE(9999, "未知错误"),
    /**
     * 任务已经执行
     */
    TASK_HAVEUSED(0, "任务已执行"),

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
    /************************************ 订单相关 ********************************/
    /**
     *
     * 库存不足
     */
    ORDER_GOODS_STACK(2001, "库存不足"),
    /**
     *
     * 限购商品
     */
    ORDER_LINMIT_NUMNBER(2002, "商品限购"),
    /**
     * 产品下架
     */
    ORDER_GOODS_STATUS(2003, "产品已下架"),
    /**
     * 地址为空
     */
    ORDER_ASDDRESS_BLANK(2004, "地址为空"),
    /**
     * 订单未支付成功
     */
    ORDER_PAY_ISSUCCESS(2005, "订单未支付成功"),
    /**
     *
     * 该用户未购买该课商品
     */
    ORDER_BUY_NO_GOODS(2006, "该用户未购买该课商品"),
    /**
     * 产品下架
     */
    ORDER_GOODS_NO_START(2007, "即将上线..."),
    /**
     * 您已购买过此课程
     *
     */
    ORDER_BUY_OVER_ONE_GOODS(2008, "你已购买过此课程"),
    /**
     * 无法继续购买
     *
     */
    ORDER_BUY_OVER_MANY_GOODS(2009, "无法继续购买"),
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