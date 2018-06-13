package com.demo.service;

import java.util.List;

import com.demo.pojo.SelfReceiveOrder;
import com.demo.pojo.TbReceiveOrder;
import com.github.pagehelper.PageInfo;

public interface TbReceiveOrderService {

    public int insertReceiveOrder(TbReceiveOrder receiveOrder);

    public int deleteReceiveOrderById(int id);

    public int updateReceiveOrderById(TbReceiveOrder receiveOrder);

    public TbReceiveOrder getReceiveOrderById(int id);

    public PageInfo<TbReceiveOrder> getReceiveOrderByStatus(int status, int id, int pageNum);

    public int checkUser(String id);

    public PageInfo<SelfReceiveOrder> getSelfReceiveOrderPage(int pageNum, String openid);

    public PageInfo<SelfReceiveOrder> getSelfEnReceiveOrderPage(int pageNum, String openid);

    public PageInfo<SelfReceiveOrder> getSelfDisReceiveOrderPage(int pageNum, String openid);

    public PageInfo<SelfReceiveOrder> getSelfHistoryReceiveOrderPage(int pageNum, String openid);


}
