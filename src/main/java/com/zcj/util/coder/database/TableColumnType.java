package com.zcj.util.coder.database;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 数据库表字段的属性，用于标注在Bean的属性上
 * 
 * @author zouchongjin@sina.com
 * @data 2015年12月23日
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TableColumnType {

	/** 字段长度，0表示不需要设置长度 */
	int length() default 0;

	/** 字段是否允许为空 */
	boolean nullable() default true;

}