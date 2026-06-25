package cn.maiba;

import java.sql.Timestamp;

public class Message extends MyTableItem {
	public static final String TABLE_NAME = "t_message";
	
	Integer senderId;
	Integer receiverId;
	String title;
	String content;
	int isRead;
	Timestamp sendTime;
	
	public Message() {
		senderId = 0;
		receiverId = 0;
		title = "";
		content = "";
		isRead = 0;
		sendTime = new Timestamp(System.currentTimeMillis());
	}

	public Integer getSenderId() {
		return senderId;
	}

	public void setSenderId(Integer senderId) {
		this.senderId = senderId;
	}

	public Integer getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(Integer receiverId) {
		this.receiverId = receiverId;
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

	public int getIsRead() {
		return isRead;
	}

	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}

	public boolean isRead() {
		return isRead == 1;
	}

	public Timestamp getSendTime() {
		return sendTime;
	}

	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}
}