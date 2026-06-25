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

@WebServlet("/logon/HandleArticleDelete")
public class HandleArticleDeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        String articleIdStr = request.getParameter("id");
        if (articleIdStr == null || articleIdStr.isEmpty()) {
            articleIdStr = request.getParameter("articleId");
        }
        
        if (articleIdStr == null || articleIdStr.isEmpty()) {
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
        if (!PermissionChecker.canDeleteArticle(user, article)) {
            request.setAttribute("errorMessage", "您没有权限删除该帖子");
            request.getRequestDispatcher("/result/operation-result.jsp").forward(request, response);
            return;
        }
        
        // 先删除相关评论
        MyDataBase.deleteByField("t_remark", "article_id", articleId);
        
        // 删除帖子
        boolean success = MyDataBase.delete(Article.TABLE_NAME, articleId);
        
        if (success) {
            response.sendRedirect(request.getContextPath() + "/logon/ArticleList");
        } else {
            request.setAttribute("errorMessage", "删除帖子失败");
            request.getRequestDispatcher("/result/operation-result.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
