package com.maiba;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import cn.maiba.MyDataBase;
import cn.maiba.User;
import cn.maiba.PermissionChecker;

@WebServlet("/logon/HandleUserDelete")
public class HandleUserDeleteServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        User currentUser = (User) request.getSession().getAttribute("user");
        
        // 1. 检查用户是否已登录
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/result/user-non-logon.jsp");
            return;
        }
        
        // 2. 权限校验：只有超级管理员可以删除用户
        if (!PermissionChecker.isSuperAdmin(currentUser)) {
            request.setAttribute("errorMessage", "您没有权限删除用户");
            request.getRequestDispatcher("/result/operation-result.jsp").forward(request, response);
            return;
        }
        
        // 获取用户ID
        String userIdStr = request.getParameter("userId");
        if (userIdStr == null || userIdStr.trim().isEmpty()) {
            request.setAttribute("errorMessage", "用户ID不能为空");
            request.getRequestDispatcher("/result/operation-result.jsp").forward(request, response);
            return;
        }
        
        Integer userId;
        try {
            userId = Integer.parseInt(userIdStr);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "无效的用户ID");
            request.getRequestDispatcher("/result/operation-result.jsp").forward(request, response);
            return;
        }
        
        // 查询目标用户
        User targetUser = (User) MyDataBase.load(User.TABLE_NAME, userId);
        if (targetUser == null) {
            request.setAttribute("errorMessage", "用户不存在");
            request.getRequestDispatcher("/result/operation-result.jsp").forward(request, response);
            return;
        }
        
        // 3. 权限校验：不能删除自己
        if (!PermissionChecker.canDeleteUser(currentUser, targetUser)) {
            request.setAttribute("errorMessage", "不能删除自己");
            request.getRequestDispatcher("/result/operation-result.jsp").forward(request, response);
            return;
        }
        
        // 从数据库中删除用户
        boolean success = MyDataBase.delete(User.TABLE_NAME, userId);
        
        if (success) {
            request.getRequestDispatcher("/logon/Success-UserDelete.jsp").forward(request, response);
        } else {
            request.setAttribute("errorMessage", "删除用户失败");
            request.getRequestDispatcher("/logon/Failure-UserDelete.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
