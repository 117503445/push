package com.wizzstudio.push.controller;

import com.alibaba.fastjson.JSONObject;
import com.wizzstudio.push.config.FileConfig;
import com.wizzstudio.push.model.CommonResult;
import com.wizzstudio.push.service.WechatService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.bean.message.WxCpMessage;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;


@RestController
public class CardController {
    @RequestMapping("/push/card/v1")
    public CommonResult<String> get(@RequestBody Map<String, String> body) {
        WxCpMessage message = WxCpMessage.TEXTCARD().agentId(FileConfig.getAgentId()).toUser(body.get("name")).title(body.get("tittle")).description(body.get("description")).url(body.get("url")).btnTxt(body.get("btnTxt")).build();
        try {
            WechatService.getWxCpService().getMessageService().send(message);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        return new CommonResult<String>(0, "success", "");
    }
}
