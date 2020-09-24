package com.boot_demo.demo1.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZhonghaoMa
 * @Description:
 * @date 2020/3/1610:56 上午
 */
public class JsonUtil {
    private static ObjectMapper objMapper = null;

    static {
        objMapper = new ObjectMapper();
        objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    }

    public JsonUtil() {
    }

    public static String objectToString(Object obj) {
        if (obj == null) {
            return null;
        } else {
            String jsonObj = null;

            try {
                jsonObj = objMapper.writeValueAsString(obj);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return jsonObj;
        }
    }

    public static <T> T StringToObject(String jsonObj, Class<T> clazz) {
        T obj=null;
        if (StringUtils.isEmpty(jsonObj)) {
            return null;
        } else {
            try {
                obj = objMapper.readValue(jsonObj, clazz);
            } catch (Exception var4) {
                var4.printStackTrace();
            }
            return obj;
        }
    }

    public static <T> T StringToObject(String jsonObj, TypeReference<T> type) {
        T obj=null;
        if (StringUtils.isEmpty(jsonObj)) {
            return null;
        } else {
            try {
                obj = objMapper.readValue(jsonObj, type);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return obj;
        }
    }


    public static List<T> StringToList(String jsonObj, Class<?>... clazz) {
        if (StringUtils.isEmpty(jsonObj)) {
            return null;
        } else {
            JavaType var2 = objMapper.getTypeFactory().constructParametricType(ArrayList.class, clazz);
            List list = null;

            try {
                list = (List) objMapper.readValue(jsonObj, var2);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return list;
        }
    }

    public static String getValue(String var0, String var1) {
        if (StringUtils.isEmpty(var0) && StringUtils.isNotEmpty(var1)) {
            return null;
        } else {
            String var2 = null;
            try {
                JsonNode var3 = objMapper.readTree(var0);
                var3 = var3.findPath(var1);
                if (var3 != null) {
                    var2 = var3.asText();
                }
            } catch (Exception var4) {
                var4.printStackTrace();
            }
            return var2;
        }
    }

}
