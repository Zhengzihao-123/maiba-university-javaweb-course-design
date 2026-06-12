<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
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
    
    <div class="container">
        <div class="article">
            <div class="article-header">
                <h1>${article.title}</h1>
                <div class="meta">
                    <span>作者ID: ${article.userId}</span>
                    <span>发布时间: <fmt:formatDate value="${article.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                    <span>点击数: ${article.hitNum}</span>
                    <span>评论数: ${article.remarkNum}</span>
                </div>
            </div>
            
            <div class="content">${article.content}</div>
            
            <div class="actions">
                <a href="ArticleList" class="btn btn-secondary">返回列表</a>
                
                <c:if test="${isOwner}">
                    <a href="ArticleModify?articleId=${article.id}" class="btn">修改</a>
                    <a href="HandleArticleDelete?articleId=${article.id}" class="btn btn-danger" onclick="return confirm('确定要删除这篇帖子吗？')">删除</a>
                </c:if>
            </div>
