package com.wantedalways.common.api.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一数据返回格式
 * @author Wantedalways
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result<T> {

    /**
     * 成功标志
     */
    private Boolean success;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 数据对象
     */
    private T data;

    /**
     * 时间戳
     */
    private Long timestamp = System.currentTimeMillis();

    public static<T> Result<T> error(int code, String message) {

        Result<T> result = new Result<>();
        result.setSuccess(false);
        result.setCode(code);
        result.setMessage(message);

        return result;
    }

    public Result<T> setError(int code, String message) {
        this.success = false;
        this.code = code;
        this.message = message;
        return this;
    }


    public static<T> Result<T> success(String message, T data) {
        Result<T> result = new Result<>();
        result.success = true;
        result.code = 200;
        result.message = message;
        result.data = data;
        return result;
    }

    public static<T> Result<T> success(String message) {
        Result<T> result = new Result<>();
        result.success = true;
        result.code = 200;
        result.message = message;
        return result;
    }

    public static<T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.success = true;
        result.code = 200;
        result.data = data;
        return result;
    }

    public static<T> Result<T> success() {
        Result<T> result = new Result<>();
        result.success = true;
        result.code = 200;
        result.message = "成功！";
        return result;
    }

    public void setSuccess(String message, T data) {
        this.success = true;
        this.code = 200;
        this.message = message;
        this.data = data;
    }

    public static<T> Result<T> fail(String message) {
        Result<T> result = new Result<>();
        result.success = false;
        result.code = 404;
        result.message = message;
        return result;
    }
}
