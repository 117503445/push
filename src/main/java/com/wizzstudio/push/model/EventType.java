package com.wizzstudio.push.model;

/**
 * @Author: TheR1sing3un
 * @Date: 2021/09/24/21:49
 * @Description: 事件的类型
 */
public class EventType {

    public static final String SUBSCRIBE = "subscribe";

    public static final String CLICK = "click";

    public class EvenKey{

        //使用中的昵称
        public static final String NICKNAME_ABLE = "nickname_able";

        //禁用中的昵称
        public static final String NICKNAME_DISABLE = "nickname_disable";

        //添加昵称
        public static final String NICKNAME_ADD = "nickname_add";

        //禁用昵称
        public static final String NICKNAME_BAN = "nickname_ban";
    }
}
