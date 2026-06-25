package com.maiba;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import cn.maiba.User;

/**
 * 增强版在线用户监听器
 * 不仅统计 session 数量，还记录具体在线用户信息
 */
@WebListener
public class OnlineUserListener implements HttpSessionListener {
    
    private static int onlineCount = 0;
    private static final Map<String, User> SESSION_USER_MAP = new ConcurrentHashMap<>();
    
    @Override
    public void sessionCreated(HttpSessionEvent se) {
    }
    
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        try {
            if (SESSION_USER_MAP.containsKey(session.getId())) {
                SESSION_USER_MAP.remove(session.getId());
                onlineCount--;
                if (onlineCount < 0) {
                    onlineCount = 0;
                }
            }
        } catch (Exception e) {
        }
    }
    
    public static void registerOnlineUser(HttpSession session, User user) {
        if (session != null && user != null) {
            String sessionId = session.getId();
            if (!SESSION_USER_MAP.containsKey(sessionId)) {
                SESSION_USER_MAP.put(sessionId, user);
                onlineCount++;
            }
        }
    }
    
    public static void unregisterOnlineUser(HttpSession session) {
        if (session != null) {
            String sessionId = session.getId();
            if (SESSION_USER_MAP.containsKey(sessionId)) {
                SESSION_USER_MAP.remove(sessionId);
                onlineCount--;
                if (onlineCount < 0) {
                    onlineCount = 0;
                }
            }
        }
    }
    
    public static int getOnlineCount() {
        return onlineCount;
    }
    
    public static void setOnlineCount(int count) {
        onlineCount = count;
    }
    
    public static List<User> getOnlineUsers() {
        return new ArrayList<>(SESSION_USER_MAP.values());
    }
    
    public static Map<String, User> getSessionUserMap() {
        return SESSION_USER_MAP;
    }
}
