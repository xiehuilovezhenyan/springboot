package com.xiehui.aop;

import java.lang.reflect.Method;
import java.util.Objects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.xiehui.common.core.annotation.Access;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试拦截指定权限
 * @author xiehui
 *
 */
@Slf4j
@Aspect
@Component
public class TestAccessAspect {
	
	@Pointcut("@annotation(com.xiehui.common.core.annotation.Access)")
	public void allLogPointCut() {

	}
	
	@Around("allLogPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
        // 执行方法
        Object result = point.proceed();
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;

        MethodSignature signature = (MethodSignature)point.getSignature();
        Method method = signature.getMethod();
        

        Access access = method.getAnnotation(Access.class);
        if (Objects.isNull(access)) {
        	return result;
        }

        // 请求的方法名
        String className = point.getTarget().getClass().getName();
        String methodName = signature.getName();
        String methodStr = className + "." + methodName + "()";
        
        log.info("请求方法:{},权限:{}{}{}{},耗时:{}ms",methodStr,access.isLogin(),access.isRight(),access.isTimestamp(),access.isRight(),time);

        return result;
	}

}
