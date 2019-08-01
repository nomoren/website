package com.ideasStudio.website.util;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.Test;

import com.ideasStudio.website.util.ex.SendEmailFailException;

/**
 *  @author 	覃远祠
 *	发送邮件工具类，在使用此工具类时，需先初始化toEmail、fromEmail、autorizationCode
 *	titleContent、contentEntity然后调用send方法即可，成功发送将返回true,发送失败则会抛出异常
 */
public class SendEmail
{
	private String toEmail;		//收件人邮件
	private String fromEmail;	//发件人邮件
	private String autorizationCode;  //QQ授权代码
	private String titleContent;  	  //标题内容
	private String contentEntity;	  //实体内容
	
	public String getToEmail() {
		return toEmail;
	}

	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}

	public String getFromEmail() {
		return fromEmail;
	}

	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	public String getAutorizationCode() {
		return autorizationCode;
	}

	public void setAutorizationCode(String autorizationCode) {
		this.autorizationCode = autorizationCode;
	}

	public String getTitleContent() {
		return titleContent;
	}


	public void setTitleContent(String titleContent) {
		this.titleContent = titleContent;
	}


	public String getContentEntity() {
		return contentEntity;
	}


	public void setContentEntity(String contentEntity) {
		this.contentEntity = contentEntity;
	}

	/**
	 * @return	成功发送邮件时返回true
	 * @throws SendEmailFailException  发送邮件失败是抛出的异常
	 */
   public boolean send() throws SendEmailFailException{
	  boolean success = true;		//设置默认发送成功为true;
      Properties properties = System.getProperties();// 获取系统属性
      properties.setProperty("mail.smtp.host", "smtp.qq.com");// 设置邮件服务器
      properties.put("mail.smtp.auth", "true");
      // 获取默认session对象
      Session session = Session.getDefaultInstance(properties,new Authenticator(){
        public PasswordAuthentication getPasswordAuthentication()
        {//"nahevrytwtwggcdb"
         return new PasswordAuthentication(fromEmail, autorizationCode); //发件人邮件用户名、授权码
        }
       });
      try{
         MimeMessage message = new MimeMessage(session); // 创建默认的 MimeMessage 对象
         message.setFrom(new InternetAddress(fromEmail));// Set From: 头部头字段
         message.addRecipient(Message.RecipientType.TO, // Set To: 头部头字段
                                  new InternetAddress(toEmail));
         message.setSubject(titleContent); // Set Subject: 头部头字段  标题
         message.setText(contentEntity);// 设置消息体		这是消息内容
         Transport.send(message); // 发送消息
         return success;
      }catch (Exception e) {
        throw new SendEmailFailException("邮件发送失败，请稍后重试!");
      }
   }
   
   @Test
   public void main() {
	   SendEmail sendemail = new SendEmail();
	   sendemail.setFromEmail("1396871994@qq.com");
	   sendemail.setToEmail("2681333056@qq.com");
	   sendemail.setAutorizationCode("nahevrytwtwggcdb");
	   sendemail.setTitleContent("标题测试内容");
	   sendemail.setContentEntity("邮件实体内容");
	   sendemail.send();
   }
}
