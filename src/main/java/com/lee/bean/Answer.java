package com.lee.bean;

import java.io.Serializable;

public class Answer implements Serializable{
	private int id;
	private String stuNum;
	private String userName;
	private String questid;
	private String answer_;
	private String answerTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStuNum() {
		return stuNum;
	}

	public void setStuNum(String stuNum) {
		this.stuNum = stuNum;
	}

	public String getQuestid() {
		return questid;
	}

	public void setQuestid(String questid) {
		this.questid = questid;
	}

	public String getAnswer_() {
		return answer_;
	}

	public void setAnswer_(String answer_) {
		this.answer_ = answer_;
	}

	public String getAnswerTime() {
		return answerTime;
	}

	public void setAnswerTime(String answerTime) {
		this.answerTime = answerTime;
	}
}
