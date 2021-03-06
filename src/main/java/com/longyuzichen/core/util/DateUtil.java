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
/**
 * Created by longyuzichen on 2017-03-12.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private static final Logger log = LoggerFactory.getLogger(DateUtil.class);
    private static final String DATE_PATTERN = "yyyy-MM-DD";
    private static final String TIME_PATTERN = "yyyy-MM-DD HH:MM:ss.SSS";

    private DateUtil() {
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 获取当天日期
     *
     * @return
     */
    public static String getNowDay() {
        return date2String("yyyy-MM-DD", new Date());
    }

    /**
     * 时间转成字符串
     *
     * @param date 时间
     * @return
     */
    public static String date2String(Date date) {
        return date2String("yyyy-MM-DD HH:MM:ss.SSS", date);
    }

    /**
     * 时间转成字符串
     *
     * @param format 时间格式
     * @param date   时间
     * @return
     */
    public static String date2String(String format, Date date) {
        return getNowTime(format, date);
    }

    /**
     * 获取格式化时间字符串
     *
     * @param pattern 时间格式化
     * @param date    时间
     * @return
     */
    public static String getNowTime(String pattern, Date date) {
        SimpleDateFormat simpleDateFormat = null;
        String nowTime = "";
        if (null == date) {
            log.error("date is null!");
        } else {
            simpleDateFormat = new SimpleDateFormat(pattern);
            nowTime = simpleDateFormat.format(date);
        }
        return nowTime;
    }

    /**
     * 字符串转成时间
     *
     * @param nowDate 时间字符串 yyyy-MM-dd
     * @return
     */
    public static Date string2Date(String nowDate) {
        return string2Date("yyyy-MM-DD", nowDate);
    }

    /**
     * 字符串转成时间
     *
     * @param format  时间格式
     * @param nowDate 时间字符串
     * @return
     */
    public static Date string2Date(String format, String nowDate) {
        SimpleDateFormat simpleDateFormat = null;
        Date now = null;
        try {
            simpleDateFormat = new SimpleDateFormat(format);
            now = simpleDateFormat.parse(nowDate);
        } catch (ParseException e) {
            log.error("字符串转换时间异常！", e);
        }
        return now;
    }

    /**
     * 获取日期
     *
     * @param timeType 时间类型，譬如：Calendar.DAY_OF_YEAR
     * @param timenum  时间数字，譬如：-1 昨天，0 今天，1 明天
     * @return 日期
     */
    public static final Date getDateFromNow(int timeType, int timenum) {
        Calendar cld = Calendar.getInstance();
        cld.set(timeType, cld.get(timeType) + timenum);
        return cld.getTime();
    }
}
