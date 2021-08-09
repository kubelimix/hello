package com.limix.hello.concurrent;

/**
 * 编写通用Java
 */
public class DeamonClass {
    public static class TestDemo2 {
        public static void main(String[] args) throws InterruptedException
        {
            Runnable tr = new Runnable(){
                @Override
                public void run() {
                    System.out.println("nothing");
                }
            };
            Thread thread = new Thread(tr);
            thread.setDaemon(true); //设置守护线程
            thread.start(); //开始执行分进程
        }
    }
}
