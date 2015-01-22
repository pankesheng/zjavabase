package test.zcj.demo.designpattern.proxy;

import java.lang.reflect.Method;
import java.util.Random;

public class MyProxy {

	public class TimeHandler implements InvocationHandler{
		private Object target;

		public TimeHandler(Object target) {
			this.target = target;
		}

		@Override
		public void invoke(Object o, Method m) {
			System.out.println(o.getClass().getName());
			System.out.println("starttime:" + System.currentTimeMillis());
			try {
				m.invoke(target);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("time:" + System.currentTimeMillis());
		}
	}
	
	public interface Moveable {
		void move();
	}
	
	public class Tank implements Moveable {
		@Override
		public void move() {
			System.out.println("Tank Moving...");
			try {
				Thread.sleep(new Random().nextInt(10000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void test() throws Exception {
		Tank t = new Tank();
		InvocationHandler h = new TimeHandler(t);
		Moveable m = (Moveable)Proxy.newProxyInstance(Moveable.class, h);
		m.move();
	}
	
	public static void main(String[] args) throws Exception {
		new MyProxy().test();
	}
	
}
