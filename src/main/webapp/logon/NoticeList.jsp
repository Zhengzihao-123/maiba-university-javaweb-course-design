<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="cn.maiba.User" %>
<%@ page import="cn.maiba.PermissionChecker" %>
<%@ page import="cn.maiba.Board" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%
    User currentUser = (User)session.getAttribute("user");
    boolean canPostNotice = PermissionChecker.canPostNotice(currentUser);
    
    Map<Integer, String> boardNameMap = new HashMap<>();
    java.util.List boardList = (java.util.List) request.getAttribute("boardList");
    if (boardList != null) {
        for (Object obj : boardList) {
            Board board = (Board) obj;
            boardNameMap.put(board.getId(), board.getName());
        }
    }
    pageContext.setAttribute("boardNameMap", boardNameMap);
    pageContext.setAttribute("canPostNotice", canPostNotice);
%>
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
            margin-bottom: 20px;
        }
        .filter-bar {
            background-color: white;
            border-radius: 8px;
            padding: 15px 20px;
            margin-bottom: 20px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            display: flex;
            gap: 15px;
            align-items: center;
            flex-wrap: wrap;
        }
        .filter-bar label {
            font-weight: bold;
            color: #333;
            font-size: 14px;
        }
        .filter-bar select {
            padding: 8px 12px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
            min-width: 150px;
        }
        .notice-card {
            background-color: white;
            border-radius: 8px;
            padding: 20px;
            margin-bottom: 20px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            border-left: 4px solid #667eea;
        }
        .notice-card.board-notice {
            border-left-color: #4CAF50;
        }
        .notice-title {
            font-size: 18px;
            font-weight: bold;
            color: #333;
            margin-bottom: 10px;
            display: flex;
            align-items: center;
            gap: 10px;
            flex-wrap: wrap;
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
            flex-wrap: wrap;
            gap: 10px;
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
            display: inline-block;
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
        .category-tag {
            padding: 2px 8px;
            border-radius: 4px;
            font-size: 12px;
        }
        .tag-system {
            background-color: #667eea;
            color: white;
        }
        .tag-board {
            background-color: #4CAF50;
            color: white;
        }
        .btn-delete {
            background-color: #e74c3c;
            margin-left: 10px;
        }
        .btn-delete:hover {
            background-color: #c0392b;
        }
        .post-btn {
            margin-left: auto;
        }
    </style>
</head>
<body>
    <%@ include file="/include/header.jsp" %>
    
    <div class="container">
        <h2>系统公告</h2>
        
        <div class="filter-bar">
            <label>分类：</label>
            <select onchange="filterNotices(this.value, '')">
                <option value="">全部公告</option>
                <option value="0" ${currentCategory == '0' ? 'selected' : ''}>系统公告</option>
                <option value="1" ${currentCategory == '1' ? 'selected' : ''}>板块公告</option>
            </select>
            <label>板块：</label>
            <select onchange="filterByBoard(this.value)">
                <option value="">全部板块</option>
                <c:forEach items="${boardList}" var="board">
                    <option value="${board.id}" ${currentBoardId == board.id ? 'selected' : ''}>${board.name}</option>
                </c:forEach>
            </select>
            <c:if test="${canPostNotice}">
                <a href="${pageContext.request.contextPath}/logon/NoticeNew" class="btn post-btn">发布新公告</a>
            </c:if>
        </div>
        
        <c:if test="${not empty noticeList}">
            <c:forEach items="${noticeList}" var="notice">
                <div class="notice-card ${notice.category == 1 ? 'board-notice' : ''}">
                    <div class="notice-title">
                        ${notice.title}
                        <span class="category-tag ${notice.category == 1 ? 'tag-board' : 'tag-system'}">
                            ${notice.category == 1 ? '板块公告' : '系统公告'}
                        </span>
                        <c:if test="${notice.category == 1 && boardNameMap[notice.boardId] != null}">
                            <span style="color:#666; font-size:13px;">【${boardNameMap[notice.boardId]}】</span>
                        </c:if>
                        <c:if test="${notice.isActive == 1}">
                            <span class="active-tag">生效</span>
                        </c:if>
                        <c:if test="${notice.isActive == 0}">
                            <span class="inactive-tag">已过期</span>
                        </c:if>
                        <% cn.maiba.Notice currentNotice = (cn.maiba.Notice) pageContext.getAttribute("notice"); %>
                        <% if (currentNotice != null && PermissionChecker.canDeleteNotice(currentUser, currentNotice)) { %>
                        <a href="${pageContext.request.contextPath}/logon/HandleNoticeDelete?id=${notice.id}" 
                           class="btn btn-delete" onclick="return confirm('确定删除此公告吗？')">删除</a>
                        <% } %>
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
    </div>
    
    <script>
    function filterNotices(category, boardId) {
        var url = '${pageContext.request.contextPath}/logon/NoticeList?';
        if (category !== '') {
            url += 'category=' + category;
        }
        if (boardId !== '') {
            if (category !== '') url += '&';
            url += 'boardId=' + boardId;
        }
        window.location.href = url;
    }
    
    function filterByBoard(boardId) {
        var categorySelect = document.querySelectorAll('select')[0];
        var category = categorySelect.value;
        filterNotices(category, boardId);
    }
    </script>
</body>
</html>