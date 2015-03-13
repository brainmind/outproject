/**
 * IndexController.java
 * @author: 杨洲
 * @date: 2015-1-19
 */
package com.wechat.client.business.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.xml.sax.SAXException;

import com.wechat.client.business.model.WxMenus;
import com.wechat.client.utils.Constants;
import com.wechat.client.utils.DecriptUtil;
import com.wechat.client.utils.JsonHttpRequestUtil;
import com.wechat.client.utils.PropertiesUtils;
import com.wechat.client.utils.WxMenusFactory;
import com.wechat.client.utils.WxUrls;
import com.wechat.client.utils.XmlReaderUtil;

@Controller
@RequestMapping("/event")
public class WxCallBackController extends BaseController {
	private Logger log = Logger.getLogger(WxCallBackController.class);
	private String Token = PropertiesUtils.getValue("wx_token");
	private String AppId = PropertiesUtils.getValue("wx_appid");
	private String AppSecret = PropertiesUtils.getValue("wx_appsecret");
	@RequestMapping(value="/valid", method=RequestMethod.GET)
	public void validate(HttpServletRequest request, HttpServletResponse response){
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		log.info("Get parameter [signature, timestamp, nonce]"+timestamp+","+nonce);
		String[] shaAry = new String[]{timestamp, nonce, Token};
		Arrays.sort(shaAry);
		log.info("Sort parameters ...");
		StringBuffer shaStr = new StringBuffer();
		shaStr.append(shaAry[0]).append(shaAry[1]).append(shaAry[2]);
		try {
			String sha = DecriptUtil.SHA1(shaStr.toString());
			if(sha.equals(signature)){
				response.getWriter().print(echostr);
				return;
			}
			response.getWriter().print("validate failure");
		} catch (IOException e) {
			log.error("validate error", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	private String getToken(HttpServletRequest request){
		try {
			HttpSession session = request.getSession();
			long curTime = new Date().getTime();
			Map<String, Object> map = (Map<String, Object>)session.getAttribute(Constants.AccessToken_SESSION_KEY);
			if(map == null || (map != null && ((long)map.get("expires")) - curTime < 1000)){
				JsonHttpRequestUtil jr = new JsonHttpRequestUtil();
				String param = "&appid="+AppId+"&secret="+AppSecret;
				String str = jr.doGet(WxUrls.AccessToken+param);
				log.info("AccessToken:"+str);
				map = new ObjectMapper().readValue(str, new TypeReference<Map<String, Object>>() {});
				map.put("expires", new Date().getTime());
				session.setAttribute(Constants.AccessToken_SESSION_KEY, map);
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
	
	private void createMenus(HttpServletRequest request){
		String accessToken = getToken(request);
		JsonHttpRequestUtil jr = new JsonHttpRequestUtil();
		WxMenus menus = WxMenusFactory.createWxMenus();
		try {
			String json = new ObjectMapper().writeValueAsString(menus);
			String result = jr.doPost(WxUrls.CreateMenus + accessToken, json);
			log.info(result);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping("/createMenu")
	public void createMenu(HttpServletRequest request){
		createMenus(request);
	}
	
	@RequestMapping(value="/valid", method=RequestMethod.POST)
	public void recevice(HttpServletRequest request, HttpServletResponse response){
		try {
			InputStream is = request.getInputStream();
			Map<String, String> xmlMap = XmlReaderUtil.read(is);
			log.info("################### Recevice text from wxserver：\r\n"+xmlMap.toString());
			String toUserName = xmlMap.get("ToUserName");
			String fromUserName = xmlMap.get("FromUserName");
			String msgType = xmlMap.get("MsgType");
			String event = xmlMap.get("Event");
			String eventKey = xmlMap.get("EventKey");
			String openId = "";
			if(StringUtils.isNotEmpty(fromUserName)){
				openId = fromUserName;
			}
			if("event".equals(msgType.toLowerCase()) && "click".equals(event.toLowerCase())){
				if("SMALLHORSE_SERVICE".equals(eventKey)){
					request.setAttribute("url", "http://122.115.62.107/WechatClient/dtds/index?openId="+openId);
				}else if("SMALLHORSE_ORDER".equals(eventKey)){
					request.setAttribute("url", "http://122.115.62.107/WechatClient/dtds/order/history?openId="+openId);
				}else if("SMALLHORSE_REPORT".equals(eventKey)){
					request.setAttribute("url", "http://122.115.62.107/WechatClient/dtds/order/history?openId="+openId);
				}
			}
			StringBuffer xml = new StringBuffer();
			xml.append("<xml>"+
						"<ToUserName><![CDATA["+fromUserName+"]]</ToUserName>"+
						"<FromUserName><![CDATA["+toUserName+"]]</FromUserName>"+
						"<CreateTime>"+new Date().getTime()+"</CreateTime>"+
						"<MsgType><![CDATA[news]]</MsgType>"+
						"<ArticleCount>1</ArticleCount>"+
						"<Articles><item>"+
						"<Title><![CDATA[小马上门-服务预约]]</Title>"+
						"<Description><![CDATA[打造一站式汽车维修保养服务]]</Description>"+
						"<PicUrl><![CDATA[http://122.115.62.107/WechatClient/styles/images/idx_logo.png]]</PicUrl>"+
						"<Url><![CDATA[http://122.115.62.107/WechatClient/dtds/index?openId="+openId+"]]</Url>"+
						"</item></Articles>"+
						"</xml>");
			log.info("################### Send text to wxserver: \r\n"+xml.toString());
			PrintWriter out = response.getWriter();
			out.print(xml.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			log.error("接收微信服务推送消息处理时，发生错误...", e);
		} catch (ParserConfigurationException e) {
			log.error("接收微信服务推送消息处理时，发生错误...", e);
		} catch (SAXException e) {
			log.error("接收微信服务推送消息处理时，发生错误...", e);
		}
	}
}
