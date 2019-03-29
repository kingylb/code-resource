package com.snowcattle.game.thread;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

//Java8中的Stream是对容器对象功能的增强，
//它专注于对容器对象进行各种非常便利、高效的 聚合操作（aggregate operation），或者大批量数据操作 (bulk data operation)。
//Stream API借助于同样新出现的Lambda表达式，极大的提高编程效率和程序可读性。
//同时，它提供串行和并行两种模式进行汇聚操作，并发模式能够充分利用多核处理器的优势，使用fork/join并行方式来拆分任务和加速处理过程。
//通常，编写并行代码很难而且容易出错, 但使用Stream API无需编写一行多线程的代码，就可以很方便地写出高性能的并发程序。
//所以说，Java8中首次出现的 java.util.stream是一个函数式语言+多核时代综合影响的产物。
public class StreamTest {

	public class Student {
		int no;
		String name;
		String sex;
		float height;

		public Student(int no, String name, String sex, float height) {
			this.no = no;
			this.name = name;
			this.sex = sex;
			this.height = height;
		}

		public int getNo() {
			return no;
		}

		public void setNo(int no) {
			this.no = no;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getSex() {
			return sex;
		}

		public void setSex(String sex) {
			this.sex = sex;
		}

		public float getHeight() {
			return height;
		}

		public void setHeight(float height) {
			this.height = height;
		}

		@Override
		public String toString() {
			return "Student [no=" + no + ", name=" + name + ", sex=" + sex + ", height=" + height + "]";
		}
	}

	public List<Student> initData() {
		Student stuA = new Student(1, "A", "M", 184);
		Student stuB = new Student(2, "B", "G", 163);
		Student stuC = new Student(3, "C", "M", 175);
		Student stuD = new Student(4, "D", "G", 158);
		Student stuE = new Student(5, "E", "M", 170);
		List<Student> list = new ArrayList<>();
		list.add(stuA);
		list.add(stuB);
		list.add(stuC);
		list.add(stuD);
		list.add(stuE);
		return list;
	}

	public void findG1(List<Student> list) {
		System.out.println("=========== findG1 Iterator ==============");
		Iterator<Student> iterator = list.iterator();
		while (iterator.hasNext()) {
			Student stu = iterator.next();
			if (stu.getSex().equals("G")) {
				System.out.println(stu);
			}
		}
	}

	public void findG2(List<Student> list) {
		System.out.println("=========== findG2 Stream ==============");
//		Map<String, String> m;
//		m
		list.stream()
		.filter(student -> student.getSex().equals("G"))
		.forEach(student -> System.out.println(student));
	}
	
