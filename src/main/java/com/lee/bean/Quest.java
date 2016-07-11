package com.lee.bean;

import java.io.Serializable;

public class Quest implements Serializable{
	private int id;
	private String stuNum;
	private String userName;
	private String quest_title;
	private String quest;
	private String sendTime;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStuNum() {
		return stuNum;
	}

	public void setStuNum(String stuNum) {
		this.stuNum = stuNum;
	}

	public String getQuest_title() {
		return quest_title;
	}

	public void setQuest_title(String quest_title) {
		this.quest_title = quest_title;
	}

	public String getQuest() {
		return quest;
	}

	public void setQuest(String quest) {
		this.quest = quest;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
}
