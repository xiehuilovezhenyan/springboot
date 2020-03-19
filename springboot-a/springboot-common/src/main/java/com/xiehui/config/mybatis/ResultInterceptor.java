package com.xiehui.config.mybatis;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.dianfeng.mp.tools.encryption.DesUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Intercepts({@Signature(type = Executor.class, method = "query",
    args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class ResultInterceptor implements Interceptor {
    private static String[] STR_COL_ARR = null;

    @SuppressWarnings({"rawtypes"})
    public Object intercept(Invocation invocation) throws Throwable {
        Object result = invocation.proceed();
        if (Objects.isNull(result)) {
            return result;
        }
        if (result instanceof ArrayList) {
            ArrayList resultList = (ArrayList)result;
            if (CollectionUtils.isEmpty(resultList)) {
                return result;
            }
            for (int i = 0; i < resultList.size(); i++) {
                Object obj = resultList.get(i);
                if (obj instanceof CommonEntity) {
                    CopyObjectValues(obj);
                } else if (obj instanceof String) {
                    // TODO -->暂时无法过滤
                }
            }
        }
        return result;
    }

    private static void CopyObjectValues(Object object)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        // 获取对象所有属性
        Field[] fields = object.getClass().getDeclaredFields();
        // 把所有的属性名放到一个数组中
        List<String> columList = new ArrayList<String>();
        for (int j = 0; j < fields.length; j++) {
            columList.add(fields[j].getName());
        }
        // 循环设置属性
        for (String colum : STR_COL_ARR) {
            if (columList.contains(colum)) {
                String columVlue = BeanUtils.getProperty(object, colum);
                if (StringUtils.isBlank(columVlue)) {
                    continue;
                }
                BeanUtils.setProperty(object, colum, DesUtil.getInstance().decrypt(columVlue));
            }
        }
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {
    	log.info("获取出参拦截器=================================================");
        String[] colunms = properties.getProperty("colums").split(",");
        log.info("获取出参拦截器=================================================" + JSON.toJSONString(colunms));
        ResultInterceptor.STR_COL_ARR = colunms;
    }

}
