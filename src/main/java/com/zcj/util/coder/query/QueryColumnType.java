package com.zcj.util.coder.query;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 查询语句的属性，用于标注在Bean的属性上<br>
 * value支持：=、like、in、time
 * 
 * @author zouchongjin@sina.com
 * @data 2015年12月23日
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryColumnType {

	/** 支持：=、like、in、time */
	String[] value() default {};

}