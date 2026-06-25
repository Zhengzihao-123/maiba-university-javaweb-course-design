<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="cn.maiba.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>系统公告</title>
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
        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 30px;
        }
        .notice-card {
            background-color: white;
            border-radius: 8px;
            padding: 20px;
            margin-bottom: 20px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            border-left: 4px solid #667eea;
        }
        .notice-title {
            font-size: 18px;
            font-weight: bold;
            color: #333;
            margin-bottom: 10px;
        }
        .notice-content {
            color: #666;
            line-height: 1.6;
            margin-bottom: 15px;
            white-space: pre-wrap;
        }
        .notice-meta {
            font-size: 14px;
            color: #999;
            display: flex;
            justify-content: space-between;
        }
        .no-data {
            text-align: center;
            padding: 50px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        .btn {
            background-color: #667eea;
            color: white;
            padding: 8px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            font-size: 14px;
        }
        .btn:hover {
            background-color: #5a6fd6;
        }
        .active-tag {
            background-color: #4CAF50;
            color: white;
            padding: 2px 8px;
            border-radius: 4px;
            font-size: 12px;
        }
        .inactive-tag {
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
        <h2>系统公告</h2>
        
        <c:if test="${not empty noticeList}">
            <c:forEach items="${noticeList}" var="notice">
                <div class="notice-card">
                    <div class="notice-title">
                        ${notice.title}
                        <c:if test="${notice.isActive == 1}">
                            <span class="active-tag">生效</span>
                        </c:if>
                        <c:if test="${notice.isActive == 0}">
                            <span class="inactive-tag">已过期</span>
                        </c:if>
                    </div>
                    <div class="notice-content">${notice.content}</div>
                    <div class="notice-meta">
                        <span>发布时间: <fmt:formatDate value="${notice.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                        <span>更新时间: <fmt:formatDate value="${notice.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                    </div>
                </div>
            </c:forEach>
        </c:if>
        
        <c:if test="${empty noticeList}">
            <div class="no-data">暂无公告</div>
        </c:if>
        
        <c:if test="${user != null && user.isAdmin()}">
            <div style="text-align: center; margin-top: 20px;">
                <a href="${pageContext.request.contextPath}/logon/admin/NoticeNew.jsp" class="btn">发布新公告</a>
            </div>
        </c:if>
    </div>
</body>
</html>