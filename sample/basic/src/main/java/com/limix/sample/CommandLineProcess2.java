package com.limix.sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandLineProcess2 {

    public static String command = "sqoop eval –connect \"limix\" jdbc:hsqldb:file:db.hsqldb –query \"select * from customers limit 2\"";

    public static void main(String[] args) {
        Scanner in = new Scanner(command);

        List<String> results = new ArrayList<String>();
        boolean enter = false;
        List<String> tmpList = new ArrayList<String>();
        while (in.hasNext()) {
            String item = in.next();
            // 开始
            if (item.startsWith("\"")){
                if (item.endsWith("\"")){
                    item = item.substring(1, item.length() -1);
                    results.add(item);
                } else {
                    tmpList.clear();
                    tmpList.add(item.substring(1));
                    enter = true;
                }
                continue;
            } else if (item.endsWith("\"")){
               enter = false;
               tmpList.add(item.substring(0,item.length()-1));
               results.add(String.join(" ", tmpList));
               continue;
            } else {
               if (enter){
                   tmpList.add(item);
               } else {
                   results.add(item);
               }
            }
        }
        for (String result : results){
            System.out.println(result);
        }
    }
}
