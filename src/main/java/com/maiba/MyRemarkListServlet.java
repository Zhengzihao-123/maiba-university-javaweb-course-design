package com.maiba;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import cn.maiba.MyDataBase;
import cn.maiba.Remark;
import cn.maiba.User;

@WebServlet("/logon/MyRemarkList")
public class MyRemarkListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            request.setAttribute("errorMessage", "请先登录");
            request.getRequestDispatcher("/result/user-non-logon.jsp").forward(request, response);
            return;
        }
        
        List remarkList = null;
        try {
            remarkList = MyDataBase.select(Remark.TABLE_NAME, "user_id", user.getId(), "=");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        request.setAttribute("remarkList", remarkList);
        
        request.getRequestDispatcher("/logon/MyRemarkList.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
