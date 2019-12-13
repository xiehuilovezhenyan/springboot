package com.xiehui.aop;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class PrintLogAspect {

	@Pointcut("execution(* com.xiehui.controller.*.*(..)) )")
	public void allLogPointCut() {

	}

	@Around("allLogPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		Object args[] = point.getArgs();
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes sra = (ServletRequestAttributes) ra;
		HttpServletRequest request = sra.getRequest();
		Enumeration<String> headerNames = request.getHeaderNames();
		Map<String, Object> headerMap = new HashMap<>(10);
		do {
			String header = headerNames.nextElement();
			headerMap.put(header, request.getHeader(header));
		} while (headerNames.hasMoreElements());
		
		//打印请求数据
		log.info(
				"\n" + "请求地址  >>>  {}\n" + "请求方法  >>>  {}\n" + "请求参数  >>>  {}\n" + "请求来源  >>>  {}\n" + "内容类型  >>>  {}\n"
						+ "用户标识  >>>  {}\n" + "用户名称  >>>  {}\n" + "请求头部  >>>  {}\n",
				request.getRequestURI(), request.getMethod(), StringUtils.join(args, ";"), request.getRemoteAddr(),
				request.getContentType(), (Long) request.getSession().getAttribute("adminId"),
				(String) request.getSession().getAttribute("adminName"), JSON.toJSONString(headerMap));

		// 执行方法
		Object result = point.proceed();

		//打印返回数据
		log.info("返回数据:\n{}", JSON.toJSONString(result, SerializerFeature.WriteMapNullValue));
		return result;
	}

}
