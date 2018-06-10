package com.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.demo.pojo.AccessToken;
import com.demo.pojo.JsApiTicket;
import com.demo.service.AutoPayforService;
import com.demo.util.WeixinUtil;

/**
 * 定时获取微信access_token的线程
 * 在WechatMpDemoApplication中注解@EnableScheduling，在程序启动时就开启定时任务。
 * 每7200秒执行一次
 */
@Component
@EnableScheduling
public class AccessTokenThread {
    private static Logger log = LoggerFactory.getLogger(AccessTokenThread.class);

    @Value("${APPID}")
    private String appid;


    // 第三方用户唯一凭证密钥
    @Value("${APPSECRET}")
    private String appsecret;

    @Autowired
    private AutoPayforService autoPayService;


    // 第三方用户唯一凭证
    public static AccessToken accessToken = null;

    public static JsApiTicket jsApiTicket = null;

    @Scheduled(fixedDelay = 2 * 3600 * 1000)
    //7200秒执行一次
    public void gettoken() {
        try {
            accessToken = WeixinUtil.getAccessToken(appid, appsecret);
            jsApiTicket = WeixinUtil.getTicket(accessToken.getToken());
            autoPayService.autoPayFor();
            if (null != accessToken) {
                log.info("获取成功，accessToken:" + accessToken.getToken());
            } else {
                log.error("获取token失败");
            }
            if (null != jsApiTicket) {
                log.info("获取成功，jsApiTicket:" + jsApiTicket.getTicket());
                //log.info("***********"+appid2);
            } else {
                log.error("获取ticket失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
