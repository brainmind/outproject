/**
 * @Title: OrderLogVO.java 
 * @Package com.global.vo 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 刘京辉 
 * @date 2015-2-2 下午1:20:20 
 * @version V1.0 
 */
package com.wechat.client.business.model;

/**
 * @author user
 *
 */
public class XmOrderLogVO {
	private String orderid;
	private long create_time;
	private String operate_detail;
	/**
	 * @return the orderid
	 */
	public String getOrderid() {
		return orderid;
	}
	/**
	 * @param orderid the orderid to set
	 */
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	/**
	 * @return the create_time
	 */
	public long getCreate_time() {
		return create_time;
	}
	/**
	 * @param create_time the create_time to set
	 */
	public void setCreate_time(long create_time) {
		this.create_time = create_time;
	}
	/**
	 * @return the operate_detail
	 */
	public String getOperate_detail() {
		return operate_detail;
	}
	/**
	 * @param operate_detail the operate_detail to set
	 */
	public void setOperate_detail(String operate_detail) {
		this.operate_detail = operate_detail;
	}
}