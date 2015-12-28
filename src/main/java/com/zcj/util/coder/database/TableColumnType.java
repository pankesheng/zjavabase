package com.zcj.util.coder.database;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据库表字段的属性
 * 
 * @author zouchongjin@sina.com
 * @data 2015年12月23日
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableColumnType {
	
	/** 字段类型，可取值：text。（默认空串[按JAVA类型自动生成]） */
	String type() default "";

	/** 字段长度，0表示不需要设置长度 */
	int length() default 0;

	/** 字段是否允许为空 */
	boolean nullable() default true;

}