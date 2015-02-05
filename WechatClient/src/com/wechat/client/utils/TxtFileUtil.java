package com.wechat.client.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

public class TxtFileUtil {
	public static final String province = "provinces";
	public static final String city = "cities";
	public static final String district = "districts";
	private static Logger log = Logger.getLogger(TxtFileUtil.class);
	public static List<Map<String, String>> getDataList(String id, String type){
		List<Map<String, String>> datalist = new ArrayList<Map<String, String>>();
		BufferedReader br = null;
		try {
			String path = TxtFileUtil.class.getResource("/").getPath();
			br = new BufferedReader(new FileReader(path+"resources/"+type+".sw"));
			if(br.ready()){
				String ds = "";
				while((ds = br.readLine()) != null){
					String[] dsa = ds.split(",");
					if(TxtFileUtil.province.equals(type) || 
							((TxtFileUtil.city.equals(type) || TxtFileUtil.district.equals(type)) && dsa[5].equals(id))){
						Map<String, String> data = new HashMap<String, String>();
						data.put("id", dsa[0]);
						data.put("name", StringEscapeUtils.unescapeJava(dsa[1]));
						datalist.add(data);
					}
				}
			}
		} catch (FileNotFoundException e) {
			log.error("文件未找到", e);
		} catch (IOException e) {
			log.error("读取流发生错误", e);
		} finally{
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					log.error("读取流关闭时发生错误", e);
				}
			}
		}
		return datalist;
	}
	
	public static boolean validType(String type){
		return province.equals(type) || city.equals(type) || district.equals(type);
	}
	
	public static void main(String[] args){
		List<Map<String, String>> datalist = TxtFileUtil.getDataList("53ec9906cb9b11e3a770025041000001",TxtFileUtil.district);
		if(datalist != null){
			for(Map<String, String> d : datalist){
				System.out.println("id:"+d.get("id")+", name:"+d.get("name"));
			}
		}
	}
}
