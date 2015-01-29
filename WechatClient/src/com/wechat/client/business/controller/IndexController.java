/**
 * IndexController.java
 * @author: 杨洲
 * @date: 2015-1-19
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
@RequestMapping(Constants.ROOT)
public class IndexController extends BaseController {
	@Autowired
	private CarService carService;
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request){
		ResultData<List<CarType>> rd = carService.getCarTypeList(request);
		request.setAttribute("carTypeList", rd.getData());
		return "wx_index";
	}
}
