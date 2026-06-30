package com.maiba;

import cn.maiba.User;
import cn.maiba.PermissionChecker;
import cn.maiba.Board;
import cn.maiba.MyDataBase;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/logon/NoticeNew")
public class NoticeNewServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        
        if (user == null || !PermissionChecker.canPostNotice(user)) {
            response.sendRedirect(request.getContextPath() + "/logon/NoticeList");
            return;
        }
        
        boolean isAdmin = PermissionChecker.isSuperAdmin(user);
        boolean isModerator = PermissionChecker.isModerator(user);
        
        List boardList = null;
        try {
            if (isModerator) {
                boardList = MyDataBase.select(Board.TABLE_NAME, "moderator_id", user.getId(), "=");
            } else if (isAdmin) {
                boardList = MyDataBase.list(Board.TABLE_NAME);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        request.setAttribute("isAdmin", isAdmin);
        request.setAttribute("isModerator", isModerator);
        request.setAttribute("boardList", boardList);
        
        request.getRequestDispatcher("/logon/NoticeNew.jsp").forward(request, response);
    }
}