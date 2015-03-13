package com.wechat.client.business.model;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class WxMenus implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private List<WxMenu> button;

	public List<WxMenu> getButton() {
		return button;
	}

	public void setButton(List<WxMenu> button) {
		this.button = button;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
