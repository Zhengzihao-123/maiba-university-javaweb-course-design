package com.maiba;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import cn.maiba.Article;
import cn.maiba.Board;
import cn.maiba.MyDataBase;
import cn.maiba.PermissionChecker;
import cn.maiba.User;

@WebServlet("/logon/HandleArticleNew")
public class HandleArticleNew extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String boardIdStr = request.getParameter("boardId");
        
        User user = (User) request.getSession().getAttribute("user");
        
        // 1. 检查用户是否已登录（使用权限工具类）
        if (!PermissionChecker.isLoggedIn(user)) {
            response.sendRedirect(request.getContextPath() + "/result/user-non-logon.jsp");
            return;
        }
        
        // 2. 检查是否有发帖权限
        if (!PermissionChecker.canPostArticle(user)) {
            request.setAttribute("errorMessage", "您没有发帖权限");
            request.getRequestDispatcher("/result/operation-result.jsp").forward(request, response);
            return;
        }
        
        boolean isValid = true;
        StringBuilder errorMessage = new StringBuilder();
        
        if (title == null || title.trim().isEmpty()) {
            isValid = false;
            errorMessage.append("标题不能为空<br>");
        } else if (title.trim().length() > 200) {
            isValid = false;
            errorMessage.append("标题不能超过200个字符<br>");
        }
        
        if (content == null || content.trim().isEmpty()) {
            isValid = false;
            errorMessage.append("内容不能为空<br>");
        } else if (content.trim().length() < 10) {
            isValid = false;
            errorMessage.append("内容字数不少于10个<br>");
        }
        
        Integer boardId = null;
        Board board = null;
        if (boardIdStr == null || boardIdStr.trim().isEmpty()) {
            isValid = false;
            errorMessage.append("请选择所属板块<br>");
        } else {
            try {
                boardId = Integer.parseInt(boardIdStr);
                // 验证板块存在
                board = (Board) MyDataBase.load(Board.TABLE_NAME, boardId);
                if (board == null) {
                    isValid = false;
                    errorMessage.append("选择的板块不存在<br>");
                }
            } catch (NumberFormatException e) {
                isValid = false;
                errorMessage.append("无效的板块选择<br>");
            }
        }
        
        if (isValid && board != null) {
            try {
                Article article = new Article();
                article.setTitle(title.trim());
                article.setContent(content.trim());
                article.setUserId(user.getId());
                article.setBoardId(boardId);
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
