package com.zcj.web.springmvc.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.zcj.util.UtilString;
import com.zcj.web.context.SystemContext;

public class SystemContextInterceptor implements HandlerInterceptor {

	/** 分页的有效值 */
	private static final String REGEX = "[0-9]+";

	private String offsetKey = "offset";// 分页偏移量的参数名
	private String pagesizeKey = "pagesize";// 每页数量的参数名
	private int defaultPagesize = 15;// 默认每页的数量
	private String contentType = "text/html;charset=utf-8";

	private boolean iframeCrossDomain;// 是否支持iframe嵌入跨域

	private String siteKey = "site";// 全局请求有效的参数名
	private boolean siteBack;// 请求结束后是否把值传回视图
	private String defaultSite;// 全局参数的默认值

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {
		// 最后执行，可用于释放资源；可以根据ex是否为null判断是否发生了异常，进行日志记录

		SystemContext.removeOffset();
		SystemContext.removePagesize();
		SystemContext.removeSite();
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {
		// 生成视图之前执行；有机会修改ModelAndView

		if (siteBack && UtilString.isNotBlank(siteKey) && arg3 != null) {
			Map<String, Object> map = arg3.getModel();
			if (map != null) {
				map.put(siteKey, SystemContext.getSite());
			}
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {

		// System.out.println(arg0.getRequestURI()); // "/ssm/login.do"
		// System.out.println(arg0.getContextPath()); // "/ssm"
		// System.out.println(arg0.getSession().getServletContext().getRealPath("/"));
		// //
		// "E:\Kuaipan\Java_Developer\eclipseWorkspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp8\wtpwebapps\spring_springmvc_mybatis\"
		// System.out.println(arg0.getServletPath()); // "/login.do"

		SystemContext.setOffset(getOffset(arg0));
		SystemContext.setPagesize(getPagesize(arg0));
		SystemContext.setSite(getSite(arg0));
		arg1.setContentType(contentType);

		if (iframeCrossDomain) {
			arg1.setHeader("P3P", "CP='IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT'");
		}

		return true;
	}

	private String getSite(HttpServletRequest request) {
		if (StringUtils.isNotBlank(siteKey)) {
			String site = request.getParameter(siteKey);
			if (StringUtils.isNotBlank(site)) {
				return site.trim();
			}
		}
		if (StringUtils.isNotBlank(defaultSite)) {
			return defaultSite.trim();
		}
		return null;
	}

	private int getOffset(HttpServletRequest request) {
		if (StringUtils.isNotBlank(offsetKey)) {
			String[] offsetKeyArray = offsetKey.split(",");
			for (String offsetKey : offsetKeyArray) {
				String offset = request.getParameter(offsetKey);
				if (!StringUtils.isBlank(offset) && offset.matches(REGEX)) {
					return Integer.parseInt(offset);
				}
			}
		}
		return 0;
	}

	private int getPagesize(HttpServletRequest request) {
		if (StringUtils.isNotBlank(pagesizeKey)) {
			String[] pagesizeKeyArray = pagesizeKey.split(",");
			for (String pagesizeKey : pagesizeKeyArray) {
				String pagesize = request.getParameter(pagesizeKey);
				if (!StringUtils.isBlank(pagesize) && pagesize.matches(REGEX)) {
					return Integer.parseInt(pagesize);
				}
			}
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

	public boolean isIframeCrossDomain() {
		return iframeCrossDomain;
	}

	public void setIframeCrossDomain(boolean iframeCrossDomain) {
		this.iframeCrossDomain = iframeCrossDomain;
	}

	public int getDefaultPagesize() {
		return defaultPagesize;
	}

	public void setDefaultPagesize(int defaultPagesize) {
		this.defaultPagesize = defaultPagesize;
	}

	public boolean isSiteBack() {
		return siteBack;
	}

	public void setSiteBack(boolean siteBack) {
		this.siteBack = siteBack;
	}

	public String getSiteKey() {
		return siteKey;
	}

	public void setSiteKey(String siteKey) {
		this.siteKey = siteKey;
	}

	public String getDefaultSite() {
		return defaultSite;
	}

	public void setDefaultSite(String defaultSite) {
		this.defaultSite = defaultSite;
	}

}
