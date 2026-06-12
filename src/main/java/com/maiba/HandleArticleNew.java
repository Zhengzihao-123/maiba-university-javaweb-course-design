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

@WebServlet("/logon/HandleArticleNew")
public class HandleArticleNew extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            request.setAttribute("errorMessage", "请先登录");
            request.getRequestDispatcher("/logon/Failure-ArticleNew.jsp").forward(request, response);
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
            try {
                Article article = new Article();
                article.setTitle(title);
                article.setContent(content);
                article.setUserId(user.getId());
                article.setRemarkNum(0);
                article.setHitNum(0);
                
                boolean success = MyDataBase.save(Article.TABLE_NAME, article);
                
                if (success) {
                    request.setAttribute("article", article);
                    request.getRequestDispatcher("/logon/Success-ArticleNew.jsp").forward(request, response);
                } else {
                    request.setAttribute("errorMessage", "发布帖子失败");
                    request.getRequestDispatcher("/logon/Failure-ArticleNew.jsp").forward(request, response);
                }
            } catch (Exception e) {
                request.setAttribute("errorMessage", "数据库操作失败：" + e.getMessage());
                request.getRequestDispatcher("/logon/Failure-ArticleNew.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("errorMessage", errorMessage.toString());
            request.setAttribute("title", title);
            request.setAttribute("content", content);
            request.getRequestDispatcher("/logon/Failure-ArticleNew.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
