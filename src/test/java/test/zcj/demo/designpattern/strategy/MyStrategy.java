package test.zcj.demo.designpattern.strategy;

import java.util.Collections;
import java.util.List;

/**
 * "策略"设计模式（类似于"模版方法"设计模式）
 * 把一系列算法封装到实现相同接口的各个类里，相互之间可以替换。
 * 实质上就是面向对象中的继承和多态。
 * 创建一个能够根据所传递的参数对象的不同(new CatHeightComparator())而具有不同行为的方法 
 * 例子：接口Comparable、Comparator,
 * 例子：当比较对象的大小时,定义一个策略的比较器,然后由具体的的比较策略判断到底谁大谁小
 */
public class MyStrategy {
	
	/** JDK里的排序策略模式 */
	public static void main(String[] args) {
		List<Cat> cats = Cat.initCatList();
		Collections.sort(cats, new CatWeightComparator());
//		Collections.sort(cats, new CatHeightComparator());
		for (Cat s : cats) {
			System.out.println(s.getWeight());
		}
	}
	
	
	
	
	
	
	
	/** 另一种简单的策略模式 */
	interface IStrategy {
		public void doSomething();
	}
	class ConcreteStrategy1 implements IStrategy {
		public void doSomething() {
			System.out.println("具体策略1");
		}
	}
	class ConcreteStrategy2 implements IStrategy {
		public void doSomething() {
			System.out.println("具体策略2");
		}
	}
	class Context {
		private IStrategy strategy;
		
		public Context(IStrategy strategy){
			this.strategy = strategy;
		}
		
		public void execute(){
			strategy.doSomething();
		}
	}
	public class Client {
		public void test(){
			Context context;
			System.out.println("-----执行策略1-----");
			context = new Context(new ConcreteStrategy1());
			context.execute();

			System.out.println("-----执行策略2-----");
			context = new Context(new ConcreteStrategy2());
			context.execute();
		}
	}

	
}
