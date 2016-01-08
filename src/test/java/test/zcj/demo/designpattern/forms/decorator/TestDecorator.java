package test.zcj.demo.designpattern.forms.decorator;

public class TestDecorator {

	public static void main(String[] args) {

		Noodle noodle = new JiDanNoodle();
		System.out.println(noodle.getDescriptin() + ",价格 " + noodle.cost());

		noodle = new JiDan(noodle);// 加一个鸡蛋
		System.out.println(noodle.getDescriptin() + ",价格 " + noodle.cost());

		noodle = new NiuRou(noodle);// 加一个牛肉
		System.out.println(noodle.getDescriptin() + ",价格 " + noodle.cost());
		
		noodle = new JiDan(new JiDan(noodle));// 加两个鸡蛋
		System.out.println(noodle.getDescriptin() + ",价格 " + noodle.cost());
	}

}
