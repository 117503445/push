package com.wizzstudio.push.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @Author: TheR1sing3un
 * @Date: 2021/09/26/17:11
 * @Description: 推送者使用推送系统进行推送时的异常
 */
@Getter
@AllArgsConstructor
@Data
public class PushException extends RuntimeException{

    //该昵称不存在
    public static final Integer NICKNAME_NO_EXISTS = 400001;

    //该昵称已被禁用
    public static final Integer NICKNAME_DISABLE = 400002;

    //推送的正文为空
    public static final Integer CONTENT_EMPTY = 400003;

    private Integer code;

    private String msg;

}
