<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>用户列表</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="/include/header.jsp" %>
    
    <div class="container">
        <h1>用户列表</h1>
        <table border="1" cellpadding="10" cellspacing="0" style="width: 100%; margin: 20px 0;">
            <tr style="background-color: #f0f0f0;">
                <th>账号</th>
                <th>昵称</th>
                <th>年龄</th>
                <th>邮箱</th>
            </tr>
            <c:forEach var="user" items="${userList}">
                <tr>
                    <td>${user.account}</td>
                    <td>${user.userName}</td>
                    <td>${user.age}</td>
                    <td>${user.email}</td>
                </tr>
            </c:forEach>
        </table>
        <p style="text-align: center;">
            <a href="${pageContext.request.contextPath}/UserLogon.jsp" style="text-decoration: none; color: #1E90FF;">返回登录页</a>
        </p>
    </div>
    
    <%@ include file="/include/footer.jsp" %>
</body>
</html>
