package com.xymzsfxy.backend.returncode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 统一响应结果
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    // 成功返回响应结果
    public static <E> Result<E> success(E data) {
        return new Result<>(200, "操作成功", data);
    }

    public static Result success() {
        return new Result(200, "操作成功", null);
    }

    public static Result error(String message) {
        return new Result(400, message, null);
    }


}
