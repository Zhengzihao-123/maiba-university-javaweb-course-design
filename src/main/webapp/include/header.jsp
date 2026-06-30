<%@ page import="cn.maiba.User" %>
<%@ page import="cn.maiba.RoleConstants" %>
<%@ page import="cn.maiba.PermissionChecker" %>
<%@ page import="com.maiba.OnlineUserListener" %>
<%@ page import="cn.maiba.MyDataBase" %>
<%@ page import="cn.maiba.Board" %>
<%@ page import="java.util.List" %>
<%
    User user = (User)session.getAttribute("user");
    int onlineCount = OnlineUserListener.getOnlineCount();
    int loggedInCount = OnlineUserListener.getLoggedInCount();
    int unreadCount = 0;
    if (user != null) {
        unreadCount = MyDataBase.countUnreadMessages(user.getId());
    }
    List headerBoardList = (List)request.getAttribute("boardList");
    if (headerBoardList == null) {
        headerBoardList = MyDataBase.list(Board.TABLE_NAME);
    }
    boolean isAdmin = PermissionChecker.isSuperAdmin(user);
    boolean isModerator = PermissionChecker.isModerator(user);
    Integer moderatorBoardId = null;
    if (isModerator) {
        List myBoards = MyDataBase.select(Board.TABLE_NAME, "moderator_id", user.getId(), "=");
        if (myBoards != null && myBoards.size() > 0) {
            Board firstBoard = (Board) myBoards.get(0);
            moderatorBoardId = firstBoard.getId();
        }
    }
    pageContext.setAttribute("isAdmin", isAdmin);
    pageContext.setAttribute("isModerator", isModerator);
    pageContext.setAttribute("moderatorBoardId", moderatorBoardId);
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
    min-width: 150px;
    box-shadow: 0 8px 16px rgba(0,0,0,0.2);
    z-index: 1;
}
.user-dropdown-content a {
    color: black;
    padding: 12px 16px;
    text-decoration: none;
    display: block;
    font-size: 14px;
    border-radius: 0;
}
.user-dropdown-content a:hover {
    background-color: #f1f1f1;
}
.user-dropdown:hover .user-dropdown-content {
    display: block;
}
.online-count {
    background: rgba(255,255,255,0.2);
    padding: 5px 12px;
    border-radius: 20px;
    color: white;
    font-size: 14px;
}
.online-btn {
    color: white;
    text-decoration: none;
    font-size: 15px;
    padding: 8px 15px;
    border-radius: 20px;
    transition: all 0.3s ease;
    background: rgba(255,255,255,0.2);
    display: inline-flex;
    align-items: center;
    gap: 5px;
}
.online-btn:hover {
    background: rgba(255,255,255,0.3);
}
.badge {
    background-color: #f44336;
    color: white;
    font-size: 12px;
    padding: 2px 6px;
    border-radius: 10px;
    margin-left: 4px;
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
            <div class="user-dropdown">
                <span class="user-name">板块</span>
                <div class="user-dropdown-content">
                    <a href="${pageContext.request.contextPath}/logon/ArticleList">全部板块</a>
                    <% if (headerBoardList != null) { %>
                        <% for (Object obj : headerBoardList) { %>
                            <% Board board = (Board) obj; %>
                            <a href="${pageContext.request.contextPath}/logon/ArticleList?boardId=<%= board.getId() %>"><%= board.getName() %></a>
                        <% } %>
                    <% } %>
                </div>
            </div>
            <a href="${pageContext.request.contextPath}/logon/NoticeList">公告</a>
            <% if (user != null) { %>
            <a href="${pageContext.request.contextPath}/logon/article-new.jsp">发帖</a>
            <% } %>
            <a href="${pageContext.request.contextPath}/logon/UserList">用户列表</a>
            <a href="javascript:void(0)" onclick="showOnlineUsers()" class="online-btn" title="查看在线用户">
                <span>●</span>在线 ${onlineCount}
            </a>
            <% if (user != null) { %>
                <div class="user-dropdown">
                    <span class="user-name"><%= user.getUserName() %>
                        <% if (isAdmin) { %> <span style="background:#dc3545;padding:2px 6px;border-radius:4px;font-size:10px;">管理员</span> <% } %>
                        <% if (isModerator) { %> <span style="background:#ffc107;padding:2px 6px;border-radius:4px;font-size:10px;color:#333;">版主</span> <% } %>
                    </span>
                    <div class="user-dropdown-content">
                        <a href="${pageContext.request.contextPath}/logon/MyArticleList">我的帖子</a>
                        <a href="${pageContext.request.contextPath}/logon/MyRemarkList">我的评论</a>
                        <a href="${pageContext.request.contextPath}/logon/MessageList">我的消息<%= unreadCount > 0 ? "<span class='badge'>" + unreadCount + "</span>" : "" %></a>
                        <a href="${pageContext.request.contextPath}/logon/UserDetail?userId=${user.id}">个人中心</a>
                        <% if (isAdmin) { %>
                            <a href="${pageContext.request.contextPath}/logon/admin/UserList">管理用户</a>
                            <a href="${pageContext.request.contextPath}/logon/admin/BoardList">板块管理</a>
                        <% } %>
                        <% if (isModerator) { %>
                            <a href="${pageContext.request.contextPath}/logon/ArticleList?boardId=${moderatorBoardId}">我的板块管理</a>
                        <% } %>
                        <a href="${pageContext.request.contextPath}/logon/HandleUserLogout">退出登录</a>
                    </div>
                </div>
            <% } else { %>
                <a href="${pageContext.request.contextPath}/UserLogon.jsp" class="btn-login">登录</a>
                <a href="${pageContext.request.contextPath}/UserRegister.jsp" class="btn-register">注册</a>
            <% } %>
        </div>
    </div>
</div>

<!-- 在线用户弹窗 -->
<div id="onlineModal" style="display:none; position:fixed; top:0; left:0; width:100%; height:100%; background:rgba(0,0,0,0.5); z-index:9999; justify-content:center; align-items:center;">
    <div style="background:white; border-radius:10px; padding:25px; max-width:500px; width:90%; max-height:80vh; overflow-y:auto; box-shadow:0 10px 40px rgba(0,0,0,0.3);">
        <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:20px; border-bottom:1px solid #eee; padding-bottom:15px;">
            <h3 style="margin:0; color:#333;">当前在线用户</h3>
            <span style="cursor:pointer; font-size:20px; color:#999; line-height:1;" onclick="closeOnlineModal()">&times;</span>
        </div>
        <div id="onlineUserList" style="min-height:50px;">
            <p style="text-align:center; color:#999;">加载中...</p>
        </div>
        <div style="text-align:center; margin-top:15px; padding-top:15px; border-top:1px solid #eee; color:#666; font-size:13px;">
            总在线人数：<strong style="color:#667eea;" id="modalTotalCount">${onlineCount}</strong>
            <br/>
            登录用户：<strong style="color:#4caf50;" id="modalLoggedInCount">${loggedInCount}</strong>
        </div>
    </div>
</div>

<script>
function showOnlineUsers() {
    var modal = document.getElementById('onlineModal');
    modal.style.display = 'flex';
    
    fetch('${pageContext.request.contextPath}/OnlineUserList', {credentials: 'same-origin'})
        .then(function(response) { return response.json(); })
        .then(function(data) {
            var listDiv = document.getElementById('onlineUserList');
            var totalCountSpan = document.getElementById('modalTotalCount');
            var loggedInCountSpan = document.getElementById('modalLoggedInCount');
            totalCountSpan.textContent = data.totalCount;
            loggedInCountSpan.textContent = data.loggedInCount;
            
            if (!data.users || data.users.length === 0) {
                listDiv.innerHTML = '<p style="text-align:center; color:#999;">当前无在线用户</p>';
                return;
            }
            
            var html = '<div style="display:flex; flex-direction:column; gap:10px;">';
            data.users.forEach(function(u) {
                var roleTag = '';
                if (u.role === 0) roleTag = '<span style="background:#dc3545;color:white;padding:2px 6px;border-radius:4px;font-size:11px;margin-left:5px;">管理员</span>';
                else if (u.role === 2) roleTag = '<span style="background:#ffc107;color:#333;padding:2px 6px;border-radius:4px;font-size:11px;margin-left:5px;">版主</span>';
                
                html += '<div style="display:flex; justify-content:space-between; align-items:center; padding:10px; background:#f8f9fa; border-radius:6px;">';
                html += '  <div><strong>' + u.userName + '</strong>' + roleTag + '<div style="color:#666; font-size:12px; margin-top:3px;">' + u.account + '</div></div>';
                html += '  <span style="color:#4caf50; font-size:12px;">● 在线</span>';
                html += '</div>';
            });
            html += '</div>';
            listDiv.innerHTML = html;
        })
        .catch(function(err) {
            document.getElementById('onlineUserList').innerHTML = '<p style="text-align:center; color:#f44336;">加载失败</p>';
        });
}

function closeOnlineModal() {
    document.getElementById('onlineModal').style.display = 'none';
}

document.getElementById('onlineModal').addEventListener('click', function(e) {
    if (e.target === this) closeOnlineModal();
});
</script>