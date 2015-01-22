package com.zcj.util.freemarker;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.output.FileWriterWithEncoding;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Freemarker 工具类</br>
 *		FreemarkerUtil.getInstance(freemarkerConfig).htmlFile("www/ohga/index.ftl", root, "D:/index.jsp");</br>
 * @author zouchongjin@sina.com
 * @data 2014年6月12日
 */
public class FreemarkerUtil {

	private static final String ENCODE = "UTF-8";
	private static FreemarkerUtil fu = null;
	private Configuration conf = null;
	private FreeMarkerConfigurer freemarkerConfig = null;

	private FreemarkerUtil() {
	}

	public static synchronized FreemarkerUtil getInstance(FreeMarkerConfigurer freemarkerConfig) {
		if (fu == null) {
			fu = new FreemarkerUtil();
		}
		if (fu.getConf() == null || fu.getFreemarkerConfig() == null) {
			fu.setFreemarkerConfig(freemarkerConfig);
			Configuration c = fu.getFreemarkerConfig().getConfiguration();
			c.setObjectWrapper(new DefaultObjectWrapper());
			fu.setConf(c);
		}
		return fu;
	}

	public Configuration getConf() {
		return conf;
	}

	public void setConf(Configuration conf) {
		this.conf = conf;
	}
	
	/**
	 * 静态化成页面
	 * @param file 模板文件的相对路径
	 * @param datas
	 * @param outFile 保存的绝对路径
	 * @return
	 */
	public synchronized boolean htmlFile(Map<String, Object> datas, String file, String outFile) {
		FileWriterWithEncoding out = null;
		try {
			Template temp = conf.getTemplate(file, ENCODE);
			
			out = new FileWriterWithEncoding(new File(outFile), ENCODE);
			
			temp.process(datas, out);
			
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {					
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public FreeMarkerConfigurer getFreemarkerConfig() {
		return freemarkerConfig;
	}

	public void setFreemarkerConfig(FreeMarkerConfigurer freemarkerConfig) {
		this.freemarkerConfig = freemarkerConfig;
	}
	
}
