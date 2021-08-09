package com.limix.sample;

import org.apache.commons.lang3.StringUtils;
import java.util.concurrent.*;

public class StringUtilTest {
    public static void main(String[] args) {
        System.out.println(StringUtils.isBlank(" \r\n  \n   \r \n\r "));
        System.out.println("there is always a better way");
    }
}