	public void findG3(List<Student> list) {
		System.out.println("=========== findG3 Stream ==============");
		list.parallelStream()
		.filter(student -> student.getSex().equals("G"))
		.forEach(student -> System.out.println(student));
	}
	
//	什么是聚合操作
//
//	　　在传统的J2EE应用中，Java代码经常不得不依赖于关系型数据库的聚合操作来完成诸如：
//
//	    客户每月平均消费金额
//	    最昂贵的在售商品
//	    本周完成的有效订单（排除了无效的）
//	    取十个数据样本作为首页推荐
	
//	在Java7中，如果要发现type为grocery的所有交易，然后返回以交易值降序排序好的交易ID集合，我们需要这样写：
	public void java7() {
//		List<Transaction> groceryTransactions = new Arraylist<>();
//		for (Transaction t : transactions) {
//			if (t.getType() == Transaction.GROCERY) {
//				groceryTransactions.add(t);
//			}
//		}
//
//		Collections.sort(groceryTransactions, new Comparator() {
//			public int compare(Transaction t1, Transaction t2) {
//				return t2.getValue().compareTo(t1.getValue());
//			}
//		});
//
//		List<Integer> transactionIds = new ArrayList<>();
//		for (Transaction t : groceryTransactions) {
//			transactionsIds.add(t.getId());
//		}
	}
//	而在 Java 8 使用 Stream，代码更加简洁易读；而且使用并发模式，程序执行速度更快。
	public void java8() {
//		List<Integer> transactionsIds = transactions.parallelStream()
//			.filter(t -> t.getType() == Transaction.GROCERY)
//			.sorted(comparing(Transaction::getValue).reversed())
//			.map(Transaction::getId).collect(toList());
	}
	
//	Stream总览
//
//	1、什么是流？
//
//	　　Stream不是集合元素，它不是数据结构并不保存数据，它是有关算法和计算的，它更像一个高级版本的Iterator。原始版本的Iterator，用户只能显式地一个一个遍历元素并对其执行某些操作；高级版本的Stream，用户只要给出需要对其包含的元素执行什么操作，比如，“过滤掉长度大于 10 的字符串”、“获取每个字符串的首字母”等，Stream会隐式地在内部进行遍历，做出相应的数据转换。Stream就如同一个迭代器（Iterator），单向，不可往复，数据只能遍历一次，遍历过一次后即用尽了，就好比流水从面前流过，一去不复返。
//
//	　　而和迭代器又不同的是，Stream可以并行化操作，迭代器只能命令式地、串行化操作。顾名思义，当使用串行方式去遍历时，每个item读完后再读下一个item。而使用并行去遍历时，数据会被分成多个段，其中每一个都在不同的线程中处理，然后将结果一起输出。Stream的并行操作依赖于Java7中引入的Fork/Join框架（JSR166y）来拆分任务和加速处理过程。
//
//	　　Stream 的另外一大特点是，数据源本身可以是无限的。
//	2、流的构成
//
//	　　当我们使用一个流的时候，通常包括三个基本步骤：获取一个数据源（source）→ 数据转换 → 执行操作获取想要的结果。每次转换原有Stream对象不改变，返回一个新的Stream对象（可以有多次转换），这就允许对其操作可以像链条一样排列，变成一个管道，如下图所示:
//		
//	3、Stream的生成方式
//
//	（1）从Collection和数组获得
//
//	    Collection.stream()
//	    Collection.parallelStream()
//	    Arrays.stream(T array) or Stream.of()
//	    
//    （2）从BufferedReader获得
//
//    	java.io.BufferedReader.lines()
//
//    （3）静态工厂
//
//        java.util.stream.IntStream.range()
//        java.nio.file.Files.walk()
//
//    （4）自己构建
//
//        java.util.Spliterator
//
//    （5）其他
//
//        Random.ints()
//        BitSet.stream()
//        Pattern.splitAsStream(java.lang.CharSequence)
//        JarFile.stream()
//	4、流的操作类型
//
//	　　流的操作类型分为两种：
//
//	    Intermediate：一个流可以后面跟随零个或多个intermediate操作。其目的主要是打开流，做出某种程度的数据映射/过滤，然后返回一个新的流，交给下一个操作使用。这类操作都是惰性化的（lazy），就是说，仅仅调用到这类方法，并没有真正开始流的遍历。
//
//	    Terminal：一个流只能有一个terminal操作，当这个操作执行后，流就被使用“光”了，无法再被操作。所以,这必定是流的最后一个操作。Terminal操作的执行，才会真正开始流的遍历，并且会生成一个结果，或者一个side effect。
//
//	    　　在对一个Stream进行多次转换操作(Intermediate 操作)，每次都对Stream的每个元素进行转换，而且是执行多次，这样时间复杂度就是N（转换次数）个for循环里把所有操作都做掉的总和吗？其实不是这样的，转换操作都是lazy的，多个转换操作只会在Terminal操作的时候融合起来，一次循环完成。我们可以这样简单的理解，Stream里有个操作函数的集合，每次转换操作就是把转换函数放入这个集合中，在Terminal 操作的时候循环Stream对应的集合，然后对每个元素执行所有的函数。
//
//	    　　还有一种操作被称为short-circuiting。用以指：对于一个intermediate操作，如果它接受的是一个无限大（infinite/unbounded）的Stream，但返回一个有限的新Stream；对于一个terminal操作，如果它接受的是一个无限大的Stream，但能在有限的时间计算出结果。
//	    当操作一个无限大的 Stream，而又希望在有限时间内完成操作，则在管道内拥有一个short-circuiting操作是必要非充分条件。

	// ----------------------
	// example
	// 简单说，对Stream的使用就是实现一个filter-map-reduce过程，产生一个最终结果，或者导致一个副作用（side effect）。
	// ----------------------
	
	public static void e1() {
		// 1. Individual values
		@SuppressWarnings("unused")
		Stream<String> stream = Stream.of("a", "b", "c");

		// 2. Arrays
		String [] strArray = new String[] {"a", "b", "c"};
		stream = Stream.of(strArray);
		stream = Arrays.stream(strArray);

		// 3. Collections
		List<String> list = Arrays.asList(strArray);
		stream = list.stream();
	}
	
	public static void e2() {
		IntStream.of(new int[]{1, 2, 3}).forEach(System.out::println);
		System.out.println("=========================");
		IntStream.range(1, 3).forEach(System.out::println);
		System.out.println("=========================");
		IntStream.rangeClosed(1, 3).forEach(System.out::println);
	}
	
