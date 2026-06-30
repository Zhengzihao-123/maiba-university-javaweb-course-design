package cn.maiba;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MyDataBase {

	public static boolean save(String tableName, MyTableItem tableItem) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtil.getConnection();
			if (tableItem instanceof User) {
				User user = (User) tableItem;
				String sql = "INSERT INTO t_user(id, account, password, user_name, age, email, role, failed_attempts, locked_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, user.getId());
				pstmt.setString(2, user.getAccount());
				pstmt.setString(3, user.getPassword());
				pstmt.setString(4, user.getUserName());
				pstmt.setInt(5, user.getAge());
				pstmt.setString(6, user.getEmail());
				pstmt.setInt(7, user.getRole());
				pstmt.setInt(8, user.getFailedAttempts());
				pstmt.setTimestamp(9, user.getLockedTime());
				return pstmt.executeUpdate() > 0;
			} else if (tableItem instanceof Article) {
				Article article = (Article) tableItem;
				String sql = "INSERT INTO t_article(id, title, content, user_id, board_id, is_top, remark_num, hit_num, create_time, last_remark_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, article.getId());
				pstmt.setString(2, article.getTitle());
				pstmt.setString(3, article.getContent());
				pstmt.setInt(4, article.getUserId());
				pstmt.setInt(5, article.getBoardId());
				pstmt.setInt(6, article.getIsTop());
				pstmt.setInt(7, article.getRemarkNum());
				pstmt.setInt(8, article.getHitNum());
				pstmt.setTimestamp(9, article.getCreateTime());
				pstmt.setTimestamp(10, article.getLastRemarkTime());
				return pstmt.executeUpdate() > 0;
			} else if (tableItem instanceof Remark) {
				Remark remark = (Remark) tableItem;
				String sql = "INSERT INTO t_remark(id, article_id, user_id, remark, remark_time) VALUES (?, ?, ?, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, remark.getId());
				pstmt.setInt(2, remark.getArticleId());
				pstmt.setInt(3, remark.getUserId());
				pstmt.setString(4, remark.getRemark());
				pstmt.setTimestamp(5, remark.getRemarkTime());
				return pstmt.executeUpdate() > 0;
			} else if (tableItem instanceof Notice) {
				Notice notice = (Notice) tableItem;
				String sql = "INSERT INTO t_notice(id, title, content, is_active, category, board_id, create_time, update_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, notice.getId());
				pstmt.setString(2, notice.getTitle());
				pstmt.setString(3, notice.getContent());
				pstmt.setInt(4, notice.getIsActive());
				pstmt.setInt(5, notice.getCategory());
				pstmt.setInt(6, notice.getBoardId());
				pstmt.setTimestamp(7, notice.getCreateTime());
				pstmt.setTimestamp(8, notice.getUpdateTime());
				return pstmt.executeUpdate() > 0;
			} else if (tableItem instanceof Message) {
				Message message = (Message) tableItem;
				String sql = "INSERT INTO t_message(sender_id, receiver_id, title, content, is_read, send_time) VALUES (?, ?, ?, ?, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, message.getSenderId());
				pstmt.setInt(2, message.getReceiverId());
				pstmt.setString(3, message.getTitle());
				pstmt.setString(4, message.getContent());
				pstmt.setInt(5, message.getIsRead());
				pstmt.setTimestamp(6, message.getSendTime());
				return pstmt.executeUpdate() > 0;
			} else if (tableItem instanceof Board) {
				Board board = (Board) tableItem;
				String sql = "INSERT INTO t_board(id, name, description, moderator_id, create_time, sort_order) VALUES (?, ?, ?, ?, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, board.getId());
				pstmt.setString(2, board.getName());
				pstmt.setString(3, board.getDescription());
				if (board.getModeratorId() != null) {
					pstmt.setInt(4, board.getModeratorId());
				} else {
					pstmt.setNull(4, java.sql.Types.INTEGER);
				}
				pstmt.setTimestamp(5, board.getCreateTime());
				pstmt.setInt(6, board.getSortOrder());
				return pstmt.executeUpdate() > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(conn, pstmt);
		}
		return false;
	}

	public static MyTableItem load(String tableName, Integer id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBUtil.getConnection();
			if ("t_user".equals(tableName)) {
				String sql = "SELECT * FROM t_user WHERE id = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, id);
				rs = pstmt.executeQuery();
				
				if (rs.next()) {
					User user = new User();
					user.setId(rs.getInt("id"));
					user.setAccount(rs.getString("account"));
					user.setPassword(rs.getString("password"));
					user.setUserName(rs.getString("user_name"));
					user.setAge(rs.getInt("age"));
					user.setEmail(rs.getString("email"));
					user.setRole(rs.getInt("role"));
					user.setFailedAttempts(rs.getInt("failed_attempts"));
					user.setLockedTime(rs.getTimestamp("locked_time"));
					return user;
				}
			} else if ("t_article".equals(tableName)) {
				String sql = "SELECT * FROM t_article WHERE id = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, id);
				rs = pstmt.executeQuery();
				
				if (rs.next()) {
					Article article = new Article();
					article.setId(rs.getInt("id"));
					article.setTitle(rs.getString("title"));
					article.setContent(rs.getString("content"));
					article.setUserId(rs.getInt("user_id"));
					article.setBoardId(rs.getInt("board_id"));
					article.setIsTop(rs.getInt("is_top"));
					article.setRemarkNum(rs.getInt("remark_num"));
					article.setHitNum(rs.getInt("hit_num"));
					article.setCreateTime(rs.getTimestamp("create_time"));
					article.setLastRemarkTime(rs.getTimestamp("last_remark_time"));
					return article;
				}
			} else if ("t_remark".equals(tableName)) {
				String sql = "SELECT * FROM t_remark WHERE id = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, id);
				rs = pstmt.executeQuery();
				
				if (rs.next()) {
					Remark remark = new Remark();
					remark.setId(rs.getInt("id"));
					remark.setArticleId(rs.getInt("article_id"));
					remark.setUserId(rs.getInt("user_id"));
					remark.setRemark(rs.getString("remark"));
					remark.setRemarkTime(rs.getTimestamp("remark_time"));
					return remark;
				}
			} else if ("t_notice".equals(tableName)) {
				String sql = "SELECT * FROM t_notice WHERE id = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, id);
				rs = pstmt.executeQuery();
				
				if (rs.next()) {
					Notice notice = new Notice();
					notice.setId(rs.getInt("id"));
					notice.setTitle(rs.getString("title"));
					notice.setContent(rs.getString("content"));
					notice.setIsActive(rs.getInt("is_active"));
					notice.setCategory(rs.getInt("category"));
					notice.setBoardId(rs.getInt("board_id"));
					notice.setCreateTime(rs.getTimestamp("create_time"));
					notice.setUpdateTime(rs.getTimestamp("update_time"));
					return notice;
				}
			} else if ("t_message".equals(tableName)) {
				String sql = "SELECT * FROM t_message WHERE id = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, id);
				rs = pstmt.executeQuery();
				
				if (rs.next()) {
					Message message = new Message();
					message.setId(rs.getInt("id"));
					message.setSenderId(rs.getInt("sender_id"));
					message.setReceiverId(rs.getInt("receiver_id"));
					message.setTitle(rs.getString("title"));
					message.setContent(rs.getString("content"));
					message.setIsRead(rs.getInt("is_read"));
					message.setSendTime(rs.getTimestamp("send_time"));
					return message;
				}
			} else if ("t_board".equals(tableName)) {
				String sql = "SELECT * FROM t_board WHERE id = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, id);
				rs = pstmt.executeQuery();
				
				if (rs.next()) {
					Board board = new Board();
					board.setId(rs.getInt("id"));
					board.setName(rs.getString("name"));
					board.setDescription(rs.getString("description"));
					board.setModeratorId(rs.getInt("moderator_id"));
					if (rs.wasNull()) {
						board.setModeratorId(null);
					}
					board.setCreateTime(rs.getTimestamp("create_time"));
					board.setSortOrder(rs.getInt("sort_order"));
					return board;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(conn, pstmt, rs);
		}
		return null;
	}

	public static boolean update(String tableName, MyTableItem tableItem) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtil.getConnection();
			if (tableItem instanceof User) {
				User user = (User) tableItem;
				String sql = "UPDATE t_user SET account=?, password=?, user_name=?, age=?, email=?, role=?, failed_attempts=?, locked_time=? WHERE id=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, user.getAccount());
				pstmt.setString(2, user.getPassword());
				pstmt.setString(3, user.getUserName());
				pstmt.setInt(4, user.getAge());
				pstmt.setString(5, user.getEmail());
				pstmt.setInt(6, user.getRole());
				pstmt.setInt(7, user.getFailedAttempts());
				pstmt.setTimestamp(8, user.getLockedTime());
				pstmt.setInt(9, user.getId());
				return pstmt.executeUpdate() > 0;
			} else if (tableItem instanceof Article) {
				Article article = (Article) tableItem;
				String sql = "UPDATE t_article SET title=?, content=?, user_id=?, board_id=?, is_top=?, remark_num=?, hit_num=?, create_time=?, last_remark_time=? WHERE id=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, article.getTitle());
				pstmt.setString(2, article.getContent());
				pstmt.setInt(3, article.getUserId());
				pstmt.setInt(4, article.getBoardId());
				pstmt.setInt(5, article.getIsTop());
				pstmt.setInt(6, article.getRemarkNum());
				pstmt.setInt(7, article.getHitNum());
				pstmt.setTimestamp(8, article.getCreateTime());
				pstmt.setTimestamp(9, article.getLastRemarkTime());
				pstmt.setInt(10, article.getId());
				return pstmt.executeUpdate() > 0;
			} else if (tableItem instanceof Remark) {
				Remark remark = (Remark) tableItem;
				String sql = "UPDATE t_remark SET article_id=?, user_id=?, remark=?, remark_time=? WHERE id=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, remark.getArticleId());
				pstmt.setInt(2, remark.getUserId());
				pstmt.setString(3, remark.getRemark());
				pstmt.setTimestamp(4, remark.getRemarkTime());
				pstmt.setInt(5, remark.getId());
				return pstmt.executeUpdate() > 0;
			} else if (tableItem instanceof Notice) {
				Notice notice = (Notice) tableItem;
				String sql = "UPDATE t_notice SET title=?, content=?, is_active=?, category=?, board_id=?, update_time=? WHERE id=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, notice.getTitle());
				pstmt.setString(2, notice.getContent());
				pstmt.setInt(3, notice.getIsActive());
				pstmt.setInt(4, notice.getCategory());
				pstmt.setInt(5, notice.getBoardId());
				pstmt.setTimestamp(6, notice.getUpdateTime());
				pstmt.setInt(7, notice.getId());
				return pstmt.executeUpdate() > 0;
			} else if (tableItem instanceof Message) {
				Message message = (Message) tableItem;
				String sql = "UPDATE t_message SET sender_id=?, receiver_id=?, title=?, content=?, is_read=?, send_time=? WHERE id=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, message.getSenderId());
				pstmt.setInt(2, message.getReceiverId());
				pstmt.setString(3, message.getTitle());
				pstmt.setString(4, message.getContent());
				pstmt.setInt(5, message.getIsRead());
				pstmt.setTimestamp(6, message.getSendTime());
				pstmt.setInt(7, message.getId());
				return pstmt.executeUpdate() > 0;
			} else if (tableItem instanceof Board) {
				Board board = (Board) tableItem;
				String sql = "UPDATE t_board SET name=?, description=?, moderator_id=?, sort_order=? WHERE id=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, board.getName());
				pstmt.setString(2, board.getDescription());
				if (board.getModeratorId() != null) {
					pstmt.setInt(3, board.getModeratorId());
				} else {
					pstmt.setNull(3, java.sql.Types.INTEGER);
				}
				pstmt.setInt(4, board.getSortOrder());
				pstmt.setInt(5, board.getId());
				return pstmt.executeUpdate() > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(conn, pstmt);
		}
		return false;
	}

	public static boolean delete(String tableName, Integer id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtil.getConnection();
			if ("t_user".equals(tableName)) {
				String sql = "DELETE FROM t_user WHERE id = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, id);
				return pstmt.executeUpdate() > 0;
			} else if ("t_article".equals(tableName)) {
				String sql = "DELETE FROM t_article WHERE id = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, id);
				return pstmt.executeUpdate() > 0;
			} else if ("t_remark".equals(tableName)) {
				String sql = "DELETE FROM t_remark WHERE id = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, id);
				return pstmt.executeUpdate() > 0;
			} else if ("t_notice".equals(tableName)) {
				String sql = "DELETE FROM t_notice WHERE id = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, id);
				return pstmt.executeUpdate() > 0;
			} else if ("t_message".equals(tableName)) {
				String sql = "DELETE FROM t_message WHERE id = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, id);
				return pstmt.executeUpdate() > 0;
			} else if ("t_board".equals(tableName)) {
				String sql = "DELETE FROM t_board WHERE id = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, id);
				return pstmt.executeUpdate() > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(conn, pstmt);
		}
		return false;
	}
	
	public static boolean deleteByField(String tableName, String fieldName, Integer fieldValue) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtil.getConnection();
			String sql = "DELETE FROM " + tableName + " WHERE " + fieldName + " = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, fieldValue);
			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(conn, pstmt);
		}
		return false;
	}

	public static List list(String tableName) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBUtil.getConnection();
			if ("t_user".equals(tableName)) {
				List<User> userList = new ArrayList<>();
				String sql = "SELECT * FROM t_user";
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					User user = new User();
					user.setId(rs.getInt("id"));
					user.setAccount(rs.getString("account"));
					user.setPassword(rs.getString("password"));
					user.setUserName(rs.getString("user_name"));
					user.setAge(rs.getInt("age"));
					user.setEmail(rs.getString("email"));
					user.setRole(rs.getInt("role"));
					user.setFailedAttempts(rs.getInt("failed_attempts"));
					user.setLockedTime(rs.getTimestamp("locked_time"));
					userList.add(user);
				}
				return userList.isEmpty() ? null : userList;
			} else if ("t_article".equals(tableName)) {
				List<Article> articleList = new ArrayList<>();
				String sql = "SELECT * FROM t_article ORDER BY is_top DESC, create_time DESC";
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					Article article = new Article();
					article.setId(rs.getInt("id"));
					article.setTitle(rs.getString("title"));
					article.setContent(rs.getString("content"));
					article.setUserId(rs.getInt("user_id"));
					article.setBoardId(rs.getInt("board_id"));
					article.setIsTop(rs.getInt("is_top"));
					article.setRemarkNum(rs.getInt("remark_num"));
					article.setHitNum(rs.getInt("hit_num"));
					article.setCreateTime(rs.getTimestamp("create_time"));
					article.setLastRemarkTime(rs.getTimestamp("last_remark_time"));
					articleList.add(article);
				}
				return articleList.isEmpty() ? null : articleList;
			} else if ("t_remark".equals(tableName)) {
				List<Remark> remarkList = new ArrayList<>();
				String sql = "SELECT * FROM t_remark ORDER BY remark_time DESC";
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					Remark remark = new Remark();
					remark.setId(rs.getInt("id"));
					remark.setArticleId(rs.getInt("article_id"));
					remark.setUserId(rs.getInt("user_id"));
					remark.setRemark(rs.getString("remark"));
					remark.setRemarkTime(rs.getTimestamp("remark_time"));
					remarkList.add(remark);
				}
				return remarkList.isEmpty() ? null : remarkList;
			} else if ("t_notice".equals(tableName)) {
				List<Notice> noticeList = new ArrayList<>();
				String sql = "SELECT * FROM t_notice ORDER BY create_time DESC";
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					Notice notice = new Notice();
					notice.setId(rs.getInt("id"));
					notice.setTitle(rs.getString("title"));
					notice.setContent(rs.getString("content"));
					notice.setIsActive(rs.getInt("is_active"));
					notice.setCategory(rs.getInt("category"));
					notice.setBoardId(rs.getInt("board_id"));
					notice.setCreateTime(rs.getTimestamp("create_time"));
					notice.setUpdateTime(rs.getTimestamp("update_time"));
					noticeList.add(notice);
				}
				return noticeList.isEmpty() ? null : noticeList;
			} else if ("t_board".equals(tableName)) {
				List<Board> boardList = new ArrayList<>();
				String sql = "SELECT * FROM t_board ORDER BY sort_order ASC, create_time DESC";
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					Board board = new Board();
					board.setId(rs.getInt("id"));
					board.setName(rs.getString("name"));
					board.setDescription(rs.getString("description"));
					board.setModeratorId(rs.getInt("moderator_id"));
					if (rs.wasNull()) {
						board.setModeratorId(null);
					}
					board.setCreateTime(rs.getTimestamp("create_time"));
					board.setSortOrder(rs.getInt("sort_order"));
					boardList.add(board);
				}
				return boardList.isEmpty() ? null : boardList;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(conn, pstmt, rs);
		}
		return null;
	}

	public static List select(String tableName, String tableColName, Object tableColValue, String operator) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBUtil.getConnection();
			if ("t_user".equals(tableName)) {
				List<User> userList = new ArrayList<>();
				String sql = "SELECT * FROM t_user WHERE " + tableColName + " = ?";
				pstmt = conn.prepareStatement(sql);
				
				if (tableColValue instanceof Integer) {
					pstmt.setInt(1, (Integer) tableColValue);
				} else {
					pstmt.setString(1, tableColValue.toString());
				}
				
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					User user = new User();
					user.setId(rs.getInt("id"));
					user.setAccount(rs.getString("account"));
					user.setPassword(rs.getString("password"));
					user.setUserName(rs.getString("user_name"));
					user.setAge(rs.getInt("age"));
					user.setEmail(rs.getString("email"));
					user.setRole(rs.getInt("role"));
					user.setFailedAttempts(rs.getInt("failed_attempts"));
					user.setLockedTime(rs.getTimestamp("locked_time"));
					userList.add(user);
				}
				return userList.isEmpty() ? null : userList;
			} else if ("t_article".equals(tableName)) {
				List<Article> articleList = new ArrayList<>();
				String sql = "SELECT * FROM t_article WHERE " + tableColName + " = ? ORDER BY is_top DESC, create_time DESC";
				pstmt = conn.prepareStatement(sql);
				
				if (tableColValue instanceof Integer) {
					pstmt.setInt(1, (Integer) tableColValue);
				} else {
					pstmt.setString(1, tableColValue.toString());
				}
				
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					Article article = new Article();
					article.setId(rs.getInt("id"));
					article.setTitle(rs.getString("title"));
					article.setContent(rs.getString("content"));
					article.setUserId(rs.getInt("user_id"));
					article.setBoardId(rs.getInt("board_id"));
					article.setIsTop(rs.getInt("is_top"));
					article.setRemarkNum(rs.getInt("remark_num"));
					article.setHitNum(rs.getInt("hit_num"));
					article.setCreateTime(rs.getTimestamp("create_time"));
					article.setLastRemarkTime(rs.getTimestamp("last_remark_time"));
					articleList.add(article);
				}
				return articleList.isEmpty() ? null : articleList;
			} else if ("t_remark".equals(tableName)) {
				List<Remark> remarkList = new ArrayList<>();
				String sql = "SELECT * FROM t_remark WHERE " + tableColName + " = ? ORDER BY remark_time DESC";
				pstmt = conn.prepareStatement(sql);
				
				if (tableColValue instanceof Integer) {
					pstmt.setInt(1, (Integer) tableColValue);
				} else {
					pstmt.setString(1, tableColValue.toString());
				}
				
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					Remark remark = new Remark();
					remark.setId(rs.getInt("id"));
					remark.setArticleId(rs.getInt("article_id"));
					remark.setUserId(rs.getInt("user_id"));
					remark.setRemark(rs.getString("remark"));
					remark.setRemarkTime(rs.getTimestamp("remark_time"));
					remarkList.add(remark);
				}
				return remarkList.isEmpty() ? null : remarkList;
			} else if ("t_message".equals(tableName)) {
				List<Message> messageList = new ArrayList<>();
				String sql = "SELECT * FROM t_message WHERE " + tableColName + " = ? ORDER BY send_time DESC";
				pstmt = conn.prepareStatement(sql);
				
				if (tableColValue instanceof Integer) {
					pstmt.setInt(1, (Integer) tableColValue);
				} else {
					pstmt.setString(1, tableColValue.toString());
				}
				
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					Message message = new Message();
					message.setId(rs.getInt("id"));
					message.setSenderId(rs.getInt("sender_id"));
					message.setReceiverId(rs.getInt("receiver_id"));
					message.setTitle(rs.getString("title"));
					message.setContent(rs.getString("content"));
					message.setIsRead(rs.getInt("is_read"));
					message.setSendTime(rs.getTimestamp("send_time"));
					messageList.add(message);
				}
				return messageList.isEmpty() ? null : messageList;
			} else if ("t_board".equals(tableName)) {
				List<Board> boardList = new ArrayList<>();
				String sql = "SELECT * FROM t_board WHERE " + tableColName + " = ? ORDER BY sort_order ASC, create_time DESC";
				pstmt = conn.prepareStatement(sql);
				
				if (tableColValue instanceof Integer) {
					pstmt.setInt(1, (Integer) tableColValue);
				} else {
					pstmt.setString(1, tableColValue.toString());
				}
				
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					Board board = new Board();
					board.setId(rs.getInt("id"));
					board.setName(rs.getString("name"));
					board.setDescription(rs.getString("description"));
					board.setModeratorId(rs.getInt("moderator_id"));
					if (rs.wasNull()) {
						board.setModeratorId(null);
					}
					board.setCreateTime(rs.getTimestamp("create_time"));
					board.setSortOrder(rs.getInt("sort_order"));
					boardList.add(board);
				}
				return boardList.isEmpty() ? null : boardList;
			} else if ("t_notice".equals(tableName)) {
				List<Notice> noticeList = new ArrayList<>();
				String sql = "SELECT * FROM t_notice WHERE " + tableColName + " = ? ORDER BY create_time DESC";
				pstmt = conn.prepareStatement(sql);
				
				if (tableColValue instanceof Integer) {
					pstmt.setInt(1, (Integer) tableColValue);
				} else {
					pstmt.setString(1, tableColValue.toString());
				}
				
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					Notice notice = new Notice();
					notice.setId(rs.getInt("id"));
					notice.setTitle(rs.getString("title"));
					notice.setContent(rs.getString("content"));
					notice.setIsActive(rs.getInt("is_active"));
					notice.setCategory(rs.getInt("category"));
					notice.setBoardId(rs.getInt("board_id"));
					notice.setCreateTime(rs.getTimestamp("create_time"));
					notice.setUpdateTime(rs.getTimestamp("update_time"));
					noticeList.add(notice);
				}
				return noticeList.isEmpty() ? null : noticeList;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.close(conn, pstmt, rs);
		}
		return null;
	}

	public static Object uniqueValue(String tableName, String tableColName, Object tableColValue, String operator) throws Exception {
		List list = MyDataBase.select(tableName, tableColName, tableColValue, operator);
		if (list != null && list.size() != 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public static boolean updateHitNum(String tableName, Integer id, int hitNum) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtil.getConnection();
			if ("t_article".equals(tableName)) {
				String sql = "UPDATE t_article SET hit_num = ? WHERE id = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, hitNum);
				pstmt.setInt(2, id);
				return pstmt.executeUpdate() > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(conn, pstmt);
		}
		return false;
	}

	public static boolean updateRemarkNum(String tableName, Integer id, int remarkNum, Timestamp lastRemarkTime) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtil.getConnection();
			if ("t_article".equals(tableName)) {
				String sql = "UPDATE t_article SET remark_num = ?, last_remark_time = ? WHERE id = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, remarkNum);
				pstmt.setTimestamp(2, lastRemarkTime);
				pstmt.setInt(3, id);
				return pstmt.executeUpdate() > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(conn, pstmt);
		}
		return false;
	}
	
	public static List searchArticles(String keyword) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBUtil.getConnection();
			List<Article> articleList = new ArrayList<>();
			String sql = "SELECT * FROM t_article WHERE title LIKE ? OR content LIKE ? ORDER BY is_top DESC, create_time DESC";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setString(2, "%" + keyword + "%");
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Article article = new Article();
				article.setId(rs.getInt("id"));
				article.setTitle(rs.getString("title"));
				article.setContent(rs.getString("content"));
				article.setUserId(rs.getInt("user_id"));
				article.setBoardId(rs.getInt("board_id"));
				article.setIsTop(rs.getInt("is_top"));
				article.setRemarkNum(rs.getInt("remark_num"));
				article.setHitNum(rs.getInt("hit_num"));
				article.setCreateTime(rs.getTimestamp("create_time"));
				article.setLastRemarkTime(rs.getTimestamp("last_remark_time"));
				articleList.add(article);
			}
			return articleList.isEmpty() ? null : articleList;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(conn, pstmt, rs);
		}
		return null;
	}
	
	public static List searchArticlesByBoard(Integer boardId, String keyword) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBUtil.getConnection();
			List<Article> articleList = new ArrayList<>();
			String sql = "SELECT * FROM t_article WHERE board_id = ? AND (title LIKE ? OR content LIKE ?) ORDER BY is_top DESC, create_time DESC";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardId);
			pstmt.setString(2, "%" + keyword + "%");
			pstmt.setString(3, "%" + keyword + "%");
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Article article = new Article();
				article.setId(rs.getInt("id"));
				article.setTitle(rs.getString("title"));
				article.setContent(rs.getString("content"));
				article.setUserId(rs.getInt("user_id"));
				article.setBoardId(rs.getInt("board_id"));
				article.setIsTop(rs.getInt("is_top"));
				article.setRemarkNum(rs.getInt("remark_num"));
				article.setHitNum(rs.getInt("hit_num"));
				article.setCreateTime(rs.getTimestamp("create_time"));
				article.setLastRemarkTime(rs.getTimestamp("last_remark_time"));
				articleList.add(article);
			}
			return articleList.isEmpty() ? null : articleList;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(conn, pstmt, rs);
		}
		return null;
	}
	
	public static boolean updateArticleTop(String tableName, Integer id, int isTop) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtil.getConnection();
			if ("t_article".equals(tableName)) {
				String sql = "UPDATE t_article SET is_top = ? WHERE id = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, isTop);
				pstmt.setInt(2, id);
				return pstmt.executeUpdate() > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(conn, pstmt);
		}
		return false;
	}
	
	public static void updateArticleBoard(Integer oldBoardId, Integer newBoardId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtil.getConnection();
			String sql = "UPDATE t_article SET board_id = ? WHERE board_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, newBoardId);
			pstmt.setInt(2, oldBoardId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(conn, pstmt);
		}
	}
	
	public static boolean updateUserFailedAttempts(String tableName, Integer id, int failedAttempts) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtil.getConnection();
			if ("t_user".equals(tableName)) {
				String sql = "UPDATE t_user SET failed_attempts = ? WHERE id = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, failedAttempts);
				pstmt.setInt(2, id);
				return pstmt.executeUpdate() > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(conn, pstmt);
		}
		return false;
	}
	
	public static boolean updateUserLockedTime(String tableName, Integer id, Timestamp lockedTime) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtil.getConnection();
			if ("t_user".equals(tableName)) {
				String sql = "UPDATE t_user SET locked_time = ?, failed_attempts = 0 WHERE id = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setTimestamp(1, lockedTime);
				pstmt.setInt(2, id);
				return pstmt.executeUpdate() > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(conn, pstmt);
		}
		return false;
	}
	
	public static boolean updateUserUnlock(String tableName, Integer id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtil.getConnection();
			if ("t_user".equals(tableName)) {
				String sql = "UPDATE t_user SET locked_time = NULL, failed_attempts = 0 WHERE id = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, id);
				return pstmt.executeUpdate() > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(conn, pstmt);
		}
		return false;
	}
	
	public static int countUnreadMessages(Integer receiverId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBUtil.getConnection();
			String sql = "SELECT COUNT(*) FROM t_message WHERE receiver_id = ? AND is_read = 0";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, receiverId);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(conn, pstmt, rs);
		}
		return 0;
	}
	
	public static boolean markMessageAsRead(Integer messageId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtil.getConnection();
			String sql = "UPDATE t_message SET is_read = 1 WHERE id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, messageId);
			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(conn, pstmt);
		}
		return false;
	}
	
	public static void initBoardTable() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtil.getConnection();
			
			String createBoardTable = "CREATE TABLE IF NOT EXISTS t_board (" +
					"id INT PRIMARY KEY AUTO_INCREMENT," +
					"name VARCHAR(100) NOT NULL UNIQUE," +
					"description TEXT," +
					"moderator_id INT NULL," +
					"create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
					"sort_order INT DEFAULT 0," +
					"FOREIGN KEY (moderator_id) REFERENCES t_user(id)" +
					") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci";
			pstmt = conn.prepareStatement(createBoardTable);
			pstmt.executeUpdate();
			
			try {
				String addBoardId = "ALTER TABLE t_article ADD COLUMN board_id INT NOT NULL DEFAULT 1 AFTER user_id";
				pstmt = conn.prepareStatement(addBoardId);
				pstmt.executeUpdate();
			} catch (SQLException e) {
			}
			
			try {
				String addForeignKey = "ALTER TABLE t_article ADD CONSTRAINT fk_article_board FOREIGN KEY (board_id) REFERENCES t_board(id)";
				pstmt = conn.prepareStatement(addForeignKey);
				pstmt.executeUpdate();
			} catch (SQLException e) {
			}
			
			try {
				String updateArticles = "UPDATE t_article SET board_id = 1 WHERE board_id IS NULL OR board_id = 0";
				pstmt = conn.prepareStatement(updateArticles);
				pstmt.executeUpdate();
			} catch (SQLException e) {
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(conn, pstmt);
		}
	}
	
	public static void initNoticeTable() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtil.getConnection();
			
			try {
				String addCategory = "ALTER TABLE t_notice ADD COLUMN category INT DEFAULT 0 AFTER is_active";
				pstmt = conn.prepareStatement(addCategory);
				pstmt.executeUpdate();
			} catch (SQLException e) {
			}
			
			try {
				String addBoardId = "ALTER TABLE t_notice ADD COLUMN board_id INT DEFAULT 0 AFTER category";
				pstmt = conn.prepareStatement(addBoardId);
				pstmt.executeUpdate();
			} catch (SQLException e) {
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(conn, pstmt);
		}
	}
	
	public static void initNewTables() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtil.getConnection();
			
			String createLoginLogTable = "CREATE TABLE IF NOT EXISTS t_login_log (" +
					"id INT PRIMARY KEY AUTO_INCREMENT," +
					"user_id INT NOT NULL," +
					"login_ip VARCHAR(50) NOT NULL," +
					"login_time DATETIME DEFAULT CURRENT_TIMESTAMP," +
					"logout_time DATETIME NULL," +
					"login_status INT NOT NULL," +
					"failure_reason VARCHAR(200)," +
					"INDEX idx_user (user_id)," +
					"INDEX idx_time (login_time)," +
					"FOREIGN KEY (user_id) REFERENCES t_user(id) ON DELETE CASCADE" +
					") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci";
			pstmt = conn.prepareStatement(createLoginLogTable);
			pstmt.executeUpdate();
			
			String createOnlineSessionTable = "CREATE TABLE IF NOT EXISTS t_online_session (" +
					"id INT PRIMARY KEY AUTO_INCREMENT," +
					"session_id VARCHAR(100) NOT NULL UNIQUE," +
					"user_id INT NULL," +
					"ip_address VARCHAR(50) NOT NULL," +
					"is_guest INT DEFAULT 1," +
					"last_active_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP," +
					"created_time DATETIME DEFAULT CURRENT_TIMESTAMP," +
					"expired_at DATETIME NULL," +
					"INDEX idx_session (session_id)," +
					"INDEX idx_user (user_id)," +
					"INDEX idx_guest (is_guest)," +
					"INDEX idx_last_active (last_active_time)," +
					"FOREIGN KEY (user_id) REFERENCES t_user(id) ON DELETE CASCADE" +
					") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci";
			pstmt = conn.prepareStatement(createOnlineSessionTable);
			pstmt.executeUpdate();
			
			String createUserBlacklistTable = "CREATE TABLE IF NOT EXISTS t_user_blacklist (" +
					"id INT PRIMARY KEY AUTO_INCREMENT," +
					"user_id INT NOT NULL UNIQUE," +
					"reason VARCHAR(500)," +
					"banned_by INT NULL," +
					"banned_at DATETIME DEFAULT CURRENT_TIMESTAMP," +
					"unbanned_at DATETIME NULL," +
					"INDEX idx_user (user_id)," +
					"FOREIGN KEY (user_id) REFERENCES t_user(id) ON DELETE CASCADE," +
					"FOREIGN KEY (banned_by) REFERENCES t_user(id) ON DELETE SET NULL" +
					") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci";
			pstmt = conn.prepareStatement(createUserBlacklistTable);
			pstmt.executeUpdate();
			
			String createIpBlacklistTable = "CREATE TABLE IF NOT EXISTS t_ip_blacklist (" +
					"id INT PRIMARY KEY AUTO_INCREMENT," +
					"ip_address VARCHAR(50) NOT NULL UNIQUE," +
					"reason VARCHAR(500)," +
					"banned_by INT NULL," +
					"banned_at DATETIME DEFAULT CURRENT_TIMESTAMP," +
					"unbanned_at DATETIME NULL," +
					"INDEX idx_ip (ip_address)," +
					"FOREIGN KEY (banned_by) REFERENCES t_user(id) ON DELETE SET NULL" +
					") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci";
			pstmt = conn.prepareStatement(createIpBlacklistTable);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(conn, pstmt);
		}
	}
}