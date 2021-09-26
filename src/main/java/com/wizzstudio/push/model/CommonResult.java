package com.wizzstudio.push.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class CommonResult<T> {

    public static final Integer SUCCESS = 20000;

    public static final Integer ERROR = 40000;

    private Integer code;

    private String msg;

    private T data;

    public static CommonResult ok() {
        return new CommonResult(SUCCESS, "success", null);
    }

    public static <T>CommonResult error(T data) {
        return new CommonResult(ERROR, "success", data);
    }

    public CommonResult code(Integer code){
        this.code = code;
        return this;
    }

    public CommonResult msg(String msg){
        this.msg = msg;
        return this;
    }

    public CommonResult<T> data(T data){
        this.data = data;
        return this;
    }
}


