<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>操作结果</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f5f5f5; margin: 0; padding: 20px; }
        .container { max-width: 600px; margin: 0 auto; background: white; padding: 40px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); text-align: center; }
        h1 { color: #333; margin-bottom: 20px; }
        .success { color: #4CAF50; font-size: 24px; }
        .error { color: #dc3545; font-size: 24px; }
        p { font-size: 16px; color: #666; margin-bottom: 30px; }
        a { display: inline-block; padding: 10px 20px; background: #667eea; color: white; text-decoration: none; border-radius: 4px; }
        a:hover { background: #5a6fd6; }
    </style>
</head>
<body>
    <%@ include file="/include/header.jsp" %>
    
    <div class="container">
        <% 
            String errorMessage = (String) request.getAttribute("errorMessage");
            if (errorMessage != null && !errorMessage.isEmpty()) {
        %>
            <h1 class="error">操作失败</h1>
            <p><%= errorMessage %></p>
        <% } else { %>
            <h1 class="success">操作成功</h1>
            <p>您的操作已完成。</p>
        <% } %>
        <p><a href="${pageContext.request.contextPath}/logon/ArticleList">返回帖子列表</a></p>
    </div>
</body>
</html>