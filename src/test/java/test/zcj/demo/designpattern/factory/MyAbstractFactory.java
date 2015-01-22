package test.zcj.demo.designpattern.factory;

/**
2、抽象工厂模式
	用来创建一组相关或者相互依赖的对象。
	产生新的产品品种的时候改动太大。
 */
public class MyAbstractFactory {
	
	interface IProduct1 {
		public void show();
	}
	interface IProduct2 {
		public void show();
	}

	class Product1 implements IProduct1 {
		public void show() {
			System.out.println("这是1型产品");
		}
	}
	class Product2 implements IProduct2 {
		public void show() {
			System.out.println("这是2型产品");
		}
	}

	interface IFactory {
		public IProduct1 createProduct1();
		public IProduct2 createProduct2();
	}
	class Factory implements IFactory{
		public IProduct1 createProduct1() {
			return new Product1();
		}
		public IProduct2 createProduct2() {
			return new Product2();
		}
	}

	public class Test {
		public void myTest() {
			IFactory factory = new Factory();
			factory.createProduct1().show();
			factory.createProduct2().show();
		}
	}

}