	public static void e3() {
		Stream<String> stream = Stream.of("a", "b", "c");
		
		// 1. Array
		System.out.println("========================= Array");
		String[] strArray = stream.toArray(String[]::new);
		System.out.println(strArray);
		System.out.println(strArray.getClass());
		// 2. Collection
		System.out.println("========================= List");
		stream = Stream.of("a", "b", "c");
		List<String> list1 = stream.collect(Collectors.toList());
		System.out.println(list1);
		System.out.println(list1.getClass());
		System.out.println("========================= LinkedList");
		stream = Stream.of("a", "b", "c");
		List<String> list2 = stream.collect(Collectors.toCollection(LinkedList::new));
		System.out.println(list2);
		System.out.println(list2.getClass());
		System.out.println("========================= Set");
		stream = Stream.of("a", "b", "c");
		Set<String> set1 = stream.collect(Collectors.toSet());
		System.out.println(set1);
		System.out.println(set1.getClass());
		System.out.println("========================= LinkedHashSet");
		stream = Stream.of("a", "b", "c");
		Set<String> set2 = stream.collect(Collectors.toCollection(LinkedHashSet::new));
		System.out.println(set2);
		System.out.println(set2.getClass());
		System.out.println("========================= Stack");
		stream = Stream.of("a", "b", "c");
		Stack<String> stack = stream.collect(Collectors.toCollection(Stack::new));
		System.out.println(stack);
		System.out.println(stack.getClass());
		// 3. String
		System.out.println("========================= String");
		stream = Stream.of("a", "b", "c");
		String str = stream.collect(Collectors.joining()).toString();
		System.out.println(str);
		System.out.println(str.getClass());
	}
	
//	Intermediate 操作
//
//	　map (mapToInt, flatMap 等)、 filter、 distinct、 sorted、 peek、 limit、 skip、 parallel、 sequential、 unordered
//
//	Terminal 操作
//
//	　forEach、 forEachOrdered、 toArray、 reduce、 collect、 min、 max、 count、 anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、 iterator
//
//	Short-circuiting 操作
//
//	　anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、 limit
	
	//map生成的是个1:1映射，每个输入元素都按照规则转换成为另外一个元素。
	public static void e4() {
		List<Integer> nums = Arrays.asList(1, 2, 3, 4);
		List<Integer> squareNums = nums.stream().map(n -> n * n)
		.collect(Collectors.toList());
		System.out.println(squareNums);
	}
	
	//flatMap把inputStream中的层级结构 扁平化，就是将最底层元素抽出来放到一起，最终output的新Stream里面已经没有List了，都是直接的数字。
	public static void e5() {
		Stream<List<Integer>> inputStream = Stream.of(
				 Arrays.asList(1),
				 Arrays.asList(2, 3),
				 Arrays.asList(4, 5, 6)
				 );
		Stream<Integer> outputStream = inputStream.flatMap((childList) -> childList.stream());
		outputStream.forEach(System.out::println);
		System.out.println("=========================");
		inputStream = Stream.of(
				 Arrays.asList(1),
				 Arrays.asList(2, 3),
				 Arrays.asList(4, 5, 6)
				 );
		inputStream.flatMap((childList) -> childList.stream()).map(n -> n).forEach(System.out::println);
	}
	
	//filter对原始Stream进行某项测试，通过测试的元素被留下来生成一个新Stream。
	public static void e6() {
		// 留下偶数
		Integer[] sixNums = {1, 2, 3, 4, 5, 6};
		Integer[] evens =
		Stream.of(sixNums).filter(n -> n%2 == 0).toArray(Integer[]::new);
		Arrays.stream(evens).forEach(System.out::println);
	}
	
