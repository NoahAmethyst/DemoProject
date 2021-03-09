package com.boot_demo.demo1.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.*;

@Slf4j
public class SimpleTestDemo {

    @Test
    public void simpleTest() throws UnsupportedEncodingException {

        int x = 5349053;

        String s = "20000000000000000000";

        log.info("{}", myAtoi(s));

    }

    public int myAtoi(String s) {
        return 0;
    }


    @Test
    public void md5Test() {
        String encodeStr = md5Encode("2001");
        System.out.println(encodeStr);
    }

    @Test
    public void listTest() {
        Map<List, String> map = new HashMap<>();
        List<String> linkedList = new LinkedList<>();
        List<String> arrayList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            linkedList.add(String.valueOf(i));
            arrayList.add(String.valueOf(i));
        }

        map.put(linkedList, "a");
        map.put(arrayList, "b");
        map.forEach((key, value) -> {
            log.info(value);
        });
    }

    /**
     * Md5encode
     */

    public static String md5Encode(String content, String key) {
        String encodeStr = DigestUtils.md5Hex(content + key);
        return encodeStr;
    }


    public static String md5Encode(String content) {
        String encodeStr = DigestUtils.md5Hex(content);
        return encodeStr;
    }

    /**
     * Md5encode
     */

    public static String md5Encode(String content, String key, int times) {
        String encodeStr = md5Encode(content, key);
        for (int i = 1; i < times; i++) {
            encodeStr = md5Encode(encodeStr, key);
        }
        return encodeStr;
    }


    public static String md5Encode(String content, int times) {
        String encodeStr = md5Encode(content);
        for (int i = 1; i < times; i++) {
            encodeStr = md5Encode(encodeStr);
        }
        return encodeStr;
    }


}
