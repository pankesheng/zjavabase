package com.zcj.util;

import java.text.DecimalFormat;

import org.apache.commons.lang3.StringUtils;

/** 类型转换和格式转换 */
public class UtilConvert {

	public static Long[] string2Long(String[] value) {
		if (value == null) {return null;}
		Long[] result = new Long[value.length];
		for (int i = 0; i < value.length; i++) {
			result[i] = Long.valueOf(value[i]);
		}
		return result;
	}
	
	public static Double[] string2Double(String[] value) {
		if (value == null) {return null;}
		Double[] result = new Double[value.length];
		for (int i = 0; i < value.length; i++) {
			result[i] = Double.valueOf(value[i]);
		}
		return result;
	}
	
	public static Integer[] string2Integer(String[] value) {
		if (value == null) {return null;}
		Integer[] result = new Integer[value.length];
		for (int i = 0; i < value.length; i++) {
			result[i] = Integer.valueOf(value[i]);
		}
		return result;
	}
	
	public static String arrayToString(Object[] arrays) {
		return arrayToString(arrays, ",");
	}
	
	public static String arrayToString(Object[] arrays, String splite) {
		if (StringUtils.isBlank(splite)) {
			splite = ",";
		}
		if (arrays == null || arrays.length == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		boolean isFirst = true;
		for (Object a : arrays) {
			if (isFirst) {
				sb.append(a);
			} else {
				sb.append(splite);
				sb.append(a);
			}
			isFirst = false;
		}
		return sb.toString();
	}
	
	/** 格式化Double的值 */
	public static String double2String(Double value, String pattern) {
		if (value == null || StringUtils.isBlank(pattern)) {return null;}
		return new DecimalFormat(pattern).format(value);
	}
	
	/** Double的值保留两位小数点 */
	public static String double2String(Double value) {
		return double2String(value, "0.00");
	}
	
	/** Double的值保留两位小数点 */
	public static Double formatDouble(Double value) {
		String result = double2String(value);
		if (StringUtils.isBlank(result)) {return null;}
		return Double.valueOf(result);
	}
	
}
