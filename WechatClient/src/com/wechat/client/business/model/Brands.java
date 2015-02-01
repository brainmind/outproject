/**
 * Brands.java
 * @author: 杨洲
 * @date: 2015-2-1
 */
package com.wechat.client.business.model;

import java.util.List;

public class Brands {
	private String label;
	private String first_letter;
	private String logo_url;
	private List<Series> series;
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
	 * @return the first_letter
	 */
	public String getFirst_letter() {
		return first_letter;
	}
	/**
	 * @param first_letter the first_letter to set
	 */
	public void setFirst_letter(String first_letter) {
		this.first_letter = first_letter;
	}
	/**
	 * @return the logo_url
	 */
	public String getLogo_url() {
		return logo_url;
	}
	/**
	 * @param logo_url the logo_url to set
	 */
	public void setLogo_url(String logo_url) {
		this.logo_url = logo_url;
	}
	/**
	 * @return the series
	 */
	public List<Series> getSeries() {
		return series;
	}
	/**
	 * @param series the series to set
	 */
	public void setSeries(List<Series> series) {
		this.series = series;
	}
}
