package com.xiehui.config.common;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiehui.common.core.exception.CustomException;
import com.xiehui.common.core.exception.ExceptionCode;
import com.xiehui.common.core.response.CustomResponse;
import com.xiehui.constant.RunMode;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

	/**
	 * 登录令牌
	 */
	public static final String TOKEN = "token";

	/** 属性相关 */
	/**
	 * 运行模式
	 */
	@Value("${mode}")
	private String mode;

	/**
	 * 处理参数验证异常
	 *
	 * @param exception 异常信息
	 * @return knowledge应答
	 */
	@ResponseBody
	@ExceptionHandler({ BindException.class, MethodArgumentNotValidException.class })
	public CustomResponse handleParameterValidException(Exception exception, HttpServletRequest request) {
		// 打印日志提示
		if (Objects.nonNull(request)) {
			log.error("#ParameterValidException request URI:{}, referer:{}, token:{}", request.getRequestURI(),
					request.getHeader("Referer"), request.getHeader(TOKEN));
		}

		if (RunMode.ONLINE.equals(mode)) {
			log.error("参数验证异常:" + exception.getMessage());
		} else {
			log.error("参数验证异常:", exception);
		}

		// 获取绑定结果
		BindingResult bindingResult = null;
		if (exception instanceof BindException) {
			bindingResult = ((BindException) exception).getBindingResult();
		} else if (exception instanceof MethodArgumentNotValidException) {
			bindingResult = ((MethodArgumentNotValidException) exception).getBindingResult();
		}

		StringBuilder sb = new StringBuilder();
		if (bindingResult != null && CollectionUtils.isNotEmpty(bindingResult.getAllErrors())) {
			boolean isFirst = true;
			for (ObjectError error : bindingResult.getAllErrors()) {
				if (isFirst) {
					isFirst = false;
				} else {
					sb.append(",");
				}
				if (error instanceof FieldError) {
					FieldError fieldError = (FieldError) error;
					sb.append(fieldError.getField());
					sb.append(fieldError.getDefaultMessage());
				} else {
					sb.append(error.getObjectName());
					sb.append(error.getDefaultMessage());
				}
			}
		}

		// 返回参数错误
		CustomResponse response = new CustomResponse();
		response.setCode(ExceptionCode.PARAMETER_ERROR.getValue());
		response.setMessage("参数验证异常");
		response.setException(sb.toString());
		return response;
	}

	/**
	 * 处理未期望的服务异常
	 *
	 * @param throwable 抛出异常
	 * @return knowledge应答
	 */
	@ResponseBody
	@ExceptionHandler(Throwable.class)
	public CustomResponse handleUnexpectedServerError(Throwable throwable, HttpServletRequest request) {
		// 打印日志提示
		if (Objects.nonNull(request)) {
			log.error("#UnexpectedServerError request URI:{}, referer:{}, token:{}", request.getRequestURI(),
					request.getHeader("Referer"), request.getHeader(TOKEN));
		}

		if (throwable instanceof CustomException) {
			if (RunMode.ONLINE.equals(mode)) {
				log.error("接口服务异常:" + throwable.getMessage());
			} else {
				log.error("接口服务异常:", throwable);
			}
		} else {
			if (RunMode.ONLINE.equals(mode)) {
				log.error("接口服务异常:", throwable);
			} else {
				log.error("接口服务异常:", throwable);
			}
		}

		// 处理异常信息
		CustomResponse<Object> response = new CustomResponse<>();
		if (throwable instanceof CustomException) {
			CustomException exception = (CustomException) throwable;
			response.setCode(exception.getCode().getValue());
			response.setMessage(exception.getMessage());
			if (Objects.nonNull(exception.getReturnData())) {
				response.setData(exception.getReturnData().toString());
			}
		} else {
			response.setCode(ExceptionCode.PARAMETER_ERROR.getValue());
			response.setMessage("数据请求失败");
		}

		// 设置默认消息
		if (response.getMessage() == null) {
			response.setMessage("数据请求失败");
		}

		// 返回应答数据
		return response;
	}

}
