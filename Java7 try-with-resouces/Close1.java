package com.snowcattle.game.thread;

public class Close1 implements AutoCloseable{

	@Override
	public void close() throws Exception {
		System.out.println("[Close1 #1]close");
	}
	
	public void doIt() throws Exception {
		System.out.println("[Close1 #1]doIt");
		Thread.sleep(1000);
		throw new Exception("in [Close1 #1]doIt");
	}

}
