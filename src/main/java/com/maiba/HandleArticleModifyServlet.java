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

@WebServlet("/logon/HandleArticleModify")
public class HandleArticleModifyServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        String articleIdStr = request.getParameter("articleId");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String boardIdStr = request.getParameter("boardId");
        
        if (articleIdStr == null || articleIdStr.trim().isEmpty()) {
            request.setAttribute("errorMessage", "帖子ID不能为空");
            request.getRequestDispatcher("/result/operation-result.jsp").forward(request, response);
            return;
        }
        
        Integer articleId;
        try {
            articleId = Integer.valueOf(articleIdStr);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "无效的帖子ID");
            request.getRequestDispatcher("/result/operation-result.jsp").forward(request, response);
            return;
        }
        
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            request.setAttribute("errorMessage", "请先登录");
            request.getRequestDispatcher("/result/operation-result.jsp").forward(request, response);
            return;
        }
        
        Article article = (Article) MyDataBase.load(Article.TABLE_NAME, articleId);
        if (article == null) {
            request.setAttribute("errorMessage", "帖子不存在");
            request.getRequestDispatcher("/result/operation-result.jsp").forward(request, response);
            return;
        }
        
        // 使用PermissionChecker进行权限校验
        if (!PermissionChecker.canModifyArticle(user, article)) {
            request.setAttribute("errorMessage", "您没有权限修改此帖子");
            request.getRequestDispatcher("/result/operation-result.jsp").forward(request, response);
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
        
        // 只有超级管理员才能修改帖子所属板块
        if (boardIdStr != null && !boardIdStr.trim().isEmpty()) {
            if (PermissionChecker.isSuperAdmin(user)) {
                try {
                    article.setBoardId(Integer.valueOf(boardIdStr));
                } catch (NumberFormatException e) {
                    // 忽略无效的板块ID
                }
            }
        }
        
        if (isValid) {
            article.setTitle(title);
            article.setContent(content);
            
            boolean success = MyDataBase.update(Article.TABLE_NAME, article);
            
            if (success) {
                response.sendRedirect(request.getContextPath() + "/logon/ArticleDetail?articleId=" + article.getId());
            } else {
                request.setAttribute("errorMessage", "修改帖子失败");
                request.getRequestDispatcher("/result/operation-result.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("errorMessage", errorMessage.toString());
            request.setAttribute("article", article);
            request.getRequestDispatcher("/result/operation-result.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
