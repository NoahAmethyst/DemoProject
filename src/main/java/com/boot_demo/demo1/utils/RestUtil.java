package com.boot_demo.demo1.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.*;
import java.util.Map;

@Component
@Slf4j
public class RestUtil {
    @Resource
    private RestTemplate restTemplate;


    /**
     * Do json post
     *
     * @param url
     * @param params
     * @param resType
     * @param <T>
     * @return
     */
    public <T> T doJsonPost(String url, String params, Class<T> resType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(params, headers);
//        log.info("\n消息请求url:{}\n参数:{}", url, params);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        String responseStr = response.getBody();
//        log.info("返回数据:{}", responseStr);
        return JSON.parseObject(responseStr, resType);
    }

    /**
     * Do format-data post
     *
     * @param url
     * @param params
     * @param resType
     * @param <T>
     * @return
     */
    public <T> T doFormatDataPost(String url, String params, Class<T> resType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        Map<String, String> paramsMap = JSONObject.parseObject(params
                , new TypeReference<Map<String, String>>() {
                });
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        if (!CollectionUtils.isEmpty(paramsMap)) {
            paramsMap.forEach((key, value) -> {
                requestBody.add(key, value);
            });
        }
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestBody, headers);
//        log.info("\n消息请求url:{}\n参数:{}", url, params);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        String responseStr = response.getBody();
//        log.info("返回数据:{}", responseStr);
        return JSON.parseObject(responseStr, resType);
    }


    public File getNetFile(String url) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        String path = "src/main/resources/temp/";
        File file = new File(path + MyStringUtil.getFileName(url));
        try {
            ResponseEntity<byte[]> response = restTemplate.getForEntity(url, byte[].class);
            byte[] result = response.getBody();
            inputStream = new ByteArrayInputStream(result);
            if (!file.exists()) {
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = inputStream.read(buf, 0, 1024)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

}
