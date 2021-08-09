package com.limix.jvm;

public class LockTest {
    public static void main(String[] args) throws InterruptedException {
        Thread A = new Thread(new Thread_(), "线程A");
        A.setUncaughtExceptionHandler(new ThreadException());
        A.start();
 
    }
}
class Thread_ implements Runnable{
    @Override
    public void run() {
        System.out.println("我准备抛出异常，你能接到吗？");
        throw new RuntimeException();
 
    }
}
class ThreadException implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("已经捕获到异常信息"+e.toString()+"可以进行处理");
    }
}