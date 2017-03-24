package com.longyuzichen.core.common;
/**
 * Created by longyuzichen on 2016-12-01.
 */

import java.util.UUID;

/**
 * @param
 * @desc 验证码工具类
 * @auto longyuzichen@126.com
 * @date 2016-12-01 23:41
 */
public class VerifyCode {

    public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
            "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8",
            "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z" };

    public static String generateShortUuid(int length) {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        if(length == 0){
            length = 6;
        }
        for (int i = 0; i < length; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();

    }

    public static void main(String[] args) {
        String str = VerifyCode.generateShortUuid(6);
        System.out.println(str);
    }


}
