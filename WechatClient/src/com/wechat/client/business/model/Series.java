package com.wechat.client.business.model;

import java.util.List;

public class Series {
	private String label;
	private List<Car> cars;
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public List<Car> getCars() {
		return cars;
	}
	public void setCars(List<Car> cars) {
		this.cars = cars;
	}
	
}
