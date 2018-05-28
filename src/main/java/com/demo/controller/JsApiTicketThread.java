package com.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.demo.pojo.AccessToken;
import com.demo.pojo.JsApiTicket;
import com.demo.util.WeixinUtil;

/**
 * ��ʱ��ȡ΢��access_token���߳�
 *��WechatMpDemoApplication��ע��@EnableScheduling���ڳ�������ʱ�Ϳ�����ʱ����
 * ÿ7200��ִ��һ��
 */
@Component
@EnableScheduling
public class JsApiTicketThread {
    private static Logger log = LoggerFactory.getLogger(JsApiTicketThread.class);
    
    
    // �������û�Ψһƾ֤
    public static JsApiTicket jsApiTicket = null;

    @Scheduled(fixedDelay = 2*3600*1000)
    //7200��ִ��һ��
    public void getJsApiTicket(){
    	jsApiTicket= WeixinUtil.getTicket( AccessTokenThread.accessToken.getToken());
        if(null!=jsApiTicket){
            log.info("��ȡ�ɹ���jsApiTicket:"+jsApiTicket.getTicket());
            //log.info("***********"+appid2);
        }else {
            log.error("��ȡticketʧ��");
        }
    }
}
