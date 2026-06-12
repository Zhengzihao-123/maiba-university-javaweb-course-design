package com.maiba;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import cn.maiba.MyDataBase;
import cn.maiba.User;

@WebServlet("/logon/HandleUserDelete")
public class HandleUserDeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        // 获取用户ID
        String userIdStr = request.getParameter("userId");
        Integer userId = Integer.valueOf(userIdStr);
        
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
