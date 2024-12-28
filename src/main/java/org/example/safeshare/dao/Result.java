package org.example.safeshare.dao;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private Integer code; // 业务状态码 0-成功  1-失败
    private String message;// 提示消息
    private T data; // 响应数据

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    // 快速返回操作成功响应结果(带响应数据)
    public static <E> Result<E> success(E data) {
        return new Result<>(0,"操作成功", data);
    }

    // 快速返回操作成功响应结果
    public static Result success() {
        return new Result(0,"操作成功", null);
    }

    public static Result error(String message) {
        return new Result<>(1,message,null);
    }


}

