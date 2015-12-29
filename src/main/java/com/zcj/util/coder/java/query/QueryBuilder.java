package com.zcj.util.coder.java.query;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class QueryBuilder {

	public static List<QueryColumn> initQueryColumnList(Class<?> className) {
		List<QueryColumn> qbuilderList = new ArrayList<QueryColumn>();

		Field[] fs = className.getDeclaredFields();
		for (Field f : fs) {
			if (valid(f)) {
				if (f.isAnnotationPresent(QueryColumnType.class)) {
					QueryColumnType qtype = f.getAnnotation(QueryColumnType.class);
					String[] es = qtype.value();
					String fieldType = "";
					if ("class java.lang.Integer".equals(f.getType().toString())) {
						fieldType = "Integer";
					} else if ("class java.lang.Long".equals(f.getType().toString())) {
						fieldType = "Long";
					} else if ("class java.lang.String".equals(f.getType().toString())) {
						fieldType = "String";
					} else if ("class java.util.Date".equals(f.getType().toString())) {
						fieldType = "Date";
					} else if ("class java.lang.Float".equals(f.getType().toString())) {
						fieldType = "Float";
					}
					for (String e : es) {
						qbuilderList.add(new QueryColumn(f.getName(), fieldType, e, qtype.listQuery()));
					}
				}
			}
		}

		return qbuilderList;
	}

	private static boolean valid(Field f) {
		return f != null && f.getModifiers() < 8 && !f.getName().startsWith("show_");
	}

}
