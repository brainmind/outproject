/**
 * OrderController.java
 * @author: 杨洲
 * @date: 2015-1-22
 */
package com.wechat.client.business.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wechat.client.business.model.LoginUser;
import com.wechat.client.utils.Constants;
import com.wechat.client.utils.JsonHttpRequestUtil;
import com.wsyb.ray.HttpEntityUtils;

@Controller
@RequestMapping(Constants.ROOT+"/order")
public class OrderController extends BaseController{
	private Logger log = Logger.getLogger(OrderController.class);
	private String GetServiceAndGoods = "/careProdOfferList.json";
	
	@RequestMapping("/service")
	public String service(HttpServletRequest request, HttpServletResponse response){
		return "order/order_services";
	}
	
	@RequestMapping("/service.json")
	public void getServiceJson(HttpServletRequest request, HttpServletResponse response){
		LoginUser user = getLoginUser(request, response);
		String accessUrl = GetServiceAndGoods;
		String param = HttpEntityUtils.toParameterString(user).substring(1);
		JsonHttpRequestUtil jr = new JsonHttpRequestUtil();
		String json = jr.doGet(accessUrl+"?"+param);
		try {
			response.getWriter().print(json);
		} catch (IOException e) {
			log.error("返回数据时出错.", e);
		}
	}
	
	@RequestMapping("/to")
	public String toOrder(){
		
		return "order/toorder";
	}
	
	@RequestMapping("/ready")
	public String readyOrder(){
		return "order/submitorder";
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public String saveOrder(){
		return "order/toorder";
	}
	
	@RequestMapping("/payonface")
	public String payOnFace(){
		return "order/payonface";
	}

	@RequestMapping("/payonline")
	public String payOnLine(){
		return "order/payonline";
	}
	
	@RequestMapping("/finishorder")
	public String finishOrder(){
		return "order/finishorder";
	}
	
	@RequestMapping("/recommend")
	public String recommendOrder(){
		return "order/recommendorder";
	}
}
