package com.xiehui.config.mybatis;

import java.util.Properties;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xiehui.plugin.shard.ShardTablePlugin;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class MyBatisInterceptorConfig {

	@Bean
	public String mybatisInterceptor(SqlSessionFactory sqlSessionFactory) {
		// 拦截器
		// 入参拦截器
		ParameterInterceptor parameterInterceptor = new ParameterInterceptor();
		// 出参拦截器
		ResultInterceptor resultInterceptor = new ResultInterceptor();
		// 分表拦截器
		ShardTablePlugin shardTablePlugin = new ShardTablePlugin();

		// 入参
		Properties properties = new Properties();
		properties.setProperty("tables",
				"T_CUSTOMER,T_CUSTOMER_BANKCARD,T_CUSTOMER_WITHDRAW_ORDER,T_BANK_CARD_APPLY_ORDER,T_INSURANCE_APPLY_ORDER,T_CAR_INSURANCE_APPLY_ORDER,T_LOAN_APPLY_ORDER,T_REPAYMENT_FEEDBACK,T_BANK_CARD_OFA_ORDER,T_BANK_FEEDBACK,T_LOAN_FEEDBACK,T_INSURANCE_FEEDBACK,T_CREDITCARD_APPLICANT,T_LOAN_APPLYER,T_INSURANCE_APPLYER,T_CAR_INSURANCE_APPLYER");
		properties.setProperty("colums", "idNumber,idCard,bankNumber,insurantIdCard");

		// 设置参数
		parameterInterceptor.setProperties(properties);
		resultInterceptor.setProperties(properties);

		// 添加拦截器
		// sqlSessionFactory.getConfiguration().addInterceptor(parameterInterceptor);
		// sqlSessionFactory.getConfiguration().addInterceptor(resultInterceptor);
		sqlSessionFactory.getConfiguration().addInterceptor(shardTablePlugin);

		log.info("一共初始化{}个mybatis拦截器", sqlSessionFactory.getConfiguration().getInterceptors().size());

		// 返回
		return "interceptor";

	}

}
