package com.maiba;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import cn.maiba.Article;
import cn.maiba.MyDataBase;
import cn.maiba.Remark;
import cn.maiba.User;

@WebServlet("/logon/ArticleDetail")
public class ArticleDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        String articleIdStr = request.getParameter("articleId");
        Integer articleId = Integer.valueOf(articleIdStr);
        
        Article article = (Article) MyDataBase.load(Article.TABLE_NAME, articleId);
        
        if (article != null) {
            article.setHitNum(article.getHitNum() + 1);
            MyDataBase.updateHitNum(Article.TABLE_NAME, articleId, article.getHitNum());
        }
        
        List remarkList = null;
        try {
            remarkList = MyDataBase.select(Remark.TABLE_NAME, "article_id", articleId, "=");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        User currentUser = (User) request.getSession().getAttribute("user");
        boolean isOwner = false;
        if (currentUser != null && article != null && currentUser.getId() == article.getUserId()) {
            isOwner = true;
        }
        
        request.setAttribute("article", article);
        request.setAttribute("remarkList", remarkList);
        request.setAttribute("isOwner", isOwner);
        
        request.getRequestDispatcher("/logon/ArticleDetail.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
