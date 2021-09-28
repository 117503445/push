package com.wizzstudio.push.utils;

import com.wizzstudio.push.exception.WxUserException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @Author: TheR1sing3un
 * @Date: 2021/09/26/19:39
 * @Description: 在用户交互过程中用到的文本处理工具类
 */
public class TextUtils {

    /**
     * 将list集合转化成每一行一个元素的字符串
     * @param nameList
     * @return
     */
    public static String convertList(List<String> nameList){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < nameList.size(); i++) {
            String name = nameList.get(i);
            builder.append(name).append('\n');
        }
        return builder.toString();
    }

    /**
     * 验证用户昵称的是否合法
     * @param name
     * @return 去掉空格后的合法昵称,不合法则返回null
     */
    public static String verifyNamePattern(String name){
        //去除空格
        String nickname = name.replaceAll("\\s", "");
        //是否合法规则
        String pattern = "^[a-zA-Z0-9]{2,12}$";
        boolean matches = nickname.matches(pattern);
        if (!matches){
            return null;
        }
        //合法则返回去掉空格等空白字符后的昵称
        return nickname;
    }

    /**
     * 从推送过来的请求中抽出需要发送的文本(优先从请求体中拿,其次从请求参数)
     * @param request
     * @param text
     * @return
     * @throws IOException
     */
    public static String getContentFromRequest(HttpServletRequest request, String text) throws IOException {
        String bodyText = new String(request.getInputStream().readAllBytes());
        if (bodyText.length() > 0) {
            return bodyText;
        } else if (text != null && text.length() > 0) {
            return text;
        } else {
            return null;
        }
    }

}
