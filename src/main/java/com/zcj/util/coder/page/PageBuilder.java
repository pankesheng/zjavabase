package com.zcj.util.coder.page;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.zcj.util.UtilString;

public class PageBuilder {

	public static PageBean initPage(Class<?> c) {
		if (c.isAnnotationPresent(PageType.class)) {
			PageBean p = new PageBean();

			PageType pageType = c.getAnnotation(PageType.class);
			p.setPname(pageType.pname());
			p.setName(pageType.name());
			p.setClassName(c.getSimpleName());
			p.setModuleName(c.getName().split("\\.")[4]);
			p.setDialog(pageType.dialog());
			p.setUeditor(pageType.ueditor());
			p.setUpload(pageType.upload());
			p.setExport(pageType.export());

			List<PageColumnBean> columnList = new ArrayList<PageColumnBean>();
			Field[] fs = c.getDeclaredFields();
			for (Field f : fs) {
				if (f.isAnnotationPresent(PageColumnType.class)) {
					PageColumnBean b = new PageColumnBean();
					PageColumnType ct = f.getAnnotation(PageColumnType.class);
					if (UtilString.isNotBlank(ct.check())) {
						b.setCheck(ct.check());
					}
					if (UtilString.isNotBlank(ct.defaultValue())) {
						b.setDefaultValue(ct.defaultValue());
					}
					b.setFieldName(f.getName());
					b.setGrid(ct.grid());
					b.setType(ct.type());
					Map<String, String> kvMap = new LinkedHashMap<String, String>();
					String[] kvArray = ct.keyValue();
					if (kvArray != null && kvArray.length > 0) {
						for (String kvString : kvArray) {
							String[] kv2Array = kvString.split("=");
							kvMap.put(kv2Array[0], kv2Array[1]);
						}
						b.setType("select");
					}
					b.setKeyValue(kvMap);
					if (ct.maxlength() != 0) {
						b.setMaxlength(ct.maxlength());
					}
					b.setModify(ct.modify());
					b.setMust(ct.must());
					b.setName(ct.name());
					b.setSearch(ct.search());
					columnList.add(b);
				}
			}
			p.setColumnList(columnList);
			return p;
		}
		return null;
	}

}
