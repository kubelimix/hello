package com.limix.jvm.memory;

import java.util.ArrayList;
import java.util.List;

/**
 * -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 */
public class HeapOOM {

    static class OOMObject {

        private String  id;

        private String text;

        private String day;

        public OOMObject(String id, String text, String day){
            this.id = id;
            this.text = text;
            this.day = day;
        }
    }

    /**
     * 比对差异报告
     * OOMObject 11430360/285759=40 对象头信息+字符信息
     * String 24003756/857277=28 字符串对象的额外开销
     * char[] 54865692/857277=63.9999
     * "342426199012083218" 消耗空间是 60
     * "342426199012083218day" 消耗控件是 66
     * 相当于每个字符消耗 2个字节
     * char[] 额外开销是60-18*2=24个字节消耗
     * 很奇怪,为什么对象也多了那么多
     * 而且对象内的char是独有的,因此如果
     * @param args
     */
    public static void main(String[] args){
        List<OOMObject> list = new ArrayList<OOMObject>();
        System.out.println("I am here");
        for (long i= 342426199012083218l ; i< 342426199022083218l; i++){
            list.add(new OOMObject(i+"", i+""+"day", i+""+"day"));
        }
    }
}
