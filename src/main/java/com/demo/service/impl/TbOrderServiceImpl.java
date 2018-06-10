package com.demo.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.demo.mapper.TbOrderMapper;
import com.demo.mapper.TbReceiveOrderMapper;
import com.demo.mapper.TbUserMapper;
import com.demo.pojo.TbOrder;
import com.demo.pojo.TbUser;
import com.demo.pojo.TbUserExample;
import com.demo.pojo.TbUserExample.Criteria;
import com.demo.pojo.UpdateStatusByOrderId;
import com.demo.service.TbOrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class TbOrderServiceImpl implements TbOrderService {

    private static Logger log = LoggerFactory.getLogger(TbOrderServiceImpl.class);

    @Value("${PAGE_SIZE}")
    private int pageSize;

    @Autowired
    private TbOrderMapper tbOrderMapper;

    @Autowired
    private TbUserMapper tbUserMapper;

    @Autowired
    private TbReceiveOrderMapper tbReceiveOrderMapper;

    @Override
    public TbOrder getOrderById(int id) {
        // TODO Auto-generated method stub
        TbOrder order = null;
        try {
            order = tbOrderMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            log.info("******查找订单失败******\n" + e);
        }
        return order;
    }

    @Override
    public int deleteOrderById(int id) {
        // TODO Auto-generated method stub
        int i = 0;
        try {
            i = tbOrderMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            log.info("******订单删除失败******");
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public int updateOrderById(TbOrder order) {
        // TODO Auto-generated method stub
        int i = 0;
        try {
            Date date = new Date();
            order.setUpdateTime(new Date(date.getTime()));
            i = tbOrderMapper.updateByPrimaryKeySelective(order);
        } catch (Exception e) {
            log.info("******订单更新失败******");
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public int insertOrder(TbOrder order) {
        // TODO Auto-generated method stub
        int i = 0;
        try {
            // 0代表还没注册 1代表正常 2代表基本不够
            int flag = checkUser(order.getOpenid());
            if (flag == 1) {
                Date date = new Date();
                order.setCreateTime(new java.sql.Date(date.getTime()));
                order.setUpdateTime(new java.sql.Date(date.getTime()));
                //设置订单的状态1表示的是 已经发单尚未被接单 2表示被接单 3表示订单已经送达 4订单超时
                order.setStatus(1);
                order.setEnable(false);

                i = tbOrderMapper.insert(order);

            } else if (flag == 2) {
                i = 2;
            } else {
                //用户还没有注册
                i = 3;
            }
        } catch (Exception e) {
            log.info("******订单添加失败******");
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public PageInfo<TbOrder> getOrderPageList(int pageNum) {
        // TODO Auto-generated method stub
        PageInfo<TbOrder> page = null;
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<TbOrder> list = tbOrderMapper.getOrderList();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            for (int i = 0; i < list.size(); i++) {
                TbOrder order = list.get(i);
                if (order.getHurry()) {
                    order.setSize((Integer.parseInt(order.getSize()) + 1) + "");
                }
                order.setShowTime(format.format(order.getCreateTime()));
                order.setReceiveNum("");
                if (order.getGetAddress().equals("1")) {
                    order.setGetAddress("南区宅25栋");
                } else if (order.getGetAddress().equals("2")) {
                    order.setGetAddress("南区宅17栋");
                } else if (order.getGetAddress().equals("3")) {
                    order.setGetAddress("南区宅35栋顺丰");
                } else if (order.getGetAddress().equals("4")) {
                    order.setGetAddress("南区7栋对面书报亭");
                } else if (order.getGetAddress().equals("5")) {
                    order.setGetAddress("火山驿站");
                } else if (order.getGetAddress().equals("6")) {
                    order.setGetAddress("北区绿野仙踪后菜鸟驿站");
                } else if (order.getGetAddress().equals("7")) {
                    order.setGetAddress("南区宅35栋邮政");
                } else if (order.getGetAddress().equals("8")) {
                    order.setGetAddress("南区校门口");
                } else if (order.getGetAddress().equals("9")) {
                    order.setGetAddress("南区后门邮政");
                } else if (order.getGetAddress().equals("10")) {
                    order.setGetAddress("北区京东派");
                } else if (order.getGetAddress().equals("11")) {
                    order.setGetAddress("南区京东派");
                }
            }
            page = new PageInfo<TbOrder>(list);
        } catch (Exception e) {
            log.info("******获取订单分页失败******");
        }

        return page;
    }

    @Override
    public int checkUser(String id) {
        // TODO Auto-generated method stub
        TbUserExample example = new TbUserExample();
        Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<TbUser> list = tbUserMapper.selectByExample(example);
        TbUser user = null;
        if (list.size() > 0) {
            user = list.get(0);
            if (user.getCreditScore() <= 80) {
                //账号信誉积分过低
                return 2;
            }
        } else {
            return 0;
        }
        return 1;
    }

    @Override
    public PageInfo<TbOrder> getSelfOrderPageList(int pageNum, String openid) {
        // TODO Auto-generated method stub
        PageInfo<TbOrder> page = null;
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<TbOrder> list = tbOrderMapper.getSelfOrderList(openid);
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            for (int i = 0; i < list.size(); i++) {
                TbOrder order = list.get(i);
                if (order.getHurry()) {
                    order.setSize((Integer.parseInt(order.getSize()) + 1) + "");
                }
                if (order.getStatus() == 2) {
                    TbUser user = tbOrderMapper.getTheUser(order.getId());
                    order.setUsername(user.getName());
                    order.setPhoneNum(user.getPhoneNum());
                }
                order.setShowTime(format.format(order.getUpdateTime()));
                if (order.getGetAddress().equals("1")) {
                    order.setGetAddress("南区宅25栋");
                } else if (order.getGetAddress().equals("2")) {
                    order.setGetAddress("南区宅17栋");
                } else if (order.getGetAddress().equals("3")) {
                    order.setGetAddress("南区宅35栋顺丰");
                } else if (order.getGetAddress().equals("4")) {
                    order.setGetAddress("南区7栋对面书报亭");
                } else if (order.getGetAddress().equals("5")) {
                    order.setGetAddress("火山驿站");
                } else if (order.getGetAddress().equals("6")) {
                    order.setGetAddress("北区绿野仙踪后菜鸟驿站");
                } else if (order.getGetAddress().equals("7")) {
                    order.setGetAddress("南区宅35栋邮政");
                } else if (order.getGetAddress().equals("8")) {
                    order.setGetAddress("南区校门口");
                } else if (order.getGetAddress().equals("9")) {
                    order.setGetAddress("南区后门邮政");
                } else if (order.getGetAddress().equals("10")) {
                    order.setGetAddress("北区京东派");
                } else if (order.getGetAddress().equals("11")) {
                    order.setGetAddress("南区京东派");
                }
            }
            page = new PageInfo<TbOrder>(list);
        } catch (Exception e) {
            log.info("******获取个人订单分页失败******");
            e.printStackTrace();
        }
        return page;
    }

    @Override
    public int receiveThing(String id) {
        // TODO Auto-generated method stub
        int i = 0;
        try {
            TbOrder order = tbOrderMapper.selectByPrimaryKey(Integer.parseInt(id));
            if (order.getStatus() == 2) {
                UpdateStatusByOrderId pojo = new UpdateStatusByOrderId(3, (short) 2, Integer.parseInt(id));
                i = tbOrderMapper.updateStatusByOrderId(pojo);
            }
        } catch (Exception e) {
            log.info("******接收货物更改状态失败******");
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public PageInfo<TbOrder> getHistoryOrderPageList(int pageNum, String openid) {
        PageInfo<TbOrder> page = null;
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<TbOrder> list = tbOrderMapper.getHistoryOrderList(openid);
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            for (int i = 0; i < list.size(); i++) {
                TbOrder order = list.get(i);
                if (order.getHurry()) {
                    order.setSize((Integer.parseInt(order.getSize()) + 1) + "");
                }
                order.setShowTime(format.format(order.getUpdateTime()));
                if (order.getGetAddress().equals("1")) {
                    order.setGetAddress("南区宅25栋");
                } else if (order.getGetAddress().equals("2")) {
                    order.setGetAddress("南区宅17栋");
                } else if (order.getGetAddress().equals("3")) {
                    order.setGetAddress("南区宅35栋顺丰");
                } else if (order.getGetAddress().equals("4")) {
                    order.setGetAddress("南区7栋对面书报亭");
                } else if (order.getGetAddress().equals("5")) {
                    order.setGetAddress("火山驿站");
                } else if (order.getGetAddress().equals("6")) {
                    order.setGetAddress("北区绿野仙踪后菜鸟驿站");
                } else if (order.getGetAddress().equals("7")) {
                    order.setGetAddress("南区宅35栋邮政");
                } else if (order.getGetAddress().equals("8")) {
                    order.setGetAddress("南区校门口");
                } else if (order.getGetAddress().equals("9")) {
                    order.setGetAddress("南区后门邮政");
                } else if (order.getGetAddress().equals("10")) {
                    order.setGetAddress("北区京东派");
                } else if (order.getGetAddress().equals("11")) {
                    order.setGetAddress("南区京东派");
                }
            }
            page = new PageInfo<TbOrder>(list);
        } catch (Exception e) {
            log.info("******获取个人订单分页失败******");
            e.printStackTrace();
        }
        return page;
    }

    @Override
    public int forOurSelt(int orderid) {
        // TODO Auto-generated method stub
        int i = 0;
        try {
            i = tbOrderMapper.forOurSelf(orderid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public int getNoAcceptOrderCount() {
        // TODO Auto-generated method stub
        return tbOrderMapper.getOrderList().size();
    }

    @Override
    public int updateEnableById(TbOrder order) {
        // TODO Auto-generated method stub
        int i = 0;
        try {
            i = tbOrderMapper.updateByPrimaryKeySelective(order);
        } catch (Exception e) {
            log.info("***更新订单取货状态失败***");
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public String getPhoneNumByOrderid(int orderid) {
        // TODO Auto-generated method stub
        String str = null;
        try {
            str = tbUserMapper.getPhoneNumByOrderid(orderid);
        } catch (Exception e) {
            log.info("***获取用户的电话失败***");
            e.printStackTrace();
        }
        return str;
    }

    @Override
    public boolean isFirstTimeUse(String openid) {
        // TODO Auto-generated method stub
        boolean flag = (tbOrderMapper.getOrderCountByOpenid(openid) != 0) ? false : true;
		/*
		boolean _tflag =false;
		_tflag = isThirdTimeUse(openid);
		boolean result =false;
		if(flag || _tflag) {
			result =true;
		}*/
        return flag;
    }

    @Override
    public boolean isThirdTimeUse(String openid) {
        // TODO Auto-generated method stub
        int count = tbOrderMapper.getThirdCountFrom11_11(openid);
        boolean _flag = false;
        if (count != 0)
            _flag = (count % 3 == 0) ? true : false;
        return _flag;
    }

}
