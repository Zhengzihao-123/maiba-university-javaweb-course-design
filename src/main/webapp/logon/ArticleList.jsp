<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="cn.maiba.User" %>
<%@ page import="cn.maiba.Board" %>
<%@ page import="cn.maiba.MyDataBase" %>
<%@ page import="cn.maiba.PermissionChecker" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%
    Map<Integer, String> boardNameMap = new HashMap<>();
    List boardList = (List) request.getAttribute("boardList");
    if (boardList != null) {
        for (Object obj : boardList) {
            Board board = (Board) obj;
            boardNameMap.put(board.getId(), board.getName());
        }
    }
    pageContext.setAttribute("boardNameMap", boardNameMap);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>帖子列表</title>
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
        .search-form {
            display: flex;
            gap: 10px;
            margin-bottom: 20px;
            justify-content: center;
        }
        .search-input {
            padding: 10px 15px;
            border: 1px solid #ddd;
            border-radius: 20px;
            width: 300px;
            font-size: 14px;
        }
        .search-btn {
            background-color: #667eea;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 20px;
            cursor: pointer;
            font-size: 14px;
        }
        .search-btn:hover {
            background-color: #5a6fd6;
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
        .title-link {
            color: #007bff;
            text-decoration: none;
        }
        .title-link:hover {
            text-decoration: underline;
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
        .btn-top {
            background-color: #ff9800;
        }
        .btn-top:hover {
            background-color: #f57c00;
        }
        .top-tag {
            background-color: #ff9800;
            color: white;
            padding: 2px 8px;
            border-radius: 4px;
            font-size: 12px;
            margin-right: 8px;
        }
    </style>
</head>
<body>
    <%@ include file="/include/header.jsp" %>
    
    <div class="container">
        <h2>帖子列表</h2>
        
        <form class="search-form" action="${pageContext.request.contextPath}/logon/ArticleList" method="get">
            <select name="boardId" class="search-input" style="width: 150px;">
                <option value="">全部分板块</option>
                <c:forEach items="${boardList}" var="board">
                    <option value="${board.id}" ${boardId != null && boardId == board.id ? 'selected' : ''}>${board.name}</option>
                </c:forEach>
            </select>
            <input type="text" class="search-input" name="keyword" placeholder="搜索帖子标题或内容..." value="${keyword != null ? keyword : ''}">
            <button type="submit" class="search-btn">搜索</button>
        </form>
        
        <c:if test="${empty articleList}">
            <div class="no-data">暂无帖子</div>
        </c:if>
        
        <c:if test="${not empty articleList}">
            <table>
                <thead>
                    <tr>
                        <th>标题</th>
                        <th>板块</th>
                        <th>作者ID</th>
                        <th>评论数</th>
                        <th>点击数</th>
                        <th>发布时间</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${articleList}" var="article">
                        <tr>
                            <td>
                                <c:if test="${article.isTop == 1}">
                                    <span class="top-tag">置顶</span>
                                </c:if>
                                <a href="${pageContext.request.contextPath}/logon/ArticleDetail?articleId=${article.id}" class="title-link">${article.title}</a>
                            </td>
                            <td>${boardNameMap[article.boardId] != null ? boardNameMap[article.boardId] : '未知'}</td>
                            <td>${article.userId}</td>
                            <td>${article.remarkNum}</td>
                            <td>${article.hitNum}</td>
                            <td><fmt:formatDate value="${article.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td>
                                <a href="${pageContext.request.contextPath}/logon/ArticleDetail?articleId=${article.id}" class="btn">查看</a>
                                <c:if test="${isAdmin}">
                                    <a href="${pageContext.request.contextPath}/logon/HandleArticleTop?articleId=${article.id}&isTop=${article.isTop == 1 ? 0 : 1}" class="btn btn-top">
                                        ${article.isTop == 1 ? '取消置顶' : '置顶'}
                                    </a>
                                    <a href="${pageContext.request.contextPath}/logon/HandleArticleDelete?id=${article.id}" class="btn btn-danger" onclick="return confirm('确定要删除这篇帖子吗？')">删除</a>
                                </c:if>
                                <c:if test="${isModerator}">
                                    <a href="${pageContext.request.contextPath}/logon/HandleArticleDelete?id=${article.id}" class="btn btn-danger" onclick="return confirm('确定要删除这篇帖子吗？')">删除</a>
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