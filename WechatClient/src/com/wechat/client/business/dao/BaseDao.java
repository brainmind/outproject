/**
 * BaseDao.java
 * @author: 杨洲
 * @date: 2015-1-19
 */
package com.wechat.client.business.dao;

import org.springframework.jdbc.core.JdbcTemplate;

public class BaseDao {
	private JdbcTemplate jdbc;

	/**
	 * @return the jdbc
	 */
	public JdbcTemplate getJdbc() {
		return jdbc;
	}

	/**
	 * @param jdbc the jdbc to set
	 */
	public void setJdbc(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}
}
