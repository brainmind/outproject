package com.wsyb.ray.mapping;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMethod;
/**
 * 封装请求类型与Mapper的方法对应关系
 * @author yangzhou
 */
public class MethodMapping {
	private Object[] param = null;
	private RequestMethod rm;
	private Method m;
	/**
	 * 构造方法
	 * @param rm 请求类型
	 * @param returnType 访问 Mybatis 的 Mapper方法返回类型
	 * @param params 访问 Mybatis 的 Mapper方法的要传递的参数【具体的参数值】
	 */
	public MethodMapping(RequestMethod rm, Object... params){
		try {
			this.getMethod(rm, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public enum Method{
		
		Get{
			
			public RequestMethod toRequestMethod(){
				return RequestMethod.GET;
			}
			
			public String getName(){
				return "selectByPrimaryKey";
			}
			
			private Class<?>[] pt = null;

			public Class<?>[] getParameterType(){
				return pt;
			}
			
			public Class<?>[] setParameterType(Class<?>[] pt){
				return this.pt = pt;
			}
			
			private Class<?> rt = Object.class;
			
			public void setReturnType(Class<?> rt){
				this.rt = rt;
			}
			
			public  Class<?> getReturnType(){
				return rt;
			}
		},
		Post{
			public RequestMethod toRequestMethod(){
				return RequestMethod.POST;
			}
			
			public String getName(){
				return "insertSelective";
			}
			
			private Class<?>[] pt = null;

			public Class<?>[] getParameterType(){
				return pt;
			}
			
			public Class<?>[] setParameterType(Class<?>[] pt){
				return this.pt = pt;
			}
			
			private Class<?> rt = Object.class;
			
			public void setReturnType(Class<?> rt){
				this.rt = rt;
			}
			
			public  Class<?> getReturnType(){
				return rt;
			}
		},
		Put{
			public RequestMethod toRequestMethod(){
				return RequestMethod.PUT;
			}
			
			public String getName(){
				return "updateByPrimaryKeySelective";
			}
			
			private Class<?>[] pt = null;

			public Class<?>[] getParameterType(){
				return pt;
			}
			
			public Class<?>[] setParameterType(Class<?>[] pt){
				return this.pt = pt;
			}
			
			private Class<?> rt = Object.class;
			
			public void setReturnType(Class<?> rt){
				this.rt = rt;
			}
			
			public  Class<?> getReturnType(){
				return rt;
			}
		},
		Delete{
			public RequestMethod toRequestMethod(){
				return RequestMethod.DELETE;
			}
			
			public String getName(){
				return "deleteByPrimaryKey";
			}
			
			private Class<?>[] pt = null;

			public Class<?>[] getParameterType(){
				return pt;
			}
			
			public Class<?>[] setParameterType(Class<?>[] pt){
				return this.pt = pt;
			}
			
			private Class<?> rt = Object.class;
			
			public void setReturnType(Class<?> rt){
				this.rt = rt;
			}
			
			public Class<?> getReturnType(){
				return rt;
			}
		},
		Options{
			public RequestMethod toRequestMethod(){
				return RequestMethod.OPTIONS;
			}
			
			public String getName(){
				return "selectBy";
			}
			
			private Class<?>[] pt = null;

			public Class<?>[] getParameterType(){
				return pt;
			}
			
			public Class<?>[] setParameterType(Class<?>[] pt){
				return this.pt = pt;
			}
			
			private Class<?> rt = Object.class;
			
			public void setReturnType(Class<?> rt){
				this.rt = rt;
			}
			
			public Class<?> getReturnType(){
				return rt;
			}
		},
		Trace{
			public RequestMethod toRequestMethod(){
				return RequestMethod.TRACE;
			}
			
			public String getName(){
				return "deleteObjectsBy";
			}
			
			private Class<?>[] pt = null;

			public Class<?>[] getParameterType(){
				return pt;
			}
			
			public Class<?>[] setParameterType(Class<?>[] pt){
				return this.pt = pt;
			}
			
			private Class<?> rt = Object.class;
			
			public void setReturnType(Class<?> rt){
				this.rt = rt;
			}
			
			public Class<?> getReturnType(){
				return rt;
			}
		};
		
		
		public RequestMethod toRequestMethod(){
			throw new AbstractMethodError();
		}
		
		public String getName(){
			throw new AbstractMethodError();
		}
		
		public Class<?>[] getParameterType(){
			throw new AbstractMethodError();
		}
		
		public Class<?>[] setParameterType(Class<?>[] pt){
			throw new AbstractMethodError();
		}
		
		public void setReturnType(Class<?> rt){
			throw new AbstractMethodError(); 
		}
		
		public Class<?> getReturnType(){
			throw new AbstractMethodError();
		}
	}
	/**
	 * 根据表单提交类型和参数，得到一个方法
	 * @param rm
	 * @param param 方法的参数[无参数可不用传递]
	 * @return
	 * @throws Exception
	 */
	public Method getMethod(RequestMethod rm, Object... param) throws Exception{
		this.rm = rm;
		if(Method.Get.toRequestMethod().equals(rm)){
			this.m = Method.Get;
		}else if(Method.Post.toRequestMethod().equals(rm)){
			this.m = Method.Post;
		}else if(Method.Put.toRequestMethod().equals(rm)){
			this.m = Method.Put;
		}else if(Method.Delete.toRequestMethod().equals(rm)){
			this.m = Method.Delete;
		}else if(Method.Options.toRequestMethod().equals(rm)){
			this.m = Method.Options;
		}else{
			throw new Exception("没有相匹配的方法或属性.");
		}
		this.m.setParameterType(getParameterType(param));
		return this.m;
	}
	
	private Class<?>[] getParameterType(Object... obj){
		if(obj == null || obj.length == 0){
			return null;
		}else{
			List<Class<?>> ptList = new ArrayList<Class<?>>();
			List<Object> paramList = new ArrayList<Object>();
			for(int i=0;i<obj.length;i++){
				if(obj[i] != null){
					ptList.add(obj[i].getClass());
					paramList.add(obj[i]);
				}
			}
			Class<?>[] pt = new Class<?>[ptList.size()];
			this.param = new Object[ptList.size()];
			ptList.toArray(pt);
			paramList.toArray(this.param);
			ptList = null;
			paramList = null;
			return pt;
		}
	}
	
	public static RequestMethod getRequestMethod(String method){
		if(Method.Get.toString().toLowerCase().equals(method.toLowerCase())){
			return RequestMethod.GET;
		}else if(Method.Post.toString().toLowerCase().equals(method.toLowerCase())){
			return RequestMethod.POST;
		}else if(Method.Put.toString().toLowerCase().equals(method.toLowerCase())){
			return RequestMethod.PUT;
		}else if(Method.Delete.toString().toLowerCase().equals(method.toLowerCase())){
			return RequestMethod.DELETE;
		}
		return null;
	}
	
	public Object[] getParam(){
		return param;
	}

	public RequestMethod getRm() {
		return rm;
	}

	public Method getM() {
		return m;
	}
}
