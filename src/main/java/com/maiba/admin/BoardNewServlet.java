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

@WebServlet("/logon/admin/BoardNew")
public class BoardNewServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        User user = (User) request.getSession().getAttribute("user");
        if (user == null || !user.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/logon/ArticleList");
            return;
        }
        
        request.getRequestDispatcher("/logon/admin/board-new.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        User user = (User) request.getSession().getAttribute("user");
        if (user == null || !user.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/logon/ArticleList");
            return;
        }
        
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String sortOrderStr = request.getParameter("sortOrder");
        
        if (name == null || name.trim().isEmpty()) {
            request.setAttribute("errorMessage", "板块名称不能为空");
            request.setAttribute("name", name);
            request.setAttribute("description", description);
            request.setAttribute("sortOrder", sortOrderStr);
            request.getRequestDispatcher("/logon/admin/board-new.jsp").forward(request, response);
            return;
        }
        
        int sortOrder = 0;
        if (sortOrderStr != null && !sortOrderStr.trim().isEmpty()) {
            try {
                sortOrder = Integer.parseInt(sortOrderStr);
            } catch (NumberFormatException e) {
                sortOrder = 0;
            }
        }
        
        Board board = new Board();
        board.setName(name.trim());
        board.setDescription(description != null ? description.trim() : "");
        board.setSortOrder(sortOrder);
        // TODO: 后续版主功能扩展点 - 当前版主ID留空，后续可在此处设置版主
        
        boolean success = MyDataBase.save(Board.TABLE_NAME, board);
        
        if (success) {
            response.sendRedirect(request.getContextPath() + "/logon/admin/BoardList");
        } else {
            request.setAttribute("errorMessage", "创建板块失败");
            request.setAttribute("name", name);
            request.setAttribute("description", description);
            request.setAttribute("sortOrder", sortOrderStr);
            request.getRequestDispatcher("/logon/admin/board-new.jsp").forward(request, response);
        }
    }
}
