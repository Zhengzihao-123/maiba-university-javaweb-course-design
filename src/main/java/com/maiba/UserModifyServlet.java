package com.maiba;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import cn.maiba.User;
import cn.maiba.MyDataBase;

@WebServlet("/logon/UserModify")
public class UserModifyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        // 获取用户ID
        String userIdStr = request.getParameter("userId");
        if (userIdStr == null || userIdStr.isEmpty()) {
            userIdStr = request.getParameter("id");
        }
        Integer userId = Integer.valueOf(userIdStr);
        
        // 从数据库中读取用户信息
        User user = (User) MyDataBase.load(User.TABLE_NAME, userId);
        
        // 把user放入request
        request.setAttribute("user", user);
        
        // 跳转到用户修改视图usermodify.jsp
        request.getRequestDispatcher("/logon/UserModify.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
