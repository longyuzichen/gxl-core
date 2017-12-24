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
package com.longyuzichen.core.common;

import com.longyuzichen.core.util.Objects;

/**
 * com.longyuzichen.core.common
 *
 * @author longyuzichen@126.com
 * @version V1.0
 * @desc
 * @date 2017-05-07 21:24
 */
public abstract class Assert {

    /**
     * 是否为空，不为空抛出异常
     *
     * @param object  判断的数据对象
     * @param message 错误信息
     */
    public static void isNull(Object object, String message) {
        if (null != object) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 是否为空，不为空抛出异常
     *
     * @param object 数据对象
     */
    public static void isNull(Object object) {
        isNull(object, "[Assertion failed] - the object argument must be null");
    }

    /**
     * 对象不为空，为空抛出异常
     *
     * @param object  数据对象
     * @param message 异常信息
     */
    public static void notNull(Object object, String message) {
        if (null == object) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 对象不为空，为空抛出异常
     *
     * @param object 数据对象
     */
    public static void notNull(Object object) {
        notNull(object, "[Asserted failed] - this argument is required; it must not be null");
    }

    /**
     * 对象数组不为空，为空抛异常
     *
     * @param array   对象数据
     * @param message 异常信息
     */
    public static void notEmpty(Object[] array, String message) {
        if (Objects.isEmpty(array)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 对象数组不为空，为空抛出异常
     *
     * @param array 对象数组
     */
    public static void notEmpty(Object[] array) {
        notEmpty(array, "[Asserted failed] - this array must not be empty: it must contain at least 1 element");
    }


    /**
     * 表达式是true,为false抛出异常
     *
     * @param expression 表达式
     * @param message    异常信息
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 表达式为true,为false抛出异常
     *
     * @param expression 表达式
     */
    public static void isTrue(boolean expression) {
        isTrue(expression, "[Asserted failed] - this expression must be true");
    }


}
