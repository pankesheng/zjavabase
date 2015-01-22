package test.zcj.demo.designpattern.state;

/**
 * "状态"设计模式
 * 定义两套规则,可方便的切换规则。
 * 在不同的状态，调用不同组的方法。相当于适配器设计模式。
 * @author ZCJ
 * @data 2012-11-8
 */
public class MyState {

	/** 定义一个抽象的规则 */
	public abstract class A {
		public abstract void doing();
	}
	
	/** 定义一个具体的规则A1 */
	public class A1 extends A {
		public void doing(){
			// doing...
		};
	}
	
	/** 定义一个具体的规则A2 */
	public class A2 extends A {
		public void doing(){
			// doing...
		};
	}
	
	/** 想用哪个规则就 new 哪个规则，还可以随时切换规则 */
	class U {
		private A a = new A1();
		public void play(){a.doing();}
		public void change(){a = new A2();}
	}
}
