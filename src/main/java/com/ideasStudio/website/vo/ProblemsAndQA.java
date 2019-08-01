package com.ideasStudio.website.vo;

import java.util.List;

import com.ideasStudio.website.entity.QA;

/**
 * VO类问题和答案，用于在faq.html页面上显示问题答案
 * @author 覃远祠
 *
 */

public class ProblemsAndQA {
	
	private Integer id;  //问题板块的id
	private String problem; //问题板块描述 
	private List<QA> qalist;  //下面的所有的问题详情
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getProblem() {
		return problem;
	}
	public void setProblem(String problem) {
		this.problem = problem;
	}
	public List<QA> getQalist() {
		return qalist;
	}
	public void setQalist(List<QA> qalist) {
		this.qalist = qalist;
	}
	@Override
	public String toString() {
		return "ProblemsAndQA [id=" + id + ", problem=" + problem + ", qalist="
				+ qalist + "]";
	}
	
	
	
}
