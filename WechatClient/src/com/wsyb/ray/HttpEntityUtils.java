package com.wsyb.ray;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;

public class HttpEntityUtils {
		
	public static <T> String toParameterString(T t){
		if(t == null){
			return "";
		}
		Field[] fields = t.getClass().getDeclaredFields();
		StringBuffer param = new StringBuffer();
		for(int i=0;i<fields.length;i++){
			Field f = fields[i];
			String name = f.getName();
			try {
				String method = "get" + name.substring(0, 1).toUpperCase()+name.substring(1);
				String value = t.getClass().getMethod(method).invoke(t)+"";
				param.append("&"+name+"="+value);
			} catch (IllegalAccessException e) {
				Logger.getLogger(HttpEntityUtils.class).error("无权限访问该类的方法或属性.", e);
			} catch (IllegalArgumentException e) {
				Logger.getLogger(HttpEntityUtils.class).error("访问该类的方法时，参数错误.", e);
			} catch (InvocationTargetException e) {
				Logger.getLogger(HttpEntityUtils.class).error("访问该类的方法时发生错误.", e);
			} catch (NoSuchMethodException e) {
				Logger.getLogger(HttpEntityUtils.class).error("没有该方法.", e);
			} catch (SecurityException e) {
				Logger.getLogger(HttpEntityUtils.class).error("访问该类的方法时发生错误.", e);
			}
		}
		return param.toString();
	}
}
