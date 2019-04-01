package com.wsmtec.multithreading.async;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

// 用于定义配置类
@Configuration
// 开启异步任务支持
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {
	/*
	 * 	线程池按以下行为执行任务
	 * 		1.当 线程数<核心线程数 时，创建线程
	 * 		2.当 线程数>=核心线程数 且 任务队列未满 时，将任务放入任务队列
	 * 		3.当 线程数>=核心线程数 且 任务队列已满 时：
	 * 			- 若 线程数 < 最大线程数 时，创建线程
	 * 			- 若 线程数 = 最大线程数 时，抛出异常，拒绝任务（也可根据setRejectedExecutionHandler设置如何处理）
	 */
	// 定义线程池
	@Override
	public Executor getAsyncExecutor() {
		// 定义线程池
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		// 设置线程名开头
		taskExecutor.setThreadNamePrefix("asyncTask--");
		// 设置核心线程数
		taskExecutor.setCorePoolSize(5);
		// 设置线程队列最大线程数
		taskExecutor.setQueueCapacity(10);
		// 设置线程池最大线程数
		taskExecutor.setMaxPoolSize(10);
		// 设置被拒绝的执行处理程序
		/*
		 * 	两种情况会拒绝处理任务：
		 * 		- 当线程数已经达到maxPoolSize，且队列已满，会拒绝新任务
		 * 		- 当线程池被调用shutdown()后，会等待线程池里的任务执行完毕，再shutdown。如果在调用shutdown()和线程池真正shutdown之间提交任务，会拒绝新任务
		 * 	线程池会调用rejectedExecutionHandler来处理这个任务。如果没有设置默认是AbortPolicy，会抛出异常
		 * 	ThreadPoolExecutor类有几个内部实现类来处理这类情况：
		 * 		- AbortPolicy 丢弃任务，抛运行时异常
		 * 		- CallerRunsPolicy 执行任务
		 * 		- DiscardPolicy 忽视，什么都不会发生
		 * 		- DiscardOldestPolicy 从队列中踢出最先进入队列（最后一个执行）的任务
		 * 	实现RejectedExecutionHandler接口，可自定义处理器
		 */
		taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
		// 用来设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean，这样这些异步任务的销毁就会先于Redis线程池的销毁
		// taskExecutor.shutdown();// 关闭线程池
		taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
		// 初始化
		taskExecutor.initialize();
		return taskExecutor;
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		// TODO Auto-generated method stub
		return AsyncConfigurer.super.getAsyncUncaughtExceptionHandler();
	}
	

}
