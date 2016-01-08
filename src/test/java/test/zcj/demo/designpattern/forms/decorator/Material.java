package test.zcj.demo.designpattern.forms.decorator;

// Material表示要额外增加的抽象食物
public abstract class Material extends Noodle {
	
	Noodle noodle;

	public Material(Noodle noodle) {
		this.noodle = noodle;
	}

	public abstract String getDescriptin();

}