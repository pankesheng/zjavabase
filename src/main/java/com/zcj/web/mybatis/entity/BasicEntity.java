package com.zcj.web.mybatis.entity;

import java.io.Serializable;
import java.util.Date;

public class BasicEntity implements Serializable {

	private static final long serialVersionUID = 51124911023734190L;

	private Long id;

	/** 创建时间 */
	private Date ctime;
	
	/** 更新时间 */
	private Date utime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Date getUtime() {
		return utime;
	}

	public void setUtime(Date utime) {
		this.utime = utime;
	}

}
