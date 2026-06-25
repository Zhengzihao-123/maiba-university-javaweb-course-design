package cn.maiba;

import java.sql.Timestamp;


public class Article extends MyTableItem {
	public static final String TABLE_NAME="t_article"; 
	
	String title;
	String content;
	Integer userId;
	Integer boardId;
	int isTop;
	int remarkNum;
	int hitNum;
	Timestamp createTime;
	Timestamp lastRemarkTime;

	public Article(){
		title = "";
		content = "";
		userId = 0;
		boardId = 1;
		isTop = 0;
		remarkNum = 0;
		hitNum = 0 ;
		createTime = new Timestamp(System.currentTimeMillis());
		lastRemarkTime = new Timestamp(System.currentTimeMillis());
	}
	
	public void IncreaseRemarkNum(){
		remarkNum ++ ;
	}
	
	public void IncreaseHitNum(){
		hitNum ++ ;
	}
	
	public int getIsTop() {
		return isTop;
	}

	public void setIsTop(int isTop) {
		this.isTop = isTop;
	}

	public boolean isTop() {
		return isTop == 1;
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

	public Integer getBoardId() {
		return boardId;
	}

	public void setBoardId(Integer boardId) {
		this.boardId = boardId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
}