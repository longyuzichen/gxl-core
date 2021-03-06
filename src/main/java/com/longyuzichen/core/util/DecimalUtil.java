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

import java.math.BigDecimal;

/**
 * @param
 * @desc 金额转换帮助类
 * @auto longyuzichen@126.com
 * @date 2016-11-13 21:55
 */
public class DecimalUtil {

    private DecimalUtil() {
    }

    /**
     * @param amount
     * @return
     * @desc 获取金额分
     * @auto guoxl
     * @date 2016年9月18日 下午2:11:06
     */
    public static String changeY2F(String amount) {
        String currency = amount.replaceAll("\\$|\\￥|\\,", ""); // 处理包含, ￥或者$的金额
        int index = currency.indexOf(".");
        int length = currency.length();
        Long amLong = 0l;
        if (index == -1) {
            amLong = Long.valueOf(currency + "00");
        } else if (length - index >= 3) {
            amLong = Long.valueOf((currency.substring(0, index + 3)).replace(".", ""));
        } else if (length - index == 2) {
            amLong = Long.valueOf((currency.substring(0, index + 2)).replace(".", "") + 0);
        } else {
            amLong = Long.valueOf((currency.substring(0, index + 1)).replace(".", "") + "00");
        }
        return amLong.toString();
    }

    /**
     * @param amount 整数分
     * @return
     * @desc 将整数分转换成金额
     */
    public static BigDecimal changeF2Y(String amount) {
        BigDecimal bd = null;
        if (amount != null) {
            bd = new BigDecimal(amount).divide(new BigDecimal(100));
        } else {
            bd = new BigDecimal("0");
        }
        return bd;
    }

    /**
     * @param sum
     * @return
     * @desc 字符串转换成BigDecimal类型
     */
    public static BigDecimal getDecimal(String sum) {
        BigDecimal bd = new BigDecimal(sum);
        return bd;
    }

    /**
     * @param sum
     * @param mon
     * @return 相加后的数值
     * @throws Exception
     * @desc 两个字符串相加
     */
    public static BigDecimal addSum(String sum, String mon) throws Exception {
        if (sum == null || "".equals(sum) || mon == null || "".equals(mon)) {
            throw new Exception("传入的数据为空，数据为：[" + sum + "],   [" + mon + "] !");
        }
        BigDecimal bd = getDecimal(sum).add(getDecimal(mon));
        return bd;
    }


}
