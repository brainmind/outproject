/**
 * CarController.java
 * @author: 杨洲
 * @date: 2015-1-22
 */
package com.wechat.client.business.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wechat.client.business.model.Car;
import com.wechat.client.business.model.LoginUser;
import com.wechat.client.business.model.Series;
import com.wechat.client.business.model.XmCarsVO;
import com.wechat.client.business.service.CarCache;
import com.wechat.client.utils.Constants;
import com.wechat.client.utils.JsonHttpRequestUtil;
import com.wechat.client.utils.PropertiesUtils;
import com.wechat.client.utils.WxUrls;
import com.wsyb.ray.HttpEntityUtils;

@Controller
@RequestMapping(Constants.ROOT+"/car")
public class CarController extends BaseController {
	private Logger log = Logger.getLogger(OrderController.class);
	private final String getByVINPath = "/carsByVIN.json";
	private final String userCars = "/userCars.json";
	private final String userCarRecord = "/userCarRecord.json";

	@RequestMapping("/sel")
	public String select(HttpServletRequest request, HttpServletResponse response){
		String backurl = request.getParameter("backurl");
		if(StringUtils.isNotEmpty(backurl)){
			backurl = backurl.replaceAll("_1q1_", "?").replaceAll("_2q2_", "&");
		}
		request.setAttribute("backurl", backurl);
		return "car_model_sel";
	}
	
	@RequestMapping("/brand.json")
	@ResponseBody
	public List<XmCarsVO> selectBrand(HttpServletRequest request, HttpServletResponse response){
		CarCache cc = CarCache.getInstance();
		return cc.getBrandList();
	}
	
	@RequestMapping("/serie.json")
	@ResponseBody
	public List<Series> selectSerie(HttpServletRequest request, HttpServletResponse response){
		String brandId = request.getParameter("brandId");
		CarCache cc = CarCache.getInstance();
		return cc.getSerieList(brandId);
	}
	
	@RequestMapping("/car.json")
	@ResponseBody
	public List<Car> selectCars(HttpServletRequest request, HttpServletResponse response){
		String serieId = request.getParameter("serieId");
		CarCache cc = CarCache.getInstance();
		return cc.getCarList(serieId);
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
	public String itemSel(HttpServletRequest request){
		String carId = request.getParameter("carId");
		request.setAttribute("carId", carId);
		return "serviceitem/itemsel";
	}
	
	@RequestMapping("/my")
	public String myCars(HttpServletRequest request){
		try {
			String openId = request.getParameter("openid");
			if(StringUtils.isEmpty(openId)){
				String code = request.getParameter("code");
				String appId = PropertiesUtils.getValue("wx_appid");
				String appKey = PropertiesUtils.getValue("wx_appsecret");
				String authAccessUrl = WxUrls.AuthToken+"&appid="+appId+"&secret="+appKey+"&code="+code;
				JsonHttpRequestUtil jr = new JsonHttpRequestUtil();
				String json = jr.doGet(authAccessUrl, true);
				Map<String, String> tokenJson = new ObjectMapper().readValue(json, new TypeReference<Map<String, String>>(){});
				openId = tokenJson.get("openid");
			}
			if(StringUtils.isNotEmpty(openId)){
				HttpSession session = request.getSession();
				LoginUser user = (LoginUser)session.getAttribute(Constants.USER_SESSION_KEY);
				if(user == null || StringUtils.isEmpty(user.getOpenid())){
					user = new LoginUser();
					user.setOpenid(openId);
					session.setAttribute(Constants.USER_SESSION_KEY, user);
				}
			}
		} catch (JsonParseException e) {
			log.error("回调页面时，转换token数据有错误.", e);
		} catch (JsonMappingException e) {
			log.error("回调页面时，转换token数据有错误.", e);
		} catch (IOException e) {
			log.error("回调页面时，转换token数据有错误.", e);
		}
		return "carereport/my_cars";
	}
	
	@RequestMapping("/my.json")
	public void myCars(HttpServletRequest request, HttpServletResponse response){
		LoginUser user = getLoginUser(request, response);
		String param = HttpEntityUtils.toParameterString(user);
		JsonHttpRequestUtil jr = new JsonHttpRequestUtil();
		String json = jr.doGet(userCars+"?"+param);
		writeJson(response, json);
	}
	
	@RequestMapping("/report")
	public String report(HttpServletRequest request){
		String recordId = request.getParameter("recordId");
		String label = request.getParameter("label");
		String license = request.getParameter("license");
		request.setAttribute("recordId", recordId);
		try {
			label = new String(label.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error("不支持的编码");;
		}
		request.setAttribute("label", label);
		request.setAttribute("license", license);
		return "carereport/report";
	}
	
	@RequestMapping("/report.json")
	public void reportData(HttpServletRequest request, HttpServletResponse response){
		String recordId = request.getParameter("recordId");
		LoginUser user = getLoginUser(request, response);
		String param = HttpEntityUtils.toParameterString(user);
		JsonHttpRequestUtil jr = new JsonHttpRequestUtil();
		String json = jr.doGet(userCarRecord+"?"+param+"&recordid="+recordId);
		writeJson(response, json);
	}
}
