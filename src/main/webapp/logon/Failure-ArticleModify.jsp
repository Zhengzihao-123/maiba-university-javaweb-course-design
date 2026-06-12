<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>修改失败</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            margin: 0;
            padding: 20px;
        }
        .container {
            max-width: 500px;
            margin: 0 auto;
            background-color: white;
            padding: 40px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            text-align: center;
        }
        h2 {
            color: #dc3545;
            margin-bottom: 20px;
        }
        .error-icon {
            font-size: 60px;
            margin-bottom: 20px;
            color: #dc3545;
        }
        .error-message {
            color: #dc3545;
            margin-bottom: 20px;
        }
        .btn {
            display: inline-block;
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            font-size: 14px;
            margin: 5px;
        }
        .btn:hover {
            background-color: #45a049;
        }
        .btn-secondary {
            background-color: #6c757d;
        }
        .btn-secondary:hover {
            background-color: #5a6268;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="error-icon">✗</div>
        <h2>修改失败！</h2>
        <div class="error-message">${errorMessage}</div>
        <div style="margin-top: 30px;">
            <c:if test="${article != null}">
                <a href="ArticleModify?articleId=${article.id}" class="btn">重新修改</a>
            </c:if>
            <a href="MyArticleList" class="btn btn-secondary">我的帖子</a>
        </div>
    </div>
</body>
</html>
