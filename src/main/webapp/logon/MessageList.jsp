<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>我的消息</title>
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
        .message-card {
            background-color: white;
            border-radius: 8px;
            padding: 20px;
            margin-bottom: 15px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            cursor: pointer;
            transition: all 0.3s ease;
        }
        .message-card:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 15px rgba(0,0,0,0.15);
        }
        .message-card.unread {
            border-left: 4px solid #667eea;
            background-color: #f8f9ff;
        }
        .message-card.read {
            border-left: 4px solid #ddd;
        }
        .message-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 10px;
        }
        .message-title {
            font-size: 16px;
            font-weight: bold;
            color: #333;
        }
        .message-sender {
            font-size: 14px;
            color: #667eea;
        }
        .message-time {
            font-size: 12px;
            color: #999;
        }
        .message-content {
            color: #666;
            font-size: 14px;
            line-height: 1.5;
            white-space: pre-wrap;
            overflow: hidden;
            text-overflow: ellipsis;
            display: -webkit-box;
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
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
    </style>
</head>
<body>
    <%@ include file="/include/header.jsp" %>
    
    <div class="container">
        <h2>我的消息</h2>
        
        <div style="text-align: right; margin-bottom: 20px;">
            <a href="${pageContext.request.contextPath}/logon/MessageNew.jsp" class="btn">发送消息</a>
        </div>
        
        <c:if test="${not empty messageList}">
            <c:forEach items="${messageList}" var="message">
                <div class="message-card ${message.isRead == 0 ? 'unread' : 'read'}" 
                     onclick="location.href='${pageContext.request.contextPath}/logon/MessageDetail?messageId=${message.id}'">
                    <div class="message-header">
                        <div>
                            <span class="message-title">${message.title}</span>
                            <span class="message-sender"> - 发件人: ${senderNames[message.senderId]}</span>
                        </div>
                        <span class="message-time"><fmt:formatDate value="${message.sendTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                    </div>
                    <div class="message-content">${message.content}</div>
                </div>
            </c:forEach>
        </c:if>
        
        <c:if test="${empty messageList}">
            <div class="no-data">暂无消息</div>
        </c:if>
    </div>
</body>
</html>