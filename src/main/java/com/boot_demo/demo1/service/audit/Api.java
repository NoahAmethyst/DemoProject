package com.boot_demo.demo1.service.audit;


import com.alibaba.fastjson.JSONObject;
import com.boot_demo.demo1.constant.TupuTechProperties;
import com.boot_demo.demo1.service.audit.httpconnection.HttpConnectionUtil;
import com.boot_demo.demo1.service.audit.model.ClassificationResult;
import com.boot_demo.demo1.service.audit.model.Options;
import com.boot_demo.demo1.service.audit.utils.ConfigUtil;
import com.boot_demo.demo1.service.audit.utils.ErrorUtil;
import com.boot_demo.demo1.service.audit.utils.SignatureAndVerifyUtil;
import lombok.extern.slf4j.Slf4j;

import java.security.PrivateKey;
import java.util.List;

/**
 * @author soap API
 */

@Slf4j
public class Api {
    private String secretId;
    private String url;
    private PrivateKey privateKey;

    /**
     * @param secretId 用户secretId
     * @param pkPath   用户私钥
     */
    public Api(String secretId, String pkPath, String requestUrl) {
        if (null == requestUrl) {
            requestUrl = ConfigUtil.NET_WORK.API_URI;
        }
        this.secretId = secretId;
        this.url = requestUrl + secretId;
        this.privateKey = SignatureAndVerifyUtil.readPrivateKey(pkPath);

    }


    /**
     * @param fileType  传入的数据类型，ConfigUtil.UPLOAD_TYPE.UPLOAD_IMAGE_TYPE为本地文件
     *                  ConfigUtil.UPLOAD_TYPE.UPLOAD_URI_TYPE 为图片 Url
     * @param fileLists 文件集合
     * @param options   [可选] tags:
     *                  用于给图片附加额外信息（比如：直播客户可能传房间号，或者主播ID信息）。方便后续根据tag搜索到相关的图片 uid:
     *                  作为第三方客户标识
     * @return
     */
    public JSONObject doApiRequest(String fileType, List<String> fileLists, Options options) {
        if (fileLists == null || fileLists.isEmpty()) {
            return ErrorUtil.getErrorMsg(ErrorUtil.ERROR_CODE_NO_FILE, "");
        }
        String timestamp = Math.round(System.currentTimeMillis() / 1000.0) + "";
        String nonce = Math.random() + "";
        String sign_string = secretId + "," + timestamp + "," + nonce;

        String signature = SignatureAndVerifyUtil.Signature(privateKey, sign_string);

        ClassificationResult classificationResult = null;

        long startTime = System.currentTimeMillis();
        long endTime = 0;
        float time = 0;
        try {
            // 得到签名
            if (fileType == ConfigUtil.UPLOAD_TYPE.UPLOAD_IMAGE_TYPE) {
                classificationResult = HttpConnectionUtil.uploadImage(url, secretId, timestamp, nonce, signature,
                        fileLists, options);
            } else if (fileType == ConfigUtil.UPLOAD_TYPE.UPLOAD_URI_TYPE) {
                classificationResult = HttpConnectionUtil.uploadUri(url, timestamp, nonce, signature, fileLists,
                        options);
            }

            if (classificationResult.getResultCode() == 200) {
                String result = classificationResult.getResult();
                // 判断当前字符串的编码格式
                if (result.equals(new String(result.getBytes("iso8859-1"), "iso8859-1"))) {
                    result = new String(result.getBytes("iso8859-1"), "utf-8");
                }
                JSONObject jsonObject = JSONObject.parseObject(result);

                JSONObject result_json = jsonObject.getJSONObject("json");
                String code = result_json.getString("code");
                String message = result_json.getString("message");
                if (Integer.valueOf(code) == 0) {
                    String result_signature = jsonObject.getString("signature");
                    // 进行验证
                    /**

                     boolean verify = SignatureAndVerifyUtil.Verify(result_signature, result_json.toJSONString());
                     endTime = System.currentTimeMillis();
                     time = (float) (endTime - startTime) / (float) 1000;
                     if (verify) {
                     log.error("TUPU API: response verify succeed, total time" + time + "s");
                     return result_json;
                     } else {
                     log.error("TUPU API: response verify failed, total time" + time + "s");
                     return ErrorUtil.getErrorMsg(ErrorUtil.ERROR_CODE_RESULT_VERIFY_FAILED, "");
                     }

                     */

                    return result_json.getJSONObject(TupuTechProperties.IDENTIFY_OF_PORN);

                } else {
                    log.error("TUPU API: response verify failed, total time" + time + "s");
                    return ErrorUtil.getErrorMsg(Integer.valueOf(code), message);
                }
            } else {
                log.error("TUPU API: response verify failed, total time" + time + "s");
                return ErrorUtil.getErrorMsg(classificationResult.getResultCode(), "");
            }
        } catch (Exception e) {
            log.error("TUPU API: response verify failed, total time" + time + "s");
            log.error("TUPU API: response verify failed, error is " + e.getMessage());
            return ErrorUtil.getErrorMsg(ErrorUtil.ERROR_CODE_OTHERS, e.getMessage());
        }
    }
}