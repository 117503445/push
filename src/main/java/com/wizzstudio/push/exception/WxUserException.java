package com.wizzstudio.push.exception;

import com.google.errorprone.annotations.concurrent.LockMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @Author: TheR1sing3un
 * @Date: 2021/09/26/16:53
 * @Description: 关于用户在企业微信与后台交互时的异常
 */
@Getter
@AllArgsConstructor
@Data
public class WxUserException extends RuntimeException{

    //该昵称已被人使用
    public static final Integer NICKNAME_EXISTS = 400010;

    //昵称不合法
    public static final Integer NICKNAME_ILLEGAL = 400011;

    //插入昵称失败
    public static final Integer NICKNAME_ADD_ERROR = 500010;

    private Integer code;

    private String msg;


}
