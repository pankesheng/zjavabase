package test.zcj.demo.designpattern.builder;

/**
 * 生成器模式(建造者模式)
 * 
 */
public class MyBuilder {
	
	/** 产品类 */
	class Product {
		private String name;
		private String type;
		public void showProduct() {
			System.out.println("名称：" + name);
			System.out.println("型号：" + type);
		}
		public void setName(String name) {this.name = name;}
		public void setType(String type) {this.type = type;}
	}
	/** 抽象建造者(组建产品；返回组建好的产品。) */
	abstract class Builder {
		public abstract void setPart(String arg1, String arg2);
		public abstract Product getProduct();
	}
	/** 建造者(组建产品；返回组建好的产品。) */
	class ConcreteBuilder extends Builder {
		private Product product = new Product();
		public Product getProduct() {return product;}
		public void setPart(String arg1, String arg2) {
			product.setName(arg1);
			product.setType(arg2);
		}
	}
	/** 导演类(负责调用适当的建造者来组建产品，导演类一般不与产品类发生依赖关系，与导演类直接交互的是建造者类。) */
	public class Director {
		private Builder builder = new ConcreteBuilder();

		public Product getAProduct() {
			builder.setPart("宝马汽车", "X7");
			return builder.getProduct();
		}

		public Product getBProduct() {
			builder.setPart("奥迪汽车", "Q5");
			return builder.getProduct();
		}
	}

	public class Client {
		public void test1() {
			Director director = new Director();
			
			Product product1 = director.getAProduct();
			product1.showProduct();

			Product product2 = director.getBProduct();
			product2.showProduct();
		}
	}

}
