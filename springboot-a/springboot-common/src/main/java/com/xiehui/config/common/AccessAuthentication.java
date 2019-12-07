package com.xiehui.config.common;

import java.io.UnsupportedEncodingException;
import java.util.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;
import com.xiehui.common.core.annotation.Access;
import com.xiehui.common.core.exception.CustomException;
import com.xiehui.common.core.exception.ExceptionCode;
import com.xiehui.common.core.xss.XssHttpServletRequestWrapper;
import com.xiehui.common.util.EncryptHelper;
import com.xiehui.common.util.HttpBodyHelper;
import com.xiehui.common.util.WebUtils;
import com.xiehui.constant.MethodConstant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AccessAuthentication extends HandlerInterceptorAdapter {

	/** 常量相关 */
	/**
	 * 登录令牌
	 */
	private static final String TOKEN = "token";

	private static final String TIMESTAMP = "timestamp";

	private static final long INTERVAL = 600L;

	private static final String NOISE = "0427fde5b5bd28dd983ee7531d98d9561981";

	private static final String SIGNATURE = "signature";

	private static final String loginCustomerId = "myCustomerId";

	private String loginToken;

	private String ApiTimestamp;

	private String md5Signature;

	private Long myCustomerId;

	// private CustomerService customerService;

	/**
	 * 处理前调用
	 *
	 * @param request  HTTP请求
	 * @param response HTTP应答
	 * @param handler  处理器
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 获取登录令牌
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Access methodAnnotation = handlerMethod.getMethodAnnotation(Access.class);
			if (Objects.isNull(methodAnnotation) || methodAnnotation.isTimestamp()) {
				if (StringUtils.isNotBlank(request.getHeader(TIMESTAMP))) {
					String timestamp = request.getHeader(TIMESTAMP);
					long tempTimestamp = 0L;
					try {
						tempTimestamp = Long.parseLong(timestamp);
					} catch (NumberFormatException e) {
						throw new CustomException(ExceptionCode.TIMESTAMP_ERROR, "时间戳格式错误", e);
					}
					if (Math.abs(System.currentTimeMillis() / 1000 - tempTimestamp) > INTERVAL) {
						throw new CustomException(ExceptionCode.TIMESTAMP_ERROR, "时间戳已过期");
					}
					this.ApiTimestamp = timestamp;
				} else {
					throw new CustomException(ExceptionCode.TIMESTAMP_ERROR, "时间戳不存在");
				}
			}
		}
		// token必须与sign一起使用，不可单独使用，否则很危险。sign可以单独使用
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Access methodAnnotation = handlerMethod.getMethodAnnotation(Access.class);
			if (Objects.isNull(methodAnnotation) || methodAnnotation.isSign()) {
				if (StringUtils.isNotBlank(request.getHeader(SIGNATURE))) {
					if (StringUtils.isNotBlank(request.getHeader(TOKEN))) {
						this.loginToken = request.getHeader(TOKEN);
						// 根据token获取customerId;
						Long cusId = null;
						if (Objects.isNull(cusId)) {
							throw new CustomException(ExceptionCode.TOKEN_INVALID, "Token已失效。");
						}
						this.myCustomerId = cusId;
						request.setAttribute(loginCustomerId, myCustomerId);
						// 设置我的标识
						request.getSession().setAttribute(loginCustomerId, this.myCustomerId);

					}
					String signature = request.getHeader(SIGNATURE);
					// 验证请求签名
					String tempSignature = "";
					if (MethodConstant.GET.equalsIgnoreCase(request.getMethod())
							|| MethodConstant.DELETE.equalsIgnoreCase(request.getMethod())) {
						tempSignature = genSignature(request, this.ApiTimestamp, this.loginToken);
						if (!signature.equalsIgnoreCase(tempSignature)) {
							log.error(String.format("签名错误(请求签名:%s,计算签名:%s)", signature, tempSignature));
							throw new CustomException(ExceptionCode.SIGNATURE_ERROR, "签名错误GET");
						}
						return super.preHandle(request, response, handler);
					}
					// ServletRequest requestWrapper = new
					// BodyReaderHttpServletRequestWrapper(request);
					tempSignature = genPostSignature(request, this.ApiTimestamp, this.loginToken);
					if (!signature.equalsIgnoreCase(tempSignature)) {
						log.error(String.format("签名错误(请求签名:%s,计算签名:%s)", signature, tempSignature));
						throw new CustomException(ExceptionCode.SIGNATURE_ERROR, "签名错误" + request.getMethod());
					}
					String requestLog = String.format("API请求人：%s，请求路径：%s，请求方式：%s，请求参数：%s", this.myCustomerId,
							request.getRequestURI(), request.getMethod(), WebUtils.getParaStr(request));
					log.info(requestLog);
					// 回调处理函数
					return super.preHandle(request, response, handler);
				} else {
					throw new CustomException(ExceptionCode.SIGNATURE_ERROR, "签名不存在");
				}
			}
		}
		// 所有都不需要验证，直接走
		return super.preHandle(request, response, handler);
	}

	/**
	 * 生成签名 签名规则如下： str=请求参数进行顺序排列再进行拼接 sign=md5(base64(timestamp)+token+私密+str)
	 *
	 * @param request HTTP请求
	 * @return 签名
	 */
	private String genSignature(HttpServletRequest request, String timestamp, String token) {
		// 初始化变量

		StringBuilder sb = new StringBuilder();
		List<String> nameList = new ArrayList();

		final Base64.Encoder encode = Base64.getEncoder();
		try {
			sb.append(encode.encodeToString(timestamp.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		if (StringUtils.isNotBlank(token)) {
			sb.append(token);
		}
		sb.append(NOISE);

		// 获取原始请求
		if (request instanceof XssHttpServletRequestWrapper) {
			ServletRequest tempRequest = ((XssHttpServletRequestWrapper) request).getRequest();
			if (tempRequest instanceof HttpServletRequest) {
				request = (HttpServletRequest) tempRequest;
			}
		}

		// 进行名称排序
		Enumeration<String> nameEnum = request.getParameterNames();
		while (nameEnum.hasMoreElements()) {
			nameList.add(nameEnum.nextElement());
		}
		Collections.sort(nameList);

		// 组装签名字符串
		for (String name : nameList) {
			if (!SIGNATURE.equals(name)) {
				sb.append(name);
				sb.append(request.getParameter(name));
			}
		}
		// 处理特殊字符
		this.md5Signature = sb.toString().replaceAll("\\[", "").replaceAll("]", "").replaceAll("\"", "");
		// log.info("=========待签名串========" + md5Signature);
		// 计算并返回签名
		return EncryptHelper.toMD5(this.md5Signature);
	}

	private String genPostSignature(ServletRequest requestWrapper, String timestamp, String token) {
		// 初始化变量
		StringBuilder sb = new StringBuilder();
		List<String> nameList = new ArrayList();

		final Base64.Encoder encode = Base64.getEncoder();
		try {
			sb.append(encode.encodeToString(timestamp.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		if (StringUtils.isNotBlank(token)) {
			sb.append(token);
		}
		sb.append(NOISE);

		String bodyContent = HttpBodyHelper.getBodyContent(requestWrapper);

		JSONObject jsonObject = JSONObject.parseObject(bodyContent);

		if (jsonObject != null && !jsonObject.isEmpty()) {
			nameList = new ArrayList<String>(jsonObject.keySet());

			Collections.sort(nameList);

			// 组装签名字符串
			for (String name : nameList) {
				if (!SIGNATURE.equals(name)) {
					sb.append(name);
					sb.append(jsonObject.get(name));
				}
			}
		}
		// 处理特殊字符
		this.md5Signature = sb.toString().replaceAll("\\[", "").replaceAll("]", "").replaceAll("\"", "");
		// log.info("=========待签名串========" + md5Signature);
		// 计算并返回签名
		return EncryptHelper.toMD5(this.md5Signature);
	}
}
