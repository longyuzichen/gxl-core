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

import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * com.longyuzichen.core.util
 *
 * @author longyuzichen@126.com
 * @version V1.0
 * @desc Properties 文件帮助类
 * @date 2017-03-24 23:36
 */
public class Propertie {

    // 默认配置文件路径
    private static final String PATH = "/WEB-INF/classes/";

    private static final Properties prop = new Properties();

    private Propertie() {

    }

    /**
     * @param filename
     * @throws Exception
     * @desc ①加载配置文件
     * @auto longyuzichen@126.com
     * @date 2016年12月16日 下午1:20:03
     */
    public static void loadProperties(String filename) throws Exception {
        FileInputStream fis = new FileInputStream(PATH + filename);
        prop.load(fis);
    }

    /**
     * @param key
     * @return
     * @desc ②获取配置文件属性值
     * @auto longyuzichen@126.com
     * @date 2016年12月16日 下午1:20:35
     */
    public static String getPropertiesValue(String key) {
        return prop.getProperty(key);
    }

    /**
     * @return
     * @desc ③获取配置文件集合
     * @auto longyuzichen@126.com
     * @date 2016年12月16日 下午1:26:41
     */
    public static Map getAllProperties() {
        Map info = new HashMap();
        Enumeration enumeration = prop.propertyNames();

        while (enumeration.hasMoreElements()) {
            String k = (String) enumeration.nextElement();
            String v = prop.getProperty(k);
            info.put(k, v);
        }
        return info;
    }

    /**
     * @param key
     * @return
     * @desc ④删除配置文件键值对
     * @auto longyuzichen@126.com
     * @date 2016年12月16日 下午1:35:23
     */
    public static String removeProperties(String key) {
        return (String) prop.remove(key);
    }
}
