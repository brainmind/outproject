package com.wechat.client.business.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.wechat.client.business.model.ResultData;
import com.wechat.client.utils.JsonHttpRequestUtil;

public class BaseService {
	
	public <T> ResultData<T> doGetDataList(HttpServletRequest request, Class<?> clazz){
		JsonHttpRequestUtil jh = new JsonHttpRequestUtil();
		ResultData<T> rd = jh.doGet(request, clazz);
		return rd;
	}
	
	public <T> ResultData<T> doGetData(HttpServletRequest request, Class<T> clazz){
		JsonHttpRequestUtil jh = new JsonHttpRequestUtil();
		ResultData<T> rd = jh.doGet(request, null);
		return rd;
	}
}
