/**
 * IndexController.java
 * @author: 杨洲
 * @date: 2015-1-19
 */
package com.wechat.client.business.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wechat.client.business.model.CarType;
import com.wechat.client.utils.Constants;
import com.wechat.client.utils.JsonHttpRequestUtil;

@Controller
@RequestMapping(Constants.ROOT)
public class IndexController extends BaseController {
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request){
		JsonHttpRequestUtil jh = new JsonHttpRequestUtil();
		jh.getList(request, CarType.class);
		return "wx_index";
	}
}
