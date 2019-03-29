package com.snowcattle.game.thread;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class LambdaTest {
	
	public String STR;
	
	LambdaTest(String str) {
		this.STR = str;
	}

	public static void main(String[] args) {
		LambdaTest lt = new LambdaTest("init");
		lt.doIt();
	}

	// 能够接收Lambda表达式的参数类型，是一个只包含一个方法的接口。只包含一个方法的接口称之为“函数接口”。
	public void test(Consumer<String> func) {
		func.accept("2");
	}
	
	public void doIt() {
		
		this.test((s) -> {});
		this.STR = "2nd";	// 闭包
		this.test(s -> {System.out.println("Another " + s + " " + STR); });
	}
	
	//======================================
	
//	根据以往的经验，是用带有单个抽象方法的接口（或者，几乎都不是抽象类）作为函数
//	类型（ function type ） 。它们的实例称作函数对象（ function object ），表示函数或者要采取的
//	动作。自从1997 年发布JDK 1.1 以来，创建函数对象的主要方式是通过匿名类（ anonymous
//	class ，详见第24 条） 。下面是一个按照字符串的长度对字符串列表进行排序的代码片段，它
//	用一个匿名类创建了排序的比较函数（加强排列顺序）：
	
	public void obsolete1(List<String> words) {
		Collections.sort(words, new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				return Integer.compare(o1.length(), o2.length());
			}
			
		});
	}
	
//	匿名类满足了传统的面向对象的设计模式对函数对象的需求，最著名的有策略（Strategy)
//	模式［ Gamma95 ］ 。Comparator 接口代表一种排序的抽象策略（ abstract strategy ）；上述
//	的匿名类则是为字符串排序的一种具体策略（ concrete strategy ） 。但是，匿名类的烦琐使得
//	在Java 中进行函数编程的前景变得十分黯淡。
	
	//---
	
//	在Java 8 中，形成了“带有单个抽象方法的接口是特殊的，值得特殊对待”的观念。
//	这些接口现在被称作函数接口（ functional interface), Java 允许利用Lambda 表达式（ Lambda
//	expression ，简称Lambda ）创建这些接口的实例。Lambda 类似于匿名类的函数，但是比它
//	简洁得多。以下是上述代码用Lambda 代替医名类之后的样子。样板代码没有了，其行为也
//	十分明确：
	
	public void lambda1(List<String> words) {
		Collections.sort(words, (s1, s2) -> Integer.compare(s1.length(), s2.length()));
	}
	
//	编译器利用一个称作类型
//	推导（ type inference ）的过程，根据上下文推断出这些类型。
	
}
