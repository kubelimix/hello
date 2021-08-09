package com.limix.sample;

import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class CommandLineProcess1 {

    public static String command = "sqoop eval –connect \"limix\" jdbc:hsqldb:file:db.hsqldb –query \"select * from customers limit 2\"";

    public static void main(String[] args) {
        ProtectionDomain pd1 = CommandLineProcess1.class.getProtectionDomain();
        ProtectionDomain pd2 = CommandLineProcess2.class.getProtectionDomain();
        Thread.currentThread().getClass().getResource("classpath:text.txt");
        boolean result = pd1.equals(pd2);
        System.out.println(pd1);
    }
}
