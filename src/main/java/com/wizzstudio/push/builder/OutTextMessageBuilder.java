package com.wizzstudio.push.builder;

import com.wizzstudio.push.model.ReplyDTO;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.message.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.message.WxCpXmlOutMessage;
import me.chanjar.weixin.cp.bean.message.WxCpXmlOutTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: TheR1sing3un
 * @Date: 2021/09/25/14:06
 * @Description: 对返回消息的封装(配置FromUser,ToUser和返回msgType)
 */
@Component
public class OutTextMessageBuilder implements OutMessageBuilder {

    @Override
    public WxCpXmlOutMessage build(ReplyDTO replyDTO) {
        WxCpXmlOutTextMessage textOutMessage = new WxCpXmlOutTextMessage();
        //设置正文
        textOutMessage.setContent(replyDTO.getContent());
        //设置返回消息的目的UserName(也就是接收到的消息的来源UserName)
        textOutMessage.setToUserName(replyDTO.getToUsername());
        //设置返回消息的来源UserName(也就是接收到的消息的目的UserName)
        textOutMessage.setFromUserName(replyDTO.getFromUsername());
        return textOutMessage;
    }

}
