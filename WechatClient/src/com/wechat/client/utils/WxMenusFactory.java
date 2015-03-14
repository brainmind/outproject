package com.wechat.client.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.xml.sax.SAXException;

import com.wechat.client.business.controller.WxCallBackController;
import com.wechat.client.business.model.WxMenu;
import com.wechat.client.business.model.WxMenus;

public class WxMenusFactory {
	private Logger log = Logger.getLogger(WxCallBackController.class);
	private String AppId = PropertiesUtils.getValue("wx_appid");
	private String AppSecret = PropertiesUtils.getValue("wx_appsecret");
	public static WxMenus createWxMenus(){
		List<WxMenu> button = new ArrayList<WxMenu>();
		WxMenu menu = new WxMenu();
		menu.setType("view");
		menu.setName("小马上门");
		menu.setUrl("http://eqxiu.com/s/WXar09?eqrcode=1");
		button.add(menu);
		
		menu = new WxMenu();
		menu.setType("click");
		menu.setName("服务预定");
		menu.setKey("SMALLHORSE_SERVICE");
		//menu.setUrl("http://007fsp.oicp.net:888/WechatClient/dtds/index");
		button.add(menu);
		
		menu = new WxMenu();
		menu.setType("click");
		menu.setName("我的");
		menu.setKey("SMALLHORSE_MY");
		List<WxMenu> sub_button = new ArrayList<WxMenu>();
		menu.setSub_button(sub_button);
		button.add(menu);
		
		menu = new WxMenu();
		menu.setType("click");
		menu.setName("订单");
		menu.setKey("SMALLHORSE_ORDER");
		//menu.setUrl("http://007fsp.oicp.net:888/WechatClient/dtds/order/history");
		sub_button.add(menu);
		menu = new WxMenu();
		menu.setType("click");
		menu.setName("车检报告");
		menu.setKey("SMALLHORSE_REPORT");
		//menu.setUrl("http://007fsp.oicp.net:888/WechatClient/dtds/order/history");
		sub_button.add(menu);
		
		WxMenus menus = new WxMenus();
		menus.setButton(button);
		return menus;
	}
	
	private String getToken(){
		try {
			Map<String, Object> map = null;
			JsonHttpRequestUtil jr = new JsonHttpRequestUtil();
			String param = "&appid="+AppId+"&secret="+AppSecret;
			String str = jr.doGet(WxUrls.AccessToken+param, true);
			log.info("AccessToken:"+str);
			map = new ObjectMapper().readValue(str, new TypeReference<Map<String, Object>>() {});
			map.put("expires", new Date().getTime());
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
	
	public void createMenus(){
		String accessToken = getToken();
		JsonHttpRequestUtil jr = new JsonHttpRequestUtil();
		try {
			WxMenus menus = WxMenusFactory.createWxMenus();
			String json = new ObjectMapper().writeValueAsString(menus);
			String result = jr.doPost(WxUrls.CreateMenus + accessToken, json, true);
			log.info(result);
			System.out.println("CreateMenu:"+result);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args){
		WxMenusFactory wf = new WxMenusFactory();
		try {
			File f = new File("d:\\template.xml");
			FileInputStream fis = new FileInputStream(f);
			Map<String, String> map = XmlReaderUtil.read(fis);
			System.out.println(map);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}	
