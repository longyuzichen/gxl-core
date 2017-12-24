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
