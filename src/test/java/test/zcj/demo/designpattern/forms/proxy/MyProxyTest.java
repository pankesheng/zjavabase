package test.zcj.demo.designpattern.forms.proxy;

import java.lang.reflect.Method;

public class MyProxyTest {

	public interface UserMgr {
		void addUser();
	}

	public class UserMgrImpl implements UserMgr {
		@Override
		public void addUser() {
			System.out.println("1: 插入记录到user表");
			System.out.println("2: 做日志在另外一张表");
		}
	}

	public class TransactionHandler implements InvocationHandler {
		private Object target;

		public TransactionHandler(Object target) {
			this.target = target;
		}

		@Override
		public void invoke(Object o, Method m) {
			System.out.println("Transaction Start");
			try {
				m.invoke(target);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("Transaction Commit");
		}
	}

	public void test() throws Exception {
		UserMgr mgr = new UserMgrImpl();
		InvocationHandler h = new TransactionHandler(mgr);
		UserMgr u = (UserMgr) Proxy.newProxyInstance(UserMgr.class, h);
		u.addUser();
	}

	public static void main(String[] args) throws Exception {
		new MyProxyTest().test();
	}
}
