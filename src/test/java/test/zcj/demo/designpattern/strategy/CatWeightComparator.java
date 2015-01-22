package test.zcj.demo.designpattern.strategy;

import java.util.Comparator;

/**
 * JavaBean(Cat)的某一种比较策略
 * @author ZCJ
 * @data 2012-12-27
 */
public class CatWeightComparator implements Comparator<Cat> {

	@Override
	public int compare(Cat c1, Cat c2) {
		if (c1.getWeight() > c2.getWeight()) return 1;
		else if (c1.getWeight() < c2.getWeight()) return -1;
		return 0;
	}

}
