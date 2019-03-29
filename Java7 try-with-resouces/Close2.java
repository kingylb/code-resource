package com.snowcattle.game.thread;

public class Close2 implements AutoCloseable{

	@Override
	public void close() throws Exception {
		System.out.println("[Close2 #2]close");
	}
	
	public void doIt() throws Exception {
		System.out.println("[Close2 #2]doIt");
		Thread.sleep(1000);
		throw new Exception("in [Close2 #2]doIt");
	}

}
