package com.travel.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.travel.mail.MailSenderInfo;
import com.travel.mail.SimpleMailSender;
import com.travel.pojo.MailOrder;
import com.travel.pojo.Mailconfig;

@Service
public class ServiceUtil {
	private static Logger logger = LoggerFactory.getLogger(ServiceUtil.class);

	public static Boolean sendActiveMail(String email, Mailconfig mc) {
		boolean flag = false;
		StringBuffer sf = new StringBuffer();
		sf.append("感谢您成为中美机票网的注册用户，请");
		sf.append("<a href=\"http://www.e-traveltochina.com/v1/user/activemail.json?email=");
		sf.append(email);
		sf.append("\">");
		sf.append(" <FONT   face=\"MS   UI   Gothic\"   size=\"3\"><b>点击这里</b></FONT>");
		sf.append("</a>");
		sf.append("激活账号，24小时生效，否则重新验证，请尽快激活！<br>");
		SimpleMailSender sms = new SimpleMailSender();
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost(mc.getServer());

		mailInfo.setToAddress(email);
		mailInfo.setPassword(mc.getPassword());
		mailInfo.setSubject("用户激活");
		mailInfo.setContent(sf.toString());
		mailInfo.setFromAddress(mc.getFrom());
		mailInfo.setUsername(mc.getUsername());
		flag = sms.sendHtmlMail(mailInfo);
		return flag;
	}

	public static Boolean sendFindPwdMail(String email, Mailconfig mc,
			String password) {

		boolean flag = false;
		StringBuffer sf = new StringBuffer();
		sf.append("重置密码成功，您的新密码是" + password + ",请妥善保管。");

		SimpleMailSender sms = new SimpleMailSender();
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost(mc.getServer());

		mailInfo.setToAddress(email);
		mailInfo.setPassword(mc.getPassword());
		mailInfo.setSubject("密码重置");
		mailInfo.setContent(sf.toString());
		mailInfo.setFromAddress(mc.getFrom());
		mailInfo.setUsername(mc.getUsername());
		flag = sms.sendHtmlMail(mailInfo);
		return flag;

	}

	public static Boolean sendOrderMail(MailOrder mo, Mailconfig mc) {
		boolean flag = false;
		StringBuffer sf = new StringBuffer();
		sf.append("您好，您订阅的<font color='blue'>");
		sf.append(mo.getLeave_date().split("\\ ")[0]);
		sf.append("日</font>从<font color='blue'>");
		sf.append(mo.getLeave_city());
		sf.append("</font>出发，<font color='blue'>");
		sf.append(mo.getBack_date().split("\\ ")[0]);
		sf.append("日</font>从<font color='blue'>");
		sf.append(mo.getDest_city());
		sf.append("</font>返程的机票已经有票源了，请访问");
		sf.append("<a style='color:blue' href=\"http://www.e-traveltochina.com\"");

		sf.append("\">");
		sf.append("美中机票网");
		sf.append("</a>查看");
		SimpleMailSender sms = new SimpleMailSender();
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost(mc.getServer());

		mailInfo.setToAddress(mo.getEmail());
		mailInfo.setPassword(mc.getPassword());
		mailInfo.setSubject("美中机票网机票订阅");
		mailInfo.setContent(sf.toString());
		mailInfo.setFromAddress(mc.getFrom());
		mailInfo.setUsername(mc.getUsername());
		flag = sms.sendHtmlMail(mailInfo);
		return flag;
	}

	public static String getRedisHost() {
		String host = "";
		try {
			host = PropFactory.getProp().getProperty(ConstantUtil.REDIS_HOST);
		} catch (Exception e) {
			e.printStackTrace();
			return host;
		}
		return host;
	}

	public static Integer getRedisPort() {
		Integer port = 0;
		try {
			port = Integer.parseInt(PropFactory.getProp().getProperty(
					ConstantUtil.REDIS_PORT));
		} catch (Exception e) {
			e.printStackTrace();
			return port;
		}
		return port;
	}

	public static String getPythonPath() {
		String path = "";
		try {
			path = PropFactory.getProp().getProperty(ConstantUtil.PYTHON_PATH);
		} catch (Exception e) {
			e.printStackTrace();
			return path;
		}
		return path;
	}

	public static int callPython(String pythonPath, String prefix,String mode,String culture) {
		Process process = null;
		Worker worker = null;
		String cmd = "python " + pythonPath + " --prefix=\""+ prefix +"\" --mode="+mode + " --culture="+culture;
		try {
			process = Runtime.getRuntime().exec(
					 new String[]{"/bin/sh", "-c", cmd}
					);
			worker = new Worker(process);
			worker.start();
			worker.join(10 * 1000);
			if (worker.exit != null) {
				logger.info("return status :" + worker.exit);
				return worker.exit;
			} else {
				logger.info("time out time :" + 10 * 1000 / 1000 + "s");
			}
		} catch (IOException e) {
			logger.error(e.getLocalizedMessage());
		} catch (InterruptedException e) {
			logger.error(e.getLocalizedMessage());
		} finally {
			process.destroy();
		}
		return 0;
	}

	public static int callPythonAir(String pythonPath, String mode,
			String prefix) {
		Process process = null;
		Worker worker = null;
		try {
			process = Runtime.getRuntime().exec(
					"python " + pythonPath + " --mode=" + mode + " --prefix="
							+ prefix);
			worker = new Worker(process);
			worker.start();
			worker.join(10 * 1000);
			if (worker.exit != null) {
				logger.info("return status :" + worker.exit);
				return worker.exit;
			} else {
				logger.info("time out time :" + 10 * 1000 / 1000 + "s");
			}
		} catch (IOException e) {
			logger.error(e.getLocalizedMessage());
		} catch (InterruptedException e) {
			logger.error(e.getLocalizedMessage());
		} finally {
			process.destroy();
		}
		return 0;
	}

	public static void main(String[] args) {
		System.out.println(getRedisHost());
	}
}
