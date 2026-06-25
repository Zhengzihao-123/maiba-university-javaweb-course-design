<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="cn.maiba.Board" %>
<%@ page import="cn.maiba.MyDataBase" %>
<%@ page import="cn.maiba.User" %>
<%@ page import="java.util.List" %>
<%
    User checkUser = (User) session.getAttribute("user");
    if (checkUser == null) {
        response.sendRedirect(request.getContextPath() + "/UserLogon.jsp");
        return;
    }
    List boardList = MyDataBase.list(Board.TABLE_NAME);
    pageContext.setAttribute("boardList", boardList);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>发布帖子</title>
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
            background-color: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 30px;
        }
        .form-group {
            margin-bottom: 20px;
        }
        label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
            color: #555;
        }
        input[type="text"], textarea {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
            box-sizing: border-box;
        }
        textarea {
            height: 200px;
            resize: vertical;
        }
        button {
            background-color: #4CAF50;
            color: white;
            padding: 12px 30px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            width: 100%;
        }
        button:hover {
            background-color: #45a049;
        }
        .back-link {
            display: block;
            text-align: center;
            margin-top: 20px;
            color: #666;
            text-decoration: none;
        }
        .back-link:hover {
            text-decoration: underline;
        }
        .error {
            color: red;
            font-size: 14px;
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>发布帖子</h2>
        
        <c:if test="${not empty errorMessage}">
            <div class="error">${errorMessage}</div>
        </c:if>
        
        <form action="HandleArticleNew" method="post">
            <div class="form-group">
                <label for="boardId">所属板块</label>
                <select id="boardId" name="boardId" required>
                    <option value="">请选择板块</option>
                    <c:forEach items="${boardList}" var="board">
                        <option value="${board.id}">${board.name}</option>
                    </c:forEach>
                </select>
            </div>
            
            <div class="form-group">
                <label for="title">标题</label>
                <input type="text" id="title" name="title" placeholder="请输入帖子标题" value="${title}" required>
            </div>
            
            <div class="form-group">
                <label for="content">内容（不少于10个字符）</label>
                <textarea id="content" name="content" placeholder="请输入帖子内容">${content}</textarea>
            </div>
            
            <button type="submit">发布帖子</button>
        </form>
        
        <a href="ArticleList" class="back-link">返回帖子列表</a>
    </div>
</body>
</html>
