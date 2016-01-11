package test.zcj.demo.designpattern.activity.visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * 访问者设计模式
 * 	
 * @author zouchongjin@sina.com
 * @data 2016年1月11日
 */
public class TestVisitor {

	// 抽象被访问者
	public interface ElementAble {
		public void accept(VisitorAble visitor);
	}

	// 具体被访问者
	public class ElementA implements ElementAble {
		@Override
		public void accept(final VisitorAble visitor) {
			visitor.visit(this);
		}
	}

	// 具体被访问者
	public class ElementB implements ElementAble {
		@Override
		public void accept(final VisitorAble visitor) {
			visitor.visit(this);
		}
	}
	
	// 抽象访问者
	public interface VisitorAble {
		public void visit(ElementA elementa);

		public void visit(ElementB elementb);
	}

	// 具体访问者
	public class VisitorA implements VisitorAble {
		@Override
		public void visit(final ElementA elementa) {
			System.out.println("VA - EA");
		}

		@Override
		public void visit(final ElementB elementb) {
			System.out.println("VA - EB");
		}
	}

	// 具体访问者
	public class VisitorB implements VisitorAble {
		@Override
		public void visit(ElementA elementa) {
			System.out.println("VB - EA");
		}

		@Override
		public void visit(ElementB elementb) {
			System.out.println("VB - EB");
		}
	}

	public class ObjectStructure {
		private final List<ElementAble> elements = new ArrayList<ElementAble>();

		public void addElement(final ElementAble e) {
			elements.add(e);
		}

		public void removeElement(final ElementAble e) {
			elements.remove(e);
		}

		public void accept(final VisitorAble visitor) {
			for (final ElementAble e : elements) {
				e.accept(visitor);
			}
		}
	}

	public void test() {
		final ObjectStructure os = new ObjectStructure();
		os.addElement(new ElementA());
		os.addElement(new ElementB());

		final VisitorAble gVisitor = new VisitorA();
		final VisitorAble chVisitor = new VisitorB();

		os.accept(gVisitor);
		os.accept(chVisitor);
	}

	public static void main(final String[] args) {
		new TestVisitor().test();
	}

}
