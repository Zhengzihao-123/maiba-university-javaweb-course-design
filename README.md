# 麦吧论坛系统 (Maiba Forum)

基于 JavaWeb (Servlet + JSP + JSTL) 技术栈构建的完整论坛系统，实现四级角色权限控制体系。

## 🎯 项目简介

麦吧是一个功能完整的校园论坛系统，支持用户注册登录、帖子管理、板块管理、站内信、系统公告等核心功能，并具备完整的权限控制体系。

## ✨ 功能特性

### 用户管理
- 用户注册/登录/退出
- 账号安全（密码错误3次锁定15分钟）
- 用户信息修改
- 用户列表查看（隐私信息屏蔽）

### 帖子管理
- 帖子发布/查看/修改/删除
- 帖子模糊搜索
- 帖子置顶/取消置顶
- 评论功能

### 板块管理
- 板块增删改查
- 版主分配
- 帖子按板块分类

### 权限系统
- 四级角色：超级管理员、版主、普通用户、游客
- 路径级权限拦截
- 业务级权限校验

### 其他功能
- 站内信（发送、接收、未读计数）
- 系统公告管理
- 实时在线人数统计

## 👥 角色权限

| 角色 | 角色值 | 权限范围 |
|------|--------|----------|
| 超级管理员 | 0 | 全站管理权限（用户管理、板块管理、帖子管理、公告管理） |
| 版主 | 2 | 管理所属板块帖子 + 普通用户所有权限 |
| 普通用户 | 1 | 发布/修改/删除自己的帖子、评论、站内信 |
| 游客 | - | 浏览帖子、公告，无法发布内容 |

## 🛠️ 技术栈

- **框架**: JavaWeb (Servlet 3.0 + JSP + JSTL)
- **数据库**: MySQL 8.0
- **服务器**: Jetty 9.4
- **构建工具**: Maven
- **测试**: JUnit 4

## 📁 项目结构

```
maiba/
├── src/main/java/
│   ├── cn/maiba/          # 实体类和工具类
│   │   ├── Article.java   # 帖子实体
│   │   ├── Board.java     # 板块实体
│   │   ├── Message.java   # 消息实体
│   │   ├── User.java      # 用户实体
│   │   ├── PermissionChecker.java  # 权限校验工具
│   │   └── MyDataBase.java         # 数据库操作类
│   └── com/maiba/         # Servlet 和过滤器
│       ├── HandleUserLogon.java    # 登录处理
│       ├── MessageListServlet.java # 消息列表
│       ├── OnlineUserListener.java # 在线用户监听
│       └── ...
├── src/main/webapp/       # JSP 页面
│   ├── logon/             # 登录后页面
│   ├── include/           # 公共组件
│   └── admin/             # 管理员页面
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

2. **修改数据库配置**

编辑 `src/main/java/cn/maiba/DBUtil.java`，修改数据库连接信息：
```java
private static final String URL = "jdbc:mysql://localhost:3306/maiba?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8";
private static final String USERNAME = "root";
private static final String PASSWORD = "123456";
```

3. **运行项目**
```bash
mvn jetty:run
```

4. **访问系统**

打开浏览器访问：http://localhost:8084/maiba

5. **初始化测试数据**

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
- `GET /logon/ArticleList` - 帖子列表
- `POST /logon/HandleArticleNew` - 发布帖子
- `GET /logon/ArticleDetail` - 帖子详情

### 消息接口
- `GET /logon/MessageList` - 消息列表
- `POST /logon/HandleMessageNew` - 发送消息
- `GET /logon/MessageDetail` - 消息详情

### 在线用户接口
- `GET /OnlineUserList` - 在线用户列表（JSON格式）

## 🔒 安全特性

- 密码错误3次自动锁定15分钟
- 权限校验在后端执行，前端仅做按钮隐藏
- 隐私信息（密码、邮箱、年龄）对非管理员屏蔽
- 使用 PreparedStatement 防止 SQL 注入

## 📄 许可证

MIT License

## 📞 联系方式

如有问题，请联系：zhengzihao@example.com