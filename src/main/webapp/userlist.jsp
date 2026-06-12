<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>用户列表</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css?v=1.0.2">
</head>
<body>
    <%@ include file="/include/header.jsp" %>
    
    <div class="container">
        <h1>用户列表</h1>
        <div class="table-wrapper">
            <table>
                <tr>
                    <th>ID</th>
                    <th>账号</th>
                    <th>密码</th>
                    <th>昵称</th>
                    <th>年龄</th>
                    <th>邮箱</th>
                </tr>
                <c:forEach var="user" items="${userList}">
                    <tr>
                        <td>${user.id}</td>
                        <td><a href="${pageContext.request.contextPath}/UserDetail?userId=${user.id}" style="text-decoration: none; color: #1E90FF;">${user.account}</a></td>
                        <td>${user.password}</td>
                        <td>${user.userName}</td>
                        <td>${user.age}</td>
                        <td>${user.email}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <p style="text-align: center;">
            <a href="${pageContext.request.contextPath}/UserLogon.jsp" style="text-decoration: none; color: #1E90FF;">返回登录页</a>
        </p>
    </div>
    
    <%@ include file="/include/footer.jsp" %>
</body>
</html>
