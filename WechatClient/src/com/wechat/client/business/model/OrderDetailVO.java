/**
 * @Title: OrderDetailVO.java 
 * @Package com.global.vo 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 刘京辉 
 * @date 2015-2-2 下午3:44:21 
 * @version V1.0 
 */
package com.wechat.client.business.model;

import java.util.List;

/**
 * @author user
 *
 */
public class OrderDetailVO {
	private List commodities;
	private List service_fees;
	/**
	 * @return the commodities
	 */
	public List getCommodities() {
		return commodities;
	}
	/**
	 * @param commodities the commodities to set
	 */
	public void setCommodities(List commodities) {
		this.commodities = commodities;
	}
	/**
	 * @return the service_fees
	 */
	public List getService_fees() {
		return service_fees;
	}
	/**
	 * @param service_fees the service_fees to set
	 */
	public void setService_fees(List service_fees) {
		this.service_fees = service_fees;
	}
}