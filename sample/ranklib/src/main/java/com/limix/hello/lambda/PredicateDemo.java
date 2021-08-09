package com.limix.hello.lambda;

import java.util.function.Predicate;

/**
 * Predicate boolean-valued function of one argument
 * 只有一个参数的布尔函数
 * @see java.util.function.Predicate 表示的是 FunctionalInterface
 * @author limix
 */
public class PredicateDemo {

	public static void main(String[] args) {
		Person p = new Person(true);
		System.out.println(isBad(p, t -> t.isBad));
	}

	public static boolean isBad(Person p, Predicate<Person> t) {
		return t.test(p);
	}

	static class Person {
		boolean isBad = false;

		public Person(boolean isBad) {
			this.isBad = isBad;
		}
	}
}
