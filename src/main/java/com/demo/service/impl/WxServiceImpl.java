package com.demo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.demo.pojo.resp.Article;
import com.demo.pojo.resp.NewsMessage;
import com.demo.pojo.resp.TextMessage;
import com.demo.service.WxService;
import com.demo.util.MessageUtil;

@Service
public class WxServiceImpl  implements WxService{
	
	@Value("${PIC_URL_ABOUTUS}")
	private String picUrl;
	
	@Value("${MAIN_URL}")
	private String mainUrl;

	@Override
	public String processReuqest(HttpServletRequest request) {
		 String respMessage = null;  
	        try {  
	            // Ĭ�Ϸ��ص��ı���Ϣ����  
	            String respContent = "�������쳣�����Ժ��ԣ�";  
	           //System.out.println("hello!");
	            // xml�������  
	            Map<String, String> requestMap = MessageUtil.parseXml(request);  
	  
	            // ���ͷ��ʺţ�open_id��  
	            String fromUserName = requestMap.get("FromUserName");  
	            // �����ʺ�  
	            String toUserName = requestMap.get("ToUserName");  
	            // ��Ϣ����  
	            String msgType = requestMap.get("MsgType");  
	  
	            // �ظ��ı���Ϣ  
	            TextMessage textMessage = new TextMessage();  
	            textMessage.setToUserName(fromUserName);  
	            textMessage.setFromUserName(toUserName);  
	            textMessage.setCreateTime(new Date().getTime());  
	            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);  
	            textMessage.setFuncFlag(0);  
	            
	            //�ظ�ͼ����Ϣ
	            NewsMessage newsMessage = new NewsMessage();
	            newsMessage.setToUserName(fromUserName);
	            newsMessage.setFromUserName(toUserName);
	            newsMessage.setCreateTime(new Date().getTime());
	            newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
	            newsMessage.setFuncFlag(0);
	            
	            //��΢�Ž��յ����ı���Ϣ
	            String content = requestMap.get("Content");
	            
	            String EventKey = requestMap.get("EventKey");
	            
	            List<Article> articleList = new ArrayList<Article>();
	  
	            // �ı���Ϣ  
	            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {  
	                //respContent = "�����͵����ı���Ϣ��";  
	            	switch(content){
		            	case "9":{
		            		StringBuffer buffer  = new StringBuffer();
		            		buffer.append("�밴��ʾ����:").append("\n\n");
		            		buffer.append("1.  ����Ӧ��").append("\n\n");
		            		buffer.append("2.  ʹ�ý���").append("\n\n");
		            		buffer.append("3.  ��������");
		            		 respContent = String.valueOf(buffer);
		            		 textMessage.setContent(respContent);  
		     	            respMessage = MessageUtil.textMessageToXml(textMessage);  
		            		 break;
		            	}
		            	case "1":{
		            		respContent = "����Ӧ��";
		            		//��ͼ�ķ���
		            		Article article = new Article();
		            	    article.setTitle("����Ӧ��");
		            	    article.setDescription("��ʼʹ�ÿ���ھ�!");
		            	    article.setPicUrl(picUrl);
		            	    //�������ת���ӵ�url
		            	    article.setUrl(mainUrl+"/WxPost/usercenter");
		            	    articleList.add(article);
		            	    
		            	    newsMessage.setArticleCount(articleList.size());
		            	    newsMessage.setArticles(articleList);
		            		 
		     	            respMessage = MessageUtil.newsMessageToXml(newsMessage);
		     	            break;
		            	}
		            	case "2":{
		            		respContent = "ʹ�ý̳�";
		            		//��ͼ�ķ���
		            		Article article = new Article();
		            	    article.setTitle("�����������ʹ�ð�");
		            	    article.setDescription("����鿴���ʹ��!");
		            	    article.setPicUrl(picUrl);
		            	    //�������ת���ӵ�url
		            	    article.setUrl(mainUrl+"/WxPost/biaoBook.jsp");
		            	    articleList.add(article);
		            	    
		            	    newsMessage.setArticleCount(articleList.size());
		            	    newsMessage.setArticles(articleList);
		            		 
		     	            respMessage = MessageUtil.newsMessageToXml(newsMessage);
		     	            break;
		            	}
		            	case "3":{
		            		respContent = "��������";
		            		//��ͼ�ķ���
		            		Article article = new Article();
		            	    article.setTitle("������һ���Ž��Ѱ�������");
		            	    article.setDescription("�������ǵ������Ŷ�!");
		            	    article.setPicUrl(picUrl);
		            	    //�������ת���ӵ�url
		            	    article.setUrl(mainUrl+"/WxPost/aboutUs.jsp");
		            	    articleList.add(article);
		            	    
		            	    newsMessage.setArticleCount(articleList.size());
		            	    newsMessage.setArticles(articleList);
		            		 
		     	            respMessage = MessageUtil.newsMessageToXml(newsMessage);
		     	            break;
		            	}
		            	
		            	default:{
		            		respContent = "�ܱ�Ǹ��������������!\n\n�ظ���9���鿴����";
		            		 textMessage.setContent(respContent);  
		     	            respMessage = MessageUtil.textMessageToXml(textMessage);  
		            	}
	            	}
	            }  
	            // ͼƬ��Ϣ  
	            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {  
	                //respContent = "�����͵���ͼƬ��Ϣ��";  
	            }  
	            // ����λ����Ϣ  
	            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {  
	               // respContent = "�����͵��ǵ���λ����Ϣ��";  
	            }  
	            // ������Ϣ  
	            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {  
	               // respContent = "�����͵���������Ϣ��";  
	            }  
	            // ��Ƶ��Ϣ  
	            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {  
	                //respContent = "�����͵�����Ƶ��Ϣ��";  
	            }  
	            // �¼�����  
	            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {  
	                // �¼�����  
	                String eventType = requestMap.get("Event");  
	                // ����  
	                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {  
	                    respContent = "��л����ע����ھ֣�";  
	                    textMessage.setContent(respContent);  
	     	            respMessage = MessageUtil.textMessageToXml(textMessage);  
	                }  
	                // ȡ������  
	                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {  
	                    // TODO ȡ�����ĺ��û����ղ������ںŷ��͵���Ϣ����˲���Ҫ�ظ���Ϣ  
	                }  
	                // �Զ���˵�����¼�  
	                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {  
	                    // TODO �Զ���˵�Ȩû�п��ţ��ݲ����������Ϣ  
	                	switch(EventKey){
	                        case "2":{
	                        	respContent = "ʹ�ý̳�";
			            		Article article = new Article();
			            		article.setTitle("�����������ʹ�ð�");
			            	    article.setDescription("����鿴���ʹ��!");
			            	    article.setPicUrl(picUrl);
			            	    article.setUrl(mainUrl+"/WxPost/biaoBook.jsp");
			            	    articleList.add(article);
			            	    newsMessage.setArticleCount(articleList.size());
			            	    newsMessage.setArticles(articleList);
			     	            respMessage = MessageUtil.newsMessageToXml(newsMessage);
			     	            break;
	                        }
	                        case "3":{
	                        	respContent = "��������";
			            		//��ͼ�ķ���
			            		Article article = new Article();
			            	    article.setTitle("������һ���Ž��Ѱ�������");
			            	    article.setDescription("�������ǵ������Ŷ�!");
			            	    article.setPicUrl(picUrl);
			            	    article.setUrl(mainUrl+"/WxPost/aboutUs.jsp");
			            	    articleList.add(article);
			            	    newsMessage.setArticleCount(articleList.size());
			            	    newsMessage.setArticles(articleList);
			     	            respMessage = MessageUtil.newsMessageToXml(newsMessage);
			     	            break;
	                        }
	                        default:{            
	                            respContent= "�ܱ�Ǹ���˰����������������޷�ʹ��";
	                            textMessage.setContent(respContent);  
			     	            respMessage = MessageUtil.textMessageToXml(textMessage);  
	                        }
	                	}
	                }  
	            }  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	        return respMessage; 
	}

}
