package com.demo.pojo;

import java.io.Serializable;

public class JsSdkConfig implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -9118620185010483270L;
	private String appid;
	private Long timestamp;
	private String noncestr;
	private String signature;
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public String getNoncestr() {
		return noncestr;
	}
	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	
	

}
