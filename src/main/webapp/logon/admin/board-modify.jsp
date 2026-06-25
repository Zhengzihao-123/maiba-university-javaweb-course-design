<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="cn.maiba.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>修改板块</title>
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
        input[type="text"], textarea, input[type="number"] {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
            box-sizing: border-box;
        }
        textarea {
            height: 100px;
            resize: vertical;
        }
        button {
            background-color: #007bff;
            color: white;
            padding: 12px 30px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            width: 100%;
        }
        button:hover {
            background-color: #0056b3;
        }
        .btn-secondary {
            background-color: #6c757d;
            margin-top: 10px;
        }
        .btn-secondary:hover {
            background-color: #5a6268;
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
            background-color: #f8d7da;
            color: #721c24;
            padding: 10px 15px;
            border-radius: 4px;
            margin-bottom: 15px;
            text-align: center;
        }
        .remark {
            font-size: 12px;
            color: #999;
            margin-top: 4px;
        }
        .info-row {
            font-size: 12px;
            color: #666;
            margin-bottom: 15px;
            padding-bottom: 15px;
            border-bottom: 1px solid #eee;
        }
    </style>
</head>
<body>
    <%@ include file="/include/header.jsp" %>
    
    <div class="container">
        <h2>修改板块</h2>
        
        <c:if test="${not empty errorMessage}">
            <div class="error">${errorMessage}</div>
        </c:if>
        
        <c:if test="${not empty board}">
            <div class="info-row">
                板块ID：${board.id} | 创建时间：<fmt:formatDate value="${board.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </div>
            
            <form action="${pageContext.request.contextPath}/logon/admin/BoardModify" method="post">
                <input type="hidden" name="boardId" value="${board.id}">
                
                <div class="form-group">
                    <label for="name">板块名称</label>
                    <input type="text" id="name" name="name" value="${board.name}" required>
                </div>
                
                <div class="form-group">
                    <label for="description">板块描述</label>
                    <textarea id="description" name="description">${board.description != null ? board.description : ''}</textarea>
                </div>
                
                <div class="form-group">
                    <label for="sortOrder">排序权重</label>
                    <input type="number" id="sortOrder" name="sortOrder" value="${board.sortOrder}">
                    <div class="remark">数值越小排序越靠前</div>
                </div>
                
                <button type="submit">保存修改</button>
                <a href="${pageContext.request.contextPath}/logon/admin/BoardList" class="btn-secondary">取消</a>
            </form>
        </c:if>
        
        <c:if test="${empty board}">
            <div class="error">板块不存在</div>
            <a href="${pageContext.request.contextPath}/logon/admin/BoardList" class="back-link">返回板块列表</a>
        </c:if>
    </div>
</body>
</html>
