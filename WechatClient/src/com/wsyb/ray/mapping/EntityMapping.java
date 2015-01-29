package com.wsyb.ray.mapping;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

public enum EntityMapping{
	cartype{
		public String getEntityName(){
			return "CarType";
		}
		
		public String getMapperName(){
			return "wmallAppIndexService";
		}
		
		public RightType getRightType(){
			return RightType.get;
		}
	},
	alipay{
		public RightType getRightType(){
			return RightType.get;
		}
	};
	
	public String getEntityName(){
		throw new AbstractMethodError();
	}
	
	public String getMapperName(){
		throw new AbstractMethodError();
	}
	
	public RightType getRightType(){
		throw new AbstractMethodError();
	}
	
	public static EntityMapping getEntityMapping(String name){
		if(StringUtils.isNotEmpty(name)){
			EntityMapping[] em = EntityMapping.values();
			for(int i=0; i<em.length; i++){
				if(em[i].name().equals(name.toLowerCase().trim())){
					return em[i];
				}
			}
		}
		return null;
	}
	
	public static EntityMapping getEntityMapping(Class<?> clazz){
		if(clazz != null){
			String name = clazz.getName();
			name = name.substring(name.lastIndexOf("."), name.length());
			return getEntityMapping(name);
		}
		return null;
	}
	
	public static EntityMapping getEntityMapping(String name, String method){
		if(RequestMethod.GET.equals(MethodMapping.getRequestMethod(method))){
			int index = -1;
			if((index = name.indexOf("/")) != -1){
				name = name.substring(0, index);
			}else{
				name = name.substring(0, name.length()-1);
			}
			return getEntityMapping(name);
		}else if(RequestMethod.POST.equals(MethodMapping.getRequestMethod(method))){
			name = name.replaceAll("\\/qnup", "");
			name = name.replaceAll("\\/", "");
			if(name.endsWith("_gid")){
				name = name.substring(0, name.length()-4);
			}
			return getEntityMapping(name);
		}else if(RequestMethod.PUT.equals(MethodMapping.getRequestMethod(method))){
			name = name.substring(0, name.indexOf("/"));
			return getEntityMapping(name);
		}else if(RequestMethod.DELETE.equals(MethodMapping.getRequestMethod(method))){
			name = name.substring(0, name.indexOf("/"));
			return getEntityMapping(name);
		}
		return null;
	}
}
