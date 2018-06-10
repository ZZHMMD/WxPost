package com.demo.pojo;

import java.io.Serializable;

public class JsApiTicket implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4541531222515676301L;

	public String ticket;
	
	public int expiresIn;

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
	
	

}
