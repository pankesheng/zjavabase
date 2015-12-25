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
					for (String e : es) {
						qbuilderList.add(new QueryColumn(f.getName(), e));
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
