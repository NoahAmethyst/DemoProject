package com.boot_demo.demo1.test.util;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import sun.misc.BASE64Decoder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;


public class OSSClientUtil {


    private static String endpoint;
    private static String accessKeyId;
    private static String accessKeySecret;

    private static final String BUCKET_NAME = "BUCKET_NAME";
    private static final String BASE_LINK_URL = "BASE_LINK_URL";
    private static final String ROOT = "ROOT/";

    public static final String HEAD_IMG_ROOT = "HEAD_IMG_ROOT";

    public static final String COUPON_PATH = "COUPON_PATH";


    public static String uploadFile(String fileName, String content) {

        try {
            endpoint = "http://****.aliyuncs.com";
            accessKeyId = "*****";
            accessKeySecret = "*****";

            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            if (!ossClient.doesBucketExist(BUCKET_NAME)) {
                ossClient.createBucket(BUCKET_NAME);
            }
            BASE64Decoder decoder = new BASE64Decoder();
            InputStream is = new ByteArrayInputStream(decoder.decodeBuffer(content));
            ossClient.putObject(BUCKET_NAME, ROOT + COUPON_PATH + fileName, is);
//            System.out.println("upload to oss success:" + BUCKET_NAME + "/" + ROOT + COUPON_PATH + fileName);
            return BASE_LINK_URL + ROOT + COUPON_PATH + fileName;
        } catch (OSSException e) {
            throw new RuntimeException("upload file to Aliyun OSS failed:" + e.getMessage());
        } catch (ClientException e) {
            throw new RuntimeException("upload file to Aliyun OSS failed:" + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("upload file to Aliyun OSS failed:" + e.getMessage());
        }
    }


}
