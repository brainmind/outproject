package com.wechat.client.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.wechat.client.business.model.ResultData;

public class JsonHttpRequestUtil {
	private Logger log = Logger.getLogger(JsonHttpRequestUtil.class);
	private final String basePath = Constants.APIBasePath;
	
	public JsonHttpRequestUtil(){}
	
	@SuppressWarnings("unchecked")
	public <T> ResultData<T> doGet(HttpServletRequest request){
		String queryString = request.getQueryString();
		HttpURLConnection connect = null;
		ResultData<T> rd = new ResultData<T>();
		try {
			String methodPath = (String)request.getAttribute("methodPath");
			String param = (String)request.getAttribute("param");
			queryString = queryString==null?"":"&"+queryString;
			URL url = new URL(basePath+methodPath+param+queryString);
			connect = (HttpURLConnection)url.openConnection();
			connect.setConnectTimeout(10000);
			connect.setRequestMethod("GET");
			connect.connect();
			if(connect.getResponseCode() == 200){
				String json = readContent(connect.getInputStream());
				JsonFactory jf = new JsonFactory();
				rd = jf.getJson(json, ResultData.class);
				return rd;
			}
		} catch (MalformedURLException e) {
			log.error("不是正确的URL", e);
			rd.setMsg("不是正确的URL");
		} catch (IOException e) {
			log.error("读取URL时发生错误", e);
			rd.setMsg("读取URL时发生错误,检查网格连接.");
		}finally {
			if(connect != null){
				connect.disconnect();
			}
		}
		rd.setCode(500);
		return rd;
	}
	
	@SuppressWarnings("unchecked")
	public <T> ResultData<?> doPost(HttpServletRequest request){
		String queryString = request.getQueryString();
		HttpURLConnection connect = null;
		ResultData<T> rd = new ResultData<T>();
		try {
			queryString = queryString==null?"":queryString;
			URL url = new URL(basePath+queryString);
			connect = (HttpURLConnection)url.openConnection();
			connect.setConnectTimeout(10000);
			connect.setRequestMethod("POST");
			connect.connect();
			if(connect.getResponseCode() == 200){
				String json = readContent(connect.getInputStream());
				JsonFactory jf = new JsonFactory();
				rd = jf.getJson(json, ResultData.class);
				return rd;
			}
		} catch (MalformedURLException e) {
			log.error("不是正确的URL", e);
			rd.setMsg("不是正确的URL");
		} catch (IOException e) {
			log.error("读取URL时发生错误", e);
			rd.setMsg("读取URL时发生错误,检查网格连接.");
		}finally {
			if(connect != null){
				connect.disconnect();
			}
		}
		rd.setCode(500);
		return rd;
	}
	
	public String readContent(InputStream is){
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = "";
		StringBuffer content = new StringBuffer();
		try {
			while((line = br.readLine()) != null){
				line = new String(line.getBytes("GBK"), "UTF-8");
				content.append(line);
			}
		} catch (IOException e) {
			log.error("在读取数据流时出错.", e);
		}
		return content.toString();
	}
}
