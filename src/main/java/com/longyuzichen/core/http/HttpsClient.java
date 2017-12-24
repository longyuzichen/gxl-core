/**
 * Copyright [2017] guoxinlei(longyuzichen@126.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/
package com.longyuzichen.core.http;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;

/**
 * com.longyuzichen.core.http
 *
 * @author longyuzichen@126.com
 * @version V1.0
 * @desc HTTPS协议请求
 * @date 2017-03-24 22:41
 */
@SuppressWarnings("ALL")
public class HttpsClient {

    private static final Logger log = LoggerFactory.getLogger(HttpsClient.class);

    /**
     * HTTPS协议
     *
     * @param requestUrl
     * @param outputStr
     * @param certPath
     * @param certPassword
     * @return
     * @throws Exception
     */
    public static String post(String requestUrl, String outputStr, String certPath, String certPassword) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        StringBuilder res = new StringBuilder("");
        FileInputStream instream = new FileInputStream(new File(certPath)); // 证书路径

        try {
            keyStore.load(instream, certPassword.toCharArray()); //证书密码
        } finally {
            instream.close();
        }

        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, certPassword.toCharArray()) // 证书密码
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        try {

            HttpPost httpost = new HttpPost(requestUrl);
            httpost.addHeader("Connection", "keep-alive");
            httpost.addHeader("Accept", "*/*");
            httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpost.addHeader("Host", "api.mch.weixin.qq.com");
            httpost.addHeader("X-Requested-With", "XMLHttpRequest");
            httpost.addHeader("Cache-Control", "max-age=0");
            httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
            StringEntity entity2 = new StringEntity(outputStr, Consts.UTF_8);
            httpost.setEntity(entity2);

            if(log.isDebugEnabled()){
                log.debug("executing request:" + httpost.getRequestLine());
            }
            CloseableHttpResponse response = httpclient.execute(httpost);

            try {
                HttpEntity entity = response.getEntity();
                if(log.isDebugEnabled()) {
                    log.debug("response execute:" + response.getStatusLine());
                }
                if (entity != null) {
                    if(log.isDebugEnabled()) {
                        log.debug("Response content length: " + entity.getContentLength());
                    }
                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(entity.getContent(), "utf-8"));
                    String text = null;
                    while ((text = bufferedReader.readLine()) != null) {
                        res.append(text);
                        if(log.isDebugEnabled()) {
                            log.debug("POST 请求返回的数据：" + text);
                        }
                    }
                }
                EntityUtils.consume(entity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
        return res.toString();
    }
}
