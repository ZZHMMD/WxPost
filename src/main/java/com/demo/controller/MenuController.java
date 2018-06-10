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

    //查询全部菜单
    @RequestMapping(value = "/get")
    public String getMenu() {
        // 调用接口获取access_token
        String at = AccessTokenThread.accessToken.getToken();
        JSONObject jsonObject =null;
        if (at != null) {
            // 调用接口查询菜单
            jsonObject = menuService.getMenu(at);
            // 判断菜单创建结果
            return String.valueOf(jsonObject);
        }
        log.info("token为"+at);
        return "无数据";
    }

    //创建菜单
    @RequestMapping(value = "/create")
    public void createMenu() {
        // 调用接口获取access_token
        String at = AccessTokenThread.accessToken.getToken();
        int result=0;
        if (at != null) {

            // 调用接口创建菜单
            result = menuService.createMenu(getFirstMenu(),at);
            // 判断菜单创建结果
            if (0 == result) {
                log.info("菜单创建成功！");
            } else {
                log.info("菜单创建失败，错误码：" + result);
            }
        }
       // System.out.println(result);
        //return result ;
    }

    //删除菜单
    @RequestMapping(value = "/delete")
    public void deleteMenu() {
        // 调用接口获取access_token
        String at = AccessTokenThread.accessToken.getToken();
        int result=0;
        if (at != null) {
            // 删除菜单
            result = menuService.deleteMenu(at);
            // 判断菜单删除结果
            if (0 == result) {
                log.info("菜单删除成功！");
            } else {
                log.info("菜单删除失败，错误码：" + result);
            }
        }
       // return  result;
    }




        /**
         * 组装菜单数据
         */
    //Map<String, Object>
    public static Map<String, Object> getFirstMenu(){
        
        Menu menu1=new Menu();
        menu1.setId("1");
        menu1.setName("进入应用");
        menu1.setType("view");
        menu1.setKey("1");
        menu1.setUrl("http://www.kuaidibiaoju.com/WxPost/usercenter");

        /*Menu menu11=new Menu();
        menu11.setId("11");
        menu11.setName("个人中心");
        menu11.setType("view");
        menu11.setKey("11");
        menu11.setUrl("http://zzhmmd.ngrok.cc/WxPost/usercenter");

        Menu menu12=new Menu();
        menu12.setId("12");
        menu12.setName("我要发单");
        menu12.setType("view");
        menu12.setKey("12");
        menu12.setUrl("http://www.baidu.com");
        
        Menu menu13=new Menu();
        menu13.setId("13");
        menu13.setName("我要接单");
        menu13.setType("view");
        menu13.setKey("13");
        menu13.setUrl("http://www.baidu.com");*/

        //第二栏
        Menu menu2=new Menu();
        menu2.setId("2");
        menu2.setName("使用教程");
        menu2.setType("click");
        menu2.setKey("2");
        //menu2.setUrl("http://www.baidu.com");

        Menu menu3=new Menu();
        menu3.setId("3");
        menu3.setName("关于我们");
        menu3.setType("click");
        menu3.setKey("3");
        //menu3.setUrl("http://www.baidu.com");

        //最外一层大括号
        Map<String, Object> wechatMenuMap = new HashMap<String, Object>();

        //包装button的List
        List<Map<String, Object>> wechatMenuMapList = new ArrayList<Map<String, Object>>();

        //包装第一栏
        Map<String, Object> menuMap1 = new HashMap<String, Object>();
        /*Map<String, Object> menuMap11 = new HashMap<String, Object>();
        Map<String, Object> menuMap12 = new HashMap<String, Object>();
        Map<String, Object> menuMap13 = new HashMap<String, Object>();*/
        List<Map<String, Object>> subMenuMapList1 = new ArrayList<Map<String, Object>>();

        /*//第一栏第一个
        menuMap11.put("name",menu11.getName());
        menuMap11.put("type",menu11.getType());
        menuMap11.put("url",menu11.getUrl());
        subMenuMapList1.add(menuMap11);

        //第二栏第二个
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

        //包装第二栏
        Map<String, Object> menuMap2 = new HashMap<String, Object>();
        List<Map<String, Object>> subMenuMapList2 = new ArrayList<Map<String, Object>>();

        menuMap2.put("name",menu2.getName());
        menuMap2.put("type",menu2.getType());
        menuMap2.put("key", menu2.getKey());
        //menuMap2.put("url",menu3.getUrl());
        menuMap2.put("sub_button",subMenuMapList2);

        //包装第三栏
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