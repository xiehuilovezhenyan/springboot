package com.xiehui.common.core.xss;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * XSS的HTTP服务请求包装类
 *
 * @author xiehui
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

	/**
	 * 构造函数
	 *
	 * @param request HTTP服务请求
	 */
	public XssHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	/**
	 * 获取头值
	 *
	 * @param name 头名称
	 * @return 头值
	 */
	@Override
	public String getHeader(String name) {
		// 转化并返回
		return StringEscapeUtils.escapeHtml3(super.getHeader(name));
	}

	/**
	 * 获取查询字符串
	 *
	 * @return 查询字符串
	 */
	@Override
	public String getQueryString() {
		// 转化并返回
		return StringEscapeUtils.escapeHtml3(super.getQueryString());
	}

	/**
	 * 获取参数值
	 *
	 * @param name 参数名称
	 * @return 参数值
	 */
	@Override
	public String getParameter(String name) {
		// 转化并返回
		return StringEscapeUtils.escapeHtml3(super.getParameter(name));
	}

	/**
	 * 获取参数值数组
	 *
	 * @param name 参数名称
	 * @return 参数值数组
	 */
	@Override
	public String[] getParameterValues(String name) {
		// 获取值数组
		String[] values = super.getParameterValues(name);
		if (values == null) {
			return null;
		}

		// 转化值数组
		int length = values.length;
		String[] escapseValues = new String[length];
		for (int i = 0; i < length; i++) {
			escapseValues[i] = StringEscapeUtils.escapeHtml3(values[i]);
		}

		// 返回值数组
		return escapseValues;
	}

}