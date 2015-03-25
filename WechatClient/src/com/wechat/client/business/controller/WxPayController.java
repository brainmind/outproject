package com.wechat.client.business.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xml.sax.SAXException;

import com.wechat.client.utils.PropertiesUtils;
import com.wechat.client.utils.UUIDUtil;
import com.wechat.client.utils.WxUtils;
import com.wechat.client.utils.XmlReaderUtil;

@Controller
@RequestMapping("/pay")
public class WxPayController extends BaseController{
	private Logger log = Logger.getLogger(WxPayController.class);
	private String AppId = PropertiesUtils.getValue("wx_appid");
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
	/**
	 * 预支付
	 */
	@RequestMapping("/prepay")
	public void prePay(HttpServletRequest request, HttpServletResponse response){
		Map<String, String> resultXml = new HashMap<String, String>();
		String out_trade_no = request.getParameter("out_trade_no");
		String body = request.getParameter("good_body");
		String total_fee = request.getParameter("total_fee");
		total_fee = "1";
		body = "test";
		String timestamp = (new Date().getTime()/1000)+"";
		String nonce_str = UUIDUtil.generateUUID();
		resultXml.put("appid", AppId);
		resultXml.put("noncestr", nonce_str);
		resultXml.put("timestamp", timestamp);
		try {
			String strPackage = WxUtils.getPackage(out_trade_no, body, total_fee);
			log.info("package:"+strPackage);
			String sign = WxUtils.getPaySign(strPackage, out_trade_no, timestamp, nonce_str);
			log.info("paysign:"+sign);
			resultXml.put("packages", strPackage);
			resultXml.put("sign", sign);
			resultXml.put("code", "200");
			log.info("js xml:"+resultXml.toString());
			String xmlJson = new ObjectMapper().writeValueAsString(resultXml);
			writeJson(response, xmlJson);
		} catch (JsonGenerationException e) {
			log.error("JSON转换出错...", e);
			writeJson(response, "{\"code\":500}");
		} catch (JsonMappingException e) {
			log.error("JSON转换出错...", e);
			writeJson(response, "{\"code\":500}");
		} catch (IOException e) {
			log.error("JSON转换出错...", e);
			writeJson(response, "{\"code\":500}");
		}
	}
	
	@RequestMapping("/back")
	public void payBack(HttpServletRequest request, HttpServletResponse response){
		PrintWriter out = null;
		try {
			out = response.getWriter();
			Map<String, String> xml = XmlReaderUtil.read(request.getInputStream());
			if("success".equals(xml.get("return_code").toLowerCase())){
				out.print("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
			}else{
				out.print("fail");
			}
		} catch (ParserConfigurationException e) {
			log.error("解析微信返回的xml数据时出错...", e);
			out.print("fail");
		} catch (SAXException e) {
			log.error("解析微信返回的xml数据时出错...", e);
			out.print("fail");
		} catch (IOException e) {
			log.error("解析微信返回的xml数据时出错...", e);
			out.print("fail");
		} finally {
			if(out != null){
				out.close();
			}
		}
	}
	
	@RequestMapping("/warning")
	public void warning(HttpServletRequest request, HttpServletResponse response){
		
	}
}
