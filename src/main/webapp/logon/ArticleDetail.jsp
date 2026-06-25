<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cn.maiba.User, cn.maiba.PermissionChecker, cn.maiba.RoleConstants" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${article.title}</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            margin: 0;
            padding: 20px;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
        }
        .article {
            background-color: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            margin-bottom: 20px;
        }
        .article-header {
            margin-bottom: 20px;
            padding-bottom: 15px;
            border-bottom: 1px solid #eee;
        }
        h1 {
            color: #333;
            margin-bottom: 15px;
        }
        .meta {
            color: #666;
            font-size: 14px;
        }
        .meta span {
            margin-right: 20px;
        }
        .board-tag {
            background-color: #007bff;
            color: white;
            padding: 2px 8px;
            border-radius: 4px;
            font-size: 12px;
        }
        .content {
            color: #333;
            line-height: 1.8;
            font-size: 16px;
            white-space: pre-wrap;
        }
        .actions {
            margin-top: 20px;
            display: flex;
            gap: 10px;
        }
        .btn {
            background-color: #4CAF50;
            color: white;
            padding: 8px 16px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            font-size: 14px;
        }
        .btn:hover {
            background-color: #45a049;
        }
        .btn-danger {
            background-color: #dc3545;
        }
        .btn-danger:hover {
            background-color: #c82333;
        }
        .btn-secondary {
            background-color: #6c757d;
        }
        .btn-secondary:hover {
            background-color: #5a6268;
        }
        .btn-warning {
            background-color: #ffc107;
            color: #333;
        }
        .btn-warning:hover {
            background-color: #e0a800;
        }
        .comments-section {
            background-color: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        .comments-section h3 {
            margin-bottom: 20px;
            color: #333;
        }
        .comment-form {
            margin-bottom: 30px;
        }
        .comment-form textarea {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
            height: 100px;
            resize: vertical;
            box-sizing: border-box;
        }
        .comment-item {
            padding: 15px;
            border-bottom: 1px solid #eee;
        }
        .comment-item:last-child {
            border-bottom: none;
        }
        .comment-meta {
            color: #666;
            font-size: 12px;
            margin-bottom: 8px;
        }
        .comment-content {
            color: #333;
        }
        .no-comments {
            text-align: center;
            color: #999;
            padding: 20px;
        }
    </style>
</head>
<body>
    <%@ include file="/include/header.jsp" %>
    <%
        User currentUser = (User) session.getAttribute("user");
        boolean isOwner = PermissionChecker.isLoggedIn(currentUser) && currentUser.getId().equals((Integer) request.getAttribute("userId"));
        boolean canModify = PermissionChecker.canModifyArticle(currentUser, (cn.maiba.Article) request.getAttribute("article"));
        boolean canDelete = PermissionChecker.canDeleteArticle(currentUser, (cn.maiba.Article) request.getAttribute("article"));
    %>
    
    <div class="container">
        <div class="article">
            <div class="article-header">
                <h1>${article.title}</h1>
                <div class="meta">
                    <span>作者ID: ${article.userId}</span>
                    <span>板块: <span class="board-tag">${boardName}</span></span>
                    <span>发布时间: <fmt:formatDate value="${article.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                    <span>点击数: ${article.hitNum}</span>
                    <span>评论数: ${article.remarkNum}</span>
                </div>
            </div>
            
            <div class="content">${article.content}</div>
            
            <div class="actions">
                <a href="ArticleList" class="btn btn-secondary">返回列表</a>
                
                <% if (canModify) { %>
                    <a href="${pageContext.request.contextPath}/logon/ArticleModify?id=${article.id}" class="btn">修改</a>
                <% } %>
                
                <% if (canDelete) { %>
                    <a href="${pageContext.request.contextPath}/logon/HandleArticleDelete?id=${article.id}" class="btn btn-danger" onclick="return confirm('确定要删除这篇帖子吗？')">删除</a>
                <% } %>
            </div>
        </div>
        
        <div class="comments-section">
            <h3>评论列表</h3>
            
            <% if (PermissionChecker.isLoggedIn(currentUser)) { %>
            <div class="comment-form">
                <form action="${pageContext.request.contextPath}/logon/HandleRemarkNew" method="post">
                    <textarea name="content" placeholder="写下你的评论..." required></textarea>
                    <input type="hidden" name="articleId" value="${article.id}">
                    <br>
                    <button type="submit" class="btn">发表评论</button>
                </form>
            </div>
            <% } else { %>
            <div class="comment-form">
                <p><a href="${pageContext.request.contextPath}/UserLogon.jsp">登录</a>后可发表评论</p>
            </div>
            <% } %>
            
            <c:if test="${empty remarkList}">
                <div class="no-comments">暂无评论</div>
            </c:if>
            
            <c:forEach items="${remarkList}" var="remark">
                <div class="comment-item">
                    <div class="comment-meta">
                        <span>用户ID: ${remark.userId}</span>
                        <span>评论时间: <fmt:formatDate value="${remark.remarkTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                    </div>
                    <div class="comment-content">${remark.remark}</div>
                </div>
            </c:forEach>
        </div>
    </div>
</body>
</html>
