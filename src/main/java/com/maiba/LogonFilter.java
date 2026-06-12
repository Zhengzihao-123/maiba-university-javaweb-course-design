package com.maiba;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class LogonFilter implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化过滤器
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();
        
        String path = httpRequest.getRequestURI();
        
        // 检查是否需要登录
        if (path.contains("/logon/")) {
            // 检查用户是否已登录
            if (session.getAttribute("user") == null) {
                // 用户未登录，保存用户要访问的URL到session
                String url = UrlUtil.getURL(httpRequest);
                session.setAttribute("redirectUrl", url);
                // 跳转到提示登录页面
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/result/user-non-logon.jsp");
                return;
            }
        }
        
        // 放行请求
        chain.doFilter(request, response);
    }
    
    @Override
    public void destroy() {
        // 销毁过滤器
    }
}
