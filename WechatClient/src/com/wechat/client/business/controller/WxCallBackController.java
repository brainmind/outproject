/**
 * IndexController.java
 * @author: 杨洲
 * @date: 2015-1-19
 */
package com.wechat.client.business.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wechat.client.business.model.LoginUser;
import com.wechat.client.utils.Constants;

@Controller
@RequestMapping("/event")
public class WxCallBackController extends BaseController {
	@RequestMapping("")
	public String token(HttpServletRequest request){
		try {
			InputStream is = request.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String line = "";
			StringBuffer xmlStr = new StringBuffer();
			while((line = br.readLine()) != null){
				xmlStr.append(line);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		HttpSession session = request.getSession();
		LoginUser user = new LoginUser();
		user.setOpenid("12345688");
		session.setAttribute(Constants.USER_SESSION_KEY, user);
		return "wx_index";
	}
}
