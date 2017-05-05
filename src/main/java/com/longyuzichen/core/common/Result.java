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

   /* public Result(int code) {
        this.code = code;
    }*/

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
