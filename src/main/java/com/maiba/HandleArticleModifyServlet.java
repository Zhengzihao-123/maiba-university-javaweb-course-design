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

@WebServlet("/logon/HandleArticleModify")
public class HandleArticleModifyServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        String articleIdStr = request.getParameter("articleId");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        
        Integer articleId = Integer.valueOf(articleIdStr);
        
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            request.setAttribute("errorMessage", "请先登录");
            request.getRequestDispatcher("/logon/Failure-ArticleModify.jsp").forward(request, response);
            return;
        }
        
        Article article = (Article) MyDataBase.load(Article.TABLE_NAME, articleId);
        if (article == null || article.getUserId() != user.getId()) {
            request.setAttribute("errorMessage", "无权修改该帖子");
            request.getRequestDispatcher("/logon/Failure-ArticleModify.jsp").forward(request, response);
            return;
        }
        
        boolean isValid = true;
        StringBuilder errorMessage = new StringBuilder();
        
        if (title == null || title.trim().isEmpty()) {
            isValid = false;
            errorMessage.append("标题不能为空<br>");
        }
        
        if (content == null || content.trim().isEmpty()) {
            isValid = false;
            errorMessage.append("内容不能为空<br>");
        } else if (content.trim().length() < 10) {
            isValid = false;
            errorMessage.append("内容字数不少于10个<br>");
        }
        
        if (isValid) {
            article.setTitle(title);
            article.setContent(content);
            
            boolean success = MyDataBase.update(Article.TABLE_NAME, article);
            
            if (success) {
                request.setAttribute("article", article);
                request.getRequestDispatcher("/logon/Success-ArticleModify.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "修改帖子失败");
                request.getRequestDispatcher("/logon/Failure-ArticleModify.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("errorMessage", errorMessage.toString());
            request.setAttribute("article", article);
            request.getRequestDispatcher("/logon/Failure-ArticleModify.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
