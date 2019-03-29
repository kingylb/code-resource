package com.snowcattle.game.thread;

public class OperationTest {
	
	public static void main(String[] args) {
		System.out.println(Operation.TIMES.apply(2, 3));
		System.out.println(OperationLambda.TIMES.apply(2, 3));
	}

}
