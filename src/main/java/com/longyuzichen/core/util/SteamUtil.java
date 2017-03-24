package com.longyuzichen.core.util;/**
 * com.longyuzichen.core.util
 *
 * @desc
 * @author longyuzichen@126.com
 * @date 2017-03-24 22:35
 * @version V1.0
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * com.longyuzichen.core.util
 *
 * @author longyuzichen@126.com
 * @version V1.0
 * @desc 输入/输出流的转化
 * @date 2017-03-24 22:35
 */
public class SteamUtil {
    private static final Logger log = LoggerFactory.getLogger(SteamUtil.class);

    private static final String CHARSET = "UTF-8";

    /**
     * @param is      输入流
     * @param charset 字符编码，默认为UTF-8
     * @return
     * @throws Exception
     * @desc 输入流转化为字符串
     */
    public static String stream2String(InputStream is, String charset) throws Exception {

        StringBuilder sb = new StringBuilder();
        try {
            if (charset == null || charset.length() == 0) {
                charset = CHARSET;
            }
            InputStreamReader inputStream = new InputStreamReader(is, charset);
            BufferedReader reader = new BufferedReader(inputStream);
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (UnsupportedEncodingException e) {
            log.error("流转化异常，流转化字符串没有指定编码！", e);
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            log.error("流转化异常，读取时IO异常！", e);
            e.printStackTrace();
            return "";
        }

        return sb.toString();
    }

}
