<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>管理员用户管理</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            margin: 0;
            padding: 20px;
        }
        .container {
            max-width: 1000px;
            margin: 0 auto;
        }
        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 30px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            background-color: white;
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        th, td {
            padding: 15px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #f8f9fa;
            font-weight: bold;
            color: #333;
        }
        tr:hover {
            background-color: #f5f5f5;
        }
        .no-data {
            text-align: center;
            padding: 50px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        .btn {
            background-color: #4CAF50;
            color: white;
            padding: 5px 10px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            font-size: 12px;
        }
        .btn:hover {
            background-color: #45a049;
        }
        .btn-danger {
            background-color: #f44336;
        }
        .btn-danger:hover {
            background-color: #d32f2f;
        }
        .btn-warning {
            background-color: #ff9800;
        }
        .btn-warning:hover {
            background-color: #f57c00;
        }
        .admin-tag {
            background-color: #f44336;
            color: white;
            padding: 2px 8px;
            border-radius: 4px;
            font-size: 12px;
        }
        .user-tag {
            background-color: #2196F3;
            color: white;
            padding: 2px 8px;
            border-radius: 4px;
            font-size: 12px;
        }
        .locked-tag {
            background-color: #9E9E9E;
            color: white;
            padding: 2px 8px;
            border-radius: 4px;
            font-size: 12px;
        }
    </style>
</head>
<body>
    <%@ include file="/include/header.jsp" %>
    
    <div class="container">
        <h2>管理员用户管理</h2>
        
        <c:if test="${empty userList}">
            <div class="no-data">暂无用户</div>
        </c:if>
        
        <c:if test="${not empty userList}">
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>账号</th>
                        <th>用户名</th>
                        <th>角色</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${userList}" var="user">
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.account}</td>
                            <td>${user.userName}</td>
                            <td>
                                <c:if test="${user.role == 0}">
                                    <span class="admin-tag">管理员</span>
                                </c:if>
                                <c:if test="${user.role == 1}">
                                    <span class="user-tag">普通用户</span>
                                </c:if>
                                <c:if test="${user.role == 2}">
                                    <span class="btn-warning" style="padding:2px 8px;border-radius:4px;font-size:12px;color:white;">版主</span>
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${user.lockedTime != null}">
                                    <span class="locked-tag">已锁定</span>
                                </c:if>
                                <c:if test="${user.lockedTime == null}">
                                    <span class="user-tag">正常</span>
                                </c:if>
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/logon/UserDetail?userId=${user.id}" class="btn">查看</a>
                                <a href="${pageContext.request.contextPath}/logon/HandleUserDeleteServlet?userId=${user.id}" class="btn btn-danger">删除</a>
                                <c:if test="${user.lockedTime != null}">
                                    <a href="${pageContext.request.contextPath}/logon/admin/UnlockUser?userId=${user.id}" class="btn btn-warning">解锁</a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</body>
</html>