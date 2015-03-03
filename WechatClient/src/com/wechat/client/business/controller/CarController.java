/**
 * CarController.java
 * @author: 杨洲
 * @date: 2015-1-22
 */
package com.wechat.client.business.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import com.wsyb.ray.HttpEntityUtils;

@Controller
@RequestMapping(Constants.ROOT+"/car")
public class CarController extends BaseController {
	
	private final String getByVINPath = "/carsByVIN.json";

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
	public String itemSel(){
		return "serviceitem/itemsel";
	}
}
