package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.demo.pojo.Menu;
import com.demo.service.MenuService;

@Controller
@RequestMapping("/menu")
public class MenuController {
	
	@Autowired
	private MenuService menuService;
	private static Logger log = LoggerFactory.getLogger(MenuController.class);

    //��ѯȫ���˵�
    @RequestMapping(value = "/get")
    public String getMenu() {
        // ���ýӿڻ�ȡaccess_token
        String at = AccessTokenThread.accessToken.getToken();
        JSONObject jsonObject =null;
        if (at != null) {
            // ���ýӿڲ�ѯ�˵�
            jsonObject = menuService.getMenu(at);
            // �жϲ˵��������
            return String.valueOf(jsonObject);
        }
        log.info("tokenΪ"+at);
        return "������";
    }

    //�����˵�
    @RequestMapping(value = "/create")
    public void createMenu() {
        // ���ýӿڻ�ȡaccess_token
        String at = AccessTokenThread.accessToken.getToken();
        int result=0;
        if (at != null) {

            // ���ýӿڴ����˵�
            result = menuService.createMenu(getFirstMenu(),at);
            // �жϲ˵��������
            if (0 == result) {
                log.info("�˵������ɹ���");
            } else {
                log.info("�˵�����ʧ�ܣ������룺" + result);
            }
        }
       // System.out.println(result);
        //return result ;
    }

    //ɾ���˵�
    @RequestMapping(value = "/delete")
    public void deleteMenu() {
        // ���ýӿڻ�ȡaccess_token
        String at = AccessTokenThread.accessToken.getToken();
        int result=0;
        if (at != null) {
            // ɾ���˵�
            result = menuService.deleteMenu(at);
            // �жϲ˵�ɾ�����
            if (0 == result) {
                log.info("�˵�ɾ���ɹ���");
            } else {
                log.info("�˵�ɾ��ʧ�ܣ������룺" + result);
            }
        }
       // return  result;
    }




        /**
         * ��װ�˵�����
         */
    //Map<String, Object>
    public static Map<String, Object> getFirstMenu(){
        
        Menu menu1=new Menu();
        menu1.setId("1");
        menu1.setName("����Ӧ��");
        menu1.setType("view");
        menu1.setKey("1");
        menu1.setUrl("https://www.huahuayu.com.cn/WxPost/usercenter");

        /*Menu menu11=new Menu();
        menu11.setId("11");
        menu11.setName("��������");
        menu11.setType("view");
        menu11.setKey("11");
        menu11.setUrl("http://zzhmmd.ngrok.cc/WxPost/usercenter");

        Menu menu12=new Menu();
        menu12.setId("12");
        menu12.setName("��Ҫ����");
        menu12.setType("view");
        menu12.setKey("12");
        menu12.setUrl("http://www.baidu.com");
        
        Menu menu13=new Menu();
        menu13.setId("13");
        menu13.setName("��Ҫ�ӵ�");
        menu13.setType("view");
        menu13.setKey("13");
        menu13.setUrl("http://www.baidu.com");*/

        //�ڶ���
        Menu menu2=new Menu();
        menu2.setId("2");
        menu2.setName("ʹ�ý̳�");
        menu2.setType("click");
        menu2.setKey("2");
        //menu2.setUrl("http://www.baidu.com");

        Menu menu3=new Menu();
        menu3.setId("3");
        menu3.setName("��������");
        menu3.setType("click");
        menu3.setKey("3");
        //menu3.setUrl("http://www.baidu.com");

        //����һ�������
        Map<String, Object> wechatMenuMap = new HashMap<String, Object>();

        //��װbutton��List
        List<Map<String, Object>> wechatMenuMapList = new ArrayList<Map<String, Object>>();

        //��װ��һ��
        Map<String, Object> menuMap1 = new HashMap<String, Object>();
        /*Map<String, Object> menuMap11 = new HashMap<String, Object>();
        Map<String, Object> menuMap12 = new HashMap<String, Object>();
        Map<String, Object> menuMap13 = new HashMap<String, Object>();*/
        List<Map<String, Object>> subMenuMapList1 = new ArrayList<Map<String, Object>>();

        /*//��һ����һ��
        menuMap11.put("name",menu11.getName());
        menuMap11.put("type",menu11.getType());
        menuMap11.put("url",menu11.getUrl());
        subMenuMapList1.add(menuMap11);

        //�ڶ����ڶ���
        menuMap12.put("name",menu12.getName());
        menuMap12.put("type",menu12.getType());
        menuMap12.put("url",menu12.getUrl());
        subMenuMapList1.add(menuMap12);
        
        menuMap13.put("name",menu13.getName());
        menuMap13.put("type",menu13.getType());
        menuMap13.put("url",menu13.getUrl());
        subMenuMapList1.add(menuMap13);*/

        menuMap1.put("name",menu1.getName());
        menuMap1.put("url",menu1.getUrl());
        menuMap1.put("type",menu1.getType());
        menuMap1.put("key", menu1.getKey());
        menuMap1.put("sub_button",subMenuMapList1);

        //��װ�ڶ���
        Map<String, Object> menuMap2 = new HashMap<String, Object>();
        List<Map<String, Object>> subMenuMapList2 = new ArrayList<Map<String, Object>>();

        menuMap2.put("name",menu2.getName());
        menuMap2.put("type",menu2.getType());
        menuMap2.put("key", menu2.getKey());
        //menuMap2.put("url",menu3.getUrl());
        menuMap2.put("sub_button",subMenuMapList2);

        //��װ������
        Map<String, Object> menuMap3 = new HashMap<String, Object>();
        List<Map<String, Object>> subMenuMapList3 = new ArrayList<Map<String, Object>>();

        menuMap3.put("name",menu3.getName());
        menuMap3.put("type",menu3.getType());
        menuMap3.put("key", menu3.getKey());
       // menuMap3.put("url",menu3.getUrl());
        menuMap3.put("sub_button",subMenuMapList3);

        wechatMenuMapList.add(menuMap1);
        wechatMenuMapList.add(menuMap2);
        wechatMenuMapList.add(menuMap3);
        wechatMenuMap.put("button",wechatMenuMapList);
        return  wechatMenuMap;
    }
}