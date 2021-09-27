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

    //修改昵称可用状态失败
    public static final Integer NICKNAME_STATUS_SET_ERROR = 500011;

    //用户流程状态设置失败
    public static final Integer USER_STATUS_SET_ERROR = 500011;

    //用户禁用了自己没有的昵称时的异常
    public static final Integer NICKNAME_ERROR_BELONG = 400012;

    //异常错误码
    private Integer code;

    //异常错误信息
    private String msg;

    //本异常出现的流程所属用户
    private String userId;

}
