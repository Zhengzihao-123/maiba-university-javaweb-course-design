CREATE DATABASE IF NOT EXISTS maiba DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE maiba;

CREATE TABLE IF NOT EXISTS t_user (
    id INT PRIMARY KEY AUTO_INCREMENT,
    account VARCHAR(50) NOT NULL UNIQUE COMMENT '登录账号',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    user_name VARCHAR(50) NOT NULL COMMENT '用户名',
    age INT DEFAULT 0 COMMENT '年龄',
    email VARCHAR(100) COMMENT '邮箱',
    role INT DEFAULT 1 COMMENT '角色(1普通用户/2版主/3管理员)',
    failed_attempts INT DEFAULT 0 COMMENT '失败尝试次数',
    locked_time DATETIME NULL COMMENT '锁定时间',
    last_login_time TIMESTAMP NULL COMMENT '最后登录时间',
    last_login_ip VARCHAR(50) COMMENT '最后登录IP',
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_account (account),
    INDEX idx_role (role),
    INDEX idx_locked (locked_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

CREATE TABLE IF NOT EXISTS t_board (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL UNIQUE COMMENT '板块名称',
    description VARCHAR(500) COMMENT '板块描述',
    moderator_id INT COMMENT '版主ID',
    sort_order INT DEFAULT 0 COMMENT '排序',
    is_active INT DEFAULT 1 COMMENT '是否启用',
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_name (name),
    INDEX idx_moderator (moderator_id),
    FOREIGN KEY (moderator_id) REFERENCES t_user(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='板块表';

CREATE TABLE IF NOT EXISTS t_article (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL COMMENT '标题',
    content TEXT NOT NULL COMMENT '内容',
    user_id INT NOT NULL COMMENT '作者ID',
    board_id INT COMMENT '所属板块ID',
    is_top INT DEFAULT 0 COMMENT '是否置顶',
    remark_num INT DEFAULT 0 COMMENT '评论数',
    hit_num INT DEFAULT 0 COMMENT '浏览数',
    is_comment_enabled INT DEFAULT 1 COMMENT '是否允许评论',
    is_deleted INT DEFAULT 0 COMMENT '是否删除',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    last_remark_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '最后评论时间',
    INDEX idx_title (title),
    INDEX idx_user (user_id),
    INDEX idx_board (board_id),
    INDEX idx_top (is_top),
    INDEX idx_deleted (is_deleted),
    FULLTEXT INDEX idx_search (title, content),
    FOREIGN KEY (user_id) REFERENCES t_user(id) ON DELETE CASCADE,
    FOREIGN KEY (board_id) REFERENCES t_board(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文章表';

CREATE TABLE IF NOT EXISTS t_remark (
    id INT PRIMARY KEY AUTO_INCREMENT,
    article_id INT NOT NULL COMMENT '文章ID',
    user_id INT NOT NULL COMMENT '评论者ID',
    remark TEXT NOT NULL COMMENT '评论内容',
    is_read INT DEFAULT 0 COMMENT '是否已读',
    is_deleted INT DEFAULT 0 COMMENT '是否删除',
    remark_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
    INDEX idx_article (article_id),
    INDEX idx_user (user_id),
    INDEX idx_read (is_read),
    FOREIGN KEY (article_id) REFERENCES t_article(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES t_user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='评论表';

CREATE TABLE IF NOT EXISTS t_notice (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL COMMENT '公告标题',
    content TEXT NOT NULL COMMENT '公告内容',
    user_id INT COMMENT '发布者ID',
    category INT DEFAULT 0 COMMENT '分类(0系统/1板块)',
    board_id INT DEFAULT 0 COMMENT '所属板块',
    is_active INT DEFAULT 1 COMMENT '是否启用',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_title (title),
    INDEX idx_category (category),
    INDEX idx_board (board_id),
    INDEX idx_active (is_active),
    FOREIGN KEY (user_id) REFERENCES t_user(id) ON DELETE SET NULL,
    FOREIGN KEY (board_id) REFERENCES t_board(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='公告表';

CREATE TABLE IF NOT EXISTS t_message (
    id INT PRIMARY KEY AUTO_INCREMENT,
    sender_id INT NOT NULL COMMENT '发送者ID',
    receiver_id INT NOT NULL COMMENT '接收者ID',
    title VARCHAR(200) NOT NULL COMMENT '消息标题',
    content TEXT NOT NULL COMMENT '消息内容',
    is_read INT DEFAULT 0 COMMENT '是否已读',
    is_deleted_by_sender INT DEFAULT 0 COMMENT '发送者删除',
    is_deleted_by_receiver INT DEFAULT 0 COMMENT '接收者删除',
    send_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
    INDEX idx_sender (sender_id),
    INDEX idx_receiver (receiver_id),
    INDEX idx_read (is_read),
    FOREIGN KEY (sender_id) REFERENCES t_user(id) ON DELETE CASCADE,
    FOREIGN KEY (receiver_id) REFERENCES t_user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='站内信表';

CREATE TABLE IF NOT EXISTS t_login_log (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL COMMENT '用户ID',
    login_ip VARCHAR(50) NOT NULL COMMENT '登录IP',
    login_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间',
    logout_time TIMESTAMP NULL COMMENT '登出时间',
    login_status INT NOT NULL COMMENT '登录状态(0失败/1成功)',
    failure_reason VARCHAR(200) COMMENT '失败原因',
    INDEX idx_user (user_id),
    INDEX idx_time (login_time),
    FOREIGN KEY (user_id) REFERENCES t_user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='登录日志表';

CREATE TABLE IF NOT EXISTS t_online_session (
    id INT PRIMARY KEY AUTO_INCREMENT,
    session_id VARCHAR(100) NOT NULL UNIQUE COMMENT '会话ID',
    user_id INT COMMENT '用户ID',
    ip_address VARCHAR(50) NOT NULL COMMENT 'IP地址',
    is_guest INT DEFAULT 1 COMMENT '是否游客',
    last_active_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后活跃时间',
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    expired_at TIMESTAMP NULL COMMENT '过期时间',
    INDEX idx_session (session_id),
    INDEX idx_user (user_id),
    INDEX idx_guest (is_guest),
    INDEX idx_last_active (last_active_time),
    FOREIGN KEY (user_id) REFERENCES t_user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='在线会话表';

CREATE TABLE IF NOT EXISTS t_user_blacklist (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL UNIQUE COMMENT '被封禁用户',
    reason VARCHAR(500) COMMENT '封禁原因',
    banned_by INT COMMENT '封禁者',
    banned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '封禁时间',
    unbanned_at TIMESTAMP NULL COMMENT '解封时间',
    INDEX idx_user (user_id),
    FOREIGN KEY (user_id) REFERENCES t_user(id) ON DELETE CASCADE,
    FOREIGN KEY (banned_by) REFERENCES t_user(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户黑名单';

CREATE TABLE IF NOT EXISTS t_ip_blacklist (
    id INT PRIMARY KEY AUTO_INCREMENT,
    ip_address VARCHAR(50) NOT NULL UNIQUE COMMENT '被封禁IP',
    reason VARCHAR(500) COMMENT '封禁原因',
    banned_by INT COMMENT '封禁者',
    banned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '封禁时间',
    unbanned_at TIMESTAMP NULL COMMENT '解封时间',
    INDEX idx_ip (ip_address),
    FOREIGN KEY (banned_by) REFERENCES t_user(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='IP黑名单';