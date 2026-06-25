package com.maiba.admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import cn.maiba.Board;
import cn.maiba.MyDataBase;
import cn.maiba.User;
import cn.maiba.PermissionChecker;

@WebServlet("/logon/admin/BoardList")
public class BoardListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        User user = (User) request.getSession().getAttribute("user");
        if (!PermissionChecker.hasManagementRole(user)) {
            request.setAttribute("errorMessage", "您没有权限访问板块管理");
            request.getRequestDispatcher("/result/operation-result.jsp").forward(request, response);
            return;
        }
        
        List boardList;
        if (PermissionChecker.isSuperAdmin(user)) {
            boardList = MyDataBase.list(Board.TABLE_NAME);
        } else {
            try {
                boardList = MyDataBase.select(Board.TABLE_NAME, "moderator_id", user.getId(), "=");
            } catch (Exception e) {
                boardList = null;
            }
        }
        request.setAttribute("boardList", boardList);
        
        request.getRequestDispatcher("/logon/admin/board-list.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
