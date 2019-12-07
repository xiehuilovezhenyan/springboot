package com.xiehui.config.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.xiehui.common.util.IpAddressHelper;
import com.xiehui.common.util.WebUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppLogInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String requestId = WebUtils.getRequestId();
		String ip = "";
		try {
			ip = IpAddressHelper.getRemoteIpAddress(request);
		} catch (Exception e) {
			log.info("获取ip失败");
		}
		String uri = "";
		try {
			uri = request.getRequestURI();
		} catch (Exception e) {
			log.info("获取请求地址失败");
		}
		String para = WebUtils.getParaStr(request);

		log.info("access_log, " + ", requestId=" + requestId + ", ip=" + ip + ", uri=" + uri + ", para=(" + para
				+ "), handler=" + handler);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
	}
}
