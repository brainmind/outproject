/**
 * ImageController.java
 * @author: 杨洲
 * @date: 2015-3-8
 */
package com.wechat.client.business.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wechat.client.utils.PropertiesUtils;
import com.wechat.client.utils.UUIDUtil;

import sun.misc.BASE64Decoder;

@Controller
@RequestMapping("/img")
public class ImageController extends BaseController {

	@RequestMapping("/upload")
	public void upload(HttpServletRequest request, HttpServletResponse response){
		try {
			InputStream is = request.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String str = "";
			StringBuffer img = new StringBuffer();
			while((str = br.readLine()) != null){
				img.append(str);
			}
			if(img.length() > 0){
				Map<String, Object> map = new ObjectMapper().readValue(img.toString(), new TypeReference<Map<String, Object>>(){});
				String image = (String)map.get("base64");
				//Integer size = (Integer)map.get("size");
				String[] imageData = image.split(",");
				byte[] filedata = new BASE64Decoder().decodeBuffer(imageData[1]);
				String uploadPath = PropertiesUtils.getValue("image_upload_path");
				String suffix = imageData[0].substring(imageData[0].indexOf("/")+1, imageData[0].indexOf(";"));
				String fileName = UUIDUtil.generateUUID()+"."+suffix;
				File file = new File(uploadPath+"/"+fileName);
				FileOutputStream os = new FileOutputStream(file);
				os.write(filedata);
				os.flush();
				os.close();
				String accessPath = request.getContextPath()+PropertiesUtils.getValue("image_access_path");
				writeJson(response, "{\"error\":false,\"name\":\""+fileName+"\",\"path\":\""+accessPath+"\"}");
				return;
			}
			writeJson(response, "{\"error\":true}");
		} catch (IOException e) {
			writeJson(response, "{\"error\":true}");
		}
	}
}
