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
				String sql = "INSERT INTO t_user(id, account, password, user_name, age, email) VALUES (?, ?, ?, ?, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, user.getId());
				pstmt.setString(2, user.getAccount());
				pstmt.setString(3, user.getPassword());
				pstmt.setString(4, user.getUserName());
				pstmt.setInt(5, user.getAge());
				pstmt.setString(6, user.getEmail());
				return pstmt.executeUpdate() > 0;
			} else if (tableItem instanceof Article) {
				Article article = (Article) tableItem;
				String sql = "INSERT INTO t_article(id, title, content, user_id, remark_num, hit_num, create_time, last_remark_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, article.getId());
				pstmt.setString(2, article.getTitle());
				pstmt.setString(3, article.getContent());
				pstmt.setInt(4, article.getUserId());
				pstmt.setInt(5, article.getRemarkNum());
				pstmt.setInt(6, article.getHitNum());
				pstmt.setTimestamp(7, article.getCreateTime());
				pstmt.setTimestamp(8, article.getLastRemarkTime());
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
				String sql = "UPDATE t_user SET account=?, password=?, user_name=?, age=?, email=? WHERE id=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, user.getAccount());
				pstmt.setString(2, user.getPassword());
				pstmt.setString(3, user.getUserName());
				pstmt.setInt(4, user.getAge());
				pstmt.setString(5, user.getEmail());
				pstmt.setInt(6, user.getId());
				return pstmt.executeUpdate() > 0;
			} else if (tableItem instanceof Article) {
				Article article = (Article) tableItem;
				String sql = "UPDATE t_article SET title=?, content=?, user_id=?, remark_num=?, hit_num=?, create_time=?, last_remark_time=? WHERE id=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, article.getTitle());
				pstmt.setString(2, article.getContent());
				pstmt.setInt(3, article.getUserId());
				pstmt.setInt(4, article.getRemarkNum());
				pstmt.setInt(5, article.getHitNum());
				pstmt.setTimestamp(6, article.getCreateTime());
				pstmt.setTimestamp(7, article.getLastRemarkTime());
				pstmt.setInt(8, article.getId());
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
			}
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
					userList.add(user);
				}
				return userList.isEmpty() ? null : userList;
			} else if ("t_article".equals(tableName)) {
				List<Article> articleList = new ArrayList<>();
				String sql = "SELECT * FROM t_article ORDER BY create_time DESC";
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					Article article = new Article();
					article.setId(rs.getInt("id"));
					article.setTitle(rs.getString("title"));
					article.setContent(rs.getString("content"));
					article.setUserId(rs.getInt("user_id"));
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
					userList.add(user);
				}
				return userList.isEmpty() ? null : userList;
			} else if ("t_article".equals(tableName)) {
				List<Article> articleList = new ArrayList<>();
				String sql = "SELECT * FROM t_article WHERE " + tableColName + " = ? ORDER BY create_time DESC";
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
}
