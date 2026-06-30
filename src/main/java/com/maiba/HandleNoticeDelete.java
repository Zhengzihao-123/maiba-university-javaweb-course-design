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

@WebServlet("/logon/HandleNoticeDelete")
public class HandleNoticeDelete extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            request.setAttribute("errorMessage", "请先登录");
            request.getRequestDispatcher("/result/operation-result.jsp").forward(request, response);
            return;
        }
        
        String noticeIdStr = request.getParameter("id");
        if (noticeIdStr == null || noticeIdStr.isEmpty()) {
            request.setAttribute("errorMessage", "公告ID不能为空");
            request.getRequestDispatcher("/result/operation-result.jsp").forward(request, response);
            return;
        }
        
        try {
            int noticeId = Integer.parseInt(noticeIdStr);
            Notice notice = (Notice) MyDataBase.load(Notice.TABLE_NAME, noticeId);
            
            if (notice == null) {
                request.setAttribute("errorMessage", "公告不存在");
                request.getRequestDispatcher("/result/operation-result.jsp").forward(request, response);
                return;
            }
            
            if (!PermissionChecker.canDeleteNotice(user, notice)) {
                request.setAttribute("errorMessage", "您没有权限删除此公告");
                request.getRequestDispatcher("/result/operation-result.jsp").forward(request, response);
                return;
            }
            
            MyDataBase.delete(Notice.TABLE_NAME, noticeId);
            
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "无效的公告ID");
            request.getRequestDispatcher("/result/operation-result.jsp").forward(request, response);
            return;
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "删除失败");
            request.getRequestDispatcher("/result/operation-result.jsp").forward(request, response);
            return;
        }
        
        response.sendRedirect(request.getContextPath() + "/logon/NoticeList");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}