package com.xiehui.common.core.exception;

/**
 * kakaka异常类
 *
 * @author xiehui
 */
public class CustomException extends Exception {

	/**
     * 版本标识
     */
    private static final long serialVersionUID = 1L;

    /**
     * 异常编码
     */
    private ExceptionCode code = ExceptionCode.COMMON_CODE;

    /**
     * 异常附带数据
     */
    private Object returnData = null;

    /**
     * 构造函数
     */
    public CustomException() {
        super();
    }

    /**
     * 构造函数
     *
     * @param code 异常编码
     */
    public CustomException(ExceptionCode code) {
        super();
        this.code = code;
    }

    /**
     * 构造函数
     *
     * @param message 异常消息
     */
    public CustomException(String message) {
        super(message);
        // this.code=ExceptionCode.PARAMETER_ERROR;
    }

    /**
     * 构造函数
     *
     * @param code 异常编码
     * @param message 异常消息
     */
    public CustomException(ExceptionCode code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 构造函数
     *
     * @param cause 异常原因
     */
    public CustomException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数
     *
     * @param code 异常编码
     * @param cause 异常原因
     */
    public CustomException(ExceptionCode code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    /**
     * 构造函数
     *
     * @param message 异常消息
     * @param cause 异常原因
     */
    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造函数
     *
     * @param code 异常编码
     * @param message 异常消息
     * @param cause 异常原因
     */
    public CustomException(ExceptionCode code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public CustomException(ExceptionCode code, String message,Object returnData) {
        super(message);
        this.code = code;
        this.returnData = returnData;
    }
    
    /**
     * 获取异常编码
     *
     * @return 异常编码
     */
    public ExceptionCode getCode() {
        return code;
    }

    /**
     * 异常附带数据值
     * 
     * @return 数据值
     */
    public Object getReturnData() {
        return returnData;
    }

}
