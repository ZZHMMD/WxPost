package com.demo.service;

import java.util.List;

import com.demo.pojo.TbOrder;
import com.demo.pojo.TbReceiveOrder;
import com.demo.pojo.TbUser;

public interface TbDataService {

    List<TbUser> getAllUser();

    List<TbOrder> getAllOrder();

    List<TbReceiveOrder> getAllReceiveOrder();

}
