package com.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.demo.pojo.AccessToken;
import com.demo.util.WeixinUtil;

/**
 * ��ʱ��ȡ΢��access_token���߳�
 *��WechatMpDemoApplication��ע��@EnableScheduling���ڳ�������ʱ�Ϳ�����ʱ����
 * ÿ7200��ִ��һ��
 */
@Component
@EnableScheduling
public class AccessTokenThread {
    private static Logger log = LoggerFactory.getLogger(AccessTokenThread.class);
    
    @Value("${APPID}")
    private String appid;


    // �������û�Ψһƾ֤��Կ
    @Value("${APPSECRET}")
    private  String appsecret ;
    // �������û�Ψһƾ֤
    public static AccessToken accessToken = null;

    @Scheduled(fixedDelay = 2*3600*1000)
    //7200��ִ��һ��
    public void gettoken(){
        accessToken= WeixinUtil.getAccessToken(appid,appsecret);
        if(null!=accessToken){
            log.info("��ȡ�ɹ���accessToken:"+accessToken.getToken());
            //log.info("***********"+appid2);
        }else {
            log.error("��ȡtokenʧ��");
        }
    }
}
