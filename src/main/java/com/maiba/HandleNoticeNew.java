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

@WebServlet("/logon/admin/HandleNoticeNew")
public class HandleNoticeNew extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        User user = (User) request.getSession().getAttribute("user");
        if (!PermissionChecker.isSuperAdmin(user)) {
            request.setAttribute("errorMessage", "您没有权限发布公告");
            request.getRequestDispatcher("/result/operation-result.jsp").forward(request, response);
            return;
        }
        
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        
        if (title != null && !title.trim().isEmpty() && content != null && !content.trim().isEmpty()) {
            Notice notice = new Notice();
            notice.setTitle(title.trim());
            notice.setContent(content.trim());
            notice.setIsActive(1);
            
            MyDataBase.save(Notice.TABLE_NAME, notice);
        }
        
        response.sendRedirect(request.getContextPath() + "/logon/NoticeList");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}