package com.xymzsfxy.backend.returncode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    // ================= 成功响应方法 =================
    public static Result<Void> success() {
        return new Result<>(HttpStatus.OK.getCode(), HttpStatus.OK.getDefaultMessage(), null);
    }

    public static <E> Result<E> success(E data) {
        return new Result<>(HttpStatus.OK.getCode(), HttpStatus.OK.getDefaultMessage(), data);
    }

    public static <E> Result<PageData<E>> successWithPage( long total,E items) {
        return new Result<>(
                HttpStatus.OK.getCode(),
                HttpStatus.OK.getDefaultMessage(),
                new PageData<>(total, items)  // 现在可以正确调用两个参数的构造函数
        );
    }

    // ================= 错误响应方法 =================
    public static Result error(HttpStatus status, String message) {
        return new Result<>(status.getCode(), message, null);
    }

    public static Result badRequest(String message) {
        return error(HttpStatus.BAD_REQUEST, message);
    }

    public static Result notFound(String message) {
        return error(HttpStatus.NOT_FOUND, message + "不存在");
    }

    // ================= 分页数据包装类 =================
    @Data
//    @AllArgsConstructor
    public static class PageData<E> {
        private long total;
        private E items;

        // 新增两个参数的构造函数
        public PageData( long total,E items) {
            this.total = total;
            this.items = items;
        }
    }

    // ================= HTTP状态码枚举 =================
    public enum HttpStatus {
        OK(200, "操作成功"),
        BAD_REQUEST(400, "请求错误"),
        UNAUTHORIZED(401, "未授权"),
        FORBIDDEN(403, "禁止访问"),
        NOT_FOUND(404, "资源未找到"),
        INTERNAL_SERVER_ERROR(500, "服务器内部错误");

        private final int code;
        private final String defaultMessage;

        HttpStatus(int code, String defaultMessage) {
            this.code = code;
            this.defaultMessage = defaultMessage;
        }

        public int getCode() { return code; }
        public String getDefaultMessage() { return defaultMessage; }
    }
}