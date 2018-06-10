package com.demo.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.demo.mapper.TbOrderMapper;
import com.demo.mapper.TbReceiveOrderMapper;
import com.demo.mapper.TbUserMapper;
import com.demo.pojo.TbOrder;
import com.demo.pojo.TbReceiveOrder;
import com.demo.pojo.TbUser;
import com.demo.pojo.UpdateStatusByOrderId;
import com.demo.service.AutoPayforService;
import com.demo.util.WXPayUtil;

@Service
public class AutoPayForServiceImpl implements AutoPayforService {

    private static Logger log = LoggerFactory.getLogger(AutoPayForServiceImpl.class);

    @Autowired
    private TbReceiveOrderMapper receiveOrderMapper;

    @Autowired
    private TbOrderMapper tbOrderMapper;

    @Autowired
    private TbUserMapper tbUserMapper;

    @Value("${APPID}")
    private String appId;

    @Value("${MCH_ID}")
    private String mchId;

    @Value("${APPSECRET}")
    private String appScr;

    @Override
    public void autoPayFor() {
        // TODO Auto-generated method stub
        try {
            List<TbReceiveOrder> list = receiveOrderMapper.selectNoPayOrder();
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    TbReceiveOrder receiveOrder = list.get(i);
                    Date date = new Date();
                    if (getSubDay(date, receiveOrder.getUpdateTime()) >= 2) {
                        TbUser user = tbUserMapper.selectByPrimaryKey(receiveOrder.getOpenid());
                        TbOrder order = tbOrderMapper.selectByPrimaryKey(receiveOrder.getOrderId());
                        if (order.getPayid() != null) {
                            if (user.getCreditScore() != 666) {
                                Map<String, String> map = doPayFor(order, receiveOrder.getOpenid());
                                if (map.get("result_code").equals("SUCCESS")) {
                                    receiveThing(receiveOrder.getOrderId());
                                } else {
                                    log.info("******循环内部自动放款失败******");
                                }
                            } else if (user.getCreditScore() == 666) {
                                receiveThing(receiveOrder.getOrderId());
                                log.info("******内部人员不放款，修改订单状态成功******");
                            }
                        } else {
                            receiveThing(receiveOrder.getOrderId());
                            log.info("******没付钱的直接确认，修改订单状态成功******");
                        }
                    } else {
                        continue;
                    }
                }
            }
        } catch (Exception e) {
            log.info("******自动放款失败******");
            e.printStackTrace();
        }
    }

    public long getSubDay(Date now, Date before) {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        String dateNow = ft.format(now);
        String dateBefore = ft.format(before);
        long subresult = 0;
        try {
            subresult = ft.parse(dateNow).getTime() - ft.parse(dateBefore).getTime();
            subresult = subresult / 1000 / 60 / 60 / 24;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subresult;
    }

    public int receiveThing(int orderid) {
        // TODO Auto-generated method stub
        int i = 0;
        try {
            TbOrder order = tbOrderMapper.selectByPrimaryKey(orderid);
            if (order.getStatus() == 2) {
                UpdateStatusByOrderId pojo = new UpdateStatusByOrderId(3, (short) 2, orderid);
                i = tbOrderMapper.updateStatusByOrderId(pojo);
            }
        } catch (Exception e) {
            log.info("******接收货物更改状态失败******");
            e.printStackTrace();
        }
        return i;
    }

    public Map<String, String> doPayFor(TbOrder order, String openid) {
        // TODO Auto-generated method stub
        Map<String, String> map = null;
        try {
            int price = (order.getHurry()) ? 100 * (Integer.parseInt(order.getSize()) + 1) : 100 * (Integer.parseInt(order.getSize()));
            String xml = createPayForXml(openid, price + "", "115.159.25.20");
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            FileInputStream instream = new FileInputStream(new File("/opt/apiclient_cert.p12"));
            try {
                keyStore.load(instream, mchId.toCharArray());
            } finally {
                instream.close();
            }
            // Trust own CA and all self-signed certs
            SSLContext sslcontext = SSLContexts.custom()
                    .loadKeyMaterial(keyStore, mchId.toCharArray())
                    .build();
            // Allow TLSv1 protocol only
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[]{"TLSv1"},
                    null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            CloseableHttpClient httpclient = HttpClients.custom()
                    .setSSLSocketFactory(sslsf)
                    .build();
            try {
                HttpPost httppost = new HttpPost("https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers");
                StringEntity reqEntity = new StringEntity(xml, "UTF-8");
                httppost.setEntity(reqEntity);
                CloseableHttpResponse response = httpclient.execute(httppost);
                try {
                    map = WXPayUtil.xmlToMap(EntityUtils.toString(response.getEntity(), "utf-8"));
                } finally {
                    response.close();
                }
            } finally {
                httpclient.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public String createPayForXml(String openid, String price, String ip) {
        String xml = "";
        try {
            Map<String, String> map = new HashMap<String, String>();
            int resultPric = (int) (Float.parseFloat(price) * 0.9);
            map.put("mch_appid", appId);
            map.put("mchid", mchId);
            map.put("nonce_str", WXPayUtil.generateNonceStr());
            map.put("partner_trade_no", WXPayUtil.generateUUID());
            map.put("openid", openid);
            map.put("check_name", "NO_CHECK");
            map.put("amount", resultPric + "");
            map.put("desc", "帮取快递的费用");
            map.put("spbill_create_ip", ip);
            map.put("sign", WXPayUtil.generateSignature(map, appScr));
            xml = WXPayUtil.mapToXml(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xml;
    }

}
