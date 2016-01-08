package test.zcj.demo.designpattern.forms.decorator;

/**

装饰者设计模式

	动态地给一个对象添加一些额外的职责（可重复添加）。就增加功能来说，Decorator模式相比生成子类更为灵活。

interface Food （抽象构件）
	|_class Noodle implements Food （基础具体构件）
	|_class Ext implements Food （抽象装饰者）
		|_class ExtEgg extends Ext （具体装饰者）
		|_class ExtBeef extends Ext（具体装饰者）

 */
public class TestDecorator {

	public static void main(String[] args) {

		// 基础的东西（一碗面）
		Food base = new Noodle();
		
		// 装饰以后的东西（加两个鸡蛋一份牛肉）
		Food decorator = new ExtBeef(new ExtEgg(new ExtEgg(base)));
		
		System.out.println(decorator.p1());
	}

}
