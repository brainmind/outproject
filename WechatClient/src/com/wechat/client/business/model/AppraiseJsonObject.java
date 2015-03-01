package com.wechat.client.business.model;

import java.util.List;

public class AppraiseJsonObject {
	private String orderid;
	private String commentid;
	private String comment_time;
	private List<AppraiseItem> fields;
	public String getCommentid() {
		return commentid;
	}
	public void setCommentid(String commentid) {
		this.commentid = commentid;
	}
	public String getComment_time() {
		return comment_time;
	}
	public void setComment_time(String comment_time) {
		this.comment_time = comment_time;
	}
	public List<AppraiseItem> getFields() {
		return fields;
	}
	public void setFields(List<AppraiseItem> fields) {
		this.fields = fields;
	}
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
}