	//forEach方法接收一个Lambda表达式，然后在Stream的每一个元素上执行该表达式。
	public static void e7() {
		// 对一个人员集合遍历，找出男性并打印姓名。
//		roster.stream().filter(p -> p.getGender() == Person.Sex.MALE)
//		.forEach(p -> System.out.println(p.getName()));
		
		// peek 对每个元素执行操作并返回一个新的 Stream
		List<String> list = Stream.of("one", "two", "three", "four").parallel().filter(e -> e.length() > 3)
		 .peek(e -> System.out.println("Filtered value: " + e)).map(String::toUpperCase)
		 .peek(e -> System.out.println("Mapped value: " + e)).collect(Collectors.toList());
		System.out.println(list);
		
//		forEach 不能修改自己包含的本地变量值，也不能用break/return之类的关键字提前结束循环。
	}
	
//	findFirst
//	这是一个termimal兼short-circuiting操作，它总是返回Stream的第一个元素或者空。
//	这里比较重点的是它的返回值类型Optional:这也是一个模仿 Scala 语言中的概念，作为一个容器，它可能含有某值，或者不包含,
//	使用它的目的是尽可能避免NullPointerException。
//	Stream中的findAny、max/min、reduce等方法等返回Optional值。还有例如IntStream.average()返回OptionalDouble等等。
	public static void e8() {
		// Optional 的两个用例:以下两组示例是等价的
		String text = "abcdefg";

		// Java 8
		Optional.ofNullable(text).ifPresent(System.out::println);

		// Pre-Java 8
		if (text != null) {
			System.out.println(text);
		}

		// ----------

		// Java 8
		Optional.ofNullable(text).map(String::length).orElse(-1);

		// Pre-Java 8
		@SuppressWarnings("unused")
		int ret = (text != null) ? text.length() : -1;
		
		Stream.of("one", "two", "three", "four").parallel().findFirst().ifPresent(System.out::println);
	}
	
//	reduce
//	这个方法的主要作用是把Stream元素组合起来。
//	它提供一个起始值（种子），然后依照运算规则（BinaryOperator），和前面Stream的第一个、第二个、第n个元素组合。
//	从这个意义上说，字符串拼接、数值的 sum、min、max、average都是特殊的reduce。
//	例如Stream的sum就相当于：
//	Integer sum = integers.reduce(0, (a, b) -> a+b);
//	Integer sum = integers.reduce(0, Integer::sum);
	public static void e9() {
		IntStream st = IntStream.rangeClosed(1, 100);
		Integer sum = st.reduce(0, (a, b) -> a+b);
		System.out.println(sum);
		System.out.println("=========================");
		st = IntStream.rangeClosed(1, 100);
		sum = st.reduce(0, Integer::sum);
		System.out.println(sum);
		System.out.println("=========================");
		
		// reduce 的用例

		// 字符串连接，concat = "ABCD"
		String concat = Stream.of("A", "B", "C", "D").reduce("", String::concat);
		System.out.println(concat);
		System.out.println("=========================");

		// 求最小值，minValue = -3.0
		double minValue = Stream.of(-1.5, 1.0, -3.0, -2.0).reduce(Double.MAX_VALUE, Double::min); 
		System.out.println(minValue);
		System.out.println("=========================");

		// 求和，sumValue = 10, 有起始值
		int sumValue = Stream.of(1, 2, 3, 4).reduce(0, Integer::sum);
		System.out.println(sumValue);
		System.out.println("=========================");

		//也有没有起始值的情况，这时会把 Stream 的前面两个元素组合起来，返回的是 Optional。
		// 求和，sumValue = 10, 无起始值
		sumValue = Stream.of(1, 2, 3, 4).reduce(Integer::sum).get();
		System.out.println(sumValue);
		System.out.println("=========================");

		// 过滤，字符串连接，concat = "ace"
		concat = Stream.of("a", "B", "c", "D", "e", "F").
		 filter(x -> x.compareTo("Z") > 0).
		 reduce("", String::concat);
		System.out.println(concat);
	}
	
	// limit返回Stream的前面n个元素；skip则是扔掉前n个元素（它是由一个叫 subStream的方法改名而来）。
	//limit 和 skip 对运行次数的影响
	public static void e10() {
		StreamTest me = new StreamTest();
		List<Person> persons = new ArrayList<>();
		for (int i = 1; i <= 10000; i++) {
			Person person = me.new Person(i, "name" + i);
			persons.add(person);
		}
		List<String> personList = persons.stream().map(Person::getName)
				.limit(10).skip(3).collect(Collectors.toList());
		System.out.println(personList);
	}

	private class Person {
		@SuppressWarnings("unused")
		public int no;
		private String name;
		private int age;

		public Person(int no, String name) {
			this.no = no;
			this.name = name;
		}
		
		public Person(int no, String name, int age) {
			this.no = no;
			this.name = name;
			this.age = age;
		}

		public String getName() {
			System.out.println(name);
			return name;
		}
		
