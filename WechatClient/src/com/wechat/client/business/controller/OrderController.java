/**
 * OrderController.java
 * @author: 杨洲
 * @date: 2015-1-22
 */
package com.wechat.client.business.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wechat.client.business.model.Commodity;
import com.wechat.client.business.model.LoginUser;
import com.wechat.client.business.model.Servicefee;
import com.wechat.client.business.model.XmOrderVO;
import com.wechat.client.utils.Constants;
import com.wechat.client.utils.JsonHttpRequestUtil;
import com.wechat.client.utils.OrderNoGenerator;
import com.wsyb.ray.HttpEntityUtils;

@Controller
@RequestMapping(Constants.ROOT+"/order")
public class OrderController extends BaseController{
	private Logger log = Logger.getLogger(OrderController.class);
	private String GetService = "/careProdOfferList.json";
	private String GetCode = "/phoneCAPTCHA.json";
	private String SubmitOrder = "/order.json";
	private String OrderDetail = "/orderDetail.json";
	
	@RequestMapping("/service")
	public String service(HttpServletRequest request, HttpServletResponse response){
		String carId = "2722B345E6BB7E18E266E542468D4427";//request.getParameter("carId");
		String type = request.getParameter("type");
		request.setAttribute("carId", carId);
		request.setAttribute("type", type);
		return "order/order_services";
	}
	
	@RequestMapping("/service.json")
	public void getServiceJson(HttpServletRequest request, HttpServletResponse response){
		LoginUser user = getLoginUser(request, response);
		String carId = request.getParameter("carId");
		String type = request.getParameter("type");
		String accessUrl = GetService;
		String param = HttpEntityUtils.toParameterString(user).substring(1)+"&car_id="+carId+"&type="+type;
		JsonHttpRequestUtil jr = new JsonHttpRequestUtil();
		String json = jr.doGet(accessUrl+"?"+param);
		writeJson(response, json);
		log.info("获取服务项目接口数据成功！");
	}
	
	@RequestMapping("/to")
	public String toOrder(HttpServletRequest request){
		String type = request.getParameter("type");
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
		request.setAttribute("commodities_checked", commoditys_checked);
		request.setAttribute("commodities_id", commoditys_id);
		request.setAttribute("commodities_label", commoditys_label);
		request.setAttribute("commodities_number", commoditys_number);
		request.setAttribute("commodities_price", commoditys_price);
		request.setAttribute("commoditys_cId", commoditys_cId);
		request.setAttribute("service_fees_checked", service_fees_checked);
		request.setAttribute("service_fees_type", service_fees_type);
		request.setAttribute("service_fees_title", service_fees_title);
		request.setAttribute("service_fees_price", service_fees_price);
		request.setAttribute("service_fees_cId", service_fees_cId);
		request.setAttribute("type", type);
		return "order/toorder";
	}
	
	@RequestMapping("/getCode")
	public void getValidCode(HttpServletRequest request, HttpServletResponse response){
		String mobile = request.getParameter("mobile");
		String accessUrl = GetCode;
		LoginUser user = getLoginUser(request, response);
		user.setMobile(mobile);
		String param = HttpEntityUtils.toParameterString(user).substring(1);
		JsonHttpRequestUtil jr = new JsonHttpRequestUtil();
		String json = jr.doGet(accessUrl+"?"+param);
		writeJson(response, json);
		log.info("获取难码成功");
	}
	
