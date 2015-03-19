package com.wechat.client.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.wechat.client.utils.DecriptUtil.DecrType;

public class WxUtils {
	private static Logger log = Logger.getLogger(WxUtils.class);
	private static String AppId = PropertiesUtils.getValue("wx_appid");
	private static String AppSecret = PropertiesUtils.getValue("wx_appsecret");
	
	@SuppressWarnings("unchecked")
	public static String getToken(HttpServletRequest request){
		try {
			HttpSession session = request.getSession();
			long curTime = new Date().getTime();
			Map<String, Object> map = (Map<String, Object>)session.getAttribute(Constants.AccessToken_SESSION_KEY);
			if(map == null || (map != null && curTime - ((Long)map.get("expires")) < 1000)){
				JsonHttpRequestUtil jr = new JsonHttpRequestUtil();
				String param = "&appid="+AppId+"&secret="+AppSecret;
				String str = jr.doGet(WxUrls.AccessToken+param, true);
				log.info("AccessToken:"+str);
				map = new ObjectMapper().readValue(str, new TypeReference<Map<String, Object>>() {});
				if(map.get("code") == null){
					map.put("expires", new Date().getTime());
					session.setAttribute(Constants.AccessToken_SESSION_KEY, map);
				}
			}
			return (String)map.get("access_token");
		} catch (JsonParseException e) {
			log.error("JSON convert failure ...", e);
		} catch (JsonMappingException e) {
			log.error("JSON convert failure ...", e);
		} catch (IOException e) {
			log.error("JSON convert failure ...", e);
		}
		return "";
	}
	
	@SuppressWarnings("unchecked")
	public static String getTicket(HttpServletRequest request){
		try {
			HttpSession session = request.getSession();
			long curTime = new Date().getTime();
			Map<String, Object> map = (Map<String, Object>)session.getAttribute(Constants.JSAPITicket_SESSION_KEY);
			if(map == null || (map != null && curTime - ((Long)map.get("expires")) < 1000)){
				JsonHttpRequestUtil jr = new JsonHttpRequestUtil();
				String accessToken = getToken(request);
				String str = jr.doGet(WxUrls.GetTicket+accessToken, true);
				log.info("JsApiTicket:"+str);
				map = new ObjectMapper().readValue(str, new TypeReference<Map<String, Object>>() {});
				if(map.get("code") == null && "ok".equals(map.get("errmsg"))){
					map.put("expires", new Date().getTime());
					session.setAttribute(Constants.JSAPITicket_SESSION_KEY, map);
				}
			}
			return (String)map.get("ticket");
		} catch (JsonParseException e) {
			log.error("JSON convert failure ...", e);
		} catch (JsonMappingException e) {
			log.error("JSON convert failure ...", e);
		} catch (IOException e) {
			log.error("JSON convert failure ...", e);
		}
		return "";
	}
	/**
	 * 根据ticket，签名规则；生成签名
	 * @param js_api_ticket
	 * @param url
	 * @return
	 */
	public static Map<String, String> getSignature(String js_api_ticket, String url){
		String noncestr = UUIDUtil.generateUUID();
		String timestamp = System.currentTimeMillis()/1000 + "";
		Map<String, String> shaAry = new HashMap<String, String>();
		shaAry.put("jsapi_ticket", js_api_ticket);
		shaAry.put("noncestr", noncestr);
		shaAry.put("timestamp", timestamp);
		shaAry.put("url", url);
		String signature = getSign(shaAry, DecrType.SHA1, false);
		shaAry.put("signature", signature);
		shaAry.put("appid", AppId);
		shaAry.remove("jsapi_ticket");
		shaAry.put("code", "200");
		log.info("signature map :"+shaAry);
		return shaAry;
	}
	
	/**
	 * 微信中生成各种签名的规则
	 * @param shaAry
	 * @return
	 */
	public static String getSign(Map<String, String> shaAry, DecrType dt, boolean toUpperCase){
		List<Map.Entry<String,String>> shaList = new ArrayList<Map.Entry<String,String>>(shaAry.entrySet());
		Collections.sort(shaList, new Comparator<Map.Entry<String,String>>() {
			public int compare(Entry<String, String> o1,
					Entry<String, String> o2) {
				return o1.getValue().compareTo(o2.getValue());
			}
		});
		String shaMapping = "";
		for(Map.Entry<String,String> mapping : shaList){
			shaMapping += "&"+mapping.getKey()+"="+mapping.getValue();
		}
		shaMapping = shaMapping.substring(1);
		log.info("key-value map is : "+shaMapping);
		String signature = dt.encrypt(shaMapping);
		if(toUpperCase)
			return signature.toUpperCase();
		else
			return signature;
	}
}
