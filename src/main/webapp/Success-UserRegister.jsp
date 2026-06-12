<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>注册成功</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="/include/header.jsp" %>
    
    <div class="container">
        <h1>注册成功！</h1>
        <p style="text-align: center; font-size: 18px; color: green;">
            ${user.userName}，恭喜您注册成功！
        </p>
        <p style="text-align: center; color: #666;">
            账号：${user.account}
        </p>
        <p style="text-align: center; margin-top: 20px;">
            <a href="${pageContext.request.contextPath}/UserLogon.jsp" style="text-decoration: none; color: #1E90FF; font-size: 16px;">去登录</a>
        </p>
    </div>
    
    <%@ include file="/include/footer.jsp" %>
</body>
</html>
