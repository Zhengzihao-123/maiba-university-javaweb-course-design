package cn.maiba;

import java.sql.Timestamp;

public class User extends MyTableItem {
	public static final String TABLE_NAME="t_user";
	
	// 使用 RoleConstants 中的常量定义，保持一致性
	// 角色定义：0=超级管理员, 1=普通用户, 2=版主
	public static final int ROLE_SUPER_ADMIN = 0;
	public static final int ROLE_USER = 1;
	public static final int ROLE_MODERATOR = 2;
	
	String userName;

	String account;

	String password;
	
	int age;
	
	String email;
	
	int role;
	
	int failedAttempts;
	
	Timestamp lockedTime;
	
	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public int getFailedAttempts() {
		return failedAttempts;
	}

	public void setFailedAttempts(int failedAttempts) {
		this.failedAttempts = failedAttempts;
	}

	public Timestamp getLockedTime() {
		return lockedTime;
	}

	public void setLockedTime(Timestamp lockedTime) {
		this.lockedTime = lockedTime;
	}

	public boolean isAdmin() {
		return role == ROLE_SUPER_ADMIN;
	}
	
	public boolean isModerator() {
		return role == ROLE_MODERATOR;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}