package com.wizzstudio.push.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: TheR1sing3un
 * @Date: 2021/09/27/13:27
 * @Description: 企业微信回复的数据封装
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReplyDTO {

    //回复给的userId
    private String toUsername;

    //回复的来源
    private String fromUsername;

    //回复正文
    private String content;

}
