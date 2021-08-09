package com.limix.hello;

/**
 * Hello world!
 * 测试代码的编辑效果,感觉编译的很快啊
 */
public class App {
	public static void main(String[] args) {
		String[] altCars = {"途观L", "汉兰达", "自由光"};
		for (int i=0; i<1000; i++) {
			int num = (int) (Math.random() * 3);
			System.out.println("选择" + altCars[num]);
		}
	}
}
