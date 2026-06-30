package cn.maiba;

import java.sql.Timestamp;

public class Notice extends MyTableItem {
	public static final String TABLE_NAME = "t_notice";
	
	public static final int CATEGORY_SYSTEM = 0;
	public static final int CATEGORY_BOARD = 1;
	
	String title;
	String content;
	int isActive;
	int category;
	int boardId;
	Timestamp createTime;
	Timestamp updateTime;
	
	public Notice() {
		title = "";
		content = "";
		isActive = 1;
		category = CATEGORY_SYSTEM;
		boardId = 0;
		createTime = new Timestamp(System.currentTimeMillis());
		updateTime = new Timestamp(System.currentTimeMillis());
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

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public boolean isActive() {
		return isActive == 1;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
}