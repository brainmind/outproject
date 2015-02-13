package com.wechat.client.business.model;

import java.util.Date;

import org.apache.commons.httpclient.util.DateUtil;

public class LoginUser {
	private String openid;
	private String mobile;
	private String unionid;
	private String version="1.0";
	private String encrypt_type="0";
	private String timestamp = DateUtil.formatDate(new Date(), "yyyyMMddHHmmssSSS");

	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getUnionid() {
		return unionid;
	}
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getEncrypt_type() {
		return encrypt_type;
	}
	public void setEncrypt_type(String encrypt_type) {
		this.encrypt_type = encrypt_type;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
}
