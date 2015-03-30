package com.wechat.client.business.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xml.sax.SAXException;

import com.wechat.client.business.model.LoginUser;
import com.wechat.client.utils.JsonHttpRequestUtil;
import com.wechat.client.utils.PropertiesUtils;
import com.wechat.client.utils.UUIDUtil;
import com.wechat.client.utils.WxUtils;
import com.wechat.client.utils.XmlReaderUtil;

@Controller
@RequestMapping("/pay")
public class WxPayController extends BaseController{
	private Logger log = Logger.getLogger(WxPayController.class);
	private String AppId = PropertiesUtils.getValue("wx_appid");
	private String PayOrderInfo = "/orderPayInfo.json";
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
		String orderId = request.getParameter("orderId");
		total_fee = "1";
		body = "test";
		String timestamp = (new Date().getTime()/1000)+"";
		String nonce_str = UUIDUtil.generateUUID();
		resultXml.put("appid", AppId);
		resultXml.put("noncestr", nonce_str);
		resultXml.put("timestamp", timestamp);
		try {
			String strPackage = WxUtils.getPackage(orderId, out_trade_no, body, total_fee);
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
		String trade_state = request.getParameter("trade_state");
		String transaction_id = request.getParameter("transaction_id");
		String orderId = request.getParameter("attach");
		String totalfee = request.getParameter("total_fee");
		try {
			log.info("Get callback paydata from wx [trade_state:"+trade_state+", transaction_id:"+transaction_id+", orderId:"+orderId+", totalfee:"+totalfee+"].");
			String state = "0".equals(trade_state) ? "Y":"N";
			out = response.getWriter();
			Map<String, String> xml = XmlReaderUtil.read(request.getInputStream());
			String openId = "";
			if(xml != null){
				openId = xml.get("OpenId");
			}
			LoginUser user = getLoginUser(request, response);
			String loginer = "";
			if(user != null){
				loginer = user.getOpenid();
			}
			String reason = "";
			String param = "{\"orderId\":\""+orderId+"\","+
					  "\"pay_way\":\"W\","+
					  "\"bank_sequence\":\""+transaction_id+"\","+
					  "\"amount\":"+totalfee+","+
					  "\"state\":\""+state+"\","+
					  "\"reason\":\""+reason+"\"}";
			
			if("y".equals(state) && StringUtils.isNotEmpty(openId) && openId.equals(loginer)){
				out.print("success");
			}else{
				out.print("fail");
				reason = "支付失败,当前用户未登录或openId与当前登录人不一致";
			}
			try{
				log.info("向接口发送数据:"+param);
				JsonHttpRequestUtil jr = new JsonHttpRequestUtil();
				jr.doPost(PayOrderInfo, param);
				log.info("向接口发送订单支付信息成功.");
			}catch(Exception e){
				log.error("向接口发送订单支付信息失败.");
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
				out.flush();
				out.close();
			}
		}
	}
	
	@RequestMapping("/warning")
	public void warning(HttpServletRequest request, HttpServletResponse response){
		
	}
}
