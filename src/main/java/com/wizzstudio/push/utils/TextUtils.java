package com.wizzstudio.push.utils;

import java.util.List;

/**
 * @Author: TheR1sing3un
 * @Date: 2021/09/26/19:39
 * @Description: 将对象处理为文本的工具类
 */
public class TextUtils {

    public static String convert(List<String> nameList){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < nameList.size(); i++) {
            String name = nameList.get(i);
            builder.append(name).append('\n');
        }
        return builder.toString();
    }

}
