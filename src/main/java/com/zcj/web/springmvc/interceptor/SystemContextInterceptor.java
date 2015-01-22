package com.zcj.web.springmvc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.zcj.web.context.SystemContext;

public class SystemContextInterceptor implements HandlerInterceptor {

	/** 分页的有效值 */
	private static final String REGEX = "[0-9]+";
	
	private String offsetKey = "offset";
	private String pagesizeKey = "pagesize";
	private int defaultPagesize = 15;
	private String contentType = "text/html;charset=utf-8";

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {
		// 最后执行，可用于释放资源；可以根据ex是否为null判断是否发生了异常，进行日志记录
		SystemContext.removeOffset();
		SystemContext.removePagesize();
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {
		// 生成视图之前执行；有机会修改ModelAndView

	}

	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {

		SystemContext.setOffset(getOffset(arg0));
		SystemContext.setPagesize(getPagesize(arg0));
		arg1.setContentType(contentType);
		
		return true;
	}

	private int getOffset(HttpServletRequest request) {
		String offset = request.getParameter(offsetKey);
		if (!StringUtils.isBlank(offset) && offset.matches(REGEX)) {
			return Integer.parseInt(offset);
		}
		return 0;
	}

	private int getPagesize(HttpServletRequest request) {
		String pagesize = request.getParameter(pagesizeKey);
		if (!StringUtils.isBlank(pagesize) && pagesize.matches(REGEX)) {
			return Integer.parseInt(pagesize);
		}
		return defaultPagesize;
	}

	public String getOffsetKey() {
		return offsetKey;
	}

	public void setOffsetKey(String offsetKey) {
		this.offsetKey = offsetKey;
	}

	public String getPagesizeKey() {
		return pagesizeKey;
	}

	public void setPagesizeKey(String pagesizeKey) {
		this.pagesizeKey = pagesizeKey;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public int getDefaultPagesize() {
		return defaultPagesize;
	}

	public void setDefaultPagesize(int defaultPagesize) {
		this.defaultPagesize = defaultPagesize;
	}

}
