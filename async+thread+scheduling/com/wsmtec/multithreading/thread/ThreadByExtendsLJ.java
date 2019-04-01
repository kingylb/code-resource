package com.wsmtec.multithreading.thread;

/**
 * @author lj
 * 通过继承Thread类来创建线程类
 */
public class ThreadByExtendsLJ extends Thread {
	
	volatile boolean flag ;
	@Override
	public void run() {
		// 当线程类继承Thread类时，直接使用this即可获取当前线程句柄。
		// 因此可以直接调用getName()方法返回当前线程的名称。
		System.out.println("当前线程名称是：" + getName());
		flag = true;
		while(flag) {
			System.out.println("kffffffffffff");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		for (int i = 0; i < 5; i++) {
			System.out.println(getName() + "：" + i);
			try {
				Thread.sleep((int) (Math.random() * 1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		// 静态方法没有this，只能通过Thread.Thread.currentThread获取当前线程句柄
		System.out.println(Thread.currentThread().getName());

		ThreadByExtendsLJ tt = new ThreadByExtendsLJ();
		tt.start();
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		tt.stopThread();
	}
	 public void stopThread() {
		 flag = false;
	 }
}
