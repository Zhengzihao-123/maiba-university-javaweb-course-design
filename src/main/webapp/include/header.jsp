<%@ page import="cn.maiba.User" %>
<%
    User user = (User)session.getAttribute("user");
%>
<style>
* { margin: 0; padding: 0; box-sizing: border-box; }
.header {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    padding: 15px 20px;
    box-shadow: 0 2px 10px rgba(0,0,0,0.1);
    position: sticky;
    top: 0;
    z-index: 1000;
}
.header-container {
    max-width: 1200px;
    margin: 0 auto;
    display: flex;
    justify-content: space-between;
    align-items: center;
}
.logo {
    display: flex;
    flex-direction: column;
    color: white;
    text-decoration: none;
}
.logo h1 {
    font-size: 24px;
    font-weight: bold;
    margin: 0;
}
.logo span {
    font-size: 12px;
    opacity: 0.8;
}
.nav-items {
    display: flex;
    align-items: center;
    gap: 20px;
}
.nav-items a {
    color: white;
    text-decoration: none;
    font-size: 15px;
    padding: 8px 15px;
    border-radius: 20px;
    transition: all 0.3s ease;
}
.nav-items a:hover {
    background: rgba(255,255,255,0.2);
}
.nav-items .btn-login, .nav-items .btn-register {
    background: rgba(255,255,255,0.2);
    padding: 8px 20px;
}
.nav-items .btn-register {
    background: white;
    color: #667eea;
}
.nav-items .btn-register:hover {
    background: rgba(255,255,255,0.9);
}
.user-dropdown {
    position: relative;
    display: inline-block;
}
.user-dropdown .user-name {
    color: white;
    padding: 8px 15px;
    cursor: pointer;
}
.user-dropdown-content {
    display: none;
    position: absolute;
    right: 0;
    background-color: white;
    min-width: 120px;
    box-shadow: 0 8px 16px rgba(0,0,0,0.2);
    z-index: 1;
}
.user-dropdown-content a {
    color: black;
    padding: 12px 16px;
    text-decoration: none;
    display: block;
    font-size: 14px;
}
.user-dropdown-content a:hover {
    background-color: #f1f1f1;
}
.user-dropdown:hover .user-dropdown-content {
    display: block;
}
</style>
<div class="header">
    <div class="header-container">
        <a href="${pageContext.request.contextPath}/logon/ArticleList" class="logo">
            <h1>麦吧</h1>
            <span>分享生活</span>
        </a>
        <div class="nav-items">
            <a href="${pageContext.request.contextPath}/logon/ArticleList">首页</a>
            <a href="${pageContext.request.contextPath}/logon/article-new.jsp">发帖</a>
            <a href="${pageContext.request.contextPath}/logon/UserList">用户列表</a>
            <% if (user != null) { %>
                <div class="user-dropdown">
                    <span class="user-name"><%= user.getUserName() %></span>
                    <div class="user-dropdown-content">
                        <a href="${pageContext.request.contextPath}/logon/MyArticleList">我的帖子</a>
                        <a href="${pageContext.request.contextPath}/logon/MyRemarkList">我的评论</a>
                        <a href="${pageContext.request.contextPath}/HandleUserLogout">退出登录</a>
                    </div>
                </div>
            <% } else { %>
                <a href="${pageContext.request.contextPath}/UserLogon.jsp" class="btn-login">登录</a>
                <a href="${pageContext.request.contextPath}/UserRegister.jsp" class="btn-register">注册</a>
            <% } %>
        </div>
    </div>
</div>