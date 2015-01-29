package com.wechat.client.utils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser.Feature;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.util.Assert;

public class JsonFactory{
	
	public JsonFactory(){
	}
	
	public JsonFactory(String json) throws NullPointerException{
		Assert.hasText(json, "待序列化的参数为空.");
	}
	
	public <T> T getJson(String json, Class<?> clazz) throws JsonParseException, JsonMappingException, IOException{
		Assert.hasText(json, "待序列化的参数为空.");
		ObjectMapper om = new ObjectMapper();
		om.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		T j = om.readValue(json, new TypeReference<T>(){});
		if(j != null && clazz != null && j.getClass().getName().endsWith("List")){
			List<Object> rl = new ArrayList<Object>();
			List<Map<String, Object>> tl = (List<Map<String, Object>>)j;
			if(tl.size() > 0){
				for(Map<String, Object> m : tl){
					try {
						rl.add(convertMapToT(m, clazz.newInstance()));
					} catch (InstantiationException e) {
						Logger.getLogger(JsonFactory.class).error("把JSON转化时，把map转成java对象时出错.", e);
						break;
					} catch (IllegalAccessException e) {
						Logger.getLogger(JsonFactory.class).error("把JSON转化时，把map转成java对象时出错.", e);
						break;
					} catch (SecurityException e) {
						Logger.getLogger(JsonFactory.class).error("把JSON转化时，把map转成java对象时出错.", e);
						break;
					}
				}
				return (T) rl;
			}
		}
		return j;
	}
	
	
	public String toJson(Object t) throws JsonGenerationException, JsonMappingException, IOException{
		ObjectMapper om = new ObjectMapper();
		return om.writeValueAsString(t);
	}
	
	@SuppressWarnings("unchecked")
	private <T> T convertMapToT(Map<String, Object> map, T t) throws InstantiationException, IllegalAccessException, SecurityException{
		try{
			Assert.notEmpty(map, "待转化的Map对象为null,转化为一个对应空对象.");
		}catch(Exception e){
			return t;
		}
		T r = t;
		Set<String> keys = map.keySet();
		for(String key : keys){
			Field f = null;
			try {
				f = r.getClass().getDeclaredField(key);
			} catch (NoSuchFieldException e1) {
				try {
					f = r.getClass().getSuperclass().getDeclaredField(key);
				} catch (NoSuchFieldException e) {
					Logger.getLogger(t.getClass()).error("该实体类["+t.toString()+"]中没有该["+key+"]字段.", e);
					continue;
				}
			}
			if(f != null){
				String methodName = "set"+key.substring(0, 1).toUpperCase()+key.substring(1);
				Method method = null;
				try{
					method = r.getClass().getDeclaredMethod(methodName, f.getType());
				}catch(NoSuchMethodException e){
					try {
						method = r.getClass().getSuperclass().getDeclaredMethod(methodName, f.getType());
					} catch (NoSuchMethodException e1) {
						Logger.getLogger(t.getClass()).error("该实体类["+t.toString()+"]中没有该["+method+"]方法. 详见类：JsonFactory", e);
						continue;
					}
				}
				if(method != null){
					try {
						Object oVal = map.get(key);
						if(oVal == null){
							continue;
						}else if(oVal.getClass().getName().equalsIgnoreCase(f.getType().getName())){
							method.invoke(r, oVal);
						}else if(f.getType().getSuperclass() != null && oVal.getClass().getSuperclass() != null && oVal.getClass().getSuperclass().getName().equals(f.getType().getSuperclass().getName())){
							Object fVal = f.getType().getDeclaredConstructor(String.class).newInstance(oVal.toString());
							method.invoke(r, fVal);
						}else if(f.getType().getName().equals("java.util.Date") && oVal.getClass().getName().equals(Long.class.getName())){
							Object fVal = f.getType().getDeclaredConstructor(long.class).newInstance(oVal);
							method.invoke(r, fVal);
						}else if(oVal.getClass().getName().endsWith("List")){
							List<Map<String, Object>> lVal = (List<Map<String, Object>>)oVal;
							ParameterizedType pt = (ParameterizedType)f.getGenericType();
							Type[] type = pt.getActualTypeArguments();
							if(type != null && type.length > 0){
								List<Object> entityList = new ArrayList<Object>();
								for(Map<String, Object> mVal : lVal){
									Object entity = ((Class<?>)type[0]).newInstance();
									entityList.add(convertMapToT(mVal, entity));
								}
								method.invoke(r, entityList);
							}
						}else if(oVal.getClass().getName().endsWith("Map")){
							String getMethod = "g"+methodName.substring(1);
							Object fieldVal = r.getClass().getDeclaredMethod(getMethod).getReturnType().newInstance();
							Object tempO = convertMapToT((Map<String, Object>)oVal, fieldVal);
							method.invoke(r, tempO);
						}else if(oVal.getClass().getName().endsWith("Integer") && f.getType().getName().equals(Boolean.class.getName())){
							Boolean _tVal = ((Integer)oVal).intValue() > 0;
							method.invoke(r, _tVal);
						}else{
							throw new IllegalArgumentException("JSON字段类型与实体["+t.toString()+"]字段类型不匹配,查看字段："+key);
						}
					} catch (IllegalArgumentException e) {
						Logger.getLogger(t.getClass()).error("JSON字段["+key+"]类型与实体["+t.toString()+"]字段类型不匹配.", e);
						continue;
					} catch (InvocationTargetException e) {
						Logger.getLogger(t.getClass()).error("反射机制调用方法["+methodName+"]失败.", e);
						continue;
					} catch (NoSuchMethodException e) {
						Logger.getLogger(t.getClass()).error("实体["+t.toString()+"]中没有该方法["+methodName+"].", e);
						continue;
					}
				}
			}
		}
		return r;
	}
	
	public static void main(String[] args){
		System.out.println(String.class.getName());
	}
}
