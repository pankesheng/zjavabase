package com.zcj.util.coder.java;

import java.lang.reflect.Field;
import java.util.List;

import com.zcj.util.coder.java.query.QueryColumn;

public class JavaCode {

	private String packageName;
	private String moduleName;
	private String className;
	private String tableName;
	private List<Field> fieldList;
	private List<QueryColumn> qbuilderList;

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<Field> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<Field> fieldList) {
		this.fieldList = fieldList;
	}

	public List<QueryColumn> getQbuilderList() {
		return qbuilderList;
	}

	public void setQbuilderList(List<QueryColumn> qbuilderList) {
		this.qbuilderList = qbuilderList;
	}

}
