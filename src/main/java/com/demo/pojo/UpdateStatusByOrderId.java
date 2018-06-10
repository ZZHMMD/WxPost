package com.demo.pojo;

import java.util.Date;

public class UpdateStatusByOrderId {
	
	private Integer orderstatus;
	
	private Short receivestatus;
	
	private Integer id;

	public UpdateStatusByOrderId(int order,short receive,int id){
		this.id = id;
		this.orderstatus = order;
		this.receivestatus = receive;
	}

	public Integer getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(Integer orderstatus) {
		this.orderstatus = orderstatus;
	}

	public Short getReceivestatus() {
		return receivestatus;
	}

	public void setReceivestatus(Short receivestatus) {
		this.receivestatus = receivestatus;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	

}
