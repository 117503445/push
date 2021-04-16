package com.wizzstudio.push.controller;

import com.wizzstudio.push.config.ApiVersion;
import com.wizzstudio.push.config.StaticFactory;
import com.wizzstudio.push.model.CardDTO;
import com.wizzstudio.push.model.CommonResult;
import com.wizzstudio.push.service.WechatService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.bean.message.WxCpMessage;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("push/card")
public class CardController{
    @RequestMapping("/{version}")
    @ApiVersion()
    public CommonResult get(@RequestBody CardDTO cardDto) throws WxErrorException {
        WxCpMessage message = WxCpMessage.TEXTCARD().
                agentId(StaticFactory.getWeChatConfig().getAgentId()).
                toUser(cardDto.getName()).
                title(cardDto.getTitle()).
                description(cardDto.getDescription()).
                url(cardDto.getUrl()).
                btnTxt(cardDto.getBtnTxt()).
                build();

        WechatService.getWxCpService().getMessageService().send(message);

        return CommonResult.Success();
    }
}
