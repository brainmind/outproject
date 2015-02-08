/**
 * CarCache.java
 * @author: 杨洲
 * @date: 2015-2-8
 */
package com.wechat.client.business.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.wechat.client.business.model.Car;
import com.wechat.client.business.model.Cars;
import com.wechat.client.business.model.Series;
import com.wechat.client.business.model.XmCarsVO;

public class CarCache {
	private static CarCache cc = new CarCache();
	private Cars cars = null;
	private Map<String, XmCarsVO> brandsIndex = new HashMap<String, XmCarsVO>();
	private Map<String, Series> seriesIndex = new HashMap<String, Series>();
	
	private CarCache(){}
	
	public static CarCache getInstance(){
		return cc;
	}
	
	public void init(Cars c){
		cars = c;
		if(cars == null){
			throw new NullPointerException("车型数据初始化失败.");
		}
		initBrands();
	}
	
	public List<XmCarsVO> getBrandList(){
		return cars.getBrands();
	}
	
	public List<Series> getSerieList(String brandId){
		XmCarsVO brand = brandsIndex.get(brandId);
		if(brand != null){
			List<Series> serieList = brand.getSeries();
			return serieList;
		}
		return null;
	}
	
	public List<Car> getCarList(String serieId){
		Series serie = seriesIndex.get(serieId);
		if(serie != null){
			return serie.getCars();
		}
		return null;
	}
	
	private void initBrands(){
		try {
			initData(cars.getBrands(), 0);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	private <T> void initData(List<T> tList, int type) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		if(tList != null){
			for(int i=0; i<tList.size(); i++){
				T t = tList.get(i);
				String id = UUID.randomUUID().toString();
				Method setId = t.getClass().getDeclaredMethod("setId", String.class);
				setId.invoke(t, id);
				if(type == 0){
					brandsIndex.put(id, (XmCarsVO)t);
					initData(((XmCarsVO)t).getSeries(), 1);
				}else if(type == 1){
					seriesIndex.put(id, (Series)t);
				}
			}
		}
	}
}
