<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
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
        .form-group textarea {
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
    </style>
</head>
<body>
    <%@ include file="/include/header.jsp" %>
    
    <div class="container">
        <h2>发布新公告</h2>
        
        <form action="${pageContext.request.contextPath}/logon/admin/HandleNoticeNew" method="post">
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
</body>
</html>