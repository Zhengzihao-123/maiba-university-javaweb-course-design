package com.maiba;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import cn.maiba.Article;
import cn.maiba.MyDataBase;
import cn.maiba.User;
import cn.maiba.PermissionChecker;

@WebServlet("/logon/HandleArticleTop")
public class HandleArticleTop extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        User user = (User) request.getSession().getAttribute("user");
        if (!PermissionChecker.isSuperAdmin(user)) {
            request.setAttribute("errorMessage", "您没有权限置顶帖子");
            request.getRequestDispatcher("/result/operation-result.jsp").forward(request, response);
            return;
        }
        
        String articleIdStr = request.getParameter("articleId");
        String isTopStr = request.getParameter("isTop");
        
        try {
            int articleId = Integer.parseInt(articleIdStr);
            int isTop = Integer.parseInt(isTopStr);
            
            MyDataBase.updateArticleTop(Article.TABLE_NAME, articleId, isTop);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        response.sendRedirect(request.getContextPath() + "/logon/ArticleList");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}