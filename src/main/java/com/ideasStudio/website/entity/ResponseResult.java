package com.ideasStudio.website.entity;

import java.io.Serializable;

import org.apache.log4j.Logger;


/**
 * 页面AJAX请求响应数据包实体类
 * @author 覃远祠
 * @param <T>  泛型类型Data保存了ajax请求返回的数据
 */
public class ResponseResult<T> implements Serializable{
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 1L;
	
	Logger logger = Logger.getLogger(ResponseResult.class);
	private Integer status=200;			//ajax请求返回的结果200代表正常返回
	private String message;			//ajax请求返回的消息实体
	private T data;					//ajax请求返回的数据内容
	
	/**
	 * 整个构造器用于返回正确操作是返回的结果
	 */
	public ResponseResult() {
	}
	
	public ResponseResult(Integer status,String message) {
		this.status = status;
		this.message = message;
	}
	
	public ResponseResult(Integer status,Exception e) {
		this.status = status;
		logger.info(e);
		if(e.getMessage()==""||e.getMessage()==null){
			this.message=e.toString().substring(e.toString().indexOf(":")+1);
		}else{
			this.message = e.getMessage();
		}
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setMessage(RuntimeException e) {
		this.message = e.getMessage();
	}
	
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "ResponseResult [status=" + status + ", message=" + message + ", data=" + data + "]";
	}
}
