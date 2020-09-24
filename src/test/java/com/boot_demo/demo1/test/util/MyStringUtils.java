package com.boot_demo.demo1.test.util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * @Description
 * @Date 2020/5/14
 * @Created by ZhonghaoMa
 */
public class MyStringUtils {

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


}
