package com.maiba.admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import cn.maiba.Board;
import cn.maiba.MyDataBase;
import cn.maiba.User;

@WebServlet("/logon/admin/BoardDelete")
public class BoardDeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        User user = (User) request.getSession().getAttribute("user");
        if (user == null || !user.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/logon/ArticleList");
            return;
        }
        
        String boardIdStr = request.getParameter("boardId");
        if (boardIdStr == null || boardIdStr.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/logon/admin/BoardList");
            return;
        }
        
        Integer boardId = null;
        try {
            boardId = Integer.parseInt(boardIdStr);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/logon/admin/BoardList");
            return;
        }
        
        if (boardId == 1) {
            request.setAttribute("errorMessage", "默认板块（综合讨论）不允许删除");
            request.setAttribute("boardList", MyDataBase.list(Board.TABLE_NAME));
            request.getRequestDispatcher("/logon/admin/board-list.jsp").forward(request, response);
            return;
        }
        
        boolean success = MyDataBase.delete(Board.TABLE_NAME, boardId);
        
        if (success) {
            MyDataBase.updateArticleBoard(boardId, 1);
        }
        
        response.sendRedirect(request.getContextPath() + "/logon/admin/BoardList");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
