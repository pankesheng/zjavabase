package test.zcj.demo.designpattern.templatemethod;

/** 模板方法模式：2、具体实现 */
public class ConcreteSort extends AbstractSort {

	@Override
	protected void sort(int[] array) {
		// 具体实现
	}

	/** 测试 */
	public static void main(String[] args) {
		int[] a = { 10, 32, 1, 9, 5, 7, 12, 0, 4, 3 }; // 预设数据数组
		AbstractSort s = new ConcreteSort();
		s.showSortResult(a);
	}

}
