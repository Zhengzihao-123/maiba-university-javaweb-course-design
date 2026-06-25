package com.maiba;

import cn.maiba.Article;
import cn.maiba.Board;
import cn.maiba.MyDataBase;
import cn.maiba.PermissionChecker;
import cn.maiba.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 显示帖子修改页面
 * 仅当用户有权限修改时，才显示修改页面
 */
@WebServlet("/logon/ArticleModify")
public class ArticleModifyServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        User user = (User) request.getSession().getAttribute("user");
        
        // 1. 检查用户是否已登录
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/result/user-non-logon.jsp");
            return;
        }
        
        // 2. 获取帖子ID
        String articleIdStr = request.getParameter("id");
        if (articleIdStr == null || articleIdStr.trim().isEmpty()) {
            request.setAttribute("errorMessage", "帖子ID不能为空");
            request.getRequestDispatcher("/result/operation-result.jsp").forward(request, response);
            return;
        }
        
        Integer articleId;
        try {
            articleId = Integer.parseInt(articleIdStr);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "无效的帖子ID");
            request.getRequestDispatcher("/result/operation-result.jsp").forward(request, response);
            return;
        }
        
        // 3. 查询帖子信息
        Article article = (Article) MyDataBase.load(Article.TABLE_NAME, articleId);
        if (article == null) {
            request.setAttribute("errorMessage", "帖子不存在");
            request.getRequestDispatcher("/result/operation-result.jsp").forward(request, response);
            return;
        }
        
        // 4. 权限校验
        if (!PermissionChecker.canModifyArticle(user, article)) {
            request.setAttribute("errorMessage", "您没有权限修改此帖子");
            request.getRequestDispatcher("/result/operation-result.jsp").forward(request, response);
            return;
        }
        
        // 5. 获取板块列表
        MyDataBase.initBoardTable();
        List boardList = MyDataBase.list(Board.TABLE_NAME);
        
        // 6. 转发到修改页面
        request.setAttribute("article", article);
        request.setAttribute("boardList", boardList);
        request.getRequestDispatcher("/logon/article-modify.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}
