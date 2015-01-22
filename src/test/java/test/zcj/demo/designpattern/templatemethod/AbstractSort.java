package test.zcj.demo.designpattern.templatemethod;

/** 模板方法模式：1、搭建逻辑的框架 */
abstract class AbstractSort {

	/** 需要实现的具体业务 */
	protected abstract void sort(int[] array);

	/** 架子 */
	public void showSortResult(int[] array) {
		this.sort(array);
		System.out.print("排序结果：");
		for (int i = 0; i < array.length; i++) {
			System.out.printf("%3s", array[i]);
		}
	}
}
