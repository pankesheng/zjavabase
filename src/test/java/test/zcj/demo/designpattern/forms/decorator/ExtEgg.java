package test.zcj.demo.designpattern.forms.decorator;

public class ExtEgg extends Ext {
	
	public ExtEgg(Food food) {
		super(food);
	}

	@Override
	public String p1() {
		return super.p1() + " 加一个鸡蛋";
	}
	
}