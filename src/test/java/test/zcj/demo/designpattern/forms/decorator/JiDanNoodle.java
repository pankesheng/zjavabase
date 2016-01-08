package test.zcj.demo.designpattern.forms.decorator;

class JiDanNoodle extends Noodle {

	public JiDanNoodle() {
		description = "鸡蛋面";
	}

	public double cost() {
		return 5.5;
	}

}