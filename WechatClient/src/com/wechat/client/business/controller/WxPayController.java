package com.wechat.client.business.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xml.sax.SAXException;

import com.wechat.client.business.model.LoginUser;
import com.wechat.client.utils.Constants;
import com.wechat.client.utils.DecriptUtil.DecrType;
import com.wechat.client.utils.JsonHttpRequestUtil;
import com.wechat.client.utils.PropertiesUtils;
import com.wechat.client.utils.UUIDUtil;
import com.wechat.client.utils.WxUtils;
import com.wechat.client.utils.XmlReaderUtil;

@Controller
@RequestMapping("/pay")
public class WxPayController extends BaseController{
	private Logger log = Logger.getLogger(WxPayController.class);
	private String unifiedPayUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	private String AppId = PropertiesUtils.getValue("wx_appid");
	private String MchId = PropertiesUtils.getValue("wx_mch_id");
	private String Host = PropertiesUtils.getValue("wx_sys_host");
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
		HttpSession session = request.getSession();
		LoginUser user = (LoginUser)session.getAttribute(Constants.USER_SESSION_KEY);
		if(user == null){
			writeJson(response, "{\"code\":204, \"msg\":\"无效的用户信息.\"}");
			return;
		}
		String openId = user.getOpenid();
		String out_trade_no = request.getParameter("out_trade_no");
		String body = request.getParameter("good_body");
		//String total_fee = request.getParameter("total_fee");
		Map<String, String> params = new HashMap<String, String>();
		params.put("appid", AppId);
		params.put("mch_id", MchId);
		params.put("nonce_str", UUIDUtil.generateUUID());
		params.put("spbill_create_ip", "115.28.65.171");
		params.put("body", body);
		params.put("notify_url", Host+"WechatClient/pay/back");
		params.put("openid", openId);
		params.put("out_trade_no", out_trade_no);
		params.put("total_fee", "1");
		params.put("trade_type", "JSAPI");
		String sign = WxUtils.getSign(params, DecrType.MD5, true);
		StringBuffer xml = new StringBuffer();
		xml.append("<xml>")
		   .append("<appid><![CDATA["+AppId+"]]></appid>")
		   .append("<body><![CDATA["+body+"]]></body>")
		   .append("<mch_id>"+MchId+"</mch_id>")
		   .append("<nonce_str><![CDATA["+params.get("nonce_str")+"]]></nonce_str>")
		   .append("<notify_url><![CDATA["+params.get("notify_url")+"]]></notify_url>")
		   .append("<openid><![CDATA["+openId+"]]></openid>")
		   .append("<out_trade_no><![CDATA["+out_trade_no+"]]></out_trade_no>")
		   .append("<spbill_create_ip><![CDATA[115.28.65.171]]></spbill_create_ip>")
		   .append("<total_fee>1</total_fee>")
		   .append("<trade_type><![CDATA[JSAPI]]></trade_type>")
		   .append("<sign><![CDATA["+sign+"]]></sign>")
		   .append("</xml>");
		JsonHttpRequestUtil jr = new JsonHttpRequestUtil();
		try {
			log.info("prepay post: "+xml.toString());
			Map<String, String> resultXml = jr.doPostXml(unifiedPayUrl, xml.toString());
			log.info("prepay result: "+resultXml);
			if(resultXml != null && "SUCCESS".equals(resultXml.get("return_code").toUpperCase()) && "SUCCESS".equals(resultXml.get("result_code").toUpperCase())){
				resultXml.put("code", "200");
				resultXml.put("timestamp", new Date().getTime()/1000+"");
				String xmlJson = new ObjectMapper().writeValueAsString(resultXml);
				writeJson(response, xmlJson);
				return;
			}
			writeJson(response, "{\"code\":500,\"msg\":\""+(resultXml==null?"预下单失败":resultXml.get("err_code_des"))+"\"}");
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
