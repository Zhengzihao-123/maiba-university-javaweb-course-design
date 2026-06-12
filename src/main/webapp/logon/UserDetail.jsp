<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>用户详情</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css?v=1.0.2">
</head>
<body>
    <%@ include file="/include/header.jsp" %>
    
    <div class="container">
        <h1>用户详情</h1>
        <table>
            <tr>
                <th style="background-color: #f0f0f0;">ID</th>
                <td>${user.id}</td>
            </tr>
            <tr>
                <th style="background-color: #f0f0f0;">账号</th>
                <td>${user.account}</td>
            </tr>
            <tr>
                <th style="background-color: #f0f0f0;">密码</th>
                <td>${user.password}</td>
            </tr>
            <tr>
                <th style="background-color: #f0f0f0;">昵称</th>
                <td>${user.userName}</td>
            </tr>
            <tr>
                <th style="background-color: #f0f0f0;">年龄</th>
                <td>${user.age}</td>
            </tr>
            <tr>
                <th style="background-color: #f0f0f0;">邮箱</th>
                <td>${user.email}</td>
            </tr>
        </table>
        <p style="text-align: center; margin-top: 20px;">
            <a href="${pageContext.request.contextPath}/logon/UserModify?userId=${user.id}" style="text-decoration: none; color: #1E90FF; margin-right: 10px;">修改用户</a>
            <a href="${pageContext.request.contextPath}/logon/HandleUserDelete?userId=${user.id}" style="text-decoration: none; color: #1E90FF; margin-right: 10px;">删除用户</a>
            <a href="${pageContext.request.contextPath}/logon/UserList" style="text-decoration: none; color: #1E90FF;">返回用户列表</a>
        </p>
    </div>
    
    <%@ include file="/include/footer.jsp" %>
</body>
</html>
