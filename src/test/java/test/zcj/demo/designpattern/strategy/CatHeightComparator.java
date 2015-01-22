package test.zcj.demo.designpattern.strategy;

import java.util.Comparator;

/**
 * JavaBean(Cat)的某一种比较策略
 * @author ZCJ
 * @data 2012-12-27
 */
public class CatHeightComparator implements Comparator<Cat> {

	@Override
	public int compare(Cat c1, Cat c2) {
		if (c1.getHeight() > c2.getHeight()) return 1;
		else if (c1.getHeight() < c2.getHeight()) return -1;
		return 0;
	}

}
