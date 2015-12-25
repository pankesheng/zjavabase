package com.zcj.util.coder.java.query;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 查询语句的属性<br>
 * value支持：=、like、in、time
 * 
 * @author zouchongjin@sina.com
 * @data 2015年12月23日
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryColumnType {

	/** 支持：=、like、in、time */
	String[] value() default {};

}