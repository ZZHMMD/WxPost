package com.demo.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.demo.pojo.JsSdkConfig;
import com.demo.pojo.WxUser;
import com.demo.service.OauthService;
import com.demo.util.SignatureUtil;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;


@Controller
public class OauthController {
	
	@Value("${APPID}")
	private String appid;
	
	private static Logger log = LoggerFactory.getLogger(OauthController.class);
	
	@Autowired
	private OauthService oauthService;
	
	//������Ϊ�˻�û�ȡaccesstoken ��code
	@RequestMapping("/usercenter")
	public String toUserCenter(HttpServletRequest request,HttpServletResponse response) throws Exception {	
		    String url = oauthService.getOatuchCode();
		    return "redirect:"+url;
	}
	
	@RequestMapping("/oauth")
	public String getAk(HttpServletRequest request,Model model){
		
		//�����Ƕ��� ��Ȩ�Ĵ���
		String code = request.getParameter("code");
		JSONObject jsonObject = oauthService.getAkAndOpenId(code);
		if(jsonObject!=null){
			String ak = jsonObject.getString("access_token");
			String openid = jsonObject.getString("openid");
			WxUser user = oauthService.getUserDetail(ak, openid);
			model.addAttribute("user", user);
		}		
		return "index";
	}
	
	@RequestMapping("/jssdkconfig")
	@ResponseBody
	public String getJssdkConfigDetail(String url){
		String nonceStr = SignatureUtil.create_nonce_str();
		Long timestamp = Long.parseLong(SignatureUtil.create_timestamp());
		String jsapi_ticket = JsApiTicketThread.jsApiTicket.getTicket();
		String signature = SignatureUtil.getSignature(jsapi_ticket, url, nonceStr, ""+timestamp);
		JsSdkConfig config = new JsSdkConfig();
		config.setAppid(appid);
		config.setNoncestr(nonceStr);
		config.setSignature(signature);
		config.setTimestamp(timestamp);
		String result =JSON.toJSONString(config);
		return result;
	}
	
	
	
	
	
	

}
