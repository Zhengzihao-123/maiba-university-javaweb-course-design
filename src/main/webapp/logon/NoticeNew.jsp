<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cn.maiba.Board" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>发布公告</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            margin: 0;
            padding: 20px;
        }
        .container {
            max-width: 600px;
            margin: 0 auto;
        }
        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 30px;
        }
        .form-group {
            margin-bottom: 20px;
        }
        .form-group label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
            color: #333;
        }
        .form-group input,
        .form-group textarea,
        .form-group select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
            box-sizing: border-box;
        }
        .form-group textarea {
            height: 200px;
            resize: vertical;
        }
        .btn {
            background-color: #667eea;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            width: 100%;
        }
        .btn:hover {
            background-color: #5a6fd6;
        }
        .btn-back {
            background-color: #9E9E9E;
            margin-top: 10px;
        }
        .btn-back:hover {
            background-color: #757575;
        }
        .category-tag {
            display: inline-block;
            padding: 2px 8px;
            border-radius: 4px;
            font-size: 12px;
            margin-left: 8px;
        }
        .tag-system {
            background-color: #667eea;
            color: white;
        }
        .tag-board {
            background-color: #4CAF50;
            color: white;
        }
    </style>
</head>
<body>
    <%@ include file="/include/header.jsp" %>
    
    <div class="container">
        <h2>发布新公告</h2>
        
        <form action="${pageContext.request.contextPath}/logon/HandleNoticeNew" method="post">
            <% Boolean isAdminVal = (Boolean) pageContext.getAttribute("isAdmin"); %>
            <% if (isAdminVal != null && isAdminVal) { %>
            <div class="form-group">
                <label for="category">公告分类</label>
                <select id="category" name="category" onchange="toggleBoardSelect()">
                    <option value="0">系统公告</option>
                    <option value="1">板块公告</option>
                </select>
            </div>
            <% } else { %>
            <div class="form-group">
                <label>公告类型 <span class="category-tag tag-board">板块公告</span></label>
                <input type="hidden" name="category" value="1">
            </div>
            <% } %>
            
            <% List boardList = (List) request.getAttribute("boardList"); %>
            <% if (boardList != null && !boardList.isEmpty()) { %>
            <div class="form-group" id="boardGroup" style="<%= (isAdminVal != null && isAdminVal) ? "display:none;" : "" %>">
                <label for="boardId">选择板块</label>
                <select id="boardId" name="boardId">
                    <% for (Object obj : boardList) { %>
                        <% Board board = (Board) obj; %>
                        <option value="<%= board.getId() %>"><%= board.getName() %></option>
                    <% } %>
                </select>
            </div>
            <% } %>
            
            <div class="form-group">
                <label for="title">公告标题</label>
                <input type="text" id="title" name="title" required placeholder="请输入公告标题">
            </div>
            
            <div class="form-group">
                <label for="content">公告内容</label>
                <textarea id="content" name="content" required placeholder="请输入公告内容"></textarea>
            </div>
            
            <button type="submit" class="btn">发布公告</button>
            <a href="${pageContext.request.contextPath}/logon/NoticeList" class="btn btn-back">返回列表</a>
        </form>
    </div>
    
    <script>
    function toggleBoardSelect() {
        var category = document.getElementById('category').value;
        var boardGroup = document.getElementById('boardGroup');
        if (category == '1') {
            boardGroup.style.display = 'block';
        } else {
            boardGroup.style.display = 'none';
        }
    }
    </script>
</body>
</html>