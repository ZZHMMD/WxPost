package com.demo.pojo;

import java.io.Serializable;

public class UserCheck implements Serializable {
	
	
	private static final long serialVersionUID = 6610015709643536006L;

	private String id;
	
	private Boolean enable;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}
	
	

}
