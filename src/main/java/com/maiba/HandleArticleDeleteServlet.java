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

@WebServlet("/logon/HandleArticleDelete")
public class HandleArticleDeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        String articleIdStr = request.getParameter("articleId");
        Integer articleId = Integer.valueOf(articleIdStr);
        
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            request.setAttribute("errorMessage", "请先登录");
            request.getRequestDispatcher("/logon/Failure-ArticleDelete.jsp").forward(request, response);
            return;
        }
        
        Article article = (Article) MyDataBase.load(Article.TABLE_NAME, articleId);
        if (article == null || article.getUserId() != user.getId()) {
            request.setAttribute("errorMessage", "无权删除该帖子");
            request.getRequestDispatcher("/logon/Failure-ArticleDelete.jsp").forward(request, response);
            return;
        }
        
        boolean success = MyDataBase.delete(Article.TABLE_NAME, articleId);
        
        if (success) {
            request.getRequestDispatcher("/logon/Success-ArticleDelete.jsp").forward(request, response);
        } else {
            request.setAttribute("errorMessage", "删除帖子失败");
            request.getRequestDispatcher("/logon/Failure-ArticleDelete.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
