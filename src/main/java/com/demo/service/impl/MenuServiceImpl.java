package com.demo.service.impl;

import java.util.Map;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.demo.pojo.Menu;
import com.demo.service.MenuService;
import com.demo.util.WeixinUtil;

@Service
public class MenuServiceImpl implements MenuService {
	
	private static Logger log = LoggerFactory.getLogger(MenuServiceImpl.class);

	// �˵�������POST�� ��1000����/�죩
    public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

    // �˵���ѯ��POST�� ��10000����/�죩
    public static String menu_get_url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";

    // �˵�ɾ����POST�� ��1000����/�죩
    public static String menu_delete_url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";



    /**
     * ��ѯ�˵�
     *
     * @param accessToken ��Ч��access_token
     * @return
     */
    public JSONObject getMenu(String accessToken) {

        // ƴװ�����˵���url
        String url = menu_get_url.replace("ACCESS_TOKEN", accessToken);
        // ���ýӿڲ�ѯ�˵�
        JSONObject jsonObject = WeixinUtil.httpRequest(url, "GET", null);

        return jsonObject;
    }
    /**
     * �����˵�(�滻�ɲ˵�)
     *
     * @param accessToken ��Ч��access_token
     * @return 0��ʾ�ɹ�������ֵ��ʾʧ��
     * Map<String, Object>
     */
    public int createMenu(Map<String, Object> menu,String accessToken) {
        int result = 0;

        // ƴװ�����˵���url
        String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
        // ���˵�����ת����json�ַ���
        String jsonMenu = JSONObject.fromObject(menu).toString();
        // ���ýӿڴ����˵�
        JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST", jsonMenu);

        if (null != jsonObject) {
            if (0 != jsonObject.getInt("errcode")) {
                result = jsonObject.getInt("errcode");
                log.error("�����˵�ʧ�� errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
                log.error("****"+jsonMenu+"****");
            }
        }
        return result;
    }

    /**
     * ɾ���˵�
     *
     * @param accessToken ��Ч��access_token
     * @return 0��ʾ�ɹ�������ֵ��ʾʧ��
     */
    public int deleteMenu(String accessToken) {
        int result = 0;

        // ƴװ�����˵���url
        String url = menu_delete_url.replace("ACCESS_TOKEN", accessToken);

        // ���ýӿڴ����˵�
        JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST", null);

        if (null != jsonObject) {
            if (0 != jsonObject.getInt("errcode")) {
                result = jsonObject.getInt("errcode");
                log.error("ɾ���˵�ʧ�� errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return result;
    }
}


