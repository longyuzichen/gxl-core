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

import java.io.Serializable;
import java.util.Map;

/**
 * com.longyuzichen.core.common
 *
 * @author longyuzichen@126.com
 * @version V1.0
 * @desc 结果集
 * @date 2017-03-25 19:56
 */
public class Result implements Serializable {
    private int code = 200;//状态码
    private String message; //返回信息

    private String result; //业务结果
    private String error; //错误代码
    private String desc; //错误代码信息

    private Map<String, Object> data; //信息集

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(String result, String error) {
        this.result = result;
        this.error = error;
    }

    public Result( String result, String error, String desc) {
        this.result = result;
        this.error = error;
        this.desc = desc;
    }

    public Result( String result, Map<String, Object> data) {
        this.result = result;
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", result='" + result + '\'' +
                ", error='" + error + '\'' +
                ", desc='" + desc + '\'' +
                ", data=" + data.toString() +
                '}';
    }
}
