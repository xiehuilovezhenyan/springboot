package com.xiehui.config.common;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

import com.xiehui.common.core.annotation.BodyReaderHttpServletRequestWrapper;

import java.io.IOException;

public class HttpServletRequestReplacedFilter implements Filter {
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		ServletRequest requestWrapper = null;
		if (servletRequest instanceof HttpServletRequest) {
			if (!((HttpServletRequest) servletRequest).getMethod().equalsIgnoreCase("GET")) {
				requestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) servletRequest);
			}
		}
		// 获取请求中的流如何，将取出来的字符串，再次转换成流，然后把它放入到新request对象中。
		// 在chain.doFiler方法中传递新的request对象
		if (requestWrapper == null) {
			filterChain.doFilter(servletRequest, servletResponse);
		} else {
			filterChain.doFilter(requestWrapper, servletResponse);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}
}
