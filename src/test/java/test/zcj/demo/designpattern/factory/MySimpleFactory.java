package test.zcj.demo.designpattern.factory;

/**
1、简单工厂(静态工厂模式)：
	将产品的实例化封装起来,调用者不用关心实例化过程，只需依赖工厂。

interface MoveAble {run();}
	class Car implements MoveAble {run(){...}}
	class Plane implements MoveAble {run(){...}}
	class Broom implements MoveAble {run(){...}}
	
abstract class IFactory {MoveAble create();}
	|_class CarFactory {MoveAble create(){return new Car()};}
	|_class PlaneFactory {MoveAble create(){return new Plane();}}
	|_class BroomFactory {MoveAble create(){return new Broom();}}

Test.java
	IFactory factory = new BroomFactory();
	MoveAble m = factory.create();
	m.run();

 */
public class MySimpleFactory {
	
}
