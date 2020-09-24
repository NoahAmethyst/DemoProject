package com.boot_demo.demo1.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ZhonghaoMa
 * @Description:
 * @date 2020/4/302:40 下午
 */
@Slf4j
public class ObjectUtil {
    /**
     * @Description: 获取一个实例化对象的所有属性和值
     * @Author: Amethyst
     * @Params: [obj]
     * @return: java.util.Map
     * @Date: 2019/10/16 10:41
     * @Update by:
     */
    public static Map<String, Object> ObjectToMap(Object obj) {

        Map<String, Object> propertiesMap = new HashMap<>();

        Field[] superFields = getSuperFields(obj);
        Field[] fields = obj.getClass().getDeclaredFields();
        if (superFields.length != 0) {
            fields = (Field[]) ArrayUtils.addAll(fields, superFields);
        }
        for (int i = 0, len = fields.length; i < len; i++) {
            // 对于每个属性，获取属性名
            String varName = fields[i].getName();
            try {
                // 获取原来的访问控制权限
                boolean accessFlag = fields[i].isAccessible();
                // 修改访问控制权限
                fields[i].setAccessible(true);
                // 获取在对象f中属性fields[i]对应的对象中的变量
                Object o;
                try {
                    o = fields[i].get(obj);
                    propertiesMap.put(varName, o);
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    log.error("convert object to map error", e);
                }
                // 恢复访问控制权限
                fields[i].setAccessible(accessFlag);
            } catch (IllegalArgumentException ex) {
                log.error("convert object to map error", ex);
            }
        }
        return propertiesMap;
    }

    public static Field[] getSuperFields(Object obj) {
        Field[] fields = null;
        Class superClazz = obj.getClass().getSuperclass();
        if (superClazz != null) {
            fields = superClazz.getDeclaredFields();
        }
        return fields;

    }


}
