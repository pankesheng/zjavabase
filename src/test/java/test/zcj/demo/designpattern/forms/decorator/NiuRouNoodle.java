package test.zcj.demo.designpattern.forms.decorator;

class NiuRouNoodle extends Noodle {
	
	public NiuRouNoodle() {
		description = "牛肉面";
	}

	@Override
	public double cost() {
		return 7.5;
	}

}