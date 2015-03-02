package com.wechat.client.business.model;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class AppraiseFile {
	private String file_id;
	private String thumb_uri;
	private String type;
	private String uri;
	public String getFile_id() {
		return file_id;
	}
	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}
	public String getThumb_uri() {
		return thumb_uri;
	}
	public void setThumb_uri(String thumb_uri) {
		this.thumb_uri = thumb_uri;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
}
