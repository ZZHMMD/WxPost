package com.demo.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.demo.pojo.WxUser;
import com.demo.service.OauthService;
import com.demo.util.WeixinUtil;

import net.sf.json.JSONObject;

@Service
public class OauthServiceImpl implements OauthService {
	
	private static Logger log = LoggerFactory.getLogger(MenuServiceImpl.class);
	
	@Value("${APPID}")
	private String appid;
	
	@Value("${APPSECRET}")
	private String appsecret;
	
	@Value("${SERVER_URL}")
	private String Url;
	
	public static String code_url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
	
	public static String access_token_url ="https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	
	public static String refresh_token_url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";

	public static String wxuser_url ="https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	
	@Override
	public String getOatuchCode() throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		int result  = 0;
		
		String serverUrl = URLEncoder.encode(Url);
	    String url = code_url.replace("APPID", appid).replace("REDIRECT_URI", serverUrl);
	    //log.info(url);
	    return url;
	}

	@Override
	public JSONObject getAkAndOpenId(String code) {
		// TODO Auto-generated method stub

		String url = access_token_url.replace("APPID", appid).replace("SECRET", appsecret).replace("CODE", code);		
		JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST", null);
		if (!jsonObject.has("errcode")) {
           
                String rTk = jsonObject.getString("refresh_token");
                JSONObject result = refreshAK(rTk);
                return result;
        }else{
        	log.error("获取Ak失败"+"errcode:"+jsonObject.getString("errcode"));
        	log.info("getAkAndOpenId 错误！");
        }
		return null;
	}

	@Override
	public JSONObject refreshAK(String refreshTK) {
		// TODO Auto-generated method stub
		String url = refresh_token_url.replace("APPID", appid).replace("REFRESH_TOKEN", refreshTK);
		
        JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST", null);
		if (jsonObject.has("errcode")) {
            log.error("刷新Ak失败"+"errcode:"+jsonObject.getString("errcode"));
        }
		return jsonObject;
	}

	@Override
	public WxUser getUserDetail(String ak, String openid) {
		// TODO Auto-generated method stub
		String url = wxuser_url.replace("ACCESS_TOKEN", ak).replace("OPENID", openid).replace("APPID", appid);
		 JSONObject jsonObject = WeixinUtil.httpRequest(url, "GET", null);
		 //log.info(jsonObject.toString());
		 if(jsonObject!=null){
			 WxUser user = new WxUser();
			 user.setOpenid(openid);
			 user.setNickname(jsonObject.getString("nickname"));
			 user.setProvince(jsonObject.getString("province"));
			 user.setCity(jsonObject.getString("city"));
			 user.setCountry(jsonObject.getString("country"));
			 user.setHeadimgurl(jsonObject.getString("headimgurl"));
			 user.setPrivilege(jsonObject.getString("privilege"));
			// user.setUnionid(jsonObject.getString("unionid"));
			 user.setSex(jsonObject.getString("sex"));
			 return user;
		 }
		return null;
	}
	
	


}
