<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="cn.maiba.User" %>
<%@ page import="cn.maiba.PermissionChecker" %>
<%
    User currentUser = (User) session.getAttribute("user");
    boolean isAdminUser = PermissionChecker.isSuperAdmin(currentUser);
%>
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
                    <% if (isAdminUser) { %>
                    <th>密码</th>
                    <% } %>
                    <th>昵称</th>
                    <% if (isAdminUser) { %>
                    <th>年龄</th>
                    <% } %>
                    <th>邮箱</th>
                </tr>
                <c:forEach var="userItem" items="${userList}">
                    <tr>
                        <td>${userItem.id}</td>
                        <td><a href="${pageContext.request.contextPath}/logon/UserDetail?userId=${userItem.id}" style="text-decoration: none; color: #1E90FF;">${userItem.account}</a></td>
                        <% if (isAdminUser) { %>
                        <td>${userItem.password}</td>
                        <% } else { %>
                        <td><span style="color:#999;">***</span></td>
                        <% } %>
                        <td>${userItem.userName}</td>
                        <% if (isAdminUser) { %>
                        <td>${userItem.age}</td>
                        <% } else { %>
                        <td><span style="color:#999;">-</span></td>
                        <% } %>
                        <td>
                            <% 
                                cn.maiba.User item = (cn.maiba.User) pageContext.getAttribute("userItem");
                                if (isAdminUser || (currentUser != null && currentUser.getId().equals(item.getId()))) { 
                            %>
                                ${userItem.email}
                            <% } else { %>
                                <span style="color:#999;">***@***</span>
                            <% } %>
                        </td>
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
