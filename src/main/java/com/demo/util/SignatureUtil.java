package com.demo.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.UUID;

public class SignatureUtil {
	
	 public static String getSignature(String jsapi_ticket, String url,String nonceStr,String timestamp) {
	        String string1;
	        String signature = "";
	  
	        //ע���������������ȫ��Сд���ұ�������
	        string1 = "jsapi_ticket=" + jsapi_ticket +
	                  "&noncestr=" + nonceStr +
	                  "&timestamp=" + timestamp +
	                  "&url=" + url;
	        //System.out.println(string1);
	  
	        try
	        {
	            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
	            crypt.reset();
	            crypt.update(string1.getBytes("UTF-8"));
	            signature = byteToHex(crypt.digest());
	        }
	        catch (NoSuchAlgorithmException e)
	        {
	            e.printStackTrace();
	        }
	        catch (UnsupportedEncodingException e)
	        {
	            e.printStackTrace();
	        }
	  
	        return signature;
	    }
	 
	 
	 private static String byteToHex(final byte[] hash) {
	        Formatter formatter = new Formatter();
	        for (byte b : hash)
	        {
	            formatter.format("%02x", b);
	        }
	        String result = formatter.toString();
	        formatter.close();
	        return result;
	    }
	  
	 public static String create_nonce_str() {
	        return UUID.randomUUID().toString();
	    }
	  
	    public  static String create_timestamp() {
	        return Long.toString(System.currentTimeMillis() / 1000);
	    }
}
