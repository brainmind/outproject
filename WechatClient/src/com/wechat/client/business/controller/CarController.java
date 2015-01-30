/**
 * CarController.java
 * @author: 杨洲
 * @date: 2015-1-22
 */
package com.wechat.client.business.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wechat.client.business.model.CarType;
import com.wechat.client.business.model.ResultData;
import com.wechat.client.business.service.CarService;
import com.wechat.client.utils.Constants;

@Controller
@RequestMapping(Constants.ROOT+"/car")
public class CarController extends BaseController {
	@Autowired
	private CarService carService;

	@RequestMapping("/sel")
	public String select(HttpServletRequest request){
		ResultData<List<CarType>> rd = carService.getCarTypeList(request);
		request.setAttribute("carTypeList", rd.getData());
		return "car_model_sel";
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
	public String selVin(HttpServletRequest request){
		ResultData<List<CarType>> rd = carService.getCarTypeList(request);
		request.setAttribute("carTypeList", rd.getData());
		return "selVin";
	}
	
	@RequestMapping("/selItem")
	public String itemSel(){
		return "serviceitem/itemsel";
	}
}
