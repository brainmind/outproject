package com.wechat.client.business.model;

/**
 * 1-日常保养；2-更换电瓶；3-更换刹车片；4-加装PM2.5空调滤膜 
 * @author yangzhou
 */
public enum ServicesType {
	Maintenance{
		public int getType(){ return 1; }
		public String getName(){ return "日常保养"; }
	},
	Battery{
		public int getType(){ return 2; }
		public String getName(){ return "更换电瓶"; }
	},
	BrakePads{
		public int getType(){ return 3; }
		public String getName(){ return "更换刹车片"; }
	},
	AirFilter{
		public int getType(){ return 4; }
		public String getName(){ return "加装PM2.5空调滤膜"; }
	},
	Tire{
		public int getType(){ return 5; }
		public String getName(){ return "更换轮胎"; }
	};
	
	public static ServicesType getType(int type){
		for(ServicesType st : ServicesType.values()){
			if(st.getType() == type){
				return st;
			}
		}
		return null;
	}
	
	public static String getNameByType(int type){
		for(ServicesType st : ServicesType.values()){
			if(st.getType() == type){
				return st.getName();
			}
		}
		return "";
	}
	
	public static String getNameByType(String type){
		if(type==null || "".equals(type)){
			return "";
		}
		return getNameByType(Integer.parseInt(type));
	}
	

	public int getType(){
		throw new AbstractMethodError();
	}
	public String getName(){
		throw new AbstractMethodError();
	}
}
