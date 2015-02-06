package com.wechat.client.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class JsonHttpRequestUtil {
	private Logger log = Logger.getLogger(JsonHttpRequestUtil.class);
	private final String basePath = Constants.APIBasePath;
	
	public JsonHttpRequestUtil(){}
	
	public String doGet(String accessUrl){
		HttpURLConnection connect = null;
		int code = 0;
		String msg = "";
		try {
			URL url = new URL(basePath + accessUrl);
			connect = (HttpURLConnection)url.openConnection();
			connect.setConnectTimeout(10000);
			connect.setRequestMethod("GET");
			connect.setRequestProperty("Content-type", "text/html");
			connect.setRequestProperty("Accept-Charset", "utf-8");
			connect.setRequestProperty("contentType", "utf-8");
			connect.connect();
			if(connect.getResponseCode() == 200){
				String json = readContent(connect.getInputStream());
				if(StringUtils.isNotEmpty(json)){
					json = json.replaceAll("\\\\", "").replaceAll("null", "\"\"").replaceAll("\"\"\"\"", "\"\"");
				}
				return json;
			}
			code = 500;
		} catch (MalformedURLException e) {
			msg = "不是正确的URL";
			log.error(msg, e);
		} catch (IOException e) {
			msg = "读取URL时发生错误";
			log.error(msg, e);
		}finally {
			if(connect != null){
				connect.disconnect();
			}
		}
		return "{'code':"+code+",'msg':'"+msg+"'}";
	}
	
	public String readContent(InputStream is){
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = "";
		StringBuffer content = new StringBuffer();
		try {
			while((line = br.readLine()) != null){
				//line = new String(line.getBytes("GBK"), "UTF-8");
				content.append(line);
			}
		} catch (IOException e) {
			log.error("在读取数据流时出错.", e);
		}
		return content.toString();
	}
}
