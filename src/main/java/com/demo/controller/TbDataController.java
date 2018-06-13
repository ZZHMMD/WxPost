package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.demo.pojo.Result;
import com.demo.pojo.TbOrder;
import com.demo.pojo.TbReceiveOrder;
import com.demo.pojo.TbUser;
import com.demo.service.TbDataService;

@Controller
@RequestMapping("/data")
public class TbDataController {

    @Autowired
    private TbDataService dataService;


    @RequestMapping("/lxj1996/getOrder")
    @ResponseBody
    public String getOrder(String id) {
        List<TbOrder> list = dataService.getAllOrder();
        Result result = new Result();
        result.setObj(list);
        return JSON.toJSONString(result);
    }

    @RequestMapping("/lxj1996/getUser")
    @ResponseBody
    public String getUser() {
        List<TbUser> list = dataService.getAllUser();
        Result result = new Result();
        result.setObj(list);
        return JSON.toJSONString(result);
    }

    @RequestMapping("/lxj1996/getReceiveOrder")
    @ResponseBody
    public String getReceiveOrder() {
        List<TbReceiveOrder> list = dataService.getAllReceiveOrder();
        Result result = new Result();
        result.setObj(list);
        return JSON.toJSONString(result);
    }


}
