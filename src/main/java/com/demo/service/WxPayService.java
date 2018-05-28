package com.demo.service;

import javax.servlet.http.HttpServletRequest;

public interface WxPayService {
	
	 public  String getPrepayId(String price, String ip, String openid);
	
	 public  String getIpAddress(HttpServletRequest request);
	 
	 public  String doRefund(int orderid);
	 
	 public  String doPayFor(int orderid, HttpServletRequest request);
	 

}
