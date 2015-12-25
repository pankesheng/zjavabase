package com.zcj.util.coder.database;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class DatabaseBuilder {

	public static Database initDatabase(Class<?>[] carray, String databaseType) {
		List<Table> tables = new ArrayList<Table>();
		for (Class<?> c : carray) {
			tables.add(initTable(c, databaseType));
		}
		String dname = carray[0].getName().split("\\.")[2];

		Database result = new Database();
		result.setName(dname);
		result.setTables(tables);
		return result;
	}

	private static Table initTable(Class<?> c, String databaseType) {
		String tableName = "t_" + StringUtils.lowerCase(c.getSimpleName());
		List<TableColumn> columns = new ArrayList<TableColumn>();
		Field[] fs = c.getDeclaredFields();
		for (Field f : fs) {
			if (valid(f)) {
				String[] typeAndLength = initDefaultTypeAndLengthByField(databaseType, f);
				String tType = typeAndLength[0];
				Integer tLength = typeAndLength[1] == null ? null : Integer.valueOf(typeAndLength[1]);
				Boolean tNullable = true;
				if (f.isAnnotationPresent(TableColumnType.class)) {
					TableColumnType sqlType = f.getAnnotation(TableColumnType.class);
					if (sqlType.length() != 0) {
						tLength = sqlType.length();
					}
					if (!sqlType.nullable()) {
						tNullable = false;
					}
				}
				columns.add(new TableColumn(f.getName(), tType, tLength, tNullable));
			}
		}
		return new Table(tableName, columns);
	}

	private static boolean valid(Field f) {
		return f != null && f.getModifiers() < 8 && !f.getName().startsWith("show_");
	}

	private static String[] initDefaultTypeAndLengthByField(String databaseType, Field ff) {
		if (Database.TYPE_MYSQL.equals(databaseType)) {
			if ("class java.lang.Integer".equals(ff.getType().toString())) {
				return new String[] { "int", "11" };
			} else if ("class java.lang.Long".equals(ff.getType().toString())) {
				return new String[] { "bigint", "20" };
			} else if ("class java.lang.String".equals(ff.getType().toString())) {
				return new String[] { "varchar", "100" };
			} else if ("class java.util.Date".equals(ff.getType().toString())) {
				return new String[] { "datetime", null };
			} else if ("class java.lang.Float".equals(ff.getType().toString())) {
				return new String[] { "float", null };
			}
		} else if (Database.TYPE_SQLSERVER.equals(databaseType)) {
			if ("class java.lang.Integer".equals(ff.getType().toString())) {
				return new String[] { "int", null };
			} else if ("class java.lang.Long".equals(ff.getType().toString())) {
				return new String[] { "bigint", null };
			} else if ("class java.lang.String".equals(ff.getType().toString())) {
				return new String[] { "nvarchar", "100" };
			} else if ("class java.util.Date".equals(ff.getType().toString())) {
				return new String[] { "datetime", null };
			}
		}
		return new String[] { null, null };
	}

}
