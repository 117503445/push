package com.wizzstudio.push.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

// https://www.cnblogs.com/Sinte-Beuve/p/13260249.html

@Slf4j
@WebFilter(urlPatterns = "/*")
public class CachingContentFilter implements Filter {
    private static final String FORM_CONTENT_TYPE = "multipart/form-data";

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        String contentType = request.getContentType();
        if (request instanceof HttpServletRequest) {
            HttpServletRequest requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) request);
            // #1
            if (contentType != null && contentType.contains(FORM_CONTENT_TYPE)) {
                chain.doFilter(request, response);
            } else {
                chain.doFilter(requestWrapper, response);
            }
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}