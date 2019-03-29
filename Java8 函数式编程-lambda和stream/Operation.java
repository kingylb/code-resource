package com.snowcattle.game.thread;

// 枚举实例域优先于特定于常量的类主体。
public enum Operation {
	PLUS("+") {
		public double apply(double x, double y) {
			return x + y;
		}
	},
	MINUS("-") {
		public double apply(double x, double y) {
			return x - y;
		}
	},
	TIMES("*") {
		public double apply(double x, double y) {
			return x * y;
		}
	},
	DIVIDE("/") {
		public double apply(double x, double y) {
			return x / y;
		}
	};

	private final String symbol;

	Operation(String symbol) {
		this.symbol = symbol;
	}

	@Override
	public String toString() {
		return symbol;
	}

	public abstract double apply(double x, double y);
}
