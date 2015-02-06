/**
 * CarController.java
 * @author: 杨洲
 * @date: 2015-1-22
 */
package com.wechat.client.business.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wechat.client.business.model.LoginUser;
import com.wechat.client.utils.Constants;
import com.wechat.client.utils.JsonHttpRequestUtil;
import com.wsyb.ray.HttpEntityUtils;

@Controller
@RequestMapping(Constants.ROOT+"/car")
public class CarController extends BaseController {
	
	private final String getByVINPath = "/carsByVIN.json";
	private final String getCarTypeList = "/cars.json";

	@RequestMapping("/sel")
	public String select(HttpServletRequest request, HttpServletResponse response){
		LoginUser user = getLoginUser(request, response);
		String accessUrl = getCarTypeList;
		String param = HttpEntityUtils.toParameterString(user).substring(1);
		JsonHttpRequestUtil jr = new JsonHttpRequestUtil();
		String json = jr.doGet(accessUrl+"?"+param);
		request.setAttribute("json", json);
		return "car_model_sel";
	}
	
	@RequestMapping("/sel.json")
	public void selectCars(HttpServletRequest request, HttpServletResponse response){
		LoginUser user = getLoginUser(request, response);
		String accessUrl = getCarTypeList;
		String param = HttpEntityUtils.toParameterString(user).substring(1);
		JsonHttpRequestUtil jr = new JsonHttpRequestUtil();
		String json = jr.doGet(accessUrl+"?"+param);
		try {
			ObjectMapper om = new ObjectMapper();
			List<Map<String, Object>> result = om.readValue(json, new TypeReference<List<Map<String, Object>>>(){});
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/subsel")
	public String subSelect(){
		return "car_model_sub_sel";
	}
	
	@RequestMapping("/toVin")
	public String toVin(){
		return "toVin";
	}
	
	@RequestMapping("/selVin")
	public String selVin(){
		return "selVin";
	}
	
	@RequestMapping("/searchVin.json")
	public void search(HttpServletRequest request, HttpServletResponse response){
		try {
			LoginUser user = getLoginUser(request, response);
			String vin = request.getParameter("vin17");
			String accessUrl = getByVINPath;
			String param = HttpEntityUtils.toParameterString(user).substring(1);
			JsonHttpRequestUtil jr = new JsonHttpRequestUtil();
			String json = jr.doGet(accessUrl+"?"+param+"&vin17="+vin);
			PrintWriter out = response.getWriter();
			out.print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/selItem")
	public String itemSel(){
		return "serviceitem/itemsel";
	}
}
