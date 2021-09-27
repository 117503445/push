package com.wizzstudio.push.handler;

import com.wizzstudio.push.builder.OutTextMessageBuilder;
import com.wizzstudio.push.model.ReplyDTO;
import com.wizzstudio.push.service.UserService;
import com.wizzstudio.push.service.impl.UserServiceImpl;
import com.wizzstudio.push.utils.TextUtils;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.message.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.message.WxCpXmlOutMessage;
import me.chanjar.weixin.cp.message.WxCpMessageHandler;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.Repeatable;
import java.util.List;
import java.util.Map;

/**
 * @Author: TheR1sing3un
 * @Date: 2021/09/25/20:56
 * @Description: 点击查看可用昵称事件的handler
 */
@Component
public class NicknameAbleHandler implements WxCpMessageHandler {

    @Autowired
    private OutTextMessageBuilder textMessageBuilder;

    @Autowired
    private UserService userService;

    @Override
    public WxCpXmlOutMessage handle(WxCpXmlMessage wxCpXmlMessage, Map<String, Object> map, WxCpService wxCpService, WxSessionManager wxSessionManager) throws WxErrorException {
        List<String> nicknamesAble = userService.listNicknamesAble(wxCpXmlMessage.getFromUserName());
        ReplyDTO replyDTO = new ReplyDTO(wxCpXmlMessage.getFromUserName(),wxCpXmlMessage.getToUserName(),null);
        if (CollectionUtils.isEmpty(nicknamesAble)){//集合为空时
            replyDTO.setContent("您没有可用的昵称,请添加昵称");
        }else {//非空时
            String list = TextUtils.convertList(nicknamesAble);
            replyDTO.setContent("您的可用昵称如下:\n\n"+list);
        }
        WxCpXmlOutMessage outMessage = textMessageBuilder.build(replyDTO);
        return outMessage;
    }
}
