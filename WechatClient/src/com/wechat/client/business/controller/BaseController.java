/**
 * BaseController.java
 * @author: 杨洲
 * @date: 2015-1-19
 */
package com.wechat.client.business.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.wechat.client.business.model.LoginUser;
import com.wechat.client.utils.Constants;

public class BaseController extends AbstractController{

	protected ModelAndView handleRequestInternal(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		return null;
	}
	
	public LoginUser getLoginUser(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		LoginUser user = (LoginUser)session.getAttribute(Constants.USER_SESSION_KEY);
		if(user == null){
			try {
				response.sendRedirect(request.getContextPath()+Constants.ROOT+"/index");
				return null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return user;
	}
	
	public void writeJson(HttpServletResponse response, String json){
		try {
			response.setContentType("application/json;charset=UTF-8");
			response.setHeader("Content-Language", "zh-CN");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(json);
		} catch (IOException e) {
			Logger.getLogger(BaseController.class).error("返回数据时出错.", e);
		}
	}
}
