package cn.maiba;

import java.sql.Timestamp;


public class Article extends MyTableItem {
	public static final String TABLE_NAME="t_article"; 
	
	String title;//文章标题
	String content;//文章内容
	Integer userId;//用户ID
	int remarkNum;//评论数量
	int hitNum;//点击数量
	Timestamp createTime;//创建时间
	Timestamp lastRemarkTime;//最后评论时间

	public Article(){
		title = "";
		content = "";
		userId = 0;
		remarkNum = 0;
		hitNum = 0 ;
		createTime = new Timestamp(System.currentTimeMillis());//当前时间
		lastRemarkTime = new Timestamp(System.currentTimeMillis());//当前时间
	}
	
	public void IncreaseRemarkNum(){
		remarkNum ++ ;
	}
	
	public void IncreaseHitNum(){
		hitNum ++ ;
	}
	
	public int getRemarkNum() {
		return remarkNum;
	}

	public void setRemarkNum(int remarkNum) {
		this.remarkNum = remarkNum;
	}

	public int getHitNum() {
		return hitNum;
	}

	public void setHitNum(int hitNum) {
		this.hitNum = hitNum;
	}

	public Timestamp getLastRemarkTime() {
		return lastRemarkTime;
	}

	public void setLastRemarkTime(Timestamp lastRemarkTime) {
		this.lastRemarkTime = lastRemarkTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
}
