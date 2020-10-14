package com.boot_demo.demo1.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.json.XML;
import org.springframework.util.CollectionUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * @author ZhonghaoMa
 * @Description:
 * @date 2020/4/30
 */
@Slf4j
public class HttpsUtils {
    private static PoolingHttpClientConnectionManager connMgr;
    private static RequestConfig requestConfig;
    private static final int MAX_TIMEOUT = 6000;
    private static final String HTTPS = "https";

    static {
        // set pool
        connMgr = new PoolingHttpClientConnectionManager();
        // set size of pool
        connMgr.setMaxTotal(100);
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());
        // Validate connections after 1 sec of inactivity
        connMgr.setValidateAfterInactivity(1000);
        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // set connnect timeout
        configBuilder.setConnectTimeout(MAX_TIMEOUT);
        // set socket tomeout
        configBuilder.setSocketTimeout(MAX_TIMEOUT);

        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);

        requestConfig = configBuilder.build();
    }

    /**
     * send get request without data
     *
     * @param url
     * @return
     */
    public static JSONObject doGet(String url) throws Exception {
        return doGet(url, new HashMap<String, Object>(), null);
    }

    public static JSONObject doGetWithHeaders(String url, Map<String, String> headers) throws Exception {
        return doGet(url, null, headers);
    }

    public static JSONObject doGetWithParams(String url, Map<String, Object> params) throws Exception {
        return doGet(url, params, null);
    }

    /**
     * send  request(get)，K-V format
     *
     * @param url
     * @param params
     * @return
     */
    public static JSONObject doGet(String url, Map<String, Object> params, Map<String, String> headers) throws Exception {
        String apiUrl = url;
        StringBuffer param = new StringBuffer();
        int i = 0;
        if (!CollectionUtils.isEmpty(params)) {
            for (String key : params.keySet()) {
                if (i == 0)
                    param.append("?");
                else
                    param.append("&");
                param.append(key).append("=").append(params.get(key));
                i++;
            }
        }

        apiUrl += param;
        String result = null;
        HttpClient httpClient = buildHttpClient(url);

        try {
            HttpGet httpGet = new HttpGet(apiUrl);
            if (!CollectionUtils.isEmpty(headers)) {
                for (String key : headers.keySet()) {
                    httpGet.setHeader(key, headers.get(key));
                }
            }
            HttpResponse response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    InputStream instream = entity.getContent();
                    result = IOUtils.toString(instream, "UTF-8");
                }
            } else {
                String errorMessage = "send request to " + apiUrl + " error:" + response.getStatusLine();
                log.error(errorMessage);
            }

        } catch (Exception e) {
            String errorMessage = "send request to " + url + "error:" + e.getMessage();
            log.error(errorMessage);
            throw new Exception(errorMessage);
        }
        return JSON.parseObject(result);
    }

    /**
     * send  request (post) without data
     *
     * @param apiUrl
     * @return
     */
    public static JSONObject doPost(String apiUrl) throws Exception {
        return doPost(apiUrl, new HashMap<String, Object>());
    }

    /**
     * send post request with K-V format
     *
     * @param apiUrl
     * @param params
     * @return
     */
    public static JSONObject doPost(String apiUrl, Map<String, Object> params) throws Exception {
        CloseableHttpClient httpClient = buildHttpClient(apiUrl);

        String httpStr = null;
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;

        try {
            httpPost.setConfig(requestConfig);
            List<NameValuePair> pairList = new ArrayList<>(params.size());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
                pairList.add(pair);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                httpStr = EntityUtils.toString(entity, "UTF-8");
            } else {
                String errorMessage = "send request to " + apiUrl + " error:" + response.getStatusLine();
                log.error(errorMessage);
            }

        } catch (Exception e) {
            buildErrorMessage(e, apiUrl);
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    log.error(e.getMessage());
                    throw new Exception(e.getMessage());
                }
            }
        }
        return JSON.parseObject(httpStr);
    }

    /**
     * send post request with json
     *
     * @param apiUrl
     * @param json   json对象
     * @return
     */
    public static JSONObject doJsonPost(String apiUrl, Object json) throws Exception {
        return doJsonPost(apiUrl, json, null);
    }

    /**
     * send post request with json and headers
     *
     * @param apiUrl
     * @param json   json对象
     * @return
     */
    public static JSONObject doJsonPost(String apiUrl, Object json, Map<String, String> headers) throws Exception {
        CloseableHttpClient httpClient = buildHttpClient(apiUrl);
        String httpStr = null;
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;

        try {
            httpPost.setConfig(requestConfig);
            if (!CollectionUtils.isEmpty(headers)) {
                for (String key : headers.keySet()) {
                    httpPost.setHeader(key, headers.get(key));
                }
            }
            StringEntity stringEntity = new StringEntity(JSONObject.toJSONString(json), "UTF-8");// 解决中文乱码问题
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                httpStr = EntityUtils.toString(entity, "UTF-8");

            } else {
                String errorMessage = "send request to " + apiUrl + " error:" + response.getStatusLine();
                log.error(errorMessage);
            }

        } catch (Exception e) {
            buildErrorMessage(e, apiUrl);
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    log.error(e.getMessage());
                    throw new Exception(e.getMessage());
                }
            }
        }
        if (httpStr.contains("xml")) {
            return JSON.parseObject(XML.toJSONObject(httpStr).toString());
        } else {
            return JSON.parseObject(httpStr);
        }
    }


    /**
     * send post request with Object convert to K-V format
     *
     * @param apiUrl
     * @param mapObj
     * @return
     */
    public static JSONObject doUrlencodedPost(String apiUrl, Object mapObj, Map<String, String> headers) throws Exception {
        CloseableHttpClient httpClient = buildHttpClient(apiUrl);
        String httpStr = null;
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;

        try {
            httpPost.setConfig(requestConfig);
            if (!CollectionUtils.isEmpty(headers)) {
                for (String key : headers.keySet()) {
                    httpPost.setHeader(key, headers.get(key));
                }
            }
            Map<String, Object> mapEntity = ObjectUtil.objectToMap(mapObj);
            List<NameValuePair> nameValuePairs = new ArrayList<>();
            if (mapEntity.size() != 0) {

                Set keySet = mapEntity.keySet();
                Iterator it = keySet.iterator();
                while (it.hasNext()) {
                    String k = it.next().toString();// key
                    Object obj = mapEntity.get(k);
                    String v = "";
                    if (obj != null) {
                        v = obj.toString();
                    }
                    nameValuePairs.add(new BasicNameValuePair(k, v));
                }
            }
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairs, Charset.forName("UTF-8"));// 解决中文乱码问题
            urlEncodedFormEntity.setContentEncoding("UTF-8");
            urlEncodedFormEntity.setContentType("application/x-www-form-urlencoded");
            httpPost.setEntity(urlEncodedFormEntity);
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                httpStr = EntityUtils.toString(entity, "UTF-8");
            } else {
                String errorMessage = "send request to " + apiUrl + " error:" + response.getStatusLine();
                log.error(errorMessage);
            }

        } catch (Exception e) {
            buildErrorMessage(e, apiUrl);
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    log.error(e.getMessage());
                    throw new Exception(e.getMessage());
                }
            }
        }
        return JSON.parseObject(httpStr);
    }

    public static JSONObject doUrlencodedPost(String apiUrl, Object mapObj) throws Exception {
        return doUrlencodedPost(apiUrl, mapObj, null);
    }

    /**
     * build SSL connection
     *
     * @return
     */
    private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
        SSLConnectionSocketFactory sslsf = null;
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {

                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            sslsf = new SSLConnectionSocketFactory(sslContext, new HostnameVerifier() {

                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return sslsf;
    }

    private static void buildErrorMessage(Exception e, String url) throws Exception {
        String errorMessage = "send request to " + url + " error:" + e.getMessage();
        log.error(errorMessage);
        throw new Exception(errorMessage);
    }

    private static CloseableHttpClient buildHttpClient(String url) {
        CloseableHttpClient httpClient;
        if (url.startsWith(HTTPS)) {
            httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory())
                    .setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
        } else {
            httpClient = HttpClients.createDefault();
        }
        return httpClient;
    }


}


