package com.wechat.client.business.model;

import java.util.List;

public class AppraiseItem {
	private String field_id;
	private String label;
	private String type;
	private List<AppraiseItemOption> options;
	private String value;
	private String requeired;
	public String getField_id() {
		return field_id;
	}
	public void setField_id(String field_id) {
		this.field_id = field_id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<AppraiseItemOption> getOptions() {
		return options;
	}
	public void setOptions(List<AppraiseItemOption> options) {
		this.options = options;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getRequeired() {
		return requeired;
	}
	public void setRequeired(String requeired) {
		this.requeired = requeired;
	}
}
