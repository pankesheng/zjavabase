package test.zcj.demo.designpattern.ioc;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseServlet extends HttpServlet {
	
	private static final long serialVersionUID = 3350407615905611229L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BeanFactory factory = (BeanFactory)getServletContext().getAttribute(InitBeanFactoryServlet.INIT_FACTORY_NAME);

		//利用反射，调用this对象中的相关的setters方法！
		Method[] methods = this.getClass().getMethods();
		for(Method m:methods){
			if(m.getName().startsWith("set")){
				String propertyName = m.getName().substring(3);//ArticleDao
				StringBuffer sb = new StringBuffer(propertyName);
				sb.replace(0, 1, (propertyName.charAt(0)+"").toLowerCase());
				propertyName = sb.toString();//articleDao
				Object bean = factory.getBean(propertyName);
				try {
					m.invoke(this, bean);//将依赖对象注入客户端==this.m(bean)
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
