package com.limix.jvm.monitor;

public class MonitorDemo {

    private int a = 0;

    public synchronized void writer() {
        a++;
    }

    public synchronized void reader() {
        int i = a;
    }

    public static void main(String[] args) throws InterruptedException {
        MonitorDemo demo = new MonitorDemo();
        Thread threadA = new Thread(()->{
            demo.a = 1;
            System.out.println("ThreadA" + demo.a);
        });
        Thread threadB = new Thread(()->{
            demo.writer();
            System.out.println("ThreadB" + demo.a);
        });
        threadA.start();
        threadB.start();
    }

}