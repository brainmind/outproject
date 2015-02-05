/**
 * IndexController.java
 * @author: 杨洲
 * @date: 2015-1-19
 */
package com.wechat.client.business.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wechat.client.business.model.LoginUser;
import com.wechat.client.utils.Constants;
import com.wechat.client.utils.TxtFileUtil;

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
	
	@RequestMapping("/region/{type}/{id}")
	@ResponseBody
	public List<Map<String, String>> getRegions(@PathVariable("type") String type, @PathVariable("id") String id){
		if(!TxtFileUtil.validType(type)){
			return null;
		}
		return TxtFileUtil.getDataList(id, type);
	}
}
