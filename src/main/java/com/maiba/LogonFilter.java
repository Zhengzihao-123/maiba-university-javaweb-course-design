package com.maiba;

import cn.maiba.PermissionChecker;
import cn.maiba.RoleConstants;
import cn.maiba.User;
import com.maiba.UrlUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebFilter("/*")
public class LogonFilter implements Filter {
    
    // 游客可访问的路径（无需登录）
    private static final List<String> GUEST_ALLOWED_PATHS = Arrays.asList(
            "/",                                           // 首页
            "/UserLogon.jsp",                              // 登录页
            "/UserRegister.jsp",                           // 注册页
            "/HandleUserLogon",                           // 登录处理
            "/user/HandleUserLogon",                      // 登录处理（新路径）
            "/HandleUserRegister",                         // 注册处理
            "/logon/NoticeList",                           // 公告列表（公开）
            "/logon/ArticleList",                          // 帖子列表（公开浏览）
            "/logon/ArticleDetail",                        // 帖子详情（公开浏览）
            "/logon/UserInfo",                             // 用户信息（公开浏览）
            "/result/"                                     // 结果页面（游客可访问）
    );
    
    // 仅管理员可访问的路径
    private static final List<String> ADMIN_ONLY_PATHS = Arrays.asList(
            "/admin/",                                     // 后台管理目录
            "/logon/admin/"                               // 后台管理目录（别名）
    );
    
    // 仅超级管理员可访问的路径
    private static final List<String> SUPER_ADMIN_ONLY_PATHS = Arrays.asList(
            "/logon/admin/User",                          // 用户管理
            "/logon/admin/Board",                         // 板块管理（已有权限控制）
            "/logon/admin/UserList"                       // 用户列表管理
    );
    
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
        String contextPath = httpRequest.getContextPath();
        String relativePath = path.substring(contextPath.length());
        
        User user = (User) session.getAttribute("user");
        
        // 1. 检查是否为游客可访问的路径
        if (isGuestAllowed(relativePath)) {
            chain.doFilter(request, response);
            return;
        }
        
        // 2. 检查用户是否已登录（对于需要登录的路径）
        if (user == null) {
            // 用户未登录，保存用户要访问的URL到session
            String url = UrlUtil.getURL(httpRequest);
            session.setAttribute("redirectUrl", url);
            // 跳转到提示登录页面
            httpResponse.sendRedirect(contextPath + "/result/user-non-logon.jsp");
            return;
        }
        
        // 3. 检查管理员路径权限
        if (isAdminOnlyPath(relativePath)) {
            if (!PermissionChecker.isSuperAdmin(user)) {
                // 非超级管理员访问管理员路径，重定向到首页
                httpResponse.sendRedirect(contextPath + "/logon/ArticleList");
                return;
            }
        }
        
        // 4. 放行请求
        chain.doFilter(request, response);
    }
    
    /**
     * 检查路径是否为游客可访问
     */
    private boolean isGuestAllowed(String relativePath) {
        // 检查完整路径匹配
        for (String allowedPath : GUEST_ALLOWED_PATHS) {
            if (relativePath.equals(allowedPath) || relativePath.equals(allowedPath + "/")) {
                return true;
            }
        }
        
        // 检查前缀匹配（用于子路径如 /logon/ArticleDetail?id=1 或 /result/page.jsp）
        for (String allowedPrefix : GUEST_ALLOWED_PATHS) {
            // 对于以 / 结尾的前缀（如 /result/），直接检查 relativePath 是否以该前缀开头
            if (allowedPrefix.endsWith("/")) {
                if (relativePath.startsWith(allowedPrefix) || relativePath.startsWith(allowedPrefix.substring(0, allowedPrefix.length() - 1))) {
                    return true;
                }
            } else if (relativePath.startsWith(allowedPrefix + "?") || relativePath.startsWith(allowedPrefix + "/")) {
                return true;
            }
        }
        
        // 检查静态资源
        if (relativePath.contains(".") && !relativePath.contains("/logon/")) {
            String suffix = relativePath.substring(relativePath.lastIndexOf("."));
            if (suffix.equals(".css") || suffix.equals(".js") || suffix.equals(".png") 
                    || suffix.equals(".jpg") || suffix.equals(".gif") || suffix.equals(".ico")) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * 检查路径是否为管理员专用
     */
    private boolean isAdminOnlyPath(String relativePath) {
        for (String adminPath : ADMIN_ONLY_PATHS) {
            if (relativePath.startsWith(adminPath)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void destroy() {
        // 销毁过滤器
    }
}
