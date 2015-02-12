package com.wechat.client.framework.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class Splitor extends TagSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9171370885178236296L;
	private String value;
	private String split;
	private int index;
	
	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		int length = value.length();
		for(int i=0; i<length; i++){
			if(i*index + index < length){
				value = value.substring(i*index, i*index + index) + split + value.substring(i*index + index, length);
			}else{
				break;
			}
		}
		try {
			this.pageContext.getResponse().getWriter().write(value);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doStartTag();
	}
	
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the split
	 */
	public String getSplit() {
		return split;
	}
	/**
	 * @param split the split to set
	 */
	public void setSplit(String split) {
		this.split = split;
	}
	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}
	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}
}
