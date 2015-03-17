package com.wechat.client.business.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wechat.client.utils.WxUtils;

@Controller
@RequestMapping("/pay")
public class WxPayController extends BaseController{
	private static Logger log = Logger.getLogger(WxPayController.class);
	@RequestMapping("/config")
	public void wxConfig(HttpServletRequest request, HttpServletResponse response){
		try {
			String url = request.getParameter("url");
			String ticket = WxUtils.getTicket(request);
			Map<String, String> signature = WxUtils.getSignature(ticket, url);
			String signJson = new ObjectMapper().writeValueAsString(signature);
			writeJson(response, signJson);
		} catch (JsonParseException e) {
			log.error("JSON convert failure ...", e);
			writeJson(response, "{\"code\":500, \"msg\":\"转化json时出错.\"}");
		} catch (JsonMappingException e) {
			log.error("JSON convert failure ...", e);
			writeJson(response, "{\"code\":500, \"msg\":\"转化json时出错.\"}");
		} catch (IOException e) {
			log.error("JSON convert failure ...", e);
			writeJson(response, "{\"code\":500, \"msg\":\"转化json时出错.\"}");
		} catch (NullPointerException e) {
			log.error("The value is null in program ...", e);
			writeJson(response, "{\"code\":500, \"msg\":\"转化json时出错.\"}");
		} 
	}
	
	@RequestMapping("/back")
	public void payBack(HttpServletRequest request, HttpServletResponse response){
		
	}
	
	@RequestMapping("/warning")
	public void warning(HttpServletRequest request, HttpServletResponse response){
		
	}
}
