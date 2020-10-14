package com.boot_demo.demo1.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Description
 * @Date 2020/5/14
 * @Created by ZhonghaoMa
 */
public class MyStringUtil {

    private static String[] SPILT_PUNCTUATE = {",", ".", " ", "，", "。", "-", "_"};

    public static Set<String> converStrToSet(String content) {
        Set<String> splitedSet = new HashSet<>();
        if (StringUtils.isEmpty(content)) {
            return splitedSet;
        }
        for (String punctuate : SPILT_PUNCTUATE) {
            if (content.contains(punctuate)) {
                String[] splitedContent = content.split(punctuate);
                for (String single : splitedContent) {
                    splitedSet.add(single);
                }
            }
        }
        if (CollectionUtils.isEmpty(splitedSet)) {
            splitedSet.add(content);
        }
        return splitedSet;
    }

    public static List<String> converStrToList(String content, String splitSymbol) {
        List<String> list = new ArrayList<>();
        if (StringUtils.isEmpty(content)) {
            return list;
        }
        for (String line : content.split(splitSymbol)) {
            list.add(line);
        }
        return list;
    }

    /**
     * only reveal left content , index decide the number of revealed char
     *
     * @param fieldContent
     * @param index
     * @return
     */
    public static String revealLeft(String fieldContent, int index) {
        if (StringUtils.isEmpty(fieldContent)) {
            return fieldContent;
        }
        String hideContent = StringUtils.left(fieldContent, index);
        return StringUtils.rightPad(hideContent, StringUtils.length(fieldContent), "*");
    }

    /**
     * only reveal right content , index decide the number of revealed char
     *
     * @param fieldContent
     * @param end
     * @return
     */
    public static String revealRight(String fieldContent, int end) {
        if (StringUtils.isEmpty(fieldContent)) {
            return fieldContent;
        }
        String hideContent = StringUtils.right(fieldContent, end);
        return StringUtils.leftPad(hideContent, StringUtils.length(fieldContent), "*");
    }


    /**
     * reveal left and right cotent both,index and end decide the number of revealed char
     *
     * @param fieldContent
     * @param index
     * @param end
     * @return
     */
    public static String revealRound(String fieldContent, int index, int end) {
        if (StringUtils.isEmpty(fieldContent)) {
            return fieldContent;
        }
        String hideContent = StringUtils.left(fieldContent, index).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(fieldContent, end),
                StringUtils.length(fieldContent), "*"), "***"));
        return hideContent;
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

    public static String getFileName(String url) {
        if (StringUtils.isEmpty(url)) {
            return url;
        }
        String newUrl = url;
        newUrl = newUrl.split("[?]")[0];
        String[] bb = newUrl.split("/");
        String fileName = bb[bb.length - 1];
        return fileName;
    }


}
