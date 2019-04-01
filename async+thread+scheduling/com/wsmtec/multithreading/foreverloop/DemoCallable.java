package com.wsmtec.multithreading.foreverloop;

import java.time.LocalTime;
import java.util.concurrent.Callable;

import org.springframework.stereotype.Component;

@Component
public class DemoCallable implements Callable<String> {

	@Override
	public String call() throws InterruptedException {
		long i = (long) (Math.random() * 5000);
		System.out.println(LocalTime.now() + " " + Thread.currentThread().getName() + "【Callable】" + " @ start " + i);
		Thread.sleep(i);
		System.out.println(LocalTime.now() + " " + Thread.currentThread().getName() + "【Callable】" + " @ end " + i);
		return "OK";
	}

}