		public int getAge() {
			return age;
		}	
	}
	
//	这是一个有10，000个元素的Stream，但在short-circuiting操作limit和skip的作用下，管道中map操作指定的getName()方法的执行次数为 limit 所限定的10次，而最终返回结果在跳过前3个元素后只有后面7个返回。
//	有一种情况是limit/skip无法达到short-circuiting目的的，就是把它们放在Stream的排序操作后，原因跟sorted这个intermediate操作有关：此时系统并不知道Stream排序后的次序如何，所以sorted中的操作看上去就像完全没有被limit或者skip一样。

	// limit 和 skip 对 sorted 后的运行次数无影响
	public static void e11() {
		StreamTest me = new StreamTest();
		List<Person> persons = new ArrayList<>();
		for (int i = 1; i <= 5; i++) {
			Person person = me.new Person(i, "name" + i);
			persons.add(person);
		}
		List<Person> personList = persons.stream()
				.sorted((p1, p2) -> p1.getName().compareTo(p2.getName()))
				.limit(2)
				.collect(Collectors.toList());
		System.out.println(personList);
		System.out.println("=========================");
		List<String> personList2 = personList.stream().map(Person::getName).collect(Collectors.toList());
		System.out.println(personList2);
	}
	
//	sorted
//	对Stream的排序通过sorted进行，
//	它比数组的排序更强之处在于你可以首先对Stream进行各类map、filter、limit、skip甚至distinct来减少元素数量后再排序，
//	这能帮助程序明显缩短执行时间。例如：
	public static void e12() {
		StreamTest me = new StreamTest();
		// 优化：排序前进行 limit 和 skip
		List<Person> persons = new ArrayList<>();
		for (int i = 1; i <= 5; i++) {
			Person person = me.new Person(i, "name" + i);
			persons.add(person);
		}

		List<Person> personList = persons.stream().limit(2)
				.sorted((p1, p2) -> p1.getName().compareTo(p2.getName()))
				.collect(Collectors.toList());
		System.out.println(personList);
	}
	
//	min/max/distinct
//	min和max的功能也可以通过对Stream元素先排序，再findFirst来实现，但前者的性能会更好为O(n)，而sorted的成本是O(nlogn)。同时它们作为特殊的reduce方法被独立出来也是因为求最大最小值是很常见的操作。
	public static void e13() throws IOException {
		// 找出最长一行的长度
		BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\wsm\\Desktop\\备份\\api-service.log.2019-02-19"));
		int longest = br.lines().parallel().mapToInt(String::length).max().getAsInt();
		br.close();
		System.out.println(longest);
	}
	
//	distinct
//	下面的例子则使用distinct来找出不重复的单词。
	public static void e14() throws IOException {
		// 找出全文的单词，转小写，并排序
		BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\wsm\\Desktop\\备份\\api-service.log.2019-02-19"));
		List<String> words = br.lines().flatMap(line -> Stream.of(line.split(" ")))
		 .filter(word -> word.length() > 0).map(String::toLowerCase).distinct().sorted()
		 .collect(Collectors.toList());
		br.close();
		System.out.println(words);
		System.out.println("=========================");
		words.stream().forEach(System.out::println);
	}
	
//	Match
//
//	　Stream有三个match方法，从语义上说：
//
//	　(1).allMatch：Stream 中全部元素符合传入的 predicate，返回 true;
//	　(2).anyMatch：Stream 中只要有一个元素符合传入的 predicate，返回 true;
//	　(3).noneMatch：Stream 中没有一个元素符合传入的 predicate，返回 true.
	public static void e15() throws IOException {
		StreamTest me = new StreamTest();
		// 使用 Match
		List<Person> persons = new ArrayList<>();
		persons.add(me.new Person(1, "name" + 1, 10));
		persons.add(me.new Person(2, "name" + 2, 21));
		persons.add(me.new Person(3, "name" + 3, 34));
		persons.add(me.new Person(4, "name" + 4, 6));
		persons.add(me.new Person(5, "name" + 5, 55));

		boolean isAllAdult = persons.stream().allMatch(p -> p.getAge() > 18);
		System.out.println("All are adult? " + isAllAdult);
		boolean isThereAnyChild = persons.stream().anyMatch(p -> p.getAge() < 12);
		System.out.println("Any child? " + isThereAnyChild);
		boolean isThereNoElder = persons.stream().noneMatch(p -> p.getAge() > 60);
		System.out.println("No Elder? " + isThereNoElder);
	}
	
//	所有Stream的操作必须以lambda表达式为参数
	public static void main(String[] args) throws IOException {
//		StreamTest st = new StreamTest();
//		st.findG1(st.initData());
//		st.findG2(st.initData());
//		st.findG3(st.initData());
		
		e15();
	}
}
