/**
 * @Title: XmOrderVO.java 
 * @Package com.global.vo 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 刘京辉 
 * @date 2015-2-2 上午9:45:49 
 * @version V1.0 
 */
package com.wechat.client.business.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author user
 *
 */
public class XmOrderVO {
	private String orderid;//订单id
	private String order_number;//订单编号
	private String car_label;//车型文本
	private String brand_logo;//车品牌url
	private String contact;//联系人
	private String mobile;//联系电话
	private String regine_code;//区域编码
	private String address;//服务地址
	private int state;//订单状态。-1：已取消；1-已提交；100-配货中；300-已完成
	private String commented;//评价状态。1:已评价；0:未评价
	private String status;//订单状态描述
	private long create_time;//创建时间 unix时间戳 毫秒
	private long finish_time;//完成时间 unix时间戳 毫秒
	private String reserve_time_string;//预约时间 字符串
	private BigDecimal total_price;//总价
	private List<XmOrderLogVO> logs;//订单日志
	private List<Commodity> commodities;
	private List<Servicefee> service_fees;
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
	 * @return the order_number
	 */
	public String getOrder_number() {
		return order_number;
	}
	/**
	 * @param order_number the order_number to set
	 */
	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}
	/**
	 * @return the car_label
	 */
	public String getCar_label() {
		return car_label;
	}
	/**
	 * @param car_label the car_label to set
	 */
	public void setCar_label(String car_label) {
		this.car_label = car_label;
	}
	/**
	 * @return the brand_logo
	 */
	public String getBrand_logo() {
		return brand_logo;
	}
	/**
	 * @param brand_logo the brand_logo to set
	 */
	public void setBrand_logo(String brand_logo) {
		this.brand_logo = brand_logo;
	}
	/**
	 * @return the contact
	 */
	public String getContact() {
		return contact;
	}
	/**
	 * @param contact the contact to set
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}
	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return the regine_code
	 */
	public String getRegine_code() {
		return regine_code;
	}
	/**
	 * @param regine_code the regine_code to set
	 */
	public void setRegine_code(String regine_code) {
		this.regine_code = regine_code;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}
	/**
	 * @return the commented
	 */
	public String getCommented() {
		return commented;
	}
	/**
	 * @param commented the commented to set
	 */
	public void setCommented(String commented) {
		this.commented = commented;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
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
	 * @return the finish_time
	 */
	public long getFinish_time() {
		return finish_time;
	}
	/**
	 * @param finish_time the finish_time to set
	 */
	public void setFinish_time(long finish_time) {
		this.finish_time = finish_time;
	}
	/**
	 * @return the reserve_time_string
	 */
	public String getReserve_time_string() {
		return reserve_time_string;
	}
	/**
	 * @param reserve_time_string the reserve_time_string to set
	 */
	public void setReserve_time_string(String reserve_time_string) {
		this.reserve_time_string = reserve_time_string;
	}
	/**
	 * @return the total_price
	 */
	public BigDecimal getTotal_price() {
		return total_price;
	}
	/**
	 * @param total_price the total_price to set
	 */
	public void setTotal_price(BigDecimal total_price) {
		this.total_price = total_price;
	}
	/**
	 * @return the logs
	 */
	public List<XmOrderLogVO> getLogs() {
		return logs;
	}
	/**
	 * @param logs the logs to set
	 */
	public void setLogs(List<XmOrderLogVO> logs) {
		this.logs = logs;
	}
	/**
	 * @return the commodities
	 */
	public List<Commodity> getCommodities() {
		return commodities;
	}
	/**
	 * @param commodities the commodities to set
	 */
	public void setCommodities(List<Commodity> commodities) {
		this.commodities = commodities;
	}
	/**
	 * @return the service_fees
	 */
	public List<Servicefee> getService_fees() {
		return service_fees;
	}
	/**
	 * @param service_fees the service_fees to set
	 */
	public void setService_fees(List<Servicefee> service_fees) {
		this.service_fees = service_fees;
	}
}
