package com.wechat.client.business.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.wechat.client.business.model.CarType;
import com.wechat.client.business.model.ResultData;
import com.wechat.client.business.service.BaseService;
import com.wechat.client.business.service.CarService;

@Service("carService")
public class CarServiceImpl extends BaseService implements CarService{
	
	public ResultData<List<CarType>> getCarTypeList(HttpServletRequest request) {
		ResultData<List<CarType>> rd = doGetDataList(request, CarType.class);
		return rd;
	}
}