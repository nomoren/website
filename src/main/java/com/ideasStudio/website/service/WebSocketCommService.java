package com.ideasStudio.website.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.ideasStudio.website.mapper.OrderMapper;

/**
 * webSocket通讯业务层实现
 * @author Administrator
 *
 */
@Component
@ServerEndpoint("/webSocket")
public class WebSocketCommService {
	
	private static OrderMapper ordermapper;
	
	@Autowired
	public void setOrderMapper(OrderMapper ordermapper) {
		this.ordermapper = ordermapper;
	}
	
	private Session session;
	private String orderNo;
	
	public String getOrderNo() {
		return orderNo;
	}
	
	private static CopyOnWriteArrayList<WebSocketCommService> websockets = new CopyOnWriteArrayList<>();
	private static Map<String,String> map  = new HashMap<>();
	
	@OnOpen
	public void onOpen(Session session){
		this.session = session;
		String s = session.getQueryString();
		String orderNo = s.split("=")[1];
		String orderNo_database = ordermapper.queryOrderNo(orderNo);
		if(orderNo_database==null){			//查询前端传输过来的单号是否存在
			String message = "订单编号异常！";
			send("Error",orderNo,message);	//发送订单编号异常消息
		}
		this.orderNo = ordermapper.getOrderNo(orderNo);	
		websockets.add(this);
	}
	
	@OnClose
	public void onClose(){
		websockets.remove(this);
	}
	
	public void send(String payStatus,String orderNo,String msg){
		System.out.println("支付完毕发送消息");
		Map<String,String> message = new HashMap<String,String>();
		message.put("payStatus", payStatus);
		message.put("orderNo", orderNo);
		message.put("msg", msg);
		String line = JSONObject.toJSON(message).toString();
		for(WebSocketCommService websocket : websockets){
			System.out.println("orderNo"+orderNo);
			System.out.println("websocket.getOrderNo():"+websocket.getOrderNo());
			if(orderNo.equals(websocket.getOrderNo())){
				try {
					websocket.session.getBasicRemote().sendText(line);
				} catch (Exception e) {
					System.out.println("发送消息异常");
					e.printStackTrace();
				}
			}else{
				System.out.println("消息发送失败");
			}
		}
	}
}
