/**
 * OrderController.java
 * @author: 杨洲
 * @date: 2015-1-22
 */
package com.wechat.client.business.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wechat.client.utils.Constants;

@Controller
@RequestMapping(Constants.ROOT+"/order")
public class OrderController extends BaseController{

	@RequestMapping("/service")
	public String service(){
		return "order/order_services";
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
