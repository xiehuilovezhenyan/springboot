package com.xiehui.common.core.xss;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

import com.xiehui.common.core.annotation.BodyReaderHttpServletRequestWrapper;
import com.xiehui.common.util.RequestHelper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * XSS过滤器类
 *
 * @author xiehui
 */
public class XssFilter implements Filter {

	/**
	 * 白名单
	 */
	public static final List<String> WHITE_LIST = Arrays.asList("upExcel", "uploadUpperExcel");

	/**
	 * 初始化
	 *
	 * @param filterConfig 过滤器配置
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	/**
	 * 做过滤
	 *
	 * @param request  服务请求
	 * @param response 服务应答
	 * @param chain    过滤器链
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String path = RequestHelper.getURI(httpServletRequest);
		Boolean flag = Boolean.FALSE;
		for (String str : WHITE_LIST) {
			if (path.indexOf(str) >= 0) {
				flag = Boolean.TRUE;
			}
		}

		if (Boolean.TRUE.equals(flag)) {
			chain.doFilter(new XssHttpServletRequestWrapper(httpServletRequest), response);
		} else {
			if (httpServletRequest.getMethod().equalsIgnoreCase("GET")) {
				chain.doFilter(new XssHttpServletRequestWrapper(httpServletRequest), response);
			} else {
				ServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(httpServletRequest);
				chain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) requestWrapper), response);
			}
		}

	}

	/**
	 * 销毁
	 */
	@Override
	public void destroy() {
	}

}