	@RequestMapping("/ready")
	public String readyOrder(XmOrderVO order, HttpServletRequest request, HttpServletResponse response){
		/*String type = request.getParameter("type");
		String[] commoditys_id = request.getParameterValues("commodities.id");
		String[] commoditys_label = request.getParameterValues("commodities.label");
		String[] commoditys_number = request.getParameterValues("commodities.number");
		String[] commoditys_price = request.getParameterValues("commodities.price");
		String[] commoditys_cId = request.getParameterValues("commodities.category_id");
		String[] service_fees_type = request.getParameterValues("service_fees.type");
		String[] service_fees_title = request.getParameterValues("service_fees.title");
		String[] service_fees_price = request.getParameterValues("service_fees.price");
		String[] service_fees_cId = request.getParameterValues("service_fees.category_id");
		if(service_fees_type != null && service_fees_type.length > 0){
			List<Commodity> commodities = new ArrayList<Commodity>();
			List<Servicefee> service_fees = new ArrayList<Servicefee>();
			for(int i=0; i<service_fees_type.length; i++){
				String feeType = service_fees_type[i];
				String feeCategory_id = service_fees_cId[i];
				String feePrice = service_fees_price[i];
				Servicefee fee = new Servicefee();
				fee.setCategory_id(feeCategory_id);
				fee.setType(feeType);
				fee.setPrice(Double.parseDouble(feePrice));
				String commId = commoditys_id[i];
				String commPrice = commoditys_price[i];
				String commCid = commoditys_cId[i];
				Commodity comm = new Commodity();
				comm.setCategory_id(commCid);
				comm.setId(commId);
				comm.setTotal_price(Double.parseDouble(commPrice));
				comm.setCategory_id(commCid);
				comm.setType(type);
				
				service_fees.add(fee);
				commodities.add(comm);
			}
			order.setCommodities(commodities);
			order.setService_fees(service_fees);
		}
		request.setAttribute("commodities_id", commoditys_id);
		request.setAttribute("commodities_label", commoditys_label);
		request.setAttribute("commodities_number", commoditys_number);
		request.setAttribute("commodities_price", commoditys_price);
		request.setAttribute("commoditys_cId", commoditys_cId);
		request.setAttribute("service_fees_type", service_fees_type);
		request.setAttribute("service_fees_title", service_fees_title);
		request.setAttribute("service_fees_price", service_fees_price);
		request.setAttribute("service_fees_cId", service_fees_cId);
		order.setOrder_number(OrderNoGenerator.getSN());
		order.setCreate_time(new Date().getTime());
		order.setState(Constants.ORDER_SUBMIT);
		//LoginUser user = getLoginUser(request, response);
		String accessUrl = SubmitOrder;
		String param;
		try {
			param = new ObjectMapper().writeValueAsString(order);
			JsonHttpRequestUtil jr = new JsonHttpRequestUtil();
			String json = jr.doPost(accessUrl, param);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		request.setAttribute("order", order);
		request.setAttribute("serviceName", ServicesType.getNameByType(type));*/
		return "order/submitorder";
	}
	
	@RequestMapping("/save")
	public void saveOrder(XmOrderVO order, HttpServletRequest request, HttpServletResponse response){
		String type = request.getParameter("type");
		String[] commoditys_id = request.getParameterValues("commodities.id");
		//String[] commoditys_label = request.getParameterValues("commodities.label");
		//String[] commoditys_number = request.getParameterValues("commodities.number");
		String[] commoditys_price = request.getParameterValues("commodities.price");
		String[] commoditys_cId = request.getParameterValues("commodities.category_id");
		String[] service_fees_type = request.getParameterValues("service_fees.type");
		//String[] service_fees_title = request.getParameterValues("service_fees.title");
		String[] service_fees_price = request.getParameterValues("service_fees.price");
		String[] service_fees_cId = request.getParameterValues("service_fees.category_id");
		if(service_fees_type != null && service_fees_type.length > 0){
			List<Commodity> commodities = new ArrayList<Commodity>();
			List<Servicefee> service_fees = new ArrayList<Servicefee>();
			for(int i=0; i<service_fees_type.length; i++){
				String feeType = service_fees_type[i];
				String feeCategory_id = service_fees_cId[i];
				String feePrice = service_fees_price[i];
				Servicefee fee = new Servicefee();
				fee.setCategory_id(feeCategory_id);
				fee.setType(feeType);
				fee.setPrice(Double.parseDouble(feePrice));
				String commId = commoditys_id[i];
				String commPrice = commoditys_price[i];
				String commCid = commoditys_cId[i];
				Commodity comm = new Commodity();
				comm.setCategory_id(commCid);
				comm.setId(commId);
				comm.setTotal_price(Double.parseDouble(commPrice));
				comm.setCategory_id(commCid);
				comm.setType(type);
				//添加订单明细
				service_fees.add(fee);
				commodities.add(comm);
			}
			order.setCommodities(commodities);
			order.setService_fees(service_fees);
		}
		order.setOrder_number(OrderNoGenerator.getSN());
		order.setCreate_time(new Date().getTime());
		order.setState(Constants.ORDER_SUBMIT);
		String accessUrl = SubmitOrder;
		String param;
		try {
			param = new ObjectMapper().writeValueAsString(order);
			JsonHttpRequestUtil jr = new JsonHttpRequestUtil();
			String json = jr.doPost(accessUrl, "str="+param);
			writeJson(response, json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/getOrder.json")
	public void getOrder(HttpServletRequest request, HttpServletResponse response){
		String orderId = request.getParameter("orderId");
		LoginUser user = getLoginUser(request, response);
		String param = HttpEntityUtils.toParameterString(user).substring(1)+"&orderid="+orderId;
		JsonHttpRequestUtil jr = new JsonHttpRequestUtil();
		String json = jr.doGet(OrderDetail+"?"+param);
		writeJson(response, json);
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
