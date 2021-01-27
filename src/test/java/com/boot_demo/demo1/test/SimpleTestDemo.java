package com.boot_demo.demo1.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import java.util.*;

@Slf4j
public class SimpleTestDemo {

    @Test
    public void simpleTest() {
        String value = "dz0WsVKYiEyvtoZ2Iat19u:APA91bE-uZ6jcRSRMGrlRsyKsnZbpYYmGgaae5VbjfpwOrAXUch3TOmBIUVe4m6OfF7wWhjjd-9cNBr2UnPAALrLZTs_9Rbpzjw-SbaOwOtGTkgLuXo6xZkHT-Gt6wv760vlkMq8PXPi";
        String value2 = "dz0WsVKYiEyvtoZ2Iat19u:APA91bE-uZ6jcRSRMGrlRsyKsnZbpYYmGgaae5VbjfpwOrAXUch3TOmBIUVe4m6OfF7wWhjjd-9cNBr2UnPAALrLZTs_9Rbpzjw-SbaOwOtGTkgLuXo6xZkHT-Gt6wv760vlkMq8PXPi";
        System.out.println(value.equals(value2));
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
