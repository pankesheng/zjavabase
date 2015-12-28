package com.zcj.util.coder.java.query;

/**
 * 查询语句的属性
 * 
 * @author zouchongjin@sina.com
 * @data 2015年12月23日
 */
public class QueryColumn {

	private String fieldName;// 对应字段名称
	private String oper;// 查询操作符，支持：=、like、in、time
	private boolean listQuery = false;// 是否作为list页面的查询条件（默认否）

	public QueryColumn() {
		super();
	}

	public QueryColumn(String fieldName, String oper, boolean listQuery) {
		super();
		this.fieldName = fieldName;
		this.oper = oper;
		this.listQuery = listQuery;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public boolean isListQuery() {
		return listQuery;
	}

	public void setListQuery(boolean listQuery) {
		this.listQuery = listQuery;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

}
