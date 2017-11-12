package com.travel.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

import com.travel.redis.RedisClient;
import com.travel.service.AjaxRequestService;
import com.travel.util.CheckCodeUtil;
import com.travel.util.ConstantUtil;
import com.travel.util.ServiceUtil;

@Service
public class AjaxRequestServiceImpl implements AjaxRequestService {
	private static Logger logger = LoggerFactory
			.getLogger(AjaxRequestServiceImpl.class);

	@Override
	public String getURLContent(String urlStr) {
		/** 网络的url地址 */
		URL url = null;
		/** http连接 */
		@SuppressWarnings("unused")
		HttpURLConnection httpConn = null;
		/**//** 输入流 */
		BufferedReader in = null;
		StringBuffer sb = new StringBuffer();
		try {
			url = new URL(urlStr);
			in = new BufferedReader(new InputStreamReader(url.openStream(),
					"UTF-8"));
			String str = null;
			while ((str = in.readLine()) != null) {
				sb.append(str);
			}
		} catch (Exception ex) {

		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
			}
		}
		String result = sb.toString();
		System.out.println(result);
		return result;
	}

	public String SendGET(String url) {
		String result = "";// 访问返回结果
		BufferedReader read = null;// 读取访问结果

		try {
			// 创建url
			URL realurl = new URL(url);
			// 打开连接
			URLConnection connection = realurl.openConnection();
			// 设置通用的请求属性
			connection
					.setRequestProperty("accept",
							"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			connection.setRequestProperty("Accept-Encoding",
					"gzip, deflate, sdch");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("Content-Type",
					"application/json; charset=utf-8");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段，获取到cookies等
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			read = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "UTF-8"));
			String line;// 循环读取
			while ((line = read.readLine()) != null) {
				result += line;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (read != null) {// 关闭流
				try {
					read.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return result;
	}

	@Override
	public Object autoComplete(String prefix) {
		String pythonPath = ServiceUtil.getPythonPath();
		Jedis redis = RedisClient.getRedisClient();
		logger.info("pythonPath=" + pythonPath);
		boolean flag = redis.exists(prefix);
		logger.info("是否存在值=" + flag);
		String content = "";
		String mode = ConstantUtil.CITY;
		String culture = CheckCodeUtil.isChineseChar(prefix);
		logger.info("culture============" + culture);
		if (flag) {
			content = redis.get(prefix);
		} else {
			ServiceUtil.callPython(pythonPath, prefix, mode, culture);
			content = redis.get(prefix);
		}

		logger.info("content====" + content);
		return content;
	}

	@Override
	public Object autoCompleteAir(String mode, String prefix) {
		String pythonPath = ServiceUtil.getPythonPath();
		Jedis redis = RedisClient.getRedisClient();
		logger.info("pythonPath=" + pythonPath);
		boolean flag = redis.exists(prefix);
		logger.info("是否存在值=" + flag);
		String content = "";
		if (flag) {
			content = redis.get("airline:" + prefix);
		} else {
			ServiceUtil.callPythonAir(pythonPath, mode, prefix);
			content = redis.get("airline:" + prefix);
		}

		logger.info("content====" + content);
		return content;
	}
}
