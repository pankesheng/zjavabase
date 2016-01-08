package test.zcj.demo.designpattern.forms.bridge;

/**
 * "桥接"设计模式
 * 
 * 将抽象部分与它的实现部分分离,使它们都可以独立地变化。 如果子类有多个维度排列组合，需要解决耦合问题，就用桥接模式（多对多）。
 * 
 * @author ZCJ
 * @data 2012-11-8
 */
public class MyBridge {

	/** 定义抽象的礼物给的方式 */
	abstract class GiftImpl {
		abstract String t1();
	}

	/** 定义礼物给的方式A */
	public class GiftImplA extends GiftImpl {
		@Override
		String t1() {
			return "快递";
		}
	}

	/** 定义礼物给的方式B */
	public class GiftImplB extends GiftImpl {
		@Override
		String t1() {
			return "亲手送";
		}
	}

	/** 定义抽象的礼物 */
	abstract class Gift {
		protected GiftImpl impl;

		abstract String t2();
	}

	/** 定义礼物C */
	public class GiftC extends Gift {
		public GiftC(GiftImpl impl) {
			this.impl = impl;
		}

		@Override
		String t2() {
			return impl.t1() + "鲜花";
		}
	}

	/** 定义礼物D */
	public class GiftD extends Gift {
		public GiftD(GiftImpl impl) {
			this.impl = impl;
		}

		@Override
		String t2() {
			return impl.t1() + "巧克力";
		}
	}

	public void test() {
		Gift a = new GiftD(new GiftImplB());
		System.out.println(a.t2());

		Gift b = new GiftC(new GiftImplA());
		System.out.println(b.t2());
	}

	public static void main(String[] args) {
		new MyBridge().test();
	}

}
