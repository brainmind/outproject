package com.wsyb.ray.mapping;

import javax.servlet.http.HttpServletRequest;

public enum RightType {
	login{
		public String getLoginToken(HttpServletRequest request, int mallId){
//			import org.springframework.web.context.WebApplicationContext;
//			import org.springframework.web.context.support.WebApplicationContextUtils;
//			WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
			return "RAYMINDLOGIN";
		}
	},
	
	access{
		public String getAccessToken(String userId){
			return "RAYMINDACCESS";
		}
	},
	
	get;
	
	public String getLoginToken(HttpServletRequest request, int mallId){
		throw new AbstractMethodError("方法未实现.");
	}
	
	public String getAccessToken(String userId){
		throw new AbstractMethodError("方法未实现.");
	}
}
