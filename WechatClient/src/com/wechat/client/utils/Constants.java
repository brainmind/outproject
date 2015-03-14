/**
 * Constants.java
 * @author: 杨洲
 * @date: 2015-1-22
 */
package com.wechat.client.utils;

public class Constants {
	public static final String ROOT = "/dtds";
	public static final String APIBasePath = PropertiesUtils.getValue("base_api_path");
	public static final String USER_SESSION_KEY = "wxchatclient_sesn";

	public static final String AccessToken_SESSION_KEY = "wxchatclient_token";
	
	
	
	//-1：已取消；1-已提交；100-配货中；300-已完成
	public static final int ORDER_CANCEL = -1;
	public static final int ORDER_SUBMIT = 1;
	public static final int ORDER_ALLOCATE = 100;
	public static final int ORDER_FINISHED = 300;
}
