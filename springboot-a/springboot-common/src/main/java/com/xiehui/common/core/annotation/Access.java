package com.xiehui.common.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Access {
	boolean isLogin() default false;

	boolean isSign() default false;

	boolean isTimestamp() default false;

	boolean isRight() default false;

}
