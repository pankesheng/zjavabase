package com.zcj.util.poi.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.zcj.util.filenameutils.FilenameUtils;

/**
 * 该类实现了基于模板的导出<br/>
 * 序号：标识sernums<br/>
 * 列表数据：标识datas<br/>
 * 标题等额外信息：标识#AAA（AAA为任意取），传入Map（key=AAA，value=标题名）<br/>
 * 样式：列表单元格标识styles，此时所有此列数据都使用该样式<br/>
 * 默认样式：任意地方标识defaultStyles，如果没有defaultStyles则使用datas单元格作为默认样式<br/>
 * 
 * @author ZCJ
 * @data 2013-7-19
 */
public class ExcelTemplate {

	private static ExcelTemplate et = new ExcelTemplate();

	private ExcelTemplate() {

	}

	/** 单例 */
	public static ExcelTemplate getInstance() {
		return et;
	}

	/** 数据行标识 */
	public final static String DATA_LINE = "datas";
	/** 默认样式标识 */
	public final static String DEFAULT_STYLE = "defaultStyles";
	/** 行样式标识 */
	public final static String STYLE = "styles";
	/** 插入序号样式标识 */
	public final static String SER_NUM = "sernums";

	private Workbook wb;
	private Sheet sheet;
	private Row curRow;// 当前行对象
	private int curRowIndex;// 当前行
	private int curColIndex;// 当前列
	private int initColIndex;// 数据的开始列
	private int initRowIndex;// 数据的开始行
	private int lastRowIndex;// 最后一行的数据
	private CellStyle defaultStyle;// 默认样式
	private float rowHeight;// 默认行高
	private Map<Integer, CellStyle> styles;// 存储一行中各列的样式
	private int serColIndex;// 序号的列

	/**
	 * 从 Classpath 路径下读取模板文件
	 * 
	 * @param path
	 *            例：/com/zcj/poi/template/default.xsl
	 * @return
	 */
	public ExcelTemplate readTemplateByClasspath(String path) {
		try {
			wb = WorkbookFactory.create(ExcelTemplate.class.getResourceAsStream(path));
			initTemplate();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
			throw new RuntimeException("模板格式错误！");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("模板不存在！");
		}
		return this;
	}

	/** 从某个路径下读取模板文件 */
	public ExcelTemplate readTemplateByPath(String path) {
		try {
			wb = WorkbookFactory.create(new File(path));
			initTemplate();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
			throw new RuntimeException("模板格式错误！");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("模板不存在！");
		}
		return this;
	}

	/**
	 * 将文件写到相应的路径下
	 * 
	 * @param filepath
	 *            例：D:/dome.xsl
	 */
	public void writeToFile(String filepath) {
		File dir = new File(FilenameUtils.getFullPathNoEndSeparator(filepath));
		if (!dir.exists()) {
			dir.mkdirs();
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(filepath);
			wb.write(fos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("写入的文件不存在");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("写入数据失败:" + e.getMessage());
		} finally {
			try {
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/** 将文件写到输出流中 */
	public void wirteToStream(OutputStream os) {
		try {
			wb.write(os);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("写入流失败:" + e.getMessage());
		}
	}

	private void initTemplate() {
		sheet = wb.getSheetAt(0);
		initConfigData();
		lastRowIndex = sheet.getLastRowNum();
		curRow = sheet.createRow(curRowIndex);
	}

	private void initConfigData() {
		boolean findData = false;
		boolean findSer = false;
		for (Row row : sheet) {
			if (findData)
				break;
			for (Cell c : row) {
				if (c.getCellType() != Cell.CELL_TYPE_STRING)
					continue;
				String str = c.getStringCellValue().trim();
				if (str.equals(SER_NUM)) {
					serColIndex = c.getColumnIndex();
					findSer = true;
				}
				if (str.equals(DATA_LINE)) {
					initColIndex = c.getColumnIndex();
					initRowIndex = row.getRowNum();
					curColIndex = initColIndex;
					curRowIndex = initRowIndex;
					findData = true;
					defaultStyle = c.getCellStyle();
					rowHeight = row.getHeightInPoints();
					initStyles();
					break;
				}
			}
		}
		if (!findSer) {
			initSer();
		}
	}

	public void createCell(String value) {
		Cell c = curRow.createCell(curColIndex);
		setCellStyle(c);
		c.setCellValue(value);
		curColIndex++;
	}

	public void createCell(int value) {
		Cell c = curRow.createCell(curColIndex);
		setCellStyle(c);
		c.setCellValue((int) value);
		curColIndex++;
	}

	public void createCell(Date value) {
		Cell c = curRow.createCell(curColIndex);
		setCellStyle(c);
		c.setCellValue(value);
		curColIndex++;
	}

	public void createCell(double value) {
		Cell c = curRow.createCell(curColIndex);
		setCellStyle(c);
		c.setCellValue(value);
		curColIndex++;
	}

	public void createCell(boolean value) {
		Cell c = curRow.createCell(curColIndex);
		setCellStyle(c);
		c.setCellValue(value);
		curColIndex++;
	}

	public void createCell(Calendar value) {
		Cell c = curRow.createCell(curColIndex);
		setCellStyle(c);
		c.setCellValue(value);
		curColIndex++;
	}

	/** 换行 */
	public void createNewRow() {
		if (lastRowIndex > curRowIndex && curRowIndex != initRowIndex) {
			sheet.shiftRows(curRowIndex, lastRowIndex, 1, true, true);
			lastRowIndex++;
		}
		curRow = sheet.createRow(curRowIndex);
		curRow.setHeightInPoints(rowHeight);
		curRowIndex++;
		curColIndex = initColIndex;
	}

	private void setCellStyle(Cell c) {
		if (styles.containsKey(curColIndex)) {
			c.setCellStyle(styles.get(curColIndex));
		} else {
			c.setCellStyle(defaultStyle);
		}
	}

	private void initStyles() {
		styles = new HashMap<Integer, CellStyle>();
		for (Row row : sheet) {
			for (Cell c : row) {
				if (c.getCellType() != Cell.CELL_TYPE_STRING)
					continue;
				String str = c.getStringCellValue().trim();
				if (str.equals(DEFAULT_STYLE)) {
					defaultStyle = c.getCellStyle();
				}
				if (str.equals(STYLE)) {
					styles.put(c.getColumnIndex(), c.getCellStyle());
				}
			}
		}
	}

	/** 通过Map中的值来替换#开头的值 */
	public void replaceFinalData(Map<String, String> datas) {
		if (datas == null)
			return;
		for (Row row : sheet) {
			for (Cell c : row) {
				if (c.getCellType() != Cell.CELL_TYPE_STRING)
					continue;
				String str = c.getStringCellValue().trim();
				if (str.startsWith("#")) {
					if (datas.containsKey(str.substring(1))) {
						c.setCellValue(datas.get(str.substring(1)));
					}
				}
			}
		}
	}

	private void initSer() {
		for (Row row : sheet) {
			for (Cell c : row) {
				if (c.getCellType() != Cell.CELL_TYPE_STRING)
					continue;
				String str = c.getStringCellValue().trim();
				if (str.equals(SER_NUM)) {
					serColIndex = c.getColumnIndex();
				}
			}
		}
	}

	/** 插入序号，会自动找相应的序号标示的位置完成插入 */
	public void insertSer() {
		int index = 1;
		Row row = null;
		Cell c = null;
		for (int i = initRowIndex; i < curRowIndex; i++) {
			row = sheet.getRow(i);
			c = row.createCell(serColIndex);
			setCellStyle(c);
			c.setCellValue(index++);
		}
	}

}
