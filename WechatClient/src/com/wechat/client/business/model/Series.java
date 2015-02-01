/**
 * Series.java
 * @author: 杨洲
 * @date: 2015-2-1
 */
package com.wechat.client.business.model;

import java.util.List;

public class Series {
	private String label;
	
	private List<Cars> cars;

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the cars
	 */
	public List<Cars> getCars() {
		return cars;
	}

	/**
	 * @param cars the cars to set
	 */
	public void setCars(List<Cars> cars) {
		this.cars = cars;
	}
}
