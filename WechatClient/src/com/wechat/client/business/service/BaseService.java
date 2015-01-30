package com.wechat.client.business.service;

import javax.servlet.http.HttpServletRequest;

import com.wechat.client.business.model.ResultData;
import com.wechat.client.utils.JsonHttpRequestUtil;

public class BaseService {
	
	public <T> ResultData<T> doGetDataList(HttpServletRequest request, Class<?> clazz){
		JsonHttpRequestUtil jh = new JsonHttpRequestUtil();
		ResultData<T> rd = jh.doGet(request);
		return rd;
	}
	
	public <T> ResultData<T> doGetData(HttpServletRequest request){
		JsonHttpRequestUtil jh = new JsonHttpRequestUtil();
		ResultData<T> rd = jh.doGet(request);
		return rd;
	}
}
