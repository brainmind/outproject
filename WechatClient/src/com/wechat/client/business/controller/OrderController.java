/**
 * OrderController.java
 * @author: 杨洲
 * @date: 2015-1-22
 */
package com.wechat.client.business.controller;

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
	private String GetService = "/careProdOfferList.json";
	
	@RequestMapping("/service")
	public String service(HttpServletRequest request, HttpServletResponse response){
		return "order/order_services";
	}
	
	@RequestMapping("/service.json")
	public void getServiceJson(HttpServletRequest request, HttpServletResponse response){
		LoginUser user = getLoginUser(request, response);
		String carId = "2722B345E6BB7E18E266E542468D4427";//request.getParameter("carId");
		String type = "1,4";//request.getParameter("type");
		String accessUrl = GetService;
		String param = HttpEntityUtils.toParameterString(user).substring(1)+"&car_id="+carId+"&type="+type;
		JsonHttpRequestUtil jr = new JsonHttpRequestUtil();
		String json = jr.doGet(accessUrl+"?"+param);
		writeJson(response, json);
		log.info("获取服务项目接口数据成功！");
	}
	
	@RequestMapping("/to")
	public String toOrder(HttpServletRequest request){
		String[] commoditys_checked = request.getParameterValues("commodities.checked");
		String[] commoditys_id = request.getParameterValues("commodities.id");
		String[] commoditys_label = request.getParameterValues("commodities.label");
		String[] commoditys_number = request.getParameterValues("commodities.number");
		String[] commoditys_price = request.getParameterValues("commodities.price");
		String[] commoditys_cId = request.getParameterValues("commodities.category_id");
		String[] service_fees_checked = request.getParameterValues("service_fees.checked");
		String[] service_fees_type = request.getParameterValues("service_fees.type");
		String[] service_fees_title = request.getParameterValues("service_fees.title");
		String[] service_fees_price = request.getParameterValues("service_fees.price");
		String[] service_fees_cId = request.getParameterValues("service_fees.category_id");
		request.setAttribute("commodities.checked", commoditys_checked);
		request.setAttribute("commodities.id", commoditys_id);
		request.setAttribute("commodities.label", commoditys_label);
		request.setAttribute("commodities.number", commoditys_number);
		request.setAttribute("commodities.price", commoditys_price);
		request.setAttribute("commodities.category_id", commoditys_cId);
		request.setAttribute("service_fees.checked", service_fees_checked);
		request.setAttribute("service_fees.type", service_fees_type);
		request.setAttribute("service_fees.title", service_fees_title);
		request.setAttribute("service_fees.price", service_fees_price);
		request.setAttribute("service_fees.category_id", service_fees_cId);
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
