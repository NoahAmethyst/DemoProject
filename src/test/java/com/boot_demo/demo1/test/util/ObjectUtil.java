package com.boot_demo.demo1.test.util;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author ZhonghaoMa
 * @Description:
 * @date 2020/4/30
 */
public class ObjectUtil {

    private static Logger logger=LoggerFactory.getLogger(ObjectUtil.class);

    /**
     * get fields and values from selected object
     *
     * @param obj
     * @return
     */
    public static Map<String, Object> objectToMap(Object obj) {

        if (obj == null) {
            return null;
        }

        Map<String, Object> propertiesMap = new HashMap<>();

        Field[] superFields = getSuperFields(obj);
        Field[] fields = obj.getClass().getDeclaredFields();
        if (superFields.length != 0) {
            fields = (Field[]) ArrayUtils.addAll(fields, superFields);
        }
        for (int i = 0, len = fields.length; i < len; i++) {
            // get field name
            String varName = fields[i].getName();
            try {
                // get right of access control
                boolean accessFlag = fields[i].isAccessible();
                // edit right of access control
                fields[i].setAccessible(true);
                // get value of field
                Object o;
                try {
                    o = fields[i].get(obj);
                    propertiesMap.put(varName, o);
                } catch (IllegalAccessException e) {
                    logger.error(e.getMessage());
                }
                // rollback right of access control
                fields[i].setAccessible(accessFlag);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            }
        }
        return propertiesMap;
    }

    /**
     * get fields and values by string type from selected object
     *
     * @param obj
     * @return
     */
    public static Map<String, String> objectToStringMap(Object obj) {

        if (obj == null) {
            return null;
        }

        Map<String, String> propertiesMap = new HashMap<>();

        Field[] fields = obj.getClass().getDeclaredFields();

        for (int i = 0, len = fields.length; i < len; i++) {
            // get field name
            String varName = fields[i].getName();
            try {
                // get right of access control
                boolean accessFlag = fields[i].isAccessible();
                // edit right of access control
                fields[i].setAccessible(true);
                // get value of field
                String value;
                Object o;
                try {
                    o = fields[i].get(obj);
                    if (o != null) {
                        value = o.toString();
                        propertiesMap.put(varName, value);
                    }
                } catch (IllegalAccessException e) {
                    logger.error(e.getMessage());
                }
                // rollback right of access control
                fields[i].setAccessible(accessFlag);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            }
        }
        return propertiesMap;
    }

    /**
     * sort by key
     * same as PHP method ksort()
     * @param map
     * @return
     */
    public static String ksort(Map<String, String> map) {
        if (CollectionUtils.isEmpty(map)) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        Set<String> set = map.keySet();
        int size = map.size();
        if (set.contains("signature")) {
            size -= 1;
        }
        String[] keys = new String[size];
        int index = 0;
        for (String k :set) {
            if (k.equalsIgnoreCase("signature")) {
                continue;
            }
            keys[index] = k;
            index++;
        }
        Arrays.sort(keys);
        for (String key : keys) {
            sb.append(map.get(key));
        }
        return sb.toString();
    }

    /**
     * get superClass's fields and values
     *
     * @param obj
     * @return
     */
    public static Field[] getSuperFields(Object obj) {
        Field[] fields = null;
        Class superClazz = obj.getClass().getSuperclass();
        if (superClazz != null) {
            fields = superClazz.getDeclaredFields();
        }
        return fields;
    }

}
