/**
 * IndexController.java
 * @author: 杨洲
 * @date: 2015-1-19
 */
package com.wechat.client.business.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wechat.client.business.model.LoginUser;
import com.wechat.client.utils.Constants;
import com.wechat.client.utils.JsonHttpRequestUtil;
import com.wechat.client.utils.PropertiesUtils;
import com.wechat.client.utils.TxtFileUtil;
import com.wechat.client.utils.WxUrls;

@Controller
@RequestMapping(Constants.ROOT)
public class IndexController extends BaseController {
	private Logger log = Logger.getLogger(IndexController.class);
	@RequestMapping("/index")
	public String index(HttpServletRequest request){
		String openId = request.getParameter("openId");
		HttpSession session = request.getSession();
		LoginUser user = (LoginUser)session.getAttribute(Constants.USER_SESSION_KEY);
		if(user == null){
			user = new LoginUser();
			user.setOpenid(openId);
			session.setAttribute(Constants.USER_SESSION_KEY, user);
		}
		return "wx_index";
	}
	
	@RequestMapping("/authorize")
	public String authorize(HttpServletRequest request){
		String code = request.getParameter("code");
		String appId = PropertiesUtils.getValue("wx_appid");
		String appKey = PropertiesUtils.getValue("wx_appsecret");
		String authAccessUrl = WxUrls.AuthToken+"&appid="+appId+"&secret="+appKey+"&code="+code;
		JsonHttpRequestUtil jr = new JsonHttpRequestUtil();
		String json = jr.doGet(authAccessUrl, true);
		try {
			Map<String, String> tokenJson = new ObjectMapper().readValue(json, new TypeReference<Map<String, String>>(){});
			String openId = tokenJson.get("openid");
			if(StringUtils.isNotEmpty(openId)){
				HttpSession session = request.getSession();
				LoginUser user = (LoginUser)session.getAttribute(Constants.USER_SESSION_KEY);
				if(user == null){
					user = new LoginUser();
					user.setOpenid(openId);
					session.setAttribute(Constants.USER_SESSION_KEY, user);
				}
				return "wx_index";
			}
		} catch (JsonParseException e) {
			log.error("回调页面时，转换token数据有错误.", e);
		} catch (JsonMappingException e) {
			log.error("回调页面时，转换token数据有错误.", e);
		} catch (IOException e) {
			log.error("回调页面时，转换token数据有错误.", e);
		}
		return "wx_index";
	}
	
	@RequestMapping("/region/{type}/{id}")
	@ResponseBody
	public List<Map<String, String>> getRegions(@PathVariable("type") String type, @PathVariable("id") String id){
		if(!TxtFileUtil.validType(type)){
			return null;
		}
		return TxtFileUtil.getDataList(id, type);
	}
	
	@RequestMapping(value="/test", method=RequestMethod.POST)
	public void test(String s, HttpServletRequest request, HttpServletResponse response, @RequestBody String str){
		String json = request.getParameter("str");
		try {
			System.out.println(json);
			System.out.println(str);
			response.getWriter().write("{\"orderid\":\"123123123JIDJ\"}");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/14")
	public String test14(String s, HttpServletRequest request, HttpServletResponse response, @RequestBody String str){
		return "14";
	}
}
