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

    public static<T> Result<T> error(int code, String message, T object) {

        return Result.<T>builder()
                .success(false)
                .code(code)
                .message(message)
                .data(object).build();
    }
}
