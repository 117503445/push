package com.wizzstudio.push.builder;

import com.wizzstudio.push.model.ReplyDTO;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.message.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.message.WxCpXmlOutMessage;

import java.lang.annotation.Repeatable;

/**
 * @Author: TheR1sing3un
 * @Date: 2021/09/25/14:14
 * @Description: 返回消息的构建接口
 */
public interface OutMessageBuilder {

    WxCpXmlOutMessage build(ReplyDTO replyDTO);

}
