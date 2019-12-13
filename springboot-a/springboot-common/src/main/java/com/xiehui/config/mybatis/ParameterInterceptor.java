package com.xiehui.config.mybatis;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.springframework.stereotype.Component;

import com.dianfeng.mp.tools.encryption.DesUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 通用参数拦截器（敏感信息加密）
 *
 * @author xiehui
 *
 */
@Slf4j
@Component
@Intercepts({ @Signature(type = ParameterHandler.class, method = "setParameters", args = { PreparedStatement.class }) })
public class ParameterInterceptor implements Interceptor {
	private static String[] STR_TAB_ARR = null;
	private static String[] STR_COL_ARR = null;

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		ParameterHandler parameterHandler = (ParameterHandler) invocation.getTarget();
		PreparedStatement ps = (PreparedStatement) invocation.getArgs()[0];
		// 反射获取 BoundSql 对象，此对象包含生成的sql和sql的参数map映射
		Field boundSqlField = parameterHandler.getClass().getDeclaredField("boundSql");
		boundSqlField.setAccessible(true);
		BoundSql boundSql = (BoundSql) boundSqlField.get(parameterHandler);
		// 【敏感信息加密】表是否存在SQL语句中
		boolean hasTab = checkTable(boundSql.getSql());
		if (!hasTab) {
			return invocation.proceed();
		}
		Field parameterField = parameterHandler.getClass().getDeclaredField("parameterObject");
		Field mappedStatement = parameterHandler.getClass().getDeclaredField("mappedStatement");
		parameterField.setAccessible(true);
		mappedStatement.setAccessible(true);
		Object parameterObject = parameterField.get(parameterHandler);
		MappedStatement ms = (MappedStatement) mappedStatement.get(parameterHandler);

		parameterObject = processColumn(boundSql.getParameterObject(), boundSql.getParameterMappings(),
				ms.getSqlCommandType().name());
		parameterField.set(parameterHandler, parameterObject);
		parameterHandler.setParameters(ps);

		return null;
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		log.info("===============================================================================================");
		String[] tables = properties.getProperty("tables").split(",");
		String[] colunms = properties.getProperty("colums").split(",");
		log.info("初始化tables:{},colunms:{}", tables, colunms);
		ParameterInterceptor.STR_TAB_ARR = tables;
		ParameterInterceptor.STR_COL_ARR = colunms;

	}

	/**
	 * 加密增删改查回调参数（回调）
	 *
	 * @param paramObj          要加密的参数对象
	 * @param paramNames        字段bean对应属性名
	 * @param parameterMappings
	 * @param sqlType
	 * @return
	 * @throws Throwable
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object processColumn(Object paramObj, List<ParameterMapping> parameterMappings, String sqlType)
			throws Throwable {
		if (paramObj == null) {
			return paramObj;
		}
		if (paramObj instanceof Map) {
			Map<String, Object> paramMap = (Map<String, Object>) paramObj;
			for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
				String key = entry.getKey();
				Object values = entry.getValue();
				if (Objects.isNull(values)) {
					continue;
				}
				if (key.indexOf("param") >= 0) {
					continue;
				}
				if (values instanceof ArrayList) {
					ArrayList resultList = (ArrayList) values;
					if (CollectionUtils.isEmpty(resultList)) {
						continue;
					}
					for (int i = 0; i < resultList.size(); i++) {
						Object obj = resultList.get(i);
						if (obj instanceof CommonEntity) {
							CopyObjectValues(obj);
						}
					}
				} else if (values instanceof CommonEntity) {
					// 获取对象所有属性
					CopyObjectValues(values);
				} else if (values instanceof String) {
					for (String colum : STR_COL_ARR) {
						if (key.equals(colum)) {
							String encrptData = DesUtil.getInstance().encrypt(values.toString());
							if (StringUtils.isBlank(encrptData)) {
								continue;
							}
							paramMap.put(key, encrptData);
						}
					}
				}
			}
			return paramMap;
		} else if (paramObj instanceof CommonEntity) {
			// 获取对象所有属性
			CopyObjectValues(paramObj);
		}

		return paramObj;
	}

	private static void CopyObjectValues(Object object)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Field[] fields = object.getClass().getDeclaredFields();
		// 把所有的属性名放到一个数组中
		List<String> columList = new ArrayList<String>();
		for (int i = 0; i < fields.length; i++) {
			columList.add(fields[i].getName());
		}
		// 循环设置属性
		for (String colum : STR_COL_ARR) {
			if (columList.contains(colum)) {
				String columVlue = BeanUtils.getProperty(object, colum);
				if (StringUtils.isBlank(columVlue)) {
					continue;
				}
				String encryptData = DesUtil.getInstance().encrypt(columVlue);
				if (StringUtils.isBlank(encryptData)) {
					continue;
				}
				BeanUtils.setProperty(object, colum, encryptData);
			}
		}
	}

	private static boolean checkTable(String sql) {
		sql = sql.toUpperCase();
		for (String tab : STR_TAB_ARR) {
			if (sql.indexOf(tab) >= 0)
				return true;
		}
		return false;
	}

}
