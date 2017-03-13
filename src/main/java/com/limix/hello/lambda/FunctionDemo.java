/**
 * 
 */
package com.limix.hello.lambda;

import java.util.Arrays;
import java.util.function.Function;

/**
 * a function that accepts one argument and produces a result
 * 函数式接口
 * @see java.util.function.Function
 * @author limix
 */
public class FunctionDemo {

	static void modifyTheValue(int valueToBeOperated, Function<Integer, Integer> function) {
		int newValue = function.apply(valueToBeOperated);
		System.out.println(newValue);
	}

	/**
	 * JAVA8函数式编程样例
	 * 
	 * @param args 参数
	 */
	public static void main(String[] args) {
		int incr = 20;
		int myNumber = 10;
		modifyTheValue(myNumber, val -> val + incr);
		myNumber = 15;
		modifyTheValue(myNumber, val -> val * 10);
		modifyTheValue(myNumber, val -> val - 100);
		modifyTheValue(myNumber, val -> "somestring".length() + val - 100);
		String[] datas = new String[] { "peng", "zhao", "li" };
		// lambda表达式对应的是接口 (v1,v2) 省略了参数的类型,是因为编译器可以推测出参数类型
		Arrays.sort(datas, (String v1, String v2) -> Integer.compare(v1.length(), v2.length())); // version 1
		Arrays.sort(datas, (v1, v2) -> Integer.compare(v1.length(), v2.length()));// version 2
	}
}
