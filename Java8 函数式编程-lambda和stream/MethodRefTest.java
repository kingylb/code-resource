package com.snowcattle.game.thread;

import java.util.HashMap;
import java.util.Map;

public class MethodRefTest {
	
	public static void main(String[] args) {
		Map<String, Integer> map = new HashMap<>();
		MethodRefTest obj = new MethodRefTest();
		String key = "a";
		// 1
		System.out.println(obj.mapKey(map, key));
		System.out.println(map.get(key));
		// 2
		System.out.println(obj.mapKey(map, key));
		System.out.println(map.get(key));
		// 3
		System.out.println(obj.mapKey(map, key));
		System.out.println(map.get(key));
		// method reference
		System.out.println(obj.mapKey2(map, key));
		System.out.println(map.get(key));		
	}
	
	//======================================
	
	public int mapKey(Map<String, Integer> map, String key) {
		return map.merge(key, 1, (count, incr) -> count + incr);
	}
	
//	从Java 8 开始， Integer （以及所有其他的数字化基本包装类型都）提供了一个名为
//	sum 的静态方法，它的作用也同样是求和。我们只要传人一个对该方法的引用，就可以更
//	轻松地得到相同的结果：
	
	public int mapKey2(Map<String, Integer> map, String key) {
		return map.merge(key, 1, Integer::sum);
	}
	
//	总而言之，方法引用常常比Lambda 表达式更加简洁明了。只要方法引用更加简洁、清
//	晰，就用方法引用；如果方法引用并不简洁，就坚持使用Lambda 。	
}
