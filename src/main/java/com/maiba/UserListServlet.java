package com.maiba;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import cn.maiba.User;
import cn.maiba.MyDataBase;

@WebServlet("/logon/UserList")
public class UserListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        // 从数据库中读取所有用户
        List userList = MyDataBase.list(User.TABLE_NAME);
        
        // 把userList放入request
        request.setAttribute("userList", userList);
        
        // 跳转到用户列表视图userlist.jsp
        request.getRequestDispatcher("/logon/UserList.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
