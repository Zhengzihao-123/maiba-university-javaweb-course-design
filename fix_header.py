header_content = '''<%@ page import="cn.maiba.User" %>
<%
    User user = (User)session.getAttribute("user");
%>
<style>
    * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
    }
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
        align-items: center;
        text-decoration: none;
    }
    .logo h1 {
        color: white;
        font-size: 28px;
        font-weight: bold;
        margin: 0;
        text-shadow: 1px 1px 2px rgba(0,0,0,0.2);
    }
    .logo span {
        color: rgba(255,255,255,0.8);
        font-size: 14px;
        margin-left: 10px;
    }
    .search-box {
        display: flex;
        align-items: center;
        background: white;
        border-radius: 25px;
        padding: 5px;
        box-shadow: 0 2px 5px rgba(0,0,0,0.1);
    }
    .search-box input {
        border: none;
        outline: none;
        padding: 8px 15px;
        width: 220px;
        font-size: 14px;
        background: transparent;
    }
    .search-box select {
        border: none;
        outline: none;
        padding: 8px 10px;
        border-left: 1px solid #eee;
        font-size: 14px;
        background: transparent;
        cursor: pointer;
    }
    .search-box button {
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        border: none;
        color: white;
        padding: 8px 20px;
        border-radius: 20px;
        cursor: pointer;
        font-size: 14px;
        transition: all 0.3s ease;
    }
    .search-box button:hover {
        transform: scale(1.05);
        box-shadow: 0 2px 8px rgba(102, 126, 234, 0.4);
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
        position: relative;
    }
    .nav-items a:hover {
        background: rgba(255,255,255,0.2);
        transform: translateY(-2px);
    }
    .nav-items a.active {
        background: rgba(255,255,255,0.25);
        font-weight: bold;
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
        font-size: 14px;
        padding: 8px 15px;
        border-radius: 20px;
        cursor: pointer;
        transition: all 0.3s ease;
        display: flex;
        align-items: center;
        gap: 5px;
    }
    .user-dropdown .user-name:hover {
        background: rgba(255,255,255,0.2);
    }
    .user-dropdown .dropdown-content {
        display: none;
        position: absolute;
        top: 100%;
        right: 0;
        background-color: white;
        min-width: 140px;
        box-shadow: 0 8px 16px rgba(0,0,0,0.2);
        border-radius: 10px;
        z-index: 1001;
        margin-top: 5px;
        overflow: hidden;
    }
    .user-dropdown .dropdown-content.show {
        display: block;
    }
    .user-dropdown .dropdown-content a {
        color: #333;
        padding: 12px 16px;
        text-decoration: none;
        display: block;
        font-size: 14px;
        text-align: left;
        border-radius: 0;
        transition: background 0.3s ease;
    }
    .user-dropdown .dropdown-content a:hover {
        background-color: #f1f1f1;
        transform: none;
    }
    .user-dropdown .user-name::after {
        content: '▼';
        font-size: 10px;
        opacity: 0.7;
        transition: transform 0.3s ease;
    }
    .user-dropdown.open .user-name::after {
        transform: rotate(180deg);
    }
</style>
<script>
document.addEventListener('DOMContentLoaded', function() {
    var dropdown = document.querySelector('.user-dropdown');
    var dropdownContent = document.querySelector('.user-dropdown .dropdown-content');
    var userName = document.querySelector('.user-dropdown .user-name');
    
    if (userName) {
        userName.addEventListener('click', function(e) {
            e.stopPropagation();
            dropdown.classList.toggle('open');
            dropdownContent.classList.toggle('show');
        });
    }
    
    document.addEventListener('click', function(e) {
        if (dropdown && !dropdown.contains(e.target)) {
            dropdown.classList.remove('open');
            if (dropdownContent) {
                dropdownContent.classList.remove('show');
            }
        }
    });
});
</script>
<div class="header">
    <div class="header-container">
        <a href="${pageContext.request.contextPath}/logon/ArticleList" class="logo">
            <h1>麦吧</h1>
            <span>分享生活</span>
        </a>
        
        <div class="search-box">
            <input type="text" placeholder="搜索帖子...">
            <select>
                <option>标题</option>
                <option>作者</option>
            </select>
            <button>搜索</button>
        </div>
        
        <div class="nav-items">
            <a href="${pageContext.request.contextPath}/logon/ArticleList">首页</a>
            <a href="${pageContext.request.contextPath}/logon/article-new.jsp">发帖</a>
            <a href="${pageContext.request.contextPath}/logon/UserList">用户列表</a>
            <a href="${pageContext.request.contextPath}/logon/MyArticleList">我的帖子</a>
            <a href="${pageContext.request.contextPath}/logon/MyRemarkList">我的评论</a>
            
            <% if (user == null) { %>
                <a href="${pageContext.request.contextPath}/UserLogon.jsp" class="btn-login">登录</a>
                <a href="${pageContext.request.contextPath}/UserRegister.jsp" class="btn-register">注册</a>
            <% } else { %>
                <div class="user-dropdown">
                    <div class="user-name"><%= user.getUserName() %></div>
                    <div class="dropdown-content">
                        <a href="${pageContext.request.contextPath}/logon/UserDetail?userId=<%= user.getId() %>">个人资料</a>
                        <a href="${pageContext.request.contextPath}/logon/HandleUserLogout">退出登录</a>
                    </div>
                </div>
            <% } %>
        </div>
    </div>
</div>'''

# Write with UTF-8 encoding
with open('d:/JavaWeb/Project/maiba/src/main/webapp/include/header.jsp', 'w', encoding='utf-8') as f:
    f.write(header_content)

print('header.jsp written with UTF-8 encoding')