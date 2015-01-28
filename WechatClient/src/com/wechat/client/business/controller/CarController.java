/**
 * CarController.java
 * @author: 杨洲
 * @date: 2015-1-22
 */
package com.wechat.client.business.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wechat.client.utils.Constants;

@Controller
@RequestMapping(Constants.ROOT+"/car")
public class CarController extends BaseController {

	@RequestMapping("/sel")
	public String select(){
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
	public String selVin(){
		return "selVin";
	}
	
	@RequestMapping("/selItem")
	public String itemSel(){
		return "serviceitem/itemsel";
	}
}
