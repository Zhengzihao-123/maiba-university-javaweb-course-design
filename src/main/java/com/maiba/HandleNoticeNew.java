package com.maiba;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import cn.maiba.Notice;
import cn.maiba.MyDataBase;
import cn.maiba.User;
import cn.maiba.PermissionChecker;

@WebServlet("/logon/HandleNoticeNew")
public class HandleNoticeNew extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        User user = (User) request.getSession().getAttribute("user");
        if (!PermissionChecker.canPostNotice(user)) {
            request.setAttribute("errorMessage", "您没有权限发布公告");
            request.getRequestDispatcher("/result/operation-result.jsp").forward(request, response);
            return;
        }
        
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String categoryStr = request.getParameter("category");
        String boardIdStr = request.getParameter("boardId");
        
        if (title != null && !title.trim().isEmpty() && content != null && !content.trim().isEmpty()) {
            Notice notice = new Notice();
            notice.setTitle(title.trim());
            notice.setContent(content.trim());
            notice.setIsActive(1);
            
            int category = Notice.CATEGORY_SYSTEM;
            if (categoryStr != null && !categoryStr.isEmpty()) {
                try {
                    category = Integer.parseInt(categoryStr);
                } catch (NumberFormatException e) {
                    category = Notice.CATEGORY_SYSTEM;
                }
            }
            
            if (PermissionChecker.isSuperAdmin(user)) {
                notice.setCategory(category);
            } else if (PermissionChecker.isModerator(user)) {
                notice.setCategory(Notice.CATEGORY_BOARD);
                if (boardIdStr != null && !boardIdStr.isEmpty()) {
                    try {
                        int boardId = Integer.parseInt(boardIdStr);
                        if (PermissionChecker.isModeratorOfBoard(user, boardId)) {
                            notice.setBoardId(boardId);
                        } else {
                            request.setAttribute("errorMessage", "您只能发布自己管理板块的公告");
                            request.getRequestDispatcher("/result/operation-result.jsp").forward(request, response);
                            return;
                        }
                    } catch (NumberFormatException e) {
                        request.setAttribute("errorMessage", "无效的板块ID");
                        request.getRequestDispatcher("/result/operation-result.jsp").forward(request, response);
                        return;
                    }
                } else {
                    request.setAttribute("errorMessage", "请选择板块");
                    request.getRequestDispatcher("/result/operation-result.jsp").forward(request, response);
                    return;
                }
            }
            
            MyDataBase.save(Notice.TABLE_NAME, notice);
        }
        
        response.sendRedirect(request.getContextPath() + "/logon/NoticeList");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}