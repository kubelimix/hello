package com.limix.hello.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 观察一下ArrayBlockQueue和LinkBlockingQueue 之间的区别和联系
 * 
 * @author limix
 */
public class BlockingQueueDemo {

	final static BlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>();

	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		Runnable producer = new Runnable() {
			@Override
			public void run() {
				int i = 0;
				while (i < 1000) {
					queue.offer(i);
					System.out.println("生产.." + i);
					i++;
					
					if (i%100==0){
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		};

		Runnable reducer = new Runnable() {
			@Override
			public void run() {
				int i = 0;
				while (i < 10) {
					System.out.println("消费.." + queue.poll());
					i++;
				}
			}
		};
		executor.execute(producer);
		executor.execute(reducer);
		executor.shutdown();
	}
}
