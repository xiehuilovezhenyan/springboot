package com.xiehui.common.core.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * kakaka应答类
 *
 * @author xiehui
 */
@Getter
@Setter
@ToString
public class KnowledgeResponse<T> implements Serializable {

    /**
     * 版本标识
     */
    private static final long serialVersionUID = 1L;

    /**
     * 应答编码
     */
    private Integer code = 0;
    /**
     * 应答消息
     */
    private String message;
    /**
     * 应答异常
     */
    private String exception;
    /**
     * 应答数据
     */
    private T data;

    /**
     * 成功应答
     *
     * @return 成功应答
     */
    public static <T> KnowledgeResponse<T> success() {
        KnowledgeResponse<T> response = new KnowledgeResponse<>();
        response.setCode(0);
        return response;
    }

    /**
     * 成功应答
     *
     * @param data 应答数据
     * @return 成功应答
     */
    public static <T> KnowledgeResponse<T> success(T data) {
        KnowledgeResponse<T> response = new KnowledgeResponse<>();
        response.setCode(0);
        response.setData(data);
        return response;
    }

    /**
     * 成功应答
     *
     * @param data 应答数据
     * @param message 应答消息
     * @return 成功应答
     */
    public static <T> KnowledgeResponse<T> success(T data, String message) {
        KnowledgeResponse<T> response = new KnowledgeResponse<>();
        response.setCode(0);
        response.setData(data);
        response.setMessage(message);
        return response;
    }
    /**
     * 自定义参数应答
     *
     * @param data 应答数据
     * @param message 应答消息
     * @return 成功应答
     */
    public static <T> KnowledgeResponse<T> DiyCodeMsg(Integer code,T data, String message) {
        KnowledgeResponse<T> response = new KnowledgeResponse<>();
        response.setCode(code);
        response.setData(data);
        response.setMessage(message);
        return response;
    }

    /**
     * 失败应答
     *
     * @param code 应答编码
     * @param message 应答消息
     * @param exception 应答异常
     * @return 失败应答
     */
    public static <T> KnowledgeResponse<T> failure(Integer code, String message, String exception) {
        KnowledgeResponse<T> response = new KnowledgeResponse<>();
        response.setCode(code);
        response.setMessage(message);
        response.setException(exception);
        return response;
    }

}
