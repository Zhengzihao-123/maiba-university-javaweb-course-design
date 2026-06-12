<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>修改失败</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css?v=1.0.2">
</head>
<body>
    <%@ include file="/include/header.jsp" %>
    
    <div class="container">
        <h1>修改失败</h1>
        <p style="text-align: center; font-size: 18px; color: red;">
            ${errorMessage}
        </p>
        <p style="text-align: center; margin-top: 20px;">
            <a href="javascript:history.back()" style="text-decoration: none; color: #1E90FF; margin-right: 10px;">返回重试</a>
            <a href="${pageContext.request.contextPath}/logon/UserList" style="text-decoration: none; color: #1E90FF;">返回用户列表</a>
        </p>
    </div>
    
    <%@ include file="/include/footer.jsp" %>
</body>
</html>
