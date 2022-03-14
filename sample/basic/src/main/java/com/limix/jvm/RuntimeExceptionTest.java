package com.limix.jvm;

public class RuntimeExceptionTest {

    public static void main(String[] args){

        try {
            System.out.println("I am here1");
            if (true){
                 throw new IllegalStateException();
            }
        } catch (Exception e) {

        }
        System.out.println("I am here2");

    }
}
