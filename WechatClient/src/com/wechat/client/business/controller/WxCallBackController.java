/**
 * IndexController.java
 * @author: 杨洲
 * @date: 2015-1-19
 */
package com.wechat.client.business.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.xml.sax.SAXException;

import com.wechat.client.business.model.WxMenus;
import com.wechat.client.utils.DecriptUtil;
import com.wechat.client.utils.JsonHttpRequestUtil;
import com.wechat.client.utils.PropertiesUtils;
import com.wechat.client.utils.WxMenusFactory;
import com.wechat.client.utils.WxUrls;
import com.wechat.client.utils.WxUtils;
import com.wechat.client.utils.XmlReaderUtil;

@Controller
@RequestMapping("/event")
public class WxCallBackController extends BaseController {
	private Logger log = Logger.getLogger(WxCallBackController.class);
	private String Token = PropertiesUtils.getValue("wx_token");
	private String newsLogo = PropertiesUtils.getValue("wx_news_logo");
	private String Host = PropertiesUtils.getValue("wx_sys_host");
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
	
	
	private void createMenus(HttpServletRequest request){
		String accessToken = WxUtils.getToken(request);
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
	public void recevice(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try {
			InputStream is = request.getInputStream();
			Map<String, String> xmlMap = XmlReaderUtil.read(is);
			log.info("################### Recevice text from wxserver：\r\n"+xmlMap.toString());
			String toUserName = xmlMap.get("ToUserName");
			String fromUserName = xmlMap.get("FromUserName");
			String msgType = xmlMap.get("MsgType");
			String event = xmlMap.get("Event");
			String eventKey = xmlMap.get("EventKey");
			if("event".equals(msgType)){
				String openId = "";
				if(StringUtils.isNotEmpty(fromUserName)){
					openId = fromUserName;
				}
				String url = "";
				String title = "";
				if("click".equals(event.toLowerCase())){
					if("SMALLHORSE_SERVICE".equals(eventKey)){
						title = "服务预约";
						url = Host + "WechatClient/dtds/index?openId="+openId;
					}else if("SMALLHORSE_ORDER".equals(eventKey)){
						title = "历史订单";
						url = Host + "WechatClient/dtds/order/history?openId="+openId;
					}else if("SMALLHORSE_REPORT".equals(eventKey)){
						title = "车检报告";
						url = Host + "WechatClient/dtds/order/history?openId="+openId;
					}
					
					StringBuffer xml = new StringBuffer();
					xml.append("<xml>"+
								"<ToUserName><![CDATA["+fromUserName+"]]></ToUserName>"+
								"<FromUserName><![CDATA["+toUserName+"]]></FromUserName>"+
								"<CreateTime>"+(new Date().getTime()/1000)+"</CreateTime>"+
								"<MsgType><![CDATA[news]]></MsgType>"+
								"<FuncFlag>1</FuncFlag>"+
								"<ArticleCount>1</ArticleCount>"+
								"<Articles><item>"+
								"<Title><![CDATA[小马上门-"+title+"]]></Title>"+
								"<Description><![CDATA[打造一站式汽车维修保养服务]]></Description>"+
								"<PicUrl><![CDATA["+newsLogo+"]]></PicUrl>"+
								"<Url><![CDATA["+url+"]]></Url>"+
								"</item></Articles>"+
								"</xml>");
					out.print(xml.toString());
					log.info("################### Send text to wxserver: \r\n"+xml.toString());
					return;
				}
			}
			out.print("");
		} catch (IOException e) {
			log.error("接收微信服务推送消息处理时，发生错误...", e);
			out.print("");
		} catch (ParserConfigurationException e) {
			log.error("接收微信服务推送消息处理时，发生错误...", e);
			out.print("");
		} catch (SAXException e) {
			log.error("接收微信服务推送消息处理时，发生错误...", e);
			out.print("");
		} finally {
			out.flush();
			out.close();
		}
	}
}
