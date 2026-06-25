<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>消息详情</title>
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
        .message-detail {
            background-color: white;
            border-radius: 8px;
            padding: 30px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        .detail-row {
            margin-bottom: 15px;
        }
        .detail-label {
            font-weight: bold;
            color: #666;
            display: inline-block;
            width: 80px;
        }
        .detail-value {
            color: #333;
        }
        .message-content {
            margin-top: 20px;
            padding-top: 20px;
            border-top: 1px solid #ddd;
            color: #333;
            line-height: 1.8;
            white-space: pre-wrap;
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
            margin-top: 20px;
            display: inline-block;
        }
        .btn:hover {
            background-color: #5a6fd6;
        }
        .btn-back {
            background-color: #9E9E9E;
        }
        .btn-back:hover {
            background-color: #757575;
        }
        .no-data {
            text-align: center;
            padding: 50px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
    </style>
</head>
<body>
    <%@ include file="/include/header.jsp" %>
    
    <div class="container">
        <h2>消息详情</h2>
        
        <c:if test="${message != null}">
            <div class="message-detail">
                <div class="detail-row">
                    <span class="detail-label">标题：</span>
                    <span class="detail-value">${message.title}</span>
                </div>
                
                <div class="detail-row">
                    <span class="detail-label">发件人：</span>
                    <span class="detail-value">${sender != null ? sender.userName : '未知用户'}</span>
                </div>
                
                <div class="detail-row">
                    <span class="detail-label">时间：</span>
                    <span class="detail-value"><fmt:formatDate value="${message.sendTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                </div>
                
                <div class="message-content">${message.content}</div>
                
                <div style="margin-top: 20px;">
                    <a href="${pageContext.request.contextPath}/logon/MessageList" class="btn btn-back">返回列表</a>
                    <a href="${pageContext.request.contextPath}/logon/MessageNew.jsp?receiverId=${sender != null ? sender.id : ''}" class="btn">回复消息</a>
                </div>
            </div>
        </c:if>
        
        <c:if test="${message == null}">
            <div class="no-data">消息不存在</div>
        </c:if>
    </div>
</body>
</html>