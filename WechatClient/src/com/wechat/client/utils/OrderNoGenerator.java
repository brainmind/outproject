package com.wechat.client.utils;

import java.util.Date;

import org.apache.commons.httpclient.util.DateUtil;

public class OrderNoGenerator {

	public static String getSN(){
		String dateStr = DateUtil.formatDate(new Date(), "yyyyMMddHHmmssSSS");
		dateStr += (int)(Math.random()*10000);
		return dateStr;
	}
}
