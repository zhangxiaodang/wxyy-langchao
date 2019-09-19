package com.qd.wxyy.util;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.URLEncoder;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Copyright(C) ShanDongYinFang 2018.
 * <p>
 * HTTP类.
 *
 * @author 张孝党 2018/07/24.
 * @version V1.00.
 * <p>
 * 更新履历： V1.00 2018/07/24 张孝党 创建.
 */
public class HttpUtil {

    /**
     * 使用GET方法得到一个URL的数据.
     */
    public static String getResponseWithGET(String pstrUrl) throws Exception {

        // 返回值
        String strResponseBody = "";
        // Http Client
        CloseableHttpClient httpclient = HttpClients.createDefault();

        try {
            // http get
            HttpGet httpget = new HttpGet(pstrUrl);
            // Response
            CloseableHttpResponse response = httpclient.execute(httpget);

            try {
                // 取得返回值
                HttpEntity entity = response.getEntity();
                // 判断返回值
                strResponseBody = entity != null ? EntityUtils.toString(entity, "UTF-8") : "";
            } finally {
                response.close();
            }

        } finally {
            httpclient.close();
        }

        // 返回
        return strResponseBody;
    }

    /**
     * 使用POST方法得到一个URL的数据.
     *
     * @param pstrUrl URL
     * @return 返回值.
     * @throws Exception
     */
    public static String getResponseWithPOST(String pstrUrl) throws Exception {

        // 返回值
        String strResponseBody = "";
        // Http Client
        CloseableHttpClient httpclient = HttpClients.createDefault();

        try {
            // http post
            HttpPost httpPost = new HttpPost(pstrUrl);
            // Response
            CloseableHttpResponse response = httpclient.execute(httpPost);

            try {
                // 取得返回值
                HttpEntity entity = response.getEntity();
                // 判断返回值
                strResponseBody = entity != null ? EntityUtils.toString(entity, "UTF-8") : "";
            } finally {
                response.close();
            }

        } finally {
            httpclient.close();
        }

        // 返回
        return strResponseBody;
    }

    /**
     * 使用POST方法得到一个URL的数据.
     *
     * @param pstrUrl          URL
     * @param pstrPostBodyData POST数据.
     * @return String POST请求返回值.
     * @throws Exception
     */
    public static String getResponseWithPOST(String pstrUrl, String pstrPostBodyData) throws Exception {

        // 返回值
        String strResponseBody = "";
        // Http Client
        CloseableHttpClient httpclient = HttpClients.createDefault();

        try {
            // http post
            HttpPost httpPost = new HttpPost(pstrUrl);

            // POST数据
            StringEntity postEntity = new StringEntity(pstrPostBodyData,
                    ContentType.create("application/x-www-form-urlencoded", "UTF-8"));
            // 设定POST数据
            httpPost.setEntity(postEntity);
            // Response
            CloseableHttpResponse response = httpclient.execute(httpPost);

            try {
                // 取得返回值
                HttpEntity entity = response.getEntity();
                // 判断返回值
                strResponseBody = entity != null ? EntityUtils.toString(entity, "UTF-8") : "";
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }

        // 返回
        return strResponseBody;
    }

    public static org.apache.http.HttpResponse doPost(String host, String path, String method,
                                                      Map<String, String> headers,
                                                      Map<String, String> querys,
                                                      Map<String, String> bodys)
            throws Exception {
        HttpClient httpClient = wrapClient(host);

        HttpPost request = new HttpPost(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue());
        }

        if (bodys != null) {
            List<NameValuePair> nameValuePairList = new ArrayList<>();

            for (String key : bodys.keySet()) {
                nameValuePairList.add(new BasicNameValuePair(key, bodys.get(key)));
            }
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nameValuePairList, "utf-8");
            formEntity.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
            request.setEntity(formEntity);
        }

        return httpClient.execute(request);
    }

    private static HttpClient wrapClient(String host) {
        HttpClient httpClient = new DefaultHttpClient();
        if (host.startsWith("https://")) {
            sslClient(httpClient);
        }

        return httpClient;
    }

    private static void sslClient(HttpClient httpClient) {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] xcs, String str) {

                }

                public void checkServerTrusted(X509Certificate[] xcs, String str) {

                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);
            ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            ClientConnectionManager ccm = httpClient.getConnectionManager();
            SchemeRegistry registry = ccm.getSchemeRegistry();
            registry.register(new Scheme("https", 443, ssf));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }

    private static String buildUrl(String host, String path, Map<String, String> querys) throws Exception {

        StringBuilder sbUrl = new StringBuilder();
        sbUrl.append(host);
        if (!StringUtils.isBlank(path)) {
            sbUrl.append(path);
        }
        if (null != querys) {
            StringBuilder sbQuery = new StringBuilder();
            for (Map.Entry<String, String> query : querys.entrySet()) {
                if (0 < sbQuery.length()) {
                    sbQuery.append("&");
                }
                if (StringUtils.isBlank(query.getKey()) && !StringUtils.isBlank(query.getValue())) {
                    sbQuery.append(query.getValue());
                }
                if (!StringUtils.isBlank(query.getKey())) {
                    sbQuery.append(query.getKey());
                    if (!StringUtils.isBlank(query.getValue())) {
                        sbQuery.append("=");
                        sbQuery.append(URLEncoder.encode(query.getValue(), "utf-8"));
                    }
                }
            }
            if (0 < sbQuery.length()) {
                sbUrl.append("?").append(sbQuery);
            }
        }

        return sbUrl.toString();
    }
}
