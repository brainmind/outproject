package com.wechat.client.framework.listener;

import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.codehaus.jackson.JsonParser.Feature;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.wechat.client.business.model.Cars;
import com.wechat.client.business.service.CarCache;
import com.wechat.client.utils.JsonHttpRequestUtil;

public class CarLoadListener implements ServletContextListener {
	private final String accessUrl = "/cars.json";
	public void contextInitialized(ServletContextEvent sce) {
		JsonHttpRequestUtil jr = new JsonHttpRequestUtil();
		String json = jr.doGet(accessUrl);
		try {
			ObjectMapper om = new ObjectMapper();
			om.configure(Feature.CANONICALIZE_FIELD_NAMES, true);
			Cars result = om.readValue(json, new TypeReference<Cars>(){});
			CarCache cc = CarCache.getInstance();
			cc.init(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void contextDestroyed(ServletContextEvent sce) {
		
	}
}
