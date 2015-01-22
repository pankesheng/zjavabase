package com.zcj.web.springmvc.interceptor;

import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.zcj.web.context.BasicWebContext;
import com.zcj.web.dto.ServiceResult;

public class LoginInterceptor implements HandlerInterceptor {

	private String loginUrl;
	private List<String> excludeUrls;
	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {
		// 最后执行，可用于释放资源；可以根据ex是否为null判断是否发生了异常，进行日志记录

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {
		// 生成视图之前执行；有机会修改ModelAndView

//		Map<String, Object> map = arg3.getModel();
//		map.put("test", "append something");
	}

	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
		
//		System.out.println(arg0.getRequestURI());	// "/ssm/login.do"
//		System.out.println(arg0.getContextPath());	// "/ssm"
//		System.out.println(arg0.getSession().getServletContext().getRealPath("/"));	// "E:\Kuaipan\Java_Developer\eclipseWorkspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp8\wtpwebapps\spring_springmvc_mybatis\"
//		System.out.println(arg0.getServletPath());	// "/login.do"

		if (exclude(arg0)) {
			return true;
		}
		
		if (BasicWebContext.getLoginUser(arg0) == null) {
			boolean ajax = "XMLHttpRequest".equalsIgnoreCase(arg0.getHeader("X-Requested-With"));
			if (ajax) {
				arg1.getWriter().write(ServiceResult.initErrorLoginJson());
			} else {
				arg1.sendRedirect(initLoginUrl(arg0));
			}
			return false;
		}
		return true;
	}
	
	private boolean exclude(HttpServletRequest arg0) {
		if (excludeUrls != null) {
			for (String exc : excludeUrls) {
				if (Pattern.matches(arg0.getContextPath()+exc, arg0.getRequestURI())) {
					return true;
				}
			}
		}
		return false;
	}
	
	private String initLoginUrl(HttpServletRequest arg0) {
		StringBuilder sb = new StringBuilder();
		sb.append(arg0.getContextPath());
		if (StringUtils.isNotBlank(loginUrl)) {
			sb.append(loginUrl);
		}
		return sb.toString();
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public List<String> getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(List<String> excludeUrls) {
		this.excludeUrls = excludeUrls;
	}

}
