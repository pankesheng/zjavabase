package test.zcj.demo.designpattern.forms.decorator;

public class ExtBeef extends Ext {
	
	public ExtBeef(Food food) {
		super(food);
	}

	@Override
	public String p1() {
		return super.p1() + " 加一份牛肉";
	}
	
}