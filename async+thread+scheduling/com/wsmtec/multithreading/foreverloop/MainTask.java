package com.wsmtec.multithreading.foreverloop;

import java.time.LocalTime;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class MainTask implements ApplicationRunner {

	@Override
	public void run(ApplicationArguments args) {
		// 定义线程池
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		// 设置线程名开头
		executor.setThreadNamePrefix("outTask--");
		// 核心线程数
		executor.setCorePoolSize(10);
		// 线程队列最大线程数
		executor.setQueueCapacity(10);
		// 线程池最大线程数
		executor.setMaxPoolSize(20);
		// 初始化
		executor.initialize();

		// 等待队列最大线程数
		int queuedTaskConfigSize = 10;
		// 当前等待队列数
		int queuedTaskCurrentSize = 0;
		// 两数之差
		int different;

		// 永真循环
		while (true) {
			// 1.求两数之差
			different = queuedTaskConfigSize - queuedTaskCurrentSize;
			
			// 2.判断两数之差是否大于0
			// 大于0
			if (different > 0) {
				System.out.println("-----------------------------------");
				System.out.println(Thread.currentThread().getName() + "【main】queuedTaskSize:" + queuedTaskCurrentSize);
				for (int i = 0; i < different; i++) {
					Runnable runnable = new DemoExecutor();
					executor.execute(runnable);
					System.out.println(
							Thread.currentThread().getName() + "【main~" + i + "】" + executor.getThreadPoolExecutor());
				}
			// 等于0
			} else {
				try {
					// 无多余等待位置则休息一会
					System.out.println(Thread.currentThread().getName() + "【main】wait----------2500");
					Thread.sleep(2500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			// 3.获取当前等待队列数
			queuedTaskCurrentSize = executor.getThreadPoolExecutor().getQueue().size();
		}
	}

	// 测试run()方法
	public static void main(String[] args) {
		// 定义线程池
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		// 设置线程名开头
		executor.setThreadNamePrefix("outTask--");
		// 核心线程数
		executor.setCorePoolSize(5);
		// 线程队列最大线程数
		executor.setQueueCapacity(5);
		// 线程池最大线程数
		executor.setMaxPoolSize(10);
		// 初始化
		executor.initialize();
		// 用来设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean
		executor.setWaitForTasksToCompleteOnShutdown(true);

		// 等待队列最大线程数
		int queuedTaskConfigsize = 5;
		// 当前等待队列数
		int queuedTaskCurrentSize = 0;
		// 两数之差
		int different = 0;
		// 测试跑4轮
		int a = 0;
		while (a < 3) {
			// 1.求两数之差
			different = queuedTaskConfigsize - queuedTaskCurrentSize;

			// 2.判断两数之差是否大于0
			// 大于0
			if (different > 0) {
				System.out.println("-----------------------------------");
				System.out.println(LocalTime.now() + "@" + "【" + Thread.currentThread().getName() + "】queuedTaskSize:" + queuedTaskCurrentSize + "~~~~~~~~~~~~~~~~~~~~");

				for (int i = 0; i < different; i++) {
					Runnable runnable = new DemoExecutor();
					executor.execute(runnable);
				}
				a++;
			// 等于0
			} else {
				try {
					// 无多余等待位置则休息一会
					Thread.sleep(2500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			// 3.获取当前等待队列数
			queuedTaskCurrentSize = executor.getThreadPoolExecutor().getQueue().size();
		}
		executor.shutdown();
	}
}
