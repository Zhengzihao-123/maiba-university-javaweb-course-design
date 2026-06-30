package com.maiba;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.maiba.User;

@WebServlet("/OnlineUserList")
public class OnlineUserListServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        
        List<Map<String, Object>> users = new ArrayList<>();
        Map<String, Object> result = new HashMap<>();
        
        try {
            List<User> onlineUsers = OnlineUserListener.getOnlineUsers();
            for (User u : onlineUsers) {
                if (u == null) continue;
                Map<String, Object> uMap = new HashMap<>();
                uMap.put("id", u.getId());
                uMap.put("account", u.getAccount());
                uMap.put("userName", u.getUserName());
                uMap.put("role", u.getRole());
                users.add(uMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        result.put("count", OnlineUserListener.getOnlineCount());
        result.put("totalCount", OnlineUserListener.getTotalOnlineCount());
        result.put("loggedInCount", OnlineUserListener.getLoggedInCount());
        result.put("users", users);
        
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"count\":").append(OnlineUserListener.getOnlineCount()).append(",");
        json.append("\"totalCount\":").append(OnlineUserListener.getTotalOnlineCount()).append(",");
        json.append("\"loggedInCount\":").append(OnlineUserListener.getLoggedInCount()).append(",");
        json.append("\"users\":[");
        for (int i = 0; i < users.size(); i++) {
            if (i > 0) json.append(",");
            Map<String, Object> u = users.get(i);
            json.append("{")
                .append("\"id\":").append(u.get("id")).append(",")
                .append("\"account\":\"").append(escapeJson(String.valueOf(u.get("account")))).append("\",")
                .append("\"userName\":\"").append(escapeJson(String.valueOf(u.get("userName")))).append("\",")
                .append("\"role\":").append(u.get("role"))
                .append("}");
        }
        json.append("]}");
        
        response.getWriter().write(json.toString());
    }
    
    private String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
