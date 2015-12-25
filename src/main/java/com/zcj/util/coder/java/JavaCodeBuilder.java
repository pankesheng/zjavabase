package com.zcj.util.coder.java;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.zcj.util.coder.java.query.QueryBuilder;

public class JavaCodeBuilder {

	public static JavaCode initJavaCode(Class<?> className) {
		String[] allName = className.getName().split("\\.");
		if (allName.length != 6) {// com.thanone.pm2.entity.us.User
			return null;
		}

		List<Field> fslist = new ArrayList<Field>();
		Field[] fs = className.getDeclaredFields();
		for (Field f : fs) {
			if (valid(f)) {
				fslist.add(f);
			}
		}

		JavaCode code = new JavaCode();
		code.setPackageName(allName[0] + "." + allName[1] + "." + allName[2]);// com.thanone.pm2
		code.setModuleName(allName[4]);// us
		code.setClassName(allName[5]);// User
		code.setTableName("t_" + StringUtils.lowerCase(allName[5]));// t_user
		code.setFieldList(fslist);
		code.setQbuilderList(QueryBuilder.initQueryColumnList(className));

		return code;
	}

	private static boolean valid(Field f) {
		return f != null && f.getModifiers() < 8 && !f.getName().startsWith("show_");
	}

}
