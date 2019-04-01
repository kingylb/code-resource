package com.wsmtec.multithreading.thread;

/**
 * @author czj
 * 通过实现Runnable接口来创建线程类
 * 1.Runnable非常适合多个相同线程来处理同一份资源的情况
 * 2.Runnable可以避免由于Java的单继承机制带来的局限
 * 3.如果想获取当前线程句柄，只能用Thread.currentThread()方法
 */
public class ThreadByRunnable implements Runnable {

	@Override
	public void run() {
		System.out.println("当前线程名称是：" + Thread.currentThread().getName());
		
		for (int i = 0; i < 5; i++) {
			System.out.println(Thread.currentThread().getName() + "：" + i);
			try {
				Thread.sleep((int) (Math.random() * 1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		Runnable st = new ThreadByRunnable();
		new Thread(st, "新线程1").start();
		new Thread(st, "新线程2").start();
	}

}
