<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="cn.maiba.User" %>
<%@ page import="cn.maiba.MyDataBase" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%
    Map<Integer, String> moderatorNameMap = new HashMap<>();
    java.util.List userList = MyDataBase.list(User.TABLE_NAME);
    if (userList != null) {
        for (Object obj : userList) {
            User u = (User) obj;
            moderatorNameMap.put(u.getId(), u.getUserName());
        }
    }
    pageContext.setAttribute("moderatorNameMap", moderatorNameMap);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>板块管理</title>
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
        .btn-add {
            display: inline-block;
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            font-size: 14px;
            margin-bottom: 20px;
        }
        .btn-add:hover {
            background-color: #45a049;
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
        .btn {
            background-color: #007bff;
            color: white;
            padding: 5px 10px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            font-size: 12px;
            margin-right: 5px;
        }
        .btn:hover {
            background-color: #0056b3;
        }
        .btn-danger {
            background-color: #dc3545;
        }
        .btn-danger:hover {
            background-color: #c82333;
        }
        .no-data {
            text-align: center;
            padding: 50px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        .error {
            background-color: #f8d7da;
            color: #721c24;
            padding: 10px 15px;
            border-radius: 4px;
            margin-bottom: 20px;
            text-align: center;
        }
        .back-link {
            display: inline-block;
            margin-top: 20px;
            color: #666;
            text-decoration: none;
        }
        .back-link:hover {
            text-decoration: underline;
        }
        .default-tag {
            background-color: #6c757d;
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
        <h2>板块管理</h2>
        
        <c:if test="${not empty errorMessage}">
            <div class="error">${errorMessage}</div>
        </c:if>
        
        <c:if test="${isAdmin}">
            <a href="${pageContext.request.contextPath}/logon/admin/BoardNew" class="btn-add">新增板块</a>
        </c:if>
        
        <c:if test="${empty boardList}">
            <div class="no-data">暂无板块</div>
        </c:if>
        
        <c:if test="${not empty boardList}">
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>板块名称</th>
                        <th>描述</th>
                        <th>版主</th>
                        <th>排序权重</th>
                        <th>创建时间</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${boardList}" var="board">
                        <tr>
                            <td>${board.id} <c:if test="${board.id == 1}"><span class="default-tag">默认</span></c:if></td>
                            <td>${board.name}</td>
                            <td>${board.description != null ? board.description : ''}</td>
                            <td>${moderatorNameMap[board.moderatorId] != null ? moderatorNameMap[board.moderatorId] : '未设置'}</td>
                            <td>${board.sortOrder}</td>
                            <td><fmt:formatDate value="${board.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td>
                                <c:if test="${isAdmin}">
                                    <a href="${pageContext.request.contextPath}/logon/admin/BoardModify?boardId=${board.id}" class="btn">修改</a>
                                    <c:if test="${board.id != 1}">
                                        <a href="${pageContext.request.contextPath}/logon/admin/BoardDelete?boardId=${board.id}" class="btn btn-danger" onclick="return confirm('确定删除该板块吗？该板块下的帖子将迁移到综合讨论板块')">删除</a>
                                    </c:if>
                                </c:if>
                                <c:if test="${isModerator && !isAdmin}">
                                    版主仅可查看
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        
        <a href="${pageContext.request.contextPath}/logon/ArticleList" class="back-link">返回首页</a>
    </div>
</body>
</html>
