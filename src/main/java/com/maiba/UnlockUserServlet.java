package com.maiba;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import cn.maiba.User;
import cn.maiba.MyDataBase;
import cn.maiba.PermissionChecker;

@WebServlet("/logon/admin/UnlockUser")
public class UnlockUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        User currentUser = (User) request.getSession().getAttribute("user");
        if (!PermissionChecker.isSuperAdmin(currentUser)) {
            request.setAttribute("errorMessage", "您没有权限解锁用户");
            request.getRequestDispatcher("/result/operation-result.jsp").forward(request, response);
            return;
        }
        
        String userIdStr = request.getParameter("userId");
        
        try {
            int userId = Integer.parseInt(userIdStr);
            MyDataBase.updateUserUnlock(User.TABLE_NAME, userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        response.sendRedirect(request.getContextPath() + "/logon/admin/UserList");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}