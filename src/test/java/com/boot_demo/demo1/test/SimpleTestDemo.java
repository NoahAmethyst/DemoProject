package com.boot_demo.demo1.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

@Slf4j
public class SimpleTestDemo {

    @Test
    public void simpleTest() {
    }


    @Test
    public void md5Test() {
        String encodeStr = md5Encode("6007720555994071425c3548981ad0e23c8bd4203b7051abe7b420092218540713760");
        System.out.println(encodeStr);

    }

    @Test
    public void listTest() {
        try {

            int i = 1 / 0;
            System.out.println(i);
            System.out.println("this is a test");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
