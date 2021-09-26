package com.wizzstudio.push.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: TheR1sing3un
 * @Date: 2021/09/26/17:18
 * @Description: 用户在企业微信和后台交互通用返回类
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WxUserResult {

    public static final Integer SUCCESS = 20000;

    public static final Integer ERROR = 40000;


}
