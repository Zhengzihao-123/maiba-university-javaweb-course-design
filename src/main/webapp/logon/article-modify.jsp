<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>修改帖子</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="/include/header.jsp" %>
    
    <div class="container">
        <h1>修改帖子</h1>
        
        <c:if test="${not empty errorMessage}">
            <div class="error-message">${errorMessage}</div>
        </c:if>
        
        <form action="${pageContext.request.contextPath}/logon/HandleArticleModify" method="post">
            <input type="hidden" name="articleId" value="${article.id}">
            
            <div class="form-group">
                <label for="boardId">所属板块</label>
                <select id="boardId" name="boardId" required>
                    <option value="">请选择板块</option>
                    <c:forEach items="${boardList}" var="board">
                        <option value="${board.id}" ${article.boardId == board.id ? 'selected' : ''}>${board.name}</option>
                    </c:forEach>
                </select>
            </div>
            
            <div class="form-group">
                <label for="title">标题</label>
                <input type="text" id="title" name="title" value="${article.title}" required maxlength="200">
            </div>
            
            <div class="form-group">
                <label for="content">内容（不少于10个字符）</label>
                <textarea id="content" name="content" rows="10" required>${article.content}</textarea>
            </div>
            
            <div class="form-group">
                <button type="submit" class="btn-primary">保存修改</button>
                <a href="${pageContext.request.contextPath}/logon/ArticleDetail?id=${article.id}" class="btn-secondary">取消</a>
            </div>
        </form>
    </div>
    
    <%@ include file="/include/footer.jsp" %>
</body>
</html>
