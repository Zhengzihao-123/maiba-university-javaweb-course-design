USE maiba;

CREATE TABLE IF NOT EXISTS t_board (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL UNIQUE COMMENT '板块名称',
    description TEXT COMMENT '板块描述',
    moderator_id INT NULL COMMENT '版主用户ID（预留）',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    sort_order INT DEFAULT 0 COMMENT '排序权重',
    FOREIGN KEY (moderator_id) REFERENCES t_user(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='板块表';

ALTER TABLE t_article ADD COLUMN board_id INT NOT NULL DEFAULT 1 AFTER user_id;
ALTER TABLE t_article ADD CONSTRAINT fk_article_board FOREIGN KEY (board_id) REFERENCES t_board(id);

INSERT IGNORE INTO t_board (id, name, description, sort_order) VALUES (1, '综合讨论', '全站综合讨论区，畅所欲言', 0);
INSERT IGNORE INTO t_board (name, description, sort_order) VALUES ('学习交流', '学习资料分享、问题讨论', 10);
INSERT IGNORE INTO t_board (name, description, sort_order) VALUES ('生活分享', '记录生活点滴、分享日常', 20);
INSERT IGNORE INTO t_board (name, description, sort_order) VALUES ('体育竞技', '体育赛事讨论、运动健身', 30);

UPDATE t_article SET board_id = 1 WHERE board_id IS NULL OR board_id = 0;
