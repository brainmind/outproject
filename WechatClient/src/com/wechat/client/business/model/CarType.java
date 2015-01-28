package com.wechat.client.business.model;

import java.io.Serializable;

public class CarType implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3048646432709751819L;

	private String id;
	
	private String label;
	
	private String logo_url;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLogo_url() {
		return logo_url;
	}

	public void setLogo_url(String logo_url) {
		this.logo_url = logo_url;
	}
}
