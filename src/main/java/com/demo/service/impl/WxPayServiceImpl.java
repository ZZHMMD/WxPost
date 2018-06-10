package com.demo.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.demo.mapper.TbOrderMapper;
import com.demo.mapper.TbReceiveOrderMapper;
import com.demo.pojo.TbOrder;
import com.demo.pojo.TbReceiveOrder;
import com.demo.pojo.TbReceiveOrderExample;
import com.demo.service.WxPayService;
import com.demo.util.WXPayUtil;

import net.sf.json.util.JSONUtils;


@Service
public class WxPayServiceImpl implements WxPayService {

    @Value("${APPID}")
    private String appId;

    @Value("${MCH_ID}")
    private String mchId;

    @Value("${APPSECRET}")
    private String appScr;

    private String orderUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    @Autowired
    private TbOrderMapper orderMapper;

    @Autowired
    private TbReceiveOrderMapper receiveOrderMapper;

    @Override
    public String getPrepayId(String price, String ip, String openid) {
        String result = "";
        try {
            String uuid = WXPayUtil.generateUUID();
            Map<String, String> map = new HashMap<String, String>();
            map.put("appid", appId);
            map.put("mch_id", mchId);
            map.put("nonce_str", WXPayUtil.generateNonceStr());
            map.put("body", "拿取快递的费用");
            map.put("out_trade_no", uuid);
            map.put("total_fee", price);
            map.put("spbill_create_ip", ip);
            map.put("notify_url", "http://www.kuaidibiaoju.com/pay/getprepay");
            map.put("trade_type", "JSAPI");
            map.put("openid", openid);

            String xml = WXPayUtil.generateSignedXml(map, appScr);
            result = WXPayUtil.httpsRequest(orderUrl, "GET", xml);
            Map<String, String> resultMap = WXPayUtil.doXMLParse(result);
            if (resultMap.containsKey("prepay_id")) {
                String prepayid = resultMap.get("prepay_id");
                Map<String, String> sendMap = new HashMap<String, String>();
                sendMap.put("appId", appId);
                sendMap.put("nonceStr", WXPayUtil.generateNonceStr());
                sendMap.put("package", "prepay_id=" + prepayid);
                sendMap.put("signType", "MD5");
                sendMap.put("timeStamp", WXPayUtil.getCurrentTimestamp() + "");
                sendMap.put("paySign", WXPayUtil.generateSignature(sendMap, appScr));
                sendMap.put("payid", uuid);
                sendMap.put("package", prepayid);
                result = JSONObject.toJSONString(sendMap);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String getIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknow".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                //根据网卡获取本机配置的IP地址
                InetAddress inetAddress = null;
                try {
                    inetAddress = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inetAddress.getHostAddress();
            }
        }
        if (null != ipAddress && ipAddress.length() > 15) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    @Override
    public String doRefund(int orderid) {
        // TODO Auto-generated method stub
        String result = "";
        try {
            TbOrder order = orderMapper.selectByPrimaryKey(orderid);
            int price = (order.getHurry()) ? 100 * (Integer.parseInt(order.getSize()) + 1) : 100 * (Integer.parseInt(order.getSize()));
            String xml = createRefundXml(order.getPayid(), price + "");
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
                HttpPost httppost = new HttpPost("https://api.mch.weixin.qq.com/secapi/pay/refund");
                StringEntity reqEntity = new StringEntity(xml, "UTF-8");
                httppost.setEntity(reqEntity);
                CloseableHttpResponse response = httpclient.execute(httppost);
                try {
                    Map<String, String> map = WXPayUtil.xmlToMap(EntityUtils.toString(response.getEntity(), "utf-8"));
                    result = JSONObject.toJSONString(map);
                } finally {
                    response.close();
                }
            } finally {
                httpclient.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 这个方法用来付款
     */
    @Override
    public String doPayFor(int orderid, HttpServletRequest request) {
        // TODO Auto-generated method stub
        String result = "";
        try {
            TbOrder order = orderMapper.selectByPrimaryKey(orderid);
            int price = (order.getHurry()) ? 100 * (Integer.parseInt(order.getSize()) + 1) : 100 * (Integer.parseInt(order.getSize()));
            TbReceiveOrderExample example = new TbReceiveOrderExample();
            example.createCriteria().andOrderIdEqualTo(orderid);
            TbReceiveOrder receiveOrder = receiveOrderMapper.selectByExample(example).get(0);
            String xml = createPayForXml(receiveOrder.getOpenid(), price + "", getIpAddress(request));
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
                    Map<String, String> map = WXPayUtil.xmlToMap(EntityUtils.toString(response.getEntity(), "utf-8"));
                    result = JSONObject.toJSONString(map);
                } finally {
                    response.close();
                }
            } finally {
                httpclient.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String createRefundXml(String out_refund_no, String price) {
        String xml = "";
        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("appid", appId);
            map.put("mch_id", mchId);
            map.put("nonce_str", WXPayUtil.generateNonceStr());
            map.put("out_refund_no", WXPayUtil.generateNonceStr());
            map.put("out_trade_no", out_refund_no);
            map.put("refund_fee", price);
            map.put("total_fee", price);
            map.put("sign", WXPayUtil.generateSignature(map, appScr));
            xml = WXPayUtil.mapToXml(map);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return xml;
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
