# 麦吧论坛系统 (Maiba Forum)

基于 JavaWeb (Servlet + JSP + JSTL) 技术栈构建的完整论坛系统，实现四级角色权限控制体系。

## 🎯 项目简介

麦吧是一个功能完整的校园论坛系统，支持用户注册登录、帖子管理、板块管理、站内信、系统公告等核心功能，并具备完整的权限控制体系。由广东技术师范大学开发。

## ✨ 功能特性

### 用户管理
- 用户注册/登录/退出
- 账号安全（密码错误3次锁定15分钟）
- 用户信息修改
- 用户列表查看（隐私信息屏蔽）

### 帖子管理
- 帖子发布/查看/修改/删除
- 帖子模糊搜索（支持标题和内容）
- 帖子置顶/取消置顶
- 评论功能
- 按板块分类浏览

### 板块管理
- 板块增删改查
- 版主分配
- 帖子按板块分类

### 公告管理
- **系统公告**：超级管理员可发布全站公告
- **板块公告**：版主可发布所属板块公告
- 公告分类筛选（按类型/板块）
- 公告删除功能

### 权限系统
- 四级角色：超级管理员(0)、版主(2)、普通用户(1)、游客
- 路径级权限拦截（LogonFilter）
- 业务级权限校验（PermissionChecker）

### 其他功能
- 站内信（发送、接收、未读计数）
- 实时在线人数统计（包含登录用户和游客）
- 版主专属"我的板块管理"入口

## 👥 角色权限

| 功能 | 游客 | 普通用户 | 版主 | 超级管理员 |
|------|------|----------|------|------------|
| 查看帖子 | ✅ | ✅ | ✅ | ✅ |
| 查看公告 | ✅ | ✅ | ✅ | ✅ |
| 发布帖子 | ❌ | ✅ | ✅ | ✅ |
| 删除帖子 | ❌ | 仅自己 | 仅本版 | ✅全部 |
| 置顶帖子 | ❌ | ❌ | 仅本版 | ✅全部 |
| 发布公告 | ❌ | ❌ | ✅板块公告 | ✅全部 |
| 删除公告 | ❌ | ❌ | 仅本版公告 | ✅全部 |
| 用户管理 | ❌ | ❌ | ❌ | ✅ |
| 板块管理 | ❌ | ❌ | ❌ | ✅ |

## 🛠️ 技术栈

- **框架**: JavaWeb (Servlet 3.0 + JSP + JSTL)
- **数据库**: MySQL 8.0
- **服务器**: Jetty 9.4
- **构建工具**: Maven
- **测试**: JUnit 4
- **Java版本**: JDK 17

## 📁 项目结构

```
maiba/
├── src/main/java/
│   ├── cn/maiba/          # 实体类和工具类
│   │   ├── Article.java   # 帖子实体
│   │   ├── Board.java     # 板块实体
│   │   ├── Notice.java    # 公告实体（支持分类）
│   │   ├── Message.java   # 消息实体
│   │   ├── User.java      # 用户实体
│   │   ├── PermissionChecker.java  # 权限校验工具
│   │   └── MyDataBase.java         # 数据库操作类
│   └── com/maiba/         # Servlet 和过滤器
│       ├── HandleUserLogon.java    # 登录处理
│       ├── HandleNoticeDelete.java # 公告删除
│       ├── HandleArticleTop.java   # 帖子置顶
│       ├── NoticeNewServlet.java   # 公告发布页面
│       ├── OnlineUserListener.java # 在线用户监听（含游客）
│       └── ...
├── src/main/webapp/       # JSP 页面
│   ├── logon/             # 登录后页面
│   │   ├── ArticleList.jsp    # 帖子列表
│   │   ├── NoticeList.jsp     # 公告列表（支持分类筛选）
│   │   ├── NoticeNew.jsp      # 发布公告
│   │   └── UserList.jsp       # 用户列表
│   ├── include/           # 公共组件
│   └── WEB-INF/web.xml    # Servlet 3.0 配置
└── pom.xml                # Maven 配置
```

## 🚀 快速开始

### 环境要求
- JDK 17+
- MySQL 8.0
- Maven 3.6+

### 配置步骤

1. **创建数据库**
```sql
CREATE DATABASE maiba CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. **导入数据表**

执行 `src/main/resources/create_table.sql` 创建所有数据表

3. **修改数据库配置**

编辑 `src/main/java/cn/maiba/DBUtil.java`，修改数据库连接信息：
```java
private static final String URL = "jdbc:mysql://localhost:3306/maiba?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8";
private static final String USERNAME = "root";
private static final String PASSWORD = "123456";
```

4. **运行项目**
```bash
mvn jetty:run
```

5. **访问系统**

打开浏览器访问：http://localhost:8084/maiba

6. **初始化测试数据**

访问：http://localhost:8084/maiba/logon/InitTestData

### 测试账号

| 角色 | 账号 | 密码 |
|------|------|------|
| 超级管理员 | admin | admin |
| 版主 | moderator | 123456 |
| 普通用户 | user | 123456 |

## 📝 API 接口

### 用户接口
- `POST /user/HandleUserLogon` - 用户登录
- `POST /HandleUserRegister` - 用户注册
- `GET /logon/HandleUserLogout` - 用户退出

### 帖子接口
- `GET /logon/ArticleList` - 帖子列表（支持板块筛选）
- `POST /logon/HandleArticleNew` - 发布帖子
- `GET /logon/ArticleDetail` - 帖子详情
- `GET /logon/HandleArticleTop` - 帖子置顶/取消置顶

### 公告接口
- `GET /logon/NoticeList` - 公告列表（支持分类筛选）
- `GET /logon/NoticeNew` - 发布公告页面
- `POST /logon/HandleNoticeNew` - 发布公告
- `GET /logon/HandleNoticeDelete` - 删除公告

### 消息接口
- `GET /logon/MessageList` - 消息列表
- `POST /logon/HandleMessageNew` - 发送消息
- `GET /logon/MessageDetail` - 消息详情

### 在线用户接口
- `GET /OnlineUserList` - 在线用户列表（JSON格式，含游客统计）

## 🔒 安全特性

- 密码错误3次自动锁定15分钟
- 权限校验在后端执行，前端仅做按钮隐藏
- 隐私信息（密码、邮箱、年龄）对非管理员屏蔽
- 使用 PreparedStatement 防止 SQL 注入
- Session 过期自动跳转登录页

## 📋 数据库表结构

| 表名 | 说明 |
|------|------|
| t_user | 用户表（含角色、锁定状态） |
| t_board | 板块表（含版主ID） |
| t_article | 帖子表（含置顶状态、软删除） |
| t_comment | 评论表 |
| t_notice | 公告表（含分类、关联板块） |
| t_message | 站内信表 |

## 📄 许可证

MIT License

## 📞 联系方式

如有问题，请联系：zhengzihao@example.com

---

**麦吧** -- 广东技术师范大学出品