package com.demo.util;

import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.demo.pojo.resp.Article;
import com.demo.pojo.resp.NewsMessage;
import com.demo.pojo.resp.TextMessage;

import com.thoughtworks.xstream.XStream;  
import com.thoughtworks.xstream.core.util.QuickWriter;  
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;  
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;  
import com.thoughtworks.xstream.io.xml.XppDriver;  

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;



/**
 * ��Ϣ������
 *
 */
public class MessageUtil {

    /**
     * ������Ϣ���ͣ��ı�
     */
    public static final String RESP_MESSAGE_TYPE_TEXT = "text";

    /**
     * ������Ϣ���ͣ�����
     */
    public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

    /**
     * ������Ϣ���ͣ�ͼ��
     */
    public static final String RESP_MESSAGE_TYPE_NEWS = "news";

    /**
     * ������Ϣ���ͣ��ı�
     */
    public static final String REQ_MESSAGE_TYPE_TEXT = "text";

    /**
     * ������Ϣ���ͣ�ͼƬ
     */
    public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

    /**
     * ������Ϣ���ͣ�����
     */
    public static final String REQ_MESSAGE_TYPE_LINK = "link";

    /**
     * ������Ϣ���ͣ�����λ��
     */
    public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

    /**
     * ������Ϣ���ͣ���Ƶ
     */
    public static final String REQ_MESSAGE_TYPE_VOICE = "voice";

    /**
     * ������Ϣ���ͣ�����
     */
    public static final String REQ_MESSAGE_TYPE_EVENT = "event";

    /**
     * �¼����ͣ�subscribe(����)and δ��עȺ��ɨ���ά��
     */
    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

    /**
     * �¼����ͣ��ѹ�עȺ��ɨ���ά��
     */
    public static final String EVENT_TYPE_SCAN="SCAN";
    /**
     * �¼����ͣ�unsubscribe(ȡ������)
     */
    public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

    /**
     * �¼����ͣ�CLICK(�Զ���˵�����¼�)
     */
    public static final String EVENT_TYPE_CLICK = "CLICK";
    /**
     * �¼����ͣ�VIEW(����Զ���˵���ת����ʱ���¼�)
     */
    public static final String EVENT_TYPE_VIEW = "VIEW";

    /**
     * �¼����ͣ�transfer_customer_service(����Ϣ���͸��ͷ�)
     */
    public static final String EVENT_TYPE_TRANSFER_CUSTOMER_SERVICE = "transfer_customer_service";


    /**
     * ����΢�ŷ���������XML��
     *
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    //����ĳЩ����ʱ�ľ�����Ϣ(��ǿ������ת����ʱ����������������)
    public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
        // ����������洢��HashMap��
        Map<String, String> map = new HashMap<String, String>();

        // ��request��ȡ��������
        InputStream inputStream = request.getInputStream();
        // ��ȡ������
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // �õ�xml��Ԫ��
        Element root = document.getRootElement();
        // �õ���Ԫ�ص������ӽڵ�
        List<Element> elementList = root.elements();

        // ���������ӽڵ�
        for (Element e : elementList)
            map.put(e.getName(), e.getText());

        // �ͷ���Դ
        inputStream.close();
        inputStream = null;

        return map;
    }

    /**
     * �ı���Ϣ����ת����xml
     *
     * @param textMessage �ı���Ϣ����
     * @return xml
     */
    public static String textMessageToXml(TextMessage textMessage) {
        xstream.alias("xml", textMessage.getClass());
        return xstream.toXML(textMessage);
    }


    /**
     * ͼ����Ϣ����ת����xml
     *
     * @param newsMessage ͼ����Ϣ����
     * @return xml
     */
    public static String newsMessageToXml(NewsMessage newsMessage) {
        xstream.alias("xml", newsMessage.getClass());
        xstream.alias("item", new Article().getClass());
        return xstream.toXML(newsMessage);
    }

    /**
     * ��չxstream��ʹ��֧��CDATA��
     *
     * @date 2013-05-19
     */
    private static XStream xstream = new XStream(new XppDriver() {
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                // ������xml�ڵ��ת��������CDATA���
                boolean cdata = true;

                @SuppressWarnings("unchecked")
                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                }

                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });
}