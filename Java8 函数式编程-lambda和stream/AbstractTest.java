package com.snowcattle.game.thread;

public class AbstractTest {
	
	public static void main(String[] args) {
		AbstractMethod am = new AbstractMethod() {
			
			@Override
			public void b() {
				// TODO Auto-generated method stub
				System.out.println("Here b!");
			}
		};
		
		am.a();
		am.b();
	}
}
