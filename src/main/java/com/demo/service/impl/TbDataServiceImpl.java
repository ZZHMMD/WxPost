package com.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.mapper.TbOrderMapper;
import com.demo.mapper.TbReceiveOrderMapper;
import com.demo.mapper.TbUserMapper;
import com.demo.pojo.TbOrder;
import com.demo.pojo.TbOrderExample;
import com.demo.pojo.TbReceiveOrder;
import com.demo.pojo.TbReceiveOrderExample;
import com.demo.pojo.TbUser;
import com.demo.pojo.TbUserExample;
import com.demo.service.TbDataService;

@Service
public class TbDataServiceImpl implements TbDataService {

    @Autowired
    private TbOrderMapper orderMapper;

    @Autowired
    private TbUserMapper userMapper;

    @Autowired
    private TbReceiveOrderMapper receiveOrderMapper;

    @Override
    public List<TbUser> getAllUser() {
        // TODO Auto-generated method stub
        TbUserExample example = new TbUserExample();
        List<TbUser> list = userMapper.selectByExample(example);
        System.out.println("getAllUser");

        return list;
    }

    @Override
    public List<TbOrder> getAllOrder() {
        // TODO Auto-generated method stub
        TbOrderExample example = new TbOrderExample();
        List<TbOrder> list = orderMapper.selectByExample(example);
        System.out.println("getAllOrder");
        return list;
    }

    @Override
    public List<TbReceiveOrder> getAllReceiveOrder() {
        // TODO Auto-generated method stub
        TbReceiveOrderExample example = new TbReceiveOrderExample();
        List<TbReceiveOrder> list = receiveOrderMapper.selectByExample(example);
        System.out.println("getAllReceiveOrder");
        return list;
    }

}
