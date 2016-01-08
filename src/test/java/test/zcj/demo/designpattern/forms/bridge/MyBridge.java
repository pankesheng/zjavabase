package test.zcj.demo.designpattern.forms.bridge;

/**
 * "桥接"设计模式
 * 
 * 		将抽象部分与它的实现部分分离,使它们都可以独立地变化。
 * 		如果子类有多个维度排列组合，需要解决耦合问题，就用桥接模式（多对多）。
 * 
 * @author ZCJ
 * @data 2012-11-8
 */
public class MyBridge {

	/** 定义抽象的礼物给的方式 */
	abstract class GiftImpl {}
	
	/** 定义礼物给的方式A */
	public class GiftImplA extends GiftImpl {}
	
	/** 定义礼物给的方式B */
	public class GiftImplB extends GiftImpl {}
	
	/** 定义抽象的礼物 */
	abstract class Gift {
		protected GiftImpl impl;
	}
	
	/** 定义礼物C */
	public class GiftC extends Gift{
		public GiftC(GiftImpl impl) {
			this.impl = impl;
		}
	}
	
	/** 定义礼物D */
	public class GiftD extends Gift {
		public GiftD(GiftImpl impl) {
			this.impl = impl;
		}
	}
	
	/** 随便用哪种方式给哪种礼物 */
	public class Boy {
		@SuppressWarnings("unused")
		public void pursue() {
			//Gift g = new GiftC(new GiftImplA()); 用方式A给礼物C
			Gift g = new GiftD(new GiftImplB());// 用方式B给礼物D
		}
	}
	
}
