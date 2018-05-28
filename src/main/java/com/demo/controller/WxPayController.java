package com.demo.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.service.WxPayService;
import com.demo.util.WXPayUtil;

@Controller
@RequestMapping("/pay")
public class WxPayController {
	
	@Autowired
	private WxPayService wpService;
	
	@RequestMapping("/sendorder")
	@ResponseBody
	public String getPrePayId(String price,String openid,HttpServletRequest request){
		String result = "";
		try {
			String ip =  wpService.getIpAddress(request);
			result = wpService.getPrepayId(price, ip, openid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping("/refund/{orderid}")
	@ResponseBody
	public String refund(@PathVariable int orderid){
		String result =  wpService.doRefund(orderid);
		return result;
	}
	
	@RequestMapping("/payfor/{orderid}")
	@ResponseBody
	public String payFor(@PathVariable int orderid,HttpServletRequest request){
		String result =  wpService.doPayFor(orderid, request);
		return result;
	}
	
	
	@RequestMapping("/getprepay")
	public String getPrePayId(){
		System.out.println("*******(99999999");

		return "";
	}

}
