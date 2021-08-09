package com.limix.jvm.stream;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamDemo {

    public static class Tuple<V1, V2> {
        private final V1 v1;
        private final V2 v2;

        public static <V1, V2> Tuple<V1, V2> tuple(V1 v1, V2 v2) {
            return new Tuple(v1, v2);
        }

        public Tuple(V1 v1, V2 v2) {
            this.v1 = v1;
            this.v2 = v2;
        }

        public V1 v1() {
            return this.v1;
        }

        public V2 v2() {
            return this.v2;
        }
    }

    /**
     * JDK的流式处理样例
     * @param args 参数
     */
    public static void main(String[] args) {
        List<String> data = new ArrayList<String>();
        data.add("Hello World Bye World");
        data.add("Hello Hadoop Goodbye Hadoop");

        // 统计每个单词出现的次数

        // 基于JDK Stream 来统计每个单词出现的次数-Java缺少Tuple元组支持
        Stream<String> wordStream = data.stream().flatMap(line -> Arrays.stream(line.split(" ")));
        // 分类并且收集结果
        Map<String, Long> perWordCount = wordStream.collect(Collectors.groupingBy(s -> s, Collectors.counting()));
        perWordCount.forEach((key, value) -> System.out.println(String.format("%s-%s", key, value)));
    }
}