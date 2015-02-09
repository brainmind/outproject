package com.wechat.client.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesUtils {
	private static Logger log = Logger.getLogger(PropertiesUtils.class);
	private static Properties props = new Properties();
	static {
		try {
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("resources/application.properties"));
		} catch (UnsupportedEncodingException e) {
			log.error("汉字的编码转换时出错", e);
		} catch (FileNotFoundException e) {
			log.error("读取错误代码文件时出错", e);
		} catch (IOException e) {
			log.error("读取错误代码文件时出错", e);
		}
	}
	public PropertiesUtils() {
	}
	
	public static String getValue(String key){
		return props.getProperty(key);
	}

	public static void updateProperties(String key, String value) {
		props.setProperty(key, value);
	}
}
