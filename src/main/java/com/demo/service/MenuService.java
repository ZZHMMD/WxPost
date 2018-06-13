package com.demo.service;

import java.util.Map;

import com.demo.pojo.Menu;

import net.sf.json.JSONObject;

public interface MenuService {
    public JSONObject getMenu(String accessToken);

    //Map<String, Object>
    public int createMenu(Map<String, Object> menu, String accessToken);

    public int deleteMenu(String accessToken);

}
