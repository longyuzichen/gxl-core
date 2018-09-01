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
package com.longyuzichen.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @desc 字符串工具
 * @auto longyuzichen@126.com
 * @date 2017-03-12 21:20
 */
public final class StringUtil {

    private static final Logger log = LoggerFactory.getLogger(StringUtil.class);

    private StringUtil() {
    }

    public static boolean isNull(String string) {
        if (null == string || string.length() == 0) {
            return true;
        }
        return false;
    }

    public static String gbk2Utf8(String input) {
        return covertString(input, "GBK", "UTF-8");
    }

    public static String gbk2Iso(String input) {
        return covertString(input, "GBK", "ISO-8859-1");
    }

    public static String utf82Gbk(String input) {
        return covertString(input, "UTF-8", "GBK");
    }

    public static String covertString(String string, String oldCode, String nowCode) {
        String resultCode = "";
        try {
            byte[] ascii = string.getBytes(oldCode);
            resultCode = new String(ascii, nowCode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return resultCode;
    }

    //替换非法字符
    public static String convertHtml(String input) {
        StringBuffer returnString = new StringBuffer(input.length());
        char ch = ' ';
        for (int i = 0; i < input.length(); i++) {
            ch = input.charAt(i);
            if (ch == '<') {
                returnString = returnString.append("&lt");
            } else if (ch == '>') {
                returnString = returnString.append("&gt");
            } else if (ch == ' ') {
                returnString = returnString.append("&nbsp");
            } else if (ch == '\\') {
                returnString = returnString.append("&acute");
            } else {
                returnString = returnString.append(ch);
            }
        }
        return returnString.toString();
    }
    /**
     * map集合转化为字符串（字符串以 & 连接）
     *
     * @param params Map集合对象
     * @return
     */
    public static String map2String(Map<String, Object> params) {
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
            if (i == list.size()-1) {
                sb.append(k).append("=").append(v);
            } else {
                sb.append(k).append("=").append(v).append("&");
            }
        }
        return sb.toString();
    }
}
