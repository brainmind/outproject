package com.wechat.client.business.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.wechat.client.business.model.CarType;
import com.wechat.client.business.model.LoginUser;
import com.wechat.client.business.model.ResultData;
import com.wechat.client.business.service.BaseService;
import com.wechat.client.business.service.CarService;
import com.wechat.client.utils.Constants;
import com.wsyb.ray.HttpEntityUtils;

@Service("carService")
public class CarServiceImpl extends BaseService implements CarService{
	
	private final String getByVINPath = "carsByVIN.json?";
	private final String getCarTypeList = "cars.json?";
	
	public ResultData<List<CarType>> getCarTypeList(HttpServletRequest request) {
		request.setAttribute("methodPath", getCarTypeList);
		LoginUser user = (LoginUser)request.getSession().getAttribute(Constants.USER_SESSION_KEY);
		if(user == null){
			return null;
		}
		String param = HttpEntityUtils.toParameterString(user).substring(1);
		request.setAttribute("param", param);
		ResultData<List<CarType>> rd = doGetDataList(request, ResultData.class);
		return rd;
	}
	
	public ResultData<List<CarType>> getCarTypeListByVin(HttpServletRequest request) {
		request.setAttribute("methodPath", getByVINPath);
		ResultData<List<CarType>> rd = doGetDataList(request, CarType.class);
		return rd;
	}
}