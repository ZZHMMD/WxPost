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
public class WxServiceImpl implements WxService {

    @Value("${PIC_URL_ABOUTUS}")
    private String picUrl;

    @Value("${MAIN_URL}")
    private String mainUrl;

    @Override
    public String processReuqest(HttpServletRequest request) {
        String respMessage = null;
        try {
            // 默认返回的文本消息内容
            String respContent = "请求处理异常，请稍候尝试！";
            //System.out.println("hello!");
            // xml请求解析
            Map<String, String> requestMap = MessageUtil.parseXml(request);

            // 发送方帐号（open_id）
            String fromUserName = requestMap.get("FromUserName");
            // 公众帐号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");

            // 回复文本消息
            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
            textMessage.setFuncFlag(0);

            //回复图文消息
            NewsMessage newsMessage = new NewsMessage();
            newsMessage.setToUserName(fromUserName);
            newsMessage.setFromUserName(toUserName);
            newsMessage.setCreateTime(new Date().getTime());
            newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
            newsMessage.setFuncFlag(0);

            //从微信接收到的文本消息
            String content = requestMap.get("Content");

            String EventKey = requestMap.get("EventKey");

            List<Article> articleList = new ArrayList<Article>();

            // 文本消息
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                //respContent = "您发送的是文本消息！";
                switch (content) {
                    case "9": {
                        StringBuffer buffer = new StringBuffer();
                        buffer.append("请按提示操作:").append("\n\n");
                        buffer.append("1.  进入应用").append("\n\n");
                        buffer.append("2.  使用介绍").append("\n\n");
                        buffer.append("3.  关于我们").append("\n\n");
                        buffer.append("4.  寻找客服");
                        respContent = String.valueOf(buffer);
                        textMessage.setContent(respContent);
                        respMessage = MessageUtil.textMessageToXml(textMessage);
                        break;
                    }
                    case "1": {
                        respContent = "进入应用";
                        //单图文发送
                        Article article = new Article();
                        article.setTitle("进入应用");
                        article.setDescription("开始使用快递镖局!");
                        article.setPicUrl(picUrl);
                        //这个用来转链接的url
                        article.setUrl(mainUrl + "/WxPost/usercenter");
                        articleList.add(article);

                        newsMessage.setArticleCount(articleList.size());
                        newsMessage.setArticles(articleList);

                        respMessage = MessageUtil.newsMessageToXml(newsMessage);
                        break;
                    }
                    case "2": {
                        respContent = "使用教程";
                        //单图文发送
                        Article article = new Article();
                        article.setTitle("进来看看如何使用吧");
                        article.setDescription("点击查看如何使用!");
                        article.setPicUrl(picUrl);
                        //这个用来转链接的url
                        article.setUrl(mainUrl + "/WxPost/biaoBook.jsp");
                        articleList.add(article);

                        newsMessage.setArticleCount(articleList.size());
                        newsMessage.setArticles(articleList);

                        respMessage = MessageUtil.newsMessageToXml(newsMessage);
                        break;
                    }
                    case "3": {
                        respContent = "关于我们";
                        //单图文发送
                        Article article = new Article();
                        article.setTitle("我们是一个团结友爱的团体");
                        article.setDescription("这是我们的整个团队!");
                        article.setPicUrl(picUrl);
                        //这个用来转链接的url
                        article.setUrl(mainUrl + "/WxPost/aboutUs.jsp");
                        articleList.add(article);

                        newsMessage.setArticleCount(articleList.size());
                        newsMessage.setArticles(articleList);

                        respMessage = MessageUtil.newsMessageToXml(newsMessage);
                        break;
                    }
                    case "4": {
                        respContent = "电话:13330308503";
                        textMessage.setContent(respContent);
                        respMessage = MessageUtil.textMessageToXml(textMessage);
                        break;
                    }

                    default: {
                        respContent = "很抱歉，功能正在完善!\n\n回复“9”查看帮助";
                        textMessage.setContent(respContent);
                        respMessage = MessageUtil.textMessageToXml(textMessage);
                    }
                }
            }
            // 图片消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
                //respContent = "您发送的是图片消息！";
            }
            // 地理位置消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
                // respContent = "您发送的是地理位置消息！";
            }
            // 链接消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
                // respContent = "您发送的是链接消息！";
            }
            // 音频消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
                //respContent = "您发送的是音频消息！";
            }
            // 事件推送
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                // 事件类型
                String eventType = requestMap.get("Event");
                // 订阅
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                    respContent = "感谢您关注快递镖局！";
                    textMessage.setContent(respContent);
                    respMessage = MessageUtil.textMessageToXml(textMessage);
                }
                // 取消订阅
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
                    // TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
                }
                // 自定义菜单点击事件
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                    // TODO 自定义菜单权没有开放，暂不处理该类消息
                    switch (EventKey) {
                        case "2": {
                            respContent = "使用教程";
                            Article article = new Article();
                            article.setTitle("进来看看如何使用吧");
                            article.setDescription("点击查看如何使用!");
                            article.setPicUrl(picUrl);
                            article.setUrl(mainUrl + "/WxPost/biaoBook.jsp");
                            articleList.add(article);
                            newsMessage.setArticleCount(articleList.size());
                            newsMessage.setArticles(articleList);
                            respMessage = MessageUtil.newsMessageToXml(newsMessage);
                            break;
                        }
                        case "3": {
                            respContent = "关于我们";
                            //单图文发送
                            Article article = new Article();
                            article.setTitle("我们是一个团结友爱的团体");
                            article.setDescription("这是我们的整个团队!");
                            article.setPicUrl(picUrl);
                            article.setUrl(mainUrl + "/WxPost/aboutUs.jsp");
                            articleList.add(article);
                            newsMessage.setArticleCount(articleList.size());
                            newsMessage.setArticles(articleList);
                            respMessage = MessageUtil.newsMessageToXml(newsMessage);
                            break;
                        }
                        case "4": {
                            respContent = "电话：13330308503";
                            textMessage.setContent(respContent);
                            respMessage = MessageUtil.textMessageToXml(textMessage);
                            break;
                        }
                        default: {
                            respContent = "很抱歉，此按键功能正在升级无法使用";
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
