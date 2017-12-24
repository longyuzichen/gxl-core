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

import com.longyuzichen.core.util.SteamUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ${PACKAGE_NAME}
 *
 * @author longyuzichen@126.com
 * @version V1.0
 * @desc HTTP协议客户端
 * @date ${DATE} ${TIME}
 */
public class HttpClient {

    private static final Logger log = LoggerFactory.getLogger(HttpClient.class);

    private static final String CHARSET = "UTF-8";

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.75 Safari/537.36";


    /**
     * httpClient请求方法
     *
     * @param url
     * @param method
     * @param params
     * @param charset
     * @return
     * @throws Exception
     */
    public static String HttpClient(String url, String method, Map<String, Object> params, String charset) throws Exception {

        String result = "";
        if (charset == null || charset.length() == 0) {
            charset = CHARSET;
        }
        if ("POST".equals(method.toUpperCase())) {
            result = post(url, params, charset);
        } else if ("GET".equals(method.toUpperCase())) {
            result = get(url, params, charset);
        } else {
            throw new Exception("HTTP协议请求方法错误！");
        }
        return result;
    }

    /**
     * HTTP协议GET请求
     *
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static String get(String url, String params) throws Exception {
        String result = get(url, params, "utf-8");
        return result;
    }

    /**
     * HTTP协议GET请求
     *
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static String get(String url, Map<String, Object> params) throws Exception {
        String result = get(url, params, "utf-8");
        return result;
    }

    /**
     * HTTP协议GET请求
     *
     * @param url
     * @param params
     * @param charset
     * @return
     */
    public static String get(String url, String params, String charset) throws Exception {
        String result = "";
        InputStream is = null;
        HttpURLConnection connection = null;
        try {
            url = url + "&" + params;
            connection = connection(url);
            connection.setRequestMethod("GET");
            connection.connect();
            is = connection.getInputStream();
            result = SteamUtil.stream2String(is, charset);
        } catch (ProtocolException e) {
            log.error("HTTP协议违规异常！", e);
        } catch (IOException e) {
            log.error("流转化异常，读取时IO异常！", e);
        } catch (Exception e) {
            log.error("HTTP协议请求异常！", e);
        } finally {
            if (is != null) {
                is.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }


    /**
     * HTTP协议GET请求
     *
     * @param url
     * @param params
     * @param charset
     * @return
     */
    public static String get(String url, Map<String, Object> params, String charset) throws Exception {
        String result = "";
        InputStream is = null;
        HttpURLConnection connection = null;
        try {
            url = url + toString(params);
            connection = connection(url);
            connection.setRequestMethod("GET");
            connection.connect();
            is = connection.getInputStream();
            result = SteamUtil.stream2String(is, charset);
        } catch (ProtocolException e) {
            log.error("HTTP协议违规异常！", e);
        } catch (IOException e) {
            log.error("流转化异常，读取时IO异常！", e);
        } catch (Exception e) {
            log.error("HTTP协议请求异常！", e);
        } finally {
            if (is != null) {
                is.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }

    /**
     * HTTP协议POST请求
     *
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static String post(String url, String params) throws Exception {
        String result = post(url, params, "utf-8");
        return result;
    }

    /**
     * HTTP协议POST请求
     *
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static String post(String url, Map<String, Object> params) throws Exception {
        String result = post(url, params, "utf-8");
        return result;
    }

    /**
     * HTTP协议POST请求
     *
     * @param url
     * @param params
     * @param charset
     * @return
     */
    public static String post(String url, Map<String, Object> params, String charset) throws Exception {
        String result = "";
        InputStream is = null;
        HttpURLConnection connection = null;
        try {
            connection = connection(url);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.connect();
            DataOutputStream dos = new DataOutputStream(connection.getOutputStream());

            dos.writeBytes(toString(params));
            is = connection.getInputStream();
            result = SteamUtil.stream2String(is, charset);
        } catch (ProtocolException e) {
            log.error("HTTP协议违规异常！", e);
        } catch (IOException e) {
            log.error("流转化IO异常！", e);
        } catch (Exception e) {
            log.error("HTTP协议请求异常！", e);
        } finally {
            if (is != null) {
                is.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }

    /**
     * HTTP协议POST请求
     *
     * @param url
     * @param params
     * @param charset
     * @return
     * @throws Exception
     */
    public static String post(String url, String params, String charset) throws Exception {
        String result = "";
        InputStream is = null;
        HttpURLConnection connection = null;
        try {
            connection = connection(url);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.connect();
            DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
            dos.write(params.getBytes("utf-8"));
            is = connection.getInputStream();
            result = SteamUtil.stream2String(is, charset);
        } catch (ProtocolException e) {
            log.error("HTTP协议违规异常！", e);
        } catch (IOException e) {
            log.error("流转化IO异常！", e);
        } catch (Exception e) {
            log.error("HTTP协议请求异常！", e);
        } finally {
            if (is != null) {
                is.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }

    /**
     * HTTP协议打开连接
     *
     * @param connectionUrl
     * @return
     */
    private static HttpURLConnection connection(String connectionUrl) {
        HttpURLConnection con = null;
        try {
            URL url = new URL(connectionUrl);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setUseCaches(false);
            con.setInstanceFollowRedirects(false);
        } catch (IOException e) {
            log.error("HTTP协议连接异常！", e);
        }
        return con;
    }

    /**
     * map集合转化为字符串（字符串以 & 连接）
     *
     * @param params Map集合对象
     * @return
     */
    public static String toString(Map<String, Object> params) {
        StringBuilder sb = new StringBuilder();
        if (null == params || params.isEmpty()) {
            return "";
        }
        List<String> list = new ArrayList<String>(params.keySet());
        if (list.size() == 0) {
            return "";
        }
        for (int i = 0; i < list.size(); i++) {
            String k = list.get(i);
            Object v = params.get(k);
            if (i == list.size()) {
                sb.append(k).append("=").append(v);
            } else {
                sb.append(k).append("=").append(v).append("&");
            }
        }
        return sb.toString();
    }
}
