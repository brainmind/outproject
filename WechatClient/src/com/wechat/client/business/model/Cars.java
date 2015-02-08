/**
 * Cars.java
 * @author: 杨洲
 * @date: 2015-2-1
 */
package com.wechat.client.business.model;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Cars implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8206625491803470480L;
	private List<XmCarsVO> brands;
	private BaseBean baseBean;
	/**
	 * @return the brands
	 */
	public List<XmCarsVO> getBrands() {
		return brands;
	}

	/**
	 * @param brands the brands to set
	 */
	public void setBrands(List<XmCarsVO> brands) {
		this.brands = brands;
	}

	/**
	 * @return the baseBean
	 */
	public BaseBean getBaseBean() {
		return baseBean;
	}

	/**
	 * @param baseBean the baseBean to set
	 */
	public void setBaseBean(BaseBean baseBean) {
		this.baseBean = baseBean;
	}
}
