package com.wsmtec.multithreading.foreverloop;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class DemoExecutor extends Thread {

	// 有参数的情况
	// 通过 DemoExecutor d = new DemoExecutor(1, "abc") 传参
	// 在DemoExecutor中通过 this.i1 使用
	private int i1;
	private String s1;

	public DemoExecutor() {
		super();
	}

	public DemoExecutor(int i1, String s1) {
		super();
		this.i1 = i1;
		this.s1 = s1;
	}

	// run()函数里写代码
	@Override
	public void run() {
		// 获取当前线程id
		long id = Thread.currentThread().getId();
		System.out.println(
				LocalTime.now() + " " + Thread.currentThread().getName() + "【Executor】" + "id-" + id + "@ start");

		// 定义线程池
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setThreadNamePrefix("inTask" + id + "--");
		executor.setCorePoolSize(1);
		executor.initialize();

		DemoCallable callable = new DemoCallable();

		// --------------------------------------------------------------
		/*
		 * FutureTask<V>类实现了RunnableFuture<V>接口
		 * RunnableFuture<V>接口继承了Runnable和Future<V>接口 FutureTask的两种构造器 public
		 * FutureTask(Callable<V> callable) {} public FutureTask(Runnable runnable, V
		 * result) {}
		 */
		// 1.使用Callable+FutureTask获取执行结果
//		FutureTask<String> futureTask = new FutureTask<String>(callable); 
		// 提交任务
//		executor.submit(futureTask);
		// --------------------------------------------------------------
		// 2.使用Callable+Future获取执行结果
		// 提交任务
		Future<String> future = executor.submit(callable);
		// --------------------------------------------------------------

		String s = null;
		try {
			// 获取返回值，并设置超时时间
			s = future.get(3, TimeUnit.SECONDS);// 1.代码
//			s = futureTask.get(3, TimeUnit.SECONDS);// 2.代码
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			// 取消任务的执行
			future.cancel(true);// 1.代码
//			futureTask.cancel(true);// 2.代码
			System.out.println(LocalTime.now() + " " + Thread.currentThread().getName() + "【Executor】" + "id-" + id
					+ " @ exception");
			e.printStackTrace();
		} finally {
			System.out.println(LocalTime.now() + " " + Thread.currentThread().getName() + "【Executor】" + "id-" + id
					+ " @ end--return：" + s);
		}
		executor.shutdown();
	}

	// 测试Future
	public static void main(String[] args) {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setThreadNamePrefix("inTask--");
		executor.setCorePoolSize(10);
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.initialize();
		List<Future<String>> list = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			System.out.println(LocalTime.now() + "【" + i + "】" + Thread.currentThread().getName() + " start");
			DemoCallable callable = new DemoCallable();
			Future<String> future = executor.submit(callable);
			list.add(future);
		}
		for (Future<String> temp : list) {
			try {
				temp.get(3, TimeUnit.SECONDS);
			} catch (InterruptedException | ExecutionException | TimeoutException e) {
				temp.cancel(true);
				e.printStackTrace();
				System.out.println(LocalTime.now() + Thread.currentThread().getName() + " exception");
			}
		}
		System.out.println(LocalTime.now() + Thread.currentThread().getName() + " end");
		executor.shutdown();
	}

//	@Override
//	public void run() {
//		System.out.println("【Executor】name-" + Thread.currentThread().getName() + "||id-"
//				+ Thread.currentThread().getId() + "@start");
//		Future<String> future = null;
//		try {
////			future = demoService.run();
//			future = go();
////			String result = future.get(4, TimeUnit.SECONDS);
//			System.out.println("【Executor】result:");
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		} catch (ExecutionException e) {
//			e.printStackTrace();
//		} catch (TimeoutException e) {
//			System.out.println("【Executor】********************************************");
//			// System.out.println("【Executor】超时");
//			future.cancel(true);
//			e.printStackTrace();
//			System.out.println("【Executor】********************************************");
//		}
//	}
//
//	private Future<String> go() throws InterruptedException {
//		System.out.println("【Service】start");
//		Thread.sleep((int) (Math.random() * 5000)); 
//		// Thread.sleep(6000);
//		// System.out.println("【Service】end");
//		Future<String> future = new AsyncResult<String>("——————————OK——————————");
//		return future;
//	}
}
