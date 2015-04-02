/**
 * ImageController.java
 * @author: 杨洲
 * @date: 2015-3-8
 */
package com.wechat.client.business.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wechat.client.business.model.LoginUser;
import com.wechat.client.utils.JsonHttpRequestUtil;

@Controller
@RequestMapping("/img")
public class ImageController extends BaseController {
	private Logger log = Logger.getLogger(ImageController.class);
	private String FileUpload = "/file.json";
	@RequestMapping("/upload")
	public void upload(HttpServletRequest request, HttpServletResponse response){
		try {
			LoginUser user = getLoginUser(request, response);
			InputStream is = request.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String str = "";
			StringBuffer img = new StringBuffer();
			while((str = br.readLine()) != null){
				img.append(str);
			}
			if(img.length() > 0){
				Map<String, Object> map = new ObjectMapper().readValue(img.toString(), new TypeReference<Map<String, Object>>(){});
				map.put("openid", user.getOpenid());
				map.put("type", "image");
				map.put("version", "1.0");
				map.put("encrypt_type", "0");
				map.put("timestamp", new Date().getTime());
				JsonHttpRequestUtil jr = new JsonHttpRequestUtil();
				String param = new ObjectMapper().writeValueAsString(map);
				String json = jr.doPost(FileUpload, param);
				Map<String, String> result = new ObjectMapper().readValue(json, new TypeReference<Map<String, Object>>(){});
				if(StringUtils.isNotEmpty(result.get("file_id"))){
					writeJson(response, "{\"error\":false,\"name\":\""+result.get("file_id")+"\",\"imagedata\":\""+map.get("base64")+"\"}");
				}else{
					writeJson(response, "{\"error\":true}");
				}
				return;
			}
			writeJson(response, "{\"error\":true}");
		} catch (IOException e) {
			writeJson(response, "{\"error\":true}");
		}
	}
}
