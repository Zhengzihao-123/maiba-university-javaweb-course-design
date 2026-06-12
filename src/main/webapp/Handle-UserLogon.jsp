<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>登录结果</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css?v=1.0.2">
</head>
<body>
    <%@ include file="/include/header.jsp" %>
    
    <div class="container">
        <c:choose>
            <c:when test="${success}">
                <h1>登录成功</h1>
                <p style="text-align: center; font-size: 18px; color: green;">
                    ${loginUser.userName}，你好！欢迎登录麦吧！
                </p>
                <p style="text-align: center; margin-top: 20px;">
                    <a href="${pageContext.request.contextPath}/UserLogon.jsp" style="text-decoration: none; color: #1E90FF; margin-right: 10px;">返回登录页</a>
                    <a href="${pageContext.request.contextPath}/logon/UserList" style="text-decoration: none; color: #1E90FF;">用户列表</a>
                </p>
                
                <h2 style="margin-top: 30px;">用户列表</h2>
                <div class="table-wrapper">
                    <table>
                        <tr>
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
                </div>
            </c:when>
            <c:otherwise>
                <h1>登录失败</h1>
                <p style="text-align: center; font-size: 18px; color: red;">${errorMessage}</p>
                <p style="text-align: center; margin-top: 20px;">
                    <a href="${pageContext.request.contextPath}/UserLogon.jsp" style="text-decoration: none; color: #1E90FF;">返回重新登录</a>
                </p>
            </c:otherwise>
        </c:choose>
    </div>
    
    <%@ include file="/include/footer.jsp" %>
</body>
</html>
