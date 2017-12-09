package com.longyuzichen.core.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * @param
 * @desc 生产唯一码工具类
 * @auto longyuzichen@126.com
 * @date 2016-12-01 23:45
 */
public class ShareCode {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShareCode.class);

    /**
     * 自定义进制(0,1没有加入,容易与o,l混淆)
     */
    private static final char[] r = new char[]{'q', 'w', 'e', '8', 'a', 's', '2', 'd', 'z', 'x', '9', 'c', '7', 'p',
            '5', 'i', 'k', '3', 'm', 'j', 'u', 'f', 'r', '4', 'v', 'y', 'l', 't', 'n', '6', 'b', 'g', 'h'};

    /**
     * (不能与自定义进制有重复)
     */
    private static final char b = 'o';

    /**
     * 进制长度
     */
    private static final int binLen = r.length;

    /**
     * 序列最小长度
     */
    private static final int s = 6;

    private ShareCode() {
    }

    /**
     * @param id  唯一ID
     * @param mix 生成唯一码的长度
     * @return 唯一码
     * @desc 根据id生产唯一码
     * 唯一码最小长度为6位
     */
    public static String shareCodeUtil(long id, int mix) {
        char[] buf = new char[32];
        int charPos = 32;

        while ((id / binLen) > 0) {
            int ind = (int) (id % binLen);
            // System.out.println(num + "-->" + ind);
            buf[--charPos] = r[ind];
            id /= binLen;
        }
        buf[--charPos] = r[(int) (id % binLen)];
        // System.out.println(num + "-->" + num % binLen);
        String str = new String(buf, charPos, (32 - charPos));
        // 不够长度的自动随机补全
        int mixs = 6;
        if (mix != 0 || mix > s) {
            mixs = mix;
        }
        if (str.length() < mixs) {
            StringBuilder sb = new StringBuilder();
            sb.append(b);
            Random rnd = new Random();
            for (int i = 1; i < s - str.length(); i++) {
                sb.append(r[rnd.nextInt(binLen)]);
            }
            str += sb.toString();
        }
        LOGGER.debug("生产的唯一码信息为：{}", str);
        return str;
    }


}
