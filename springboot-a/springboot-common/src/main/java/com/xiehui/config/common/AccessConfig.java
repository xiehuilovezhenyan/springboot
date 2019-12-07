package com.xiehui.config.common;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Access Interceptor Config 使用拦截器进行统一权限验证
 * 
 * @author xiehui
 **/
@Configuration
public class AccessConfig implements WebMvcConfigurer {

	/**
	 * 所有API URL的定义都走/api开始，所有推送都走/notify，所有推送的签名验证由相应的Controller完成。
	 * 例：/api/v1/customer /notify/v1/bank/{bankName}
	 * /notify/v1/withdraw/{channelName}/{channelId}
	 * 
	 * @param registry
	 */
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new AppLogInterceptor());
		registry.addInterceptor(accessAuthentication()).addPathPatterns("/api/**").excludePathPatterns("/notify/**")
				.excludePathPatterns("/api/v1/wechat/handle");
	}

	@Bean
	AccessAuthentication accessAuthentication() {
		return new AccessAuthentication();
	}

	@Bean
	public FilterRegistrationBean httpServletRequestReplacedRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new HttpServletRequestReplacedFilter());
		registration.addUrlPatterns("/*");
		registration.addInitParameter("paramName", "paramValue");
		registration.setName("httpServletRequestReplacedFilter");
		registration.setOrder(1);
		return registration;
	}
}