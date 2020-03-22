package com.xiehui.config.mybatis;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.pagehelper.PageHelper;

import lombok.extern.slf4j.Slf4j;

/**
 * 分页插件初始化
 * 
 * @author xiehui
 *
 */

@Slf4j
@Configuration
public class PageHelperConfig {

	@Bean
	public PageHelper pageHelper() {
		log.info("初始化mybatis分页插件........................");
		PageHelper pageHelper = new PageHelper();
		Properties properties = new Properties();
		properties.setProperty("dialect", "mysql");
		properties.setProperty("offsetAsPageNum", "false");
		properties.setProperty("rowBoundsWithCount", "false");
		properties.setProperty("pageSizeZero", "true");
		properties.setProperty("reasonable", "false");
		properties.setProperty("supportMethodsArguments", "false");
		properties.setProperty("returnPageInfo", "none");

		pageHelper.setProperties(properties);
		return pageHelper;
	}

}
