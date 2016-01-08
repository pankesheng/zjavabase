package test.zcj.demo.designpattern.forms.decorator;

public class Ext implements Food {

	private Food food;

	public Ext(Food food) {
		this.food = food;
	}

	public String p1() {
		return food.p1();
	}

}