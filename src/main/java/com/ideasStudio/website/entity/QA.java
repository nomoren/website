package com.ideasStudio.website.entity;

/**
 * 常见问题具体问题 实体类
 * @author 覃远祠
 */
public class QA {
	private Integer id;    //问题ID
	private String question; //问题内容
	private String answer;   //问题答案
	private Integer parentId;  //父级板块ID
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	@Override
	public String toString() {
		return "QA [id=" + id + ", question=" + question + ", answer=" + answer
				+ ", parentId=" + parentId + "]";
	}
	
	
}
