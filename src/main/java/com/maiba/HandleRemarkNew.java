package com.maiba;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import cn.maiba.Article;
import cn.maiba.MyDataBase;
import cn.maiba.Remark;
import cn.maiba.User;

@WebServlet("/logon/HandleRemarkNew")
public class HandleRemarkNew extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        String articleIdStr = request.getParameter("articleId");
        String remarkContent = request.getParameter("content");
        
        Integer articleId = Integer.valueOf(articleIdStr);
        
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            request.setAttribute("errorMessage", "请先登录");
            request.getRequestDispatcher("/logon/Failure-RemarkNew.jsp").forward(request, response);
            return;
        }
        
        boolean isValid = true;
        StringBuilder errorMessage = new StringBuilder();
        
        if (remarkContent == null || remarkContent.trim().isEmpty()) {
            isValid = false;
            errorMessage.append("评论内容不能为空<br>");
        } else if (remarkContent.trim().length() < 5) {
            isValid = false;
            errorMessage.append("评论内容不少于5个字符<br>");
        }
        
        if (isValid) {
            try {
                Remark remark = new Remark();
                remark.setArticleId(articleId);
                remark.setUserId(user.getId());
                remark.setRemark(remarkContent);
                
                boolean success = MyDataBase.save(Remark.TABLE_NAME, remark);
                
                if (success) {
                    Article article = (Article) MyDataBase.load(Article.TABLE_NAME, articleId);
                    if (article != null) {
                        int newRemarkNum = article.getRemarkNum() + 1;
                        Timestamp now = new Timestamp(System.currentTimeMillis());
                        MyDataBase.updateRemarkNum(Article.TABLE_NAME, articleId, newRemarkNum, now);
                    }
                    
                    response.sendRedirect(request.getContextPath() + "/logon/ArticleDetail?articleId=" + articleId);
                } else {
                    request.setAttribute("errorMessage", "发布评论失败");
                    request.getRequestDispatcher("/logon/Failure-RemarkNew.jsp").forward(request, response);
                }
            } catch (Exception e) {
                request.setAttribute("errorMessage", "数据库操作失败：" + e.getMessage());
                request.getRequestDispatcher("/logon/Failure-RemarkNew.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("errorMessage", errorMessage.toString());
            request.setAttribute("articleId", articleId);
            request.setAttribute("remark", remarkContent);
            request.getRequestDispatcher("/logon/Failure-RemarkNew.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
