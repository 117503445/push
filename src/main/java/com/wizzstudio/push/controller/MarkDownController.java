package com.wizzstudio.push.controller;

import com.wizzstudio.push.config.ApiVersion;
import com.wizzstudio.push.config.StaticFactory;
import com.wizzstudio.push.exception.PushException;
import com.wizzstudio.push.model.CommonResult;
import com.wizzstudio.push.service.UserService;
import com.wizzstudio.push.service.WechatService;
import com.wizzstudio.push.utils.TextUtils;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.message.WxCpMessage;
import me.chanjar.weixin.cp.bean.message.WxCpMessageSendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/push/markdown")
public class MarkDownController {

    @Autowired
    private UserService userService;


    @RequestMapping("/{version}")
    @ApiVersion
    public CommonResult pushMarkdown(HttpServletRequest request, @RequestParam String name, @RequestParam(required = false) String text) throws WxErrorException, IOException {
        String content = TextUtils.getContentFromRequest(request, text);
        WxCpMessage message = WxCpMessage.MARKDOWN().agentId(StaticFactory.getWeChatConfig().getAgentId()).toUser(name).content(content).build();
        WechatService.getWxCpService().getMessageService().send(message);
        return CommonResult.ok();
    }

    @RequestMapping("/{version}")
    @ApiVersion(2)
    public CommonResult getMarkdownWithNickname(HttpServletRequest request,@RequestParam String name,@RequestParam(required = false) String text) throws IOException, WxErrorException {
        //获取推送正文
        String content = TextUtils.getContentFromRequest(request, text);
        if (content == null || content.isEmpty()) throw new PushException(PushException.CONTENT_EMPTY,"推送的正文不允许为空");
        //组成回复正文
        String replyContent = name+",您有一条新的推送:\n"+content;
        //根据昵称去获取用户id
        String userId = userService.getUserIdByAbleNickname(name);
        //根据id发送消息
        WxCpMessage message = WxCpMessage.MARKDOWN().agentId(StaticFactory.getWeChatConfig().getAgentId()).toUser(userId).content(replyContent).build();
        WechatService.getWxCpService().getMessageService().send(message);
        return CommonResult.ok();
    }
}
