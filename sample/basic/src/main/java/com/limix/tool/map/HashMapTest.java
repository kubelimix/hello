package com.limix.tool.map;

import java.util.HashMap;
import java.util.Map;

/**
 * HashMap及其测试
 */
public class HashMapTest {

    /**
     * 测试HashMap及其相关处理逻辑
     * @param args 参数
     */
    public static void main(String[] args) {
        Map<String, String> demo = new HashMap<String, String>(10);
        for (int i = 0; i < 100000; i++) {
            demo.put(i + "", "1");
        }
        System.out.println(demo);
    }
}