package test.zcj.demo.designpattern.strategy;

import java.util.ArrayList;
import java.util.List;

/**
 * 某一种JavaBean
 * @author ZCJ
 * @data 2012-12-27
 */
public class Cat {

	private int height;
	private int weight;
	
	/** 构建一个Cat集合 */
	public static List<Cat> initCatList() {
		List<Cat> cats = new ArrayList<Cat>();
		cats.add(new Cat(12, 5));
		cats.add(new Cat(16, 8));
		cats.add(new Cat(18, 1));
		return cats;
	}
	
	public Cat(int height, int weight) {
		super();
		this.height = height;
		this.weight = weight;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	
}
