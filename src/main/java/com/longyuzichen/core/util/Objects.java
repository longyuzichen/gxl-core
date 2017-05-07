package com.longyuzichen.core.util;

/**
 * com.longyuzichen.core.util
 *
 * @author longyuzichen@126.com
 * @version V1.0
 * @desc
 * @date 2017-05-07 21:38
 */
public class Objects {

    private Objects() {
    }

    /**
     * 判断对象数组是否为空，为空返回true,不为空返回false
     *
     * @param objects 对象数组
     * @return 为空返回true, 不为空返回false
     */
    public static boolean isEmpty(Object[] objects) {
        return (null == objects && objects.length == 0);
    }

    /**
     * 判断对象数组不为空
     *
     * @param array 对象数组
     * @return 为空返回false, 不为空返回true
     */
    public static boolean notEmpty(Object[] array) {
        return (null != array && array.length > 0);
    }
}
