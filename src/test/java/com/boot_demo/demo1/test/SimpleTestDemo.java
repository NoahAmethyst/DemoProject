package com.boot_demo.demo1.test;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

public class SimpleTestDemo {


    @Test
    public void test() {
        System.out.println("101569_20200922161242179".length());
    }


    @Test
    public void md5Test() {
        String encodeStr = md5Encode("6007720555994071425c3548981ad0e23c8bd4203b7051abe7b420092218540713760");
        System.out.println(encodeStr);

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
