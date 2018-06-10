package com.demo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.service.WxService;
import com.demo.util.StringUtil;

@Controller
public class WechatController {
	
	@Autowired
	private WxService wxService;
	
	@RequestMapping(value = "/wechat",method =RequestMethod.GET,produces = {"text/plain;charset=UTF-8"})
	@ResponseBody
	public String checkSignature( HttpServletResponse response,@RequestParam(value="signature",required=false)String signature,@RequestParam(value="nonce",required=false)String nonce,
			@RequestParam(value="timestamp",required=false)String timestamp,@RequestParam(value="echostr",required=false)String echostr) throws IOException{
		if(signature.equals(null)||timestamp.equals(null)||nonce.equals(null)){
			System.out.println("是谁进了这个方法！");
			return "";
		}
		if(StringUtil.checkSignature(signature, timestamp, nonce)){
			//System.out.println("接入成功");
			return echostr;
		}
		//System.out.println("接入失败");
		return "";
	}
	
	@RequestMapping(value="/wechat",method = RequestMethod.POST)
	public void receiveContent(HttpServletRequest request,HttpServletResponse response) throws Exception{
		 request.setCharacterEncoding("UTF-8");
	     response.setCharacterEncoding("UTF-8");
	     
	     PrintWriter out  = response.getWriter();
	    
		 String result = wxService.processReuqest(request);
	     out.print(result);
	     out.close();
	}

}
