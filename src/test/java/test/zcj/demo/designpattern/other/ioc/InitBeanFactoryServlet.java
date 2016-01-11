package test.zcj.demo.designpattern.other.ioc;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class InitBeanFactoryServlet extends HttpServlet {
	
	private static final long serialVersionUID = -3179225366169232285L;
	
	public static final String INIT_FACTORY_NAME = "_my_bean_factory";
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		BeanFactory factory = null;
		String configLocation = config.getInitParameter("configLocation");
		factory = new PropertiesBeanFactory(configLocation);
		getServletContext().setAttribute(INIT_FACTORY_NAME, factory);
	}
}
