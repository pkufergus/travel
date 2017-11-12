package com.travel.mail;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleMailSender {


	private static final Logger logger = LoggerFactory
			.getLogger(SimpleMailSender.class);

	/**
	 * 以HTML格式发送邮件
	 * 
	 * @param mailInfo
	 *            待发送的邮件信息
	 */
	public boolean sendHtmlMail(final MailSenderInfo mailInfo) {
		// 判断是否需要身份认证
		Properties pro = mailInfo.getProperties();
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getInstance(pro, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailInfo.getUsername(), mailInfo.getPassword());
			}
		});
		try {
			String nick = javax.mail.internet.MimeUtility.encodeText("美中机票网");
			 
			// 根据session创建一个邮件消息
			Message mailMessage = new MimeMessage(sendMailSession);
			// 创建邮件发送者地址
			Address from = new InternetAddress(nick+"<"+mailInfo.getFromAddress()+">");
			// 设置邮件消息的发送者
			
			mailMessage.setFrom(from);
			// 创建邮件的接收者地址，并设置到邮件消息中
			Address to = new InternetAddress(mailInfo.getToAddress());
			// Message.RecipientType.TO属性表示接收者的类型为TO
			mailMessage.setRecipient(Message.RecipientType.TO, to);
			// 设置邮件消息的主题
			mailMessage.setSubject(mailInfo.getSubject());
			// 设置邮件消息发送的时间
			mailMessage.setSentDate(new Date());
			// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
			Multipart mainPart = new MimeMultipart();
			// 创建一个包含HTML内容的MimeBodyPart
			BodyPart html = new MimeBodyPart();
			// 设置HTML内容
			html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
			mainPart.addBodyPart(html);
			// 将MiniMultipart对象设置为邮件内容
			mailMessage.setContent(mainPart);
			
			// 发送邮件
			Transport.send(mailMessage);
			return true;
		} catch (MessagingException ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage(), ex);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}	
	
	public static void main(String[] args) {
		  StringBuffer sf=new StringBuffer();  
          sf.append("<a href=\"http://192.168.0.84:8088/ompxm/activateEmail.action?email=");  
          sf.append("123@sina.com");  
          sf.append("&validateCode=");  
          sf.append(true);  
          sf.append("\">");  
          sf.append(" <FONT   face=\"MS   UI   Gothic\"   size=\"3\"><b>点击这里</b></FONT>");  
          sf.append("</a>");  
          sf.append("激活账号，24小时生效，否则重新验证，请尽快激活！<br>"); 
		SimpleMailSender sms = new SimpleMailSender();
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("smtp.sina.com.cn");
		
		mailInfo.setToAddress("yafei.lv@pactera.com");
		mailInfo.setPassword("lvyafei916");
		mailInfo.setSubject("Hello World");
		mailInfo.setContent(sf.toString());
		mailInfo.setFromAddress("lvxia916@sina.com");
		mailInfo.setUsername("lvxia916@sina.com");
		sms.sendHtmlMail(mailInfo);
	}
}
