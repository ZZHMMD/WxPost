package com.demo.service;

import com.demo.pojo.WxUser;

import net.sf.json.JSONObject;

public interface OauthService {
	
	public String getOatuchCode() throws Exception;
	
	public JSONObject getAkAndOpenId(String code) ;
	
	public JSONObject refreshAK(String refreshTK);
	
	public WxUser getUserDetail(String ak, String openid);

}
