<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>删除成功</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css?v=1.0.2">
</head>
<body>
    <%@ include file="/include/header.jsp" %>
    
    <div class="container">
        <h1>删除成功</h1>
        <p style="text-align: center; font-size: 18px; color: green;">
            用户已成功删除！
        </p>
        <p style="text-align: center; margin-top: 20px;">
            <a href="${pageContext.request.contextPath}/logon/UserList" style="text-decoration: none; color: #1E90FF;">返回用户列表</a>
        </p>
    </div>
    
    <%@ include file="/include/footer.jsp" %>
</body>
</html>
