package com.snowcattle.game.thread;

import java.util.function.DoubleBinaryOperator;

// 枚举实例域优先于特定于常量的类主体。
// 从Java 8 开始， Lambda 就成了表示小函数对象的最佳方式。
// Lambda 使得表示小函数对象变得如此轻松，因此打开了之前从未实践过的在Java 中进行函数编程的大门。
public enum OperationLambda {
	PLUS("+", (x, y) -> x + y),
	MINUS("-", (x, y) -> x - y),
	TIMES("*", (x, y) -> x * y),
	DIVIDE("/", (x, y) -> x / y);

	private final String symbol;
	private final DoubleBinaryOperator op;

	OperationLambda(String symbol, DoubleBinaryOperator op) {
		this.symbol = symbol;
		this.op = op;
	}

	@Override
	public String toString() {
		return symbol;
	}

	public double apply(double x, double y) {
		return op.applyAsDouble(x, y);
	}
}
