package com.maiba;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import cn.maiba.Notice;
import cn.maiba.MyDataBase;
import cn.maiba.Board;
import cn.maiba.User;
import cn.maiba.PermissionChecker;

@WebServlet("/logon/NoticeList")
public class NoticeListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        MyDataBase.initNoticeTable();
        
        String categoryStr = request.getParameter("category");
        String boardIdStr = request.getParameter("boardId");
        
        List noticeList;
        
        boolean hasCategory = categoryStr != null && !categoryStr.isEmpty();
        boolean hasBoardId = boardIdStr != null && !boardIdStr.isEmpty();
        
        if (hasBoardId) {
            try {
                int boardId = Integer.parseInt(boardIdStr);
                if (hasCategory) {
                    int category = Integer.parseInt(categoryStr);
                    if (category == Notice.CATEGORY_BOARD) {
                        noticeList = MyDataBase.select(Notice.TABLE_NAME, "board_id", boardId, "=");
                    } else {
                        noticeList = MyDataBase.select(Notice.TABLE_NAME, "category", category, "=");
                    }
                } else {
                    List boardNotices = MyDataBase.select(Notice.TABLE_NAME, "board_id", boardId, "=");
                    List systemNotices = MyDataBase.select(Notice.TABLE_NAME, "category", Notice.CATEGORY_SYSTEM, "=");
                    
                    noticeList = new ArrayList();
                    if (systemNotices != null) {
                        noticeList.addAll(systemNotices);
                    }
                    if (boardNotices != null) {
                        noticeList.addAll(boardNotices);
                    }
                }
            } catch (Exception e) {
                noticeList = MyDataBase.list(Notice.TABLE_NAME);
            }
        } else if (hasCategory) {
            try {
                int category = Integer.parseInt(categoryStr);
                noticeList = MyDataBase.select(Notice.TABLE_NAME, "category", category, "=");
            } catch (Exception e) {
                noticeList = MyDataBase.list(Notice.TABLE_NAME);
            }
        } else {
            noticeList = MyDataBase.list(Notice.TABLE_NAME);
        }
        
        request.setAttribute("noticeList", noticeList);
        request.setAttribute("currentCategory", categoryStr);
        request.setAttribute("currentBoardId", boardIdStr);
        
        List boardList = MyDataBase.list(Board.TABLE_NAME);
        request.setAttribute("boardList", boardList);
        
        User user = (User) request.getSession().getAttribute("user");
        boolean canPostNotice = PermissionChecker.canPostNotice(user);
        request.setAttribute("canPostNotice", canPostNotice);
        
        request.getRequestDispatcher("/logon/NoticeList.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}