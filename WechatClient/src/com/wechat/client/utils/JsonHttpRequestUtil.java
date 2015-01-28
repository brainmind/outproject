package com.wechat.client.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class JsonHttpRequestUtil {
	private Logger log = Logger.getLogger(JsonHttpRequestUtil.class);
	private final String basePath = "http://122.115.62.107:8080/o2oCRM/";
	
	public JsonHttpRequestUtil(){}
	
	public <T> T getObject(HttpServletRequest request){
		String queryString = request.getQueryString();
		String uri = request.getRequestURI();
		try {
			URL url = new URL(basePath+uri+queryString);
			HttpURLConnection connect = (HttpURLConnection)url.openConnection();
			connect.setConnectTimeout(30);
			connect.setRequestMethod("GET");
			connect.connect();
			System.out.println(connect.getContent());
		} catch (MalformedURLException e) {
			log.error("不是正确的URL", e);
		} catch (IOException e) {
			log.error("读取URL时发生错误", e);
		}
		return null;
	}
	
	public <T> List<T> getList(HttpServletRequest request, Class<T> clazz){
		String queryString = request.getQueryString();
		String uri = request.getRequestURI();
		try {
			URL url = new URL(basePath+uri+queryString);
			HttpURLConnection connect = (HttpURLConnection)url.openConnection();
			connect.setConnectTimeout(30);
			connect.setRequestMethod("GET");
			connect.connect();
			System.out.println(connect.getContent());
		} catch (MalformedURLException e) {
			log.error("不是正确的URL", e);
		} catch (IOException e) {
			log.error("读取URL时发生错误", e);
		}
		return null;
	}
}
