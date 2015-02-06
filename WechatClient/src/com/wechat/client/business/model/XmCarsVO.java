package com.wechat.client.business.model;

import java.util.List;


public class XmCarsVO {
	private String label;  //小马车型标识
	private String first_letter; //品牌
	private String logo_url;//品牌图片路径
	private List<Series> series;//系列
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getFirst_letter() {
		return first_letter;
	}
	public void setFirst_letter(String first_letter) {
		this.first_letter = first_letter;
	}
	public String getLogo_url() {
		return logo_url;
	}
	public void setLogo_url(String logo_url) {
		this.logo_url = logo_url;
	}
	public List<Series> getSeries() {
		return series;
	}
	public void setSeries(List<Series> series) {
		this.series = series;
	}
	
}
