package com.wsyb.ray.mapping;

import org.springframework.web.bind.annotation.RequestMethod;
/**
 * 封装请求类型与Mapper的方法对应关系
 * @author yangzhou
 */
public class DataGetPathMapping {
	private String param = null;
	private RequestMethod rm;
	private DataAccessPath m;
	/**
	 * 构造方法
	 * @param rm 请求类型
	 * @param returnType 访问 Mybatis 的 Mapper方法返回类型
	 * @param params 访问 Mybatis 的 Mapper方法的要传递的参数【具体的参数值】
	 */
	public DataGetPathMapping(RequestMethod rm, String params){
		try {
			this.getPath(rm, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public enum DataAccessPath{
		
		Get{
			
			public RequestMethod toRequestMethod(){
				return RequestMethod.GET;
			}
			
			public String getURL(){
				return "selectByPrimaryKey";
			}
			
			private String pt = null;

			public String getParameter(){
				return pt;
			}
			
			public void setParameter(String pt){
				this.pt = pt;
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
			
			public String getURL(){
				return "insertSelective";
			}
			
			private String pt = null;

			public String getParameter(){
				return pt;
			}
			
			public void setParameter(String pt){
				this.pt = pt;
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
			
			public String getURL(){
				return "updateByPrimaryKeySelective";
			}
			
			private String pt = null;

			public String getParameter(){
				return pt;
			}
			
			public void setParameter(String pt){
				this.pt = pt;
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
			
			public String getURL(){
				return "deleteByPrimaryKey";
			}
			
			private String pt = null;

			public String getParameter(){
				return pt;
			}
			
			public void setParameter(String pt){
				this.pt = pt;
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
			
			public String getURL(){
				return "selectBy";
			}
			
			private String pt = null;

			public String getParameter(){
				return pt;
			}
			
			public void setParameter(String pt){
				this.pt = pt;
			}
			
			private Class<?> rt = Object.class;
			
			public void setReturnType(Class<?> rt){
				this.rt = rt;
			}
			
			public Class<?> getReturnType(){
				return rt;
			}
		},
		Patch{
			public RequestMethod toRequestMethod(){
				return RequestMethod.PATCH;
			}
			
			public String getURL(){
				return "selectObjectBy";
			}
			
			private String pt = null;

			public String getParameter(){
				return pt;
			}
			
			public void setParameter(String pt){
				this.pt = pt;
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
			
			public String getURL(){
				return "deleteObjectsBy";
			}
			
			private String pt = null;

			public String getParameter(){
				return pt;
			}
			
			public void setParameter(String pt){
				this.pt = pt;
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
		
		public String getURL(){
			throw new AbstractMethodError();
		}
		
		public String getParameter(){
			throw new AbstractMethodError();
		}
		
		public void setParameter(String pt){
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
	public DataAccessPath getPath(RequestMethod rm, String param) throws Exception{
		this.rm = rm;
		if(DataAccessPath.Get.toRequestMethod().equals(rm)){
			this.m = DataAccessPath.Get;
		}else if(DataAccessPath.Post.toRequestMethod().equals(rm)){
			this.m = DataAccessPath.Post;
		}else if(DataAccessPath.Put.toRequestMethod().equals(rm)){
			this.m = DataAccessPath.Put;
		}else if(DataAccessPath.Delete.toRequestMethod().equals(rm)){
			this.m = DataAccessPath.Delete;
		}else if(DataAccessPath.Options.toRequestMethod().equals(rm)){
			this.m = DataAccessPath.Options;
		}else if(DataAccessPath.Patch.toRequestMethod().equals(rm)){
			this.m = DataAccessPath.Patch;
		}else{
			throw new Exception("没有相匹配的方法或属性.");
		}
		this.m.setParameter(param);
		return this.m;
	}
	
	public static RequestMethod getRequestMethod(String method){
		if(DataAccessPath.Get.toString().toLowerCase().equals(method.toLowerCase())){
			return RequestMethod.GET;
		}else if(DataAccessPath.Post.toString().toLowerCase().equals(method.toLowerCase())){
			return RequestMethod.POST;
		}else if(DataAccessPath.Put.toString().toLowerCase().equals(method.toLowerCase())){
			return RequestMethod.PUT;
		}else if(DataAccessPath.Delete.toString().toLowerCase().equals(method.toLowerCase())){
			return RequestMethod.DELETE;
		}
		return null;
	}
	
	public String getParam(){
		return param;
	}

	public RequestMethod getRm() {
		return rm;
	}

	public DataAccessPath getPath() {
		return m;
	}
}
