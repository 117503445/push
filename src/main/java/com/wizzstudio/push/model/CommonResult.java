package com.wizzstudio.push.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class CommonResult {

    public static final int USER_NO_EXISTS = 400001;//用户不存在

    public static final int USERNAME_ABANDON = 400002;//用户昵称已禁用

    private int code = 0;

    private String message = "";

    private Object data;

    public static CommonResult Success() {
        return new CommonResult(0, "success", null);
    }

    public static CommonResult Success(Object data) {
        return new CommonResult(0, "success", data);
    }
}


