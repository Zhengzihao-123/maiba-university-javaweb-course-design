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

@WebServlet("/logon/admin/BoardModify")
public class BoardModifyServlet extends HttpServlet {
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
        
        Board board = (Board) MyDataBase.load(Board.TABLE_NAME, boardId);
        if (board == null) {
            response.sendRedirect(request.getContextPath() + "/logon/admin/BoardList");
            return;
        }
        
        request.setAttribute("board", board);
        request.getRequestDispatcher("/logon/admin/board-modify.jsp").forward(request, response);
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
        
        String boardIdStr = request.getParameter("boardId");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String sortOrderStr = request.getParameter("sortOrder");
        
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
        
        if (name == null || name.trim().isEmpty()) {
            Board board = (Board) MyDataBase.load(Board.TABLE_NAME, boardId);
            request.setAttribute("errorMessage", "板块名称不能为空");
            request.setAttribute("board", board);
            request.getRequestDispatcher("/logon/admin/board-modify.jsp").forward(request, response);
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
        board.setId(boardId);
        board.setName(name.trim());
        board.setDescription(description != null ? description.trim() : "");
        board.setSortOrder(sortOrder);
        // TODO: 后续版主功能扩展点 - 当前版主ID留空，后续可在此处设置版主
        
        boolean success = MyDataBase.update(Board.TABLE_NAME, board);
        
        if (success) {
            response.sendRedirect(request.getContextPath() + "/logon/admin/BoardList");
        } else {
            Board originalBoard = (Board) MyDataBase.load(Board.TABLE_NAME, boardId);
            request.setAttribute("errorMessage", "修改板块失败");
            request.setAttribute("board", originalBoard);
            request.getRequestDispatcher("/logon/admin/board-modify.jsp").forward(request, response);
        }
    }
}
