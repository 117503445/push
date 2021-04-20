package com.wizzstudio.push.log;

import com.wizzstudio.push.config.StaticFactory;
import com.wizzstudio.push.service.ESClient;
import org.apache.commons.io.IOUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class HttpLog {

    /**
     * 以 controller 包下定义的所有请求为切入点
     */
    @Pointcut("execution(public * com.wizzstudio.push.controller..*.*(..))")
    public void webLog() {
    }


    /**
     * 环绕
     *
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes == null) {
            return proceedingJoinPoint.proceed();
        }

        HttpServletRequest request = attributes.getRequest();

        Object result = proceedingJoinPoint.proceed();
        if (!StaticFactory.getEsConfig().getEnabled()) {
            return result;
        }

        // todo 强制转换失败时，说明是表单数据，不应当添加 requestBody
        ContentCachingRequestWrapper wrapper = (ContentCachingRequestWrapper) request;
        String requestBody = IOUtils.toString(wrapper.getContentAsByteArray(), "utf-8");

        Map<String, Object> jsonMap = new HashMap<>();

        // 调用 controller 的全路径以及执行方法
        jsonMap.put("classMethod", proceedingJoinPoint.getSignature().getDeclaringTypeName() + "." + proceedingJoinPoint.getSignature().getName());
        // 请求的 IP
        jsonMap.put("ip", getIpAddr(request));

        jsonMap.put("url", request.getMethod() + " " + request.getRequestURI() + "?" + request.getQueryString());


        jsonMap.put("requestBody", requestBody);
        // 返回 的 body
        // todo 大小
        jsonMap.put("responseBody", result.toString());

        jsonMap.put("startTime", startTime);

        jsonMap.put("duration", System.currentTimeMillis() - startTime);

        var response = attributes.getResponse();
        if (response != null) {
            jsonMap.put("statusCode", response.getStatus());
        }

        //todo asynchronous
        ESClient.client.index(new IndexRequest().source(jsonMap).index("push-http-log"), RequestOptions.DEFAULT);


        return result;
    }

    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
