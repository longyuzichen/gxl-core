package com.longyuzichen.core.util;/**
 * Created by longyuzichen on 2017-03-12.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * @desc 字符串工具
 * @auto longyuzichen@126.com
 * @date 2017-03-12 21:20
 */
public class StringUtil {
    private StringUtil(){}
    private static final Logger log = LoggerFactory.getLogger(StringUtil.class);

    public static boolean isNull(String string){
        if(null == string || string.length() == 0){
            return true;
        }
        return false;
    }

    public static String gbk2Utf8(String input){
        return covertString(input,"GBK","UTF-8");
    }
    public static String gbk2Iso(String input){
        return covertString(input,"GBK","ISO-8859-1");
    }
    public static String utf82Gbk(String input){
        return covertString(input,"UTF-8","GBK");
    }

    public static String covertString(String string,String oldCode,String nowCode){
        String resultCode = "";
        try {
            byte[] ascii = string.getBytes(oldCode);
            resultCode = new String(ascii,nowCode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return resultCode;
    }
    //替换非法字符
    public static String convertHtml(String input){
        StringBuffer returnString = new StringBuffer(input.length());

        char ch = ' ';
        for (int i = 0;i<input.length();i++){
            ch = input.charAt( i);
            if (ch == '<'){
                returnString = returnString.append("&lt");
            }else if (ch == '>'){
                returnString = returnString.append("&gt");
            }else if (ch == ' '){
                returnString = returnString.append("&nbsp");
            }else if (ch == '\\'){
                returnString = returnString.append("&acute");
            }else{
                returnString = returnString.append(ch);
            }
        }
        return returnString.toString();
    }

}
