package com.wsmtec.multithreading.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
// 开启定时任务支持
@EnableScheduling
public class AsyncDemoJob {
	// 定义线程任务
	@Async
	
	// 定义定时任务
	/*
	 * 	推荐一个在线cron表达式生成器：http://cron.qqe2.com/
	 * 	cron表达式由6或7个空格分隔的时间字段组成：秒 分钟 小时 日期 月份 星期 年(可选)
	 * 		字段		允许值		允许的特殊字符
	 * 		秒		0-59		/,-*
	 * 		分		0-59		/,-*
	 * 		小时		0-23		/,-*
	 * 		日期		1-31		/,-*?LWC	JAN,FEB,MAR,APR,MAY,JUN,JUL,AUG,SEP,OCT,NOV,DEC
	 * 		月份		1-12		/,-*
	 * 		星期		1-7			/,-*?LC#	SUN,MON,TUES,WED,THUR,FRI,SAT
	 * 		年份		1970-2099	/,-*
	 * 	解析：
	 * 		"/"指定增量：
	 * 				"0/20"在秒位，0秒、20秒、40秒；
	 * 				"5/15"在分位，5分、20分、35分、50分；
	 * 				"*"在"/"前等价于"0"；
	 * 		","指定另外的值：
	 * 				"MON,WED,FRI"在星期位，星期一、星期三、星期五；
	 * 		"-"指定范围：
	 * 				"10-12"在小时位，10点、11点、12点；
	 * 		"*"指定所有值
	 * 		"?"指定非明确值
	 * 		"L"单词"last"的缩写，
	 * 				在日期位表示，一个月的最后一天；
	 * 				在星期位表示，一个星期的最后一天；"6L"表示一个月的最后一个星期五
	 * 		"W"指定日期的最近工作日（只能配合单独的数值使用，不能是数字段）
	 * 				"15W"，该月15号最近的工作日，15号为周六则14号触发，15号为周天则16号触发；
	 * 				"1W"，该月1号最近的工作日，1号为周六也是3号触发；
	 * 				"LW",该月最后一个工作日；
	 * 		"C"该字符只在日期和星期字段中使用，代表“Calendar”的意思。它的意思是计划所关联的日期，如果日期没有被关联，则相当于日历中所有日期。
	 * 				"5C"在日期字段中就相当于日历5日以后的第一天；
	 * 				"1C"在星期字段中相当于星期日后的第一天；
	 * 		"#"指定每月第几个星期几
	 * 				"4#2",该月第二个星期三；
	 */
	@Scheduled(cron = "0/5 * * * * ?")//0秒开始，每5秒一执行 
//	@Scheduled(fixedRate = 5000)// 任务开始后5秒再次执行
//	@Scheduled(fixedDelay = 5000)// 任务结束后5秒再次执行
	public void asyncjob() {
		System.out.println("请求线程名称：" + Thread.currentThread().getName());
	}
}
