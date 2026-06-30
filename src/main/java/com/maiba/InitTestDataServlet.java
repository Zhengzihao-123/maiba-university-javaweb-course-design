package com.maiba;

import cn.maiba.Article;
import cn.maiba.Board;
import cn.maiba.MyDataBase;
import cn.maiba.RoleConstants;
import cn.maiba.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

/**
 * 测试数据初始化Servlet
 * 用于创建版主测试用户和设置板块版主
 */
@WebServlet("/logon/InitTestData")
public class InitTestDataServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        StringBuilder result = new StringBuilder();
        result.append("<html><head><title>测试数据初始化</title></head><body>");
        result.append("<h1>测试数据初始化</h1>");
        
        try {
            // 初始化板块表
            MyDataBase.initBoardTable();
            result.append("<p>板块表初始化完成</p>");
            
            // 初始化公告表
            MyDataBase.initNoticeTable();
            result.append("<p>公告表初始化完成</p>");
            
            // 初始化新表（登录日志、在线会话、用户黑名单、IP黑名单）
            MyDataBase.initNewTables();
            result.append("<p>新表初始化完成</p>");
            
            // 获取现有板块
            java.util.List<Board> boardList = MyDataBase.list(Board.TABLE_NAME);
            if (boardList == null || boardList.isEmpty()) {
                // 创建默认板块
                Board board1 = new Board();
                board1.setId(1);
                board1.setName("技术交流");
                board1.setDescription("技术讨论板块");
                board1.setCreateTime(new Timestamp(System.currentTimeMillis()));
                board1.setSortOrder(1);
                MyDataBase.save(Board.TABLE_NAME, board1);
                result.append("<p>创建板块：技术交流</p>");
                
                Board board2 = new Board();
                board2.setId(2);
                board2.setName("生活分享");
                board2.setDescription("生活分享板块");
                board2.setCreateTime(new Timestamp(System.currentTimeMillis()));
                board2.setSortOrder(2);
                MyDataBase.save(Board.TABLE_NAME, board2);
                result.append("<p>创建板块：生活分享</p>");
                
                Board board3 = new Board();
                board3.setId(3);
                board3.setName("休闲娱乐");
                board3.setDescription("休闲娱乐板块");
                board3.setCreateTime(new Timestamp(System.currentTimeMillis()));
                board3.setSortOrder(3);
                MyDataBase.save(Board.TABLE_NAME, board3);
                result.append("<p>创建板块：休闲娱乐</p>");
                
                boardList = MyDataBase.list(Board.TABLE_NAME);
            } else {
                result.append("<p>板块已存在，共 ").append(boardList.size()).append(" 个板块</p>");
            }
            
            // 检查是否已有版主测试用户
            java.util.List<User> userList = MyDataBase.list(User.TABLE_NAME);
            boolean hasModerator = false;
            boolean hasNormalUser = false;
            boolean hasAdmin = false;
            User moderatorUser = null;
            User normalUser = null;
            User adminUser = null;
            
            if (userList != null) {
                for (User u : userList) {
                    if (u.getRole() == RoleConstants.ROLE_MODERATOR) {
                        hasModerator = true;
                        moderatorUser = u;
                    }
                    if (u.getRole() == RoleConstants.ROLE_USER) {
                        hasNormalUser = true;
                        normalUser = u;
                    }
                    if (u.getRole() == RoleConstants.ROLE_SUPER_ADMIN) {
                        hasAdmin = true;
                        adminUser = u;
                    }
                }
            }
            
            // 创建超级管理员（先查找，没有则创建，有则更新角色）
            User admin = null;
            if (userList != null) {
                for (User u : userList) {
                    if ("admin".equals(u.getAccount())) {
                        admin = u;
                        break;
                    }
                }
            }
            
            if (admin == null) {
                admin = new User();
                admin.setId(200);
                admin.setAccount("admin");
                admin.setPassword("admin");
                admin.setUserName("超级管理员");
                admin.setAge(25);
                admin.setEmail("admin@test.com");
                admin.setRole(RoleConstants.ROLE_SUPER_ADMIN);
                admin.setFailedAttempts(0);
                admin.setLockedTime(null);
                
                if (MyDataBase.save(User.TABLE_NAME, admin)) {
                    result.append("<p>创建超级管理员成功：admin / admin</p>");
                } else {
                    result.append("<p>创建超级管理员失败</p>");
                }
            } else {
                admin.setRole(RoleConstants.ROLE_SUPER_ADMIN);
                admin.setPassword("admin");
                if (MyDataBase.update(User.TABLE_NAME, admin)) {
                    result.append("<p>更新超级管理员角色成功：admin / admin</p>");
                } else {
                    result.append("<p>更新超级管理员角色失败</p>");
                }
            }
            adminUser = admin;
            
            // 创建版主测试用户
            if (!hasModerator) {
                User modUser = new User();
                modUser.setId(100);
                modUser.setAccount("moderator");
                modUser.setPassword("123456"); // 实际应该加密
                modUser.setUserName("测试版主");
                modUser.setAge(30);
                modUser.setEmail("moderator@test.com");
                modUser.setRole(RoleConstants.ROLE_MODERATOR);
                modUser.setFailedAttempts(0);
                modUser.setLockedTime(null);
                
                if (MyDataBase.save(User.TABLE_NAME, modUser)) {
                    result.append("<p>创建版主用户成功：moderator / 123456</p>");
                    moderatorUser = modUser;
                } else {
                    result.append("<p>创建版主用户失败</p>");
                }
            } else {
                result.append("<p>版主用户已存在：").append(moderatorUser.getUserName()).append(" (ID: ").append(moderatorUser.getId()).append(")</p>");
            }
            
            // 创建普通测试用户
            if (!hasNormalUser) {
                User normUser = new User();
                normUser.setId(101);
                normUser.setAccount("user");
                normUser.setPassword("123456");
                normUser.setUserName("测试用户");
                normUser.setAge(25);
                normUser.setEmail("user@test.com");
                normUser.setRole(RoleConstants.ROLE_USER);
                normUser.setFailedAttempts(0);
                normUser.setLockedTime(null);
                
                if (MyDataBase.save(User.TABLE_NAME, normUser)) {
                    result.append("<p>创建普通用户成功：user / 123456</p>");
                    normalUser = normUser;
                } else {
                    result.append("<p>创建普通用户失败</p>");
                }
            } else {
                result.append("<p>普通用户已存在：").append(normalUser.getUserName()).append(" (ID: ").append(normalUser.getId()).append(")</p>");
            }
            
            // 设置版主为第一个板块的版主
            if (moderatorUser != null && boardList != null && !boardList.isEmpty()) {
                Board firstBoard = boardList.get(0);
                firstBoard.setModeratorId(moderatorUser.getId());
                
                if (MyDataBase.update(Board.TABLE_NAME, firstBoard)) {
                    result.append("<p>设置 ").append(firstBoard.getName()).append(" 的版主为：")
                          .append(moderatorUser.getUserName()).append(" (ID: ").append(moderatorUser.getId()).append(")</p>");
                } else {
                    result.append("<p>设置版主失败</p>");
                }
            }
            
            // 创建测试帖子
            result.append("<h2>创建测试帖子</h2>");
            java.util.List<Article> articleList = MyDataBase.list(Article.TABLE_NAME);
            int existingArticleCount = articleList != null ? articleList.size() : 0;
            
            if (existingArticleCount == 0) {
                // 超级管理员发帖（技术交流板块）
                Article adminArticle1 = new Article();
                adminArticle1.setId(1000);
                adminArticle1.setTitle("欢迎来到麦吧！");
                adminArticle1.setContent("欢迎各位用户来到麦吧论坛！这是一个分享技术、生活和娱乐的社区平台。\n\n请遵守社区规则，文明发言。如有任何问题，请联系管理员。");
                adminArticle1.setUserId(adminUser.getId());
                adminArticle1.setBoardId(1);
                adminArticle1.setIsTop(1);
                adminArticle1.setRemarkNum(0);
                adminArticle1.setHitNum(0);
                adminArticle1.setCreateTime(new Timestamp(System.currentTimeMillis()));
                adminArticle1.setLastRemarkTime(new Timestamp(System.currentTimeMillis()));
                MyDataBase.save(Article.TABLE_NAME, adminArticle1);
                result.append("<p>超级管理员发帖：欢迎来到麦吧！(置顶帖)</p>");
                
                // 超级管理员发帖（生活分享板块）
                Article adminArticle2 = new Article();
                adminArticle2.setId(1001);
                adminArticle2.setTitle("社区公告：新版主招募中");
                adminArticle2.setContent("麦吧社区正在招募新版主！\n\n如果你热爱社区，愿意为大家服务，请私信管理员申请。\n\n要求：\n1. 有足够的在线时间\n2. 熟悉社区规则\n3. 有良好的沟通能力");
                adminArticle2.setUserId(adminUser.getId());
                adminArticle2.setBoardId(2);
                adminArticle2.setIsTop(1);
                adminArticle2.setRemarkNum(0);
                adminArticle2.setHitNum(0);
                adminArticle2.setCreateTime(new Timestamp(System.currentTimeMillis()));
                adminArticle2.setLastRemarkTime(new Timestamp(System.currentTimeMillis()));
                MyDataBase.save(Article.TABLE_NAME, adminArticle2);
                result.append("<p>超级管理员发帖：社区公告(置顶帖)</p>");
                
                // 版主发帖（技术交流板块 - 自己管理的板块）
                Article modArticle1 = new Article();
                modArticle1.setId(1002);
                modArticle1.setTitle("JavaWeb学习心得分享");
                modArticle1.setContent("最近学习了JavaWeb开发，分享一些心得：\n\n1. Servlet是JavaWeb的核心，要深入理解\n2. JSP和Servlet配合使用可以很好地分离视图和逻辑\n3. 数据库连接池可以提高性能\n\n希望对大家有帮助！");
                modArticle1.setUserId(moderatorUser.getId());
                modArticle1.setBoardId(1);
                modArticle1.setIsTop(0);
                modArticle1.setRemarkNum(0);
                modArticle1.setHitNum(0);
                modArticle1.setCreateTime(new Timestamp(System.currentTimeMillis()));
                modArticle1.setLastRemarkTime(new Timestamp(System.currentTimeMillis()));
                MyDataBase.save(Article.TABLE_NAME, modArticle1);
                result.append("<p>版主发帖：JavaWeb学习心得分享(技术交流板块)</p>");
                
                // 版主发帖（休闲娱乐板块 - 非管理板块）
                Article modArticle2 = new Article();
                modArticle2.setId(1003);
                modArticle2.setTitle("周末电影推荐");
                modArticle2.setContent("周末到了，推荐几部不错的电影：\n\n1. 《肖申克的救赎》 - 经典必看\n2. 《阿甘正传》 - 励志感人\n3. 《星际穿越》 - 科幻神作\n\n大家还有什么推荐吗？");
                modArticle2.setUserId(moderatorUser.getId());
                modArticle2.setBoardId(3);
                modArticle2.setIsTop(0);
                modArticle2.setRemarkNum(0);
                modArticle2.setHitNum(0);
                modArticle2.setCreateTime(new Timestamp(System.currentTimeMillis()));
                modArticle2.setLastRemarkTime(new Timestamp(System.currentTimeMillis()));
                MyDataBase.save(Article.TABLE_NAME, modArticle2);
                result.append("<p>版主发帖：周末电影推荐(休闲娱乐板块)</p>");
                
                // 普通用户发帖（技术交流板块）
                Article userArticle1 = new Article();
                userArticle1.setId(1004);
                userArticle1.setTitle("关于Servlet的问题请教");
                userArticle1.setContent("大家好，我有一个关于Servlet的问题想请教：\n\n在Servlet中，doGet和doPost方法有什么区别？什么时候应该用哪个？\n\n我查了一些资料，但还是不太明白，希望有经验的同学能解答一下。");
                userArticle1.setUserId(normalUser.getId());
                userArticle1.setBoardId(1);
                userArticle1.setIsTop(0);
                userArticle1.setRemarkNum(0);
                userArticle1.setHitNum(0);
                userArticle1.setCreateTime(new Timestamp(System.currentTimeMillis()));
                userArticle1.setLastRemarkTime(new Timestamp(System.currentTimeMillis()));
                MyDataBase.save(Article.TABLE_NAME, userArticle1);
                result.append("<p>普通用户发帖：关于Servlet的问题请教(技术交流板块)</p>");
                
                // 普通用户发帖（生活分享板块）
                Article userArticle2 = new Article();
                userArticle2.setId(1005);
                userArticle2.setTitle("今天天气真好");
                userArticle2.setContent("今天的天气特别好，阳光明媚，微风轻拂。\n\n早上出去跑步了，感觉心情特别舒畅。\n\n大家今天都在做什么呢？");
                userArticle2.setUserId(normalUser.getId());
                userArticle2.setBoardId(2);
                userArticle2.setIsTop(0);
                userArticle2.setRemarkNum(0);
                userArticle2.setHitNum(0);
                userArticle2.setCreateTime(new Timestamp(System.currentTimeMillis()));
                userArticle2.setLastRemarkTime(new Timestamp(System.currentTimeMillis()));
                MyDataBase.save(Article.TABLE_NAME, userArticle2);
                result.append("<p>普通用户发帖：今天天气真好(生活分享板块)</p>");
                
                // 普通用户发帖（休闲娱乐板块）
                Article userArticle3 = new Article();
                userArticle3.setId(1006);
                userArticle3.setTitle("游戏推荐：我的世界");
                userArticle3.setContent("推荐一款非常好玩的游戏：我的世界(Minecraft)\n\n这是一款沙盒建造游戏，可以自由建造各种建筑。\n\n特点：\n1. 无限创造力\n2. 丰富的资源\n3. 多人联机\n\n强烈推荐！");
                userArticle3.setUserId(normalUser.getId());
                userArticle3.setBoardId(3);
                userArticle3.setIsTop(0);
                userArticle3.setRemarkNum(0);
                userArticle3.setHitNum(0);
                userArticle3.setCreateTime(new Timestamp(System.currentTimeMillis()));
                userArticle3.setLastRemarkTime(new Timestamp(System.currentTimeMillis()));
                MyDataBase.save(Article.TABLE_NAME, userArticle3);
                result.append("<p>普通用户发帖：游戏推荐(休闲娱乐板块)</p>");
                
                result.append("<p>共创建 ").append(6).append(" 篇测试帖子</p>");
            } else {
                result.append("<p>已存在 ").append(existingArticleCount).append(" 篇帖子，跳过创建测试帖子</p>");
            }
            
            // 列出所有用户
            userList = MyDataBase.list(User.TABLE_NAME);
            if (userList != null) {
                result.append("<h2>当前用户列表</h2>");
                result.append("<table border='1'><tr><th>ID</th><th>账号</th><th>用户名</th><th>角色</th></tr>");
                for (User u : userList) {
                    String roleName = "未知";
                    if (u.getRole() == RoleConstants.ROLE_SUPER_ADMIN) roleName = "超级管理员";
                    else if (u.getRole() == RoleConstants.ROLE_USER) roleName = "普通用户";
                    else if (u.getRole() == RoleConstants.ROLE_MODERATOR) roleName = "版主";
                    
                    result.append("<tr><td>").append(u.getId())
                          .append("</td><td>").append(u.getAccount())
                          .append("</td><td>").append(u.getUserName())
                          .append("</td><td>").append(roleName).append("</td></tr>");
                }
                result.append("</table>");
            }
            
            // 列出所有板块
            boardList = MyDataBase.list(Board.TABLE_NAME);
            if (boardList != null) {
                result.append("<h2>当前板块列表</h2>");
                result.append("<table border='1'><tr><th>ID</th><th>名称</th><th>版主ID</th></tr>");
                for (Board b : boardList) {
                    result.append("<tr><td>").append(b.getId())
                          .append("</td><td>").append(b.getName())
                          .append("</td><td>").append(b.getModeratorId() != null ? b.getModeratorId().toString() : "无")
                          .append("</td></tr>");
                }
                result.append("</table>");
            }
            
            result.append("<h2>测试账号</h2>");
            result.append("<ul>");
            result.append("<li>超级管理员：admin / admin (role=0)</li>");
            result.append("<li>普通用户：user / 123456 (role=1)</li>");
            result.append("<li>版主：moderator / 123456 (role=2)</li>");
            result.append("</ul>");
            
            result.append("<p><a href='").append(request.getContextPath()).append("/logon/ArticleList'>返回帖子列表</a></p>");
            
        } catch (Exception e) {
            result.append("<p style='color:red;'>错误：").append(e.getMessage()).append("</p>");
            e.printStackTrace();
        }
        
        result.append("</body></html>");
        response.getWriter().write(result.toString());
    }
}