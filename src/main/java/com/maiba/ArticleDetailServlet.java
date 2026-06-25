package com.maiba;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import cn.maiba.Article;
import cn.maiba.Board;
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
        if (articleIdStr == null || articleIdStr.isEmpty()) {
            articleIdStr = request.getParameter("id");
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
        
        Article article = (Article) MyDataBase.load(Article.TABLE_NAME, articleId);
        
        if (article == null) {
            request.setAttribute("errorMessage", "帖子不存在");
            request.getRequestDispatcher("/result/operation-result.jsp").forward(request, response);
            return;
        }
        
        // 更新点击数
        article.setHitNum(article.getHitNum() + 1);
        MyDataBase.updateHitNum(Article.TABLE_NAME, articleId, article.getHitNum());
        
        // 获取评论列表
        List remarkList = null;
        try {
            remarkList = MyDataBase.select(Remark.TABLE_NAME, "article_id", articleId, "=");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // 获取板块名称
        String boardName = "未知板块";
        if (article.getBoardId() != null) {
            Board board = (Board) MyDataBase.load(Board.TABLE_NAME, article.getBoardId());
            if (board != null) {
                boardName = board.getName();
            }
        }
        
        User currentUser = (User) request.getSession().getAttribute("user");
        boolean isOwner = false;
        if (currentUser != null && currentUser.getId().equals(article.getUserId())) {
            isOwner = true;
        }
        
        request.setAttribute("article", article);
        request.setAttribute("remarkList", remarkList);
        request.setAttribute("isOwner", isOwner);
        request.setAttribute("userId", article.getUserId());
        request.setAttribute("boardName", boardName);
        
        request.getRequestDispatcher("/logon/ArticleDetail.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
