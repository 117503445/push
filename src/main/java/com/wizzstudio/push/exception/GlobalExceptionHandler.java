package com.wizzstudio.push.exception;


import com.wizzstudio.push.builder.OutTextMessageBuilder;
import com.wizzstudio.push.model.CommonResult;
import com.wizzstudio.push.model.ReplyDTO;
import com.wizzstudio.push.model.WxUserResult;
import com.wizzstudio.push.service.WechatService;
import me.chanjar.weixin.cp.bean.message.WxCpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Value("${wechat.corpId}")
    private String corpId;

    @Autowired
    OutTextMessageBuilder messageBuilder;

    /**
     * 拦截全局异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public CommonResult exceptionHandler( Exception e) {
        return CommonResult.error();
    }

    /**
     * 对自定义的企业微信用户交互时的异常进行统一拦截并返回
     * @param e
     * @return 返回给企业微信(再传给用户)
     */
    @ExceptionHandler(value = WxUserException.class)
    public String wxUserExceptionHandler(WxUserException e){
        //设置回复消息DTO的回复接收者,发送者(企业微信的corpId)和返回正文(异常信息)
        ReplyDTO replyDTO = new ReplyDTO(e.getUserId(),corpId,e.getMsg());
        WxCpXmlOutMessage outMessage = messageBuilder.build(replyDTO);
        //进行消息的加密后推送给用户
        String encryptedXml = outMessage.toEncryptedXml(WechatService.getWxCpService().getWxCpConfigStorage());
        return encryptedXml;
    }
}