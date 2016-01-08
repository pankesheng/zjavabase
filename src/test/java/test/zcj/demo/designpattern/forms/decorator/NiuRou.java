package test.zcj.demo.designpattern.forms.decorator;

// 额外加一个牛肉
class NiuRou extends Material {

	public NiuRou(Noodle noodle) {
		super(noodle);
	}

	@Override
	public String getDescriptin() {
		return noodle.getDescriptin() + " + 牛肉";
	}

	@Override
	public double cost() {
		return noodle.cost() + 2.0;
	}

}