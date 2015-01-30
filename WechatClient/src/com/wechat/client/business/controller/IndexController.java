/**
 * IndexController.java
 * @author: 杨洲
 * @date: 2015-1-19
 */
package com.wechat.client.business.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wechat.client.business.model.LoginUser;
import com.wechat.client.utils.Constants;

@Controller
@RequestMapping(Constants.ROOT)
public class IndexController extends BaseController {
	@RequestMapping("/index")
	public String index(HttpServletRequest request){
		HttpSession session = request.getSession();
		LoginUser user = new LoginUser();
		user.setOpenId("12345688");
		session.setAttribute(Constants.USER_SESSION_KEY, user);
		return "wx_index";
	}
}
