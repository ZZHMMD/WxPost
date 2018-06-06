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
import com.demo.pojo.SelfReceiveOrder;
import com.demo.pojo.TbOrder;
import com.demo.pojo.TbReceiveOrder;
import com.demo.pojo.TbReceiveOrderExample;
import com.demo.pojo.TbUser;
import com.demo.pojo.TbUserExample;
import com.demo.pojo.TbUserExample.Criteria;
import com.demo.service.TbReceiveOrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class TbReceiveOrderServiceImpl implements TbReceiveOrderService {
	
	@Value("${PAGE_SIZE}")
	private int pageSize;
	
	private static Logger log = LoggerFactory.getLogger(TbReceiveOrderServiceImpl.class);
	
	@Autowired
	private TbReceiveOrderMapper receiveOrderMapper;
	
	@Autowired
	private TbOrderMapper orderMapper;
	
	@Autowired
	private TbUserMapper userMapper;

	@Override
	public int insertReceiveOrder(TbReceiveOrder receiveOrder) {
		// TODO Auto-generated method stub
		int i = 0;
	   try{
		   
		   int j = checkUser(receiveOrder.getOpenid());
		   if(j ==1){
			   TbReceiveOrderExample example =  new TbReceiveOrderExample();
			   example.createCriteria().andOrderIdEqualTo(receiveOrder.getOrderId());
			   List<TbReceiveOrder> list = receiveOrderMapper.selectByExample(example);
			   
			   if(list.isEmpty()){
				   Date date = new Date();
				   TbOrder order = orderMapper.selectByPrimaryKey(receiveOrder.getOrderId());
				   order.setStatus(2);
				   int flag = orderMapper.updateByPrimaryKeySelective(order);
				   receiveOrder.setStatus((short)1);
				   receiveOrder.setUpdateTime(new java.sql.Date(date.getTime()));
				   receiveOrder.setCreateTime(new java.sql.Date(date.getTime()));
				   if(flag!=0)
					   i = receiveOrderMapper.insert(receiveOrder);
			   }else{
			   i =4;
			   }
		   }else if( j == 2){
			   i=2;
		   }else if(j==4){
			   //用户没注册
			   i=5;   
		   }else{
			   i=3;
		   }
	   }catch(Exception e){
		   log.info("******增加接单信息失败******");
		   e.printStackTrace();
	   }
		return i;
	}

	@Override
	public int deleteReceiveOrderById(int id) {
		// TODO Auto-generated method stub
		int i= 0;
		try{
			i = receiveOrderMapper.deleteByPrimaryKey(id);
		}catch(Exception e){
			log.info("******删除接单信息失败******\n"+e);
		}
		return i;
	}

	@Override
	public int updateReceiveOrderById(TbReceiveOrder receiveOrder) {
		// TODO Auto-generated method stub
		int i=0;
		try{
			i =  receiveOrderMapper.updateByPrimaryKeySelective(receiveOrder);
		}catch(Exception e){
			log.info("******更新接单信息失败******\n"+e);
		}
		return i;
	}

	@Override
	public TbReceiveOrder getReceiveOrderById(int id) {
		// TODO Auto-generated method stub
		TbReceiveOrder receiveOrder = null;
		try{
			receiveOrder = receiveOrderMapper.selectByPrimaryKey(id);
		}catch(Exception e){
			log.info("******查找接单信息失败******\n"+e);
		}
		return receiveOrder;
	}

	@Override
	public PageInfo<TbReceiveOrder> getReceiveOrderByStatus(int status, int id,int pageNum) {
		// TODO Auto-generated method stub
		PageInfo<TbReceiveOrder>  page= null;
		try{
			PageHelper.startPage(pageNum, pageSize);
			
			//List<TbReceiveOrder> list = receiveOrderMapper.selectReceiveOrderByStatus(status, id);
			
			//page = new PageInfo<TbReceiveOrder>(list);
		}catch(Exception e){
			log.info("******查找历史接单数据失败******");
		}
		return page;
	}
	
	@Override
	public int checkUser(String id) {
		// TODO Auto-generated method stub
		TbUserExample example = new TbUserExample();
		Criteria criteria =  example.createCriteria();
		criteria.andIdEqualTo(id);
		List<TbUser> list = userMapper.selectByExample(example);
		TbUser user = null;
		if(list.size() >0){
			 user = list.get(0);
				if(!user.getEnable()){
					//账号未通过验证
					return 2;
				}else if(user.getCreditScore() <=80){
					//账号信誉积分过低
					return 3;
				}
		}else{
			return 4;
		}
		
		return 1;
	}

	@Override
	public PageInfo<SelfReceiveOrder> getSelfReceiveOrderPage(int pageNum,String openid) {
		// TODO Auto-generated method stub
		PageInfo<SelfReceiveOrder> page = null;
		try{
			PageHelper.startPage(pageNum, pageSize);
			List<SelfReceiveOrder> list = receiveOrderMapper.selectSelfReceiveOrderByOpenid(openid);
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			for(int i=0;i<list.size();i++){
				SelfReceiveOrder receiveOrder = list.get(i);
				//System.out.println("getSelfReceiveOrderPage:"+receiveOrder.getFetchg());
				receiveOrder.setShowOrderTime(format.format(receiveOrder.getUpdateTime()));
				receiveOrder.setShowReceiveOrderTime(format.format(receiveOrder.getReceiveOrderTime()));
				if(receiveOrder.isHurry()){
					receiveOrder.setSize((Integer.parseInt(receiveOrder.getSize())+1)+"");
				}
				if(receiveOrder.getGetAddress().equals("1")){
					receiveOrder.setGetAddress("南区宅25栋");
				}else if(receiveOrder.getGetAddress().equals("2")){
					receiveOrder.setGetAddress("南区宅17栋");
				}else if(receiveOrder.getGetAddress().equals("3")){
					receiveOrder.setGetAddress("南区宅35栋顺丰");
				}else if(receiveOrder.getGetAddress().equals("4")){
					receiveOrder.setGetAddress("南区7栋对面书报亭");
				}else if(receiveOrder.getGetAddress().equals("5")){
					receiveOrder.setGetAddress("南区一食堂旁书报亭");
				}else if(receiveOrder.getGetAddress().equals("6")){
					receiveOrder.setGetAddress("北区绿野仙踪后菜鸟驿站");
				}else if(receiveOrder.getGetAddress().equals("7")){
					receiveOrder.setGetAddress("南区宅35栋邮政");
				}else if(receiveOrder.getGetAddress().equals("8")){
					receiveOrder.setGetAddress("南区校门口");
				}else if(receiveOrder.getGetAddress().equals("9")){
					receiveOrder.setGetAddress("南区后门邮政");
				}else if(receiveOrder.getGetAddress().equals("10")){
					receiveOrder.setGetAddress("北区京东派");
				}else if(receiveOrder.getGetAddress().equals("11")){
					receiveOrder.setGetAddress("南区京东派");
				}
			}
			page = new PageInfo<SelfReceiveOrder>(list);
		}catch(Exception e){
			log.info("******查找接单记录失败******");
			e.printStackTrace();
		}
		return page;
	}

	@Override
	public PageInfo<SelfReceiveOrder> getSelfHistoryReceiveOrderPage(int pageNum, String openid) {
		// TODO Auto-generated method stub
				PageInfo<SelfReceiveOrder> page = null;
				try{
					PageHelper.startPage(pageNum, pageSize);
					List<SelfReceiveOrder> list = receiveOrderMapper.selectSelfHistoryReceiveOrderByOpenid(openid);
					DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					for(int i=0;i<list.size();i++){
						SelfReceiveOrder receiveOrder = list.get(i);
						receiveOrder.setShowOrderTime(format.format(receiveOrder.getUpdateTime()));
						receiveOrder.setShowReceiveOrderTime(format.format(receiveOrder.getReceiveOrderTime()));
						if(receiveOrder.isHurry()){
							receiveOrder.setSize((Integer.parseInt(receiveOrder.getSize())+1)+"");
						}
						if(receiveOrder.getGetAddress().equals("1")){
							receiveOrder.setGetAddress("南区宅25栋");
						}else if(receiveOrder.getGetAddress().equals("2")){
							receiveOrder.setGetAddress("南区宅17栋");
						}else if(receiveOrder.getGetAddress().equals("3")){
							receiveOrder.setGetAddress("南区宅35栋顺丰");
						}else if(receiveOrder.getGetAddress().equals("4")){
							receiveOrder.setGetAddress("南区7栋对面书报亭");
						}else if(receiveOrder.getGetAddress().equals("5")){
							receiveOrder.setGetAddress("南区一食堂旁书报亭");
						}else if(receiveOrder.getGetAddress().equals("6")){
							receiveOrder.setGetAddress("北区绿野仙踪后菜鸟驿站");
						}else if(receiveOrder.getGetAddress().equals("7")){
							receiveOrder.setGetAddress("南区宅35栋邮政");
						}else if(receiveOrder.getGetAddress().equals("8")){
							receiveOrder.setGetAddress("南区校门口");
						}else if(receiveOrder.getGetAddress().equals("9")){
							receiveOrder.setGetAddress("南区后门邮政");
						}else if(receiveOrder.getGetAddress().equals("10")){
							receiveOrder.setGetAddress("北区京东派");
						}else if(receiveOrder.getGetAddress().equals("11")){
							receiveOrder.setGetAddress("南区京东派");
						}
					}
					page = new PageInfo<SelfReceiveOrder>(list);
				}catch(Exception e){
					log.info("******查找接单记录失败******");
					e.printStackTrace();
				}
				return page;
	}

	@Override
	public PageInfo<SelfReceiveOrder> getSelfEnReceiveOrderPage(int pageNum, String openid) {
		PageInfo<SelfReceiveOrder> page = null;
		try{
			PageHelper.startPage(pageNum, pageSize);
			List<SelfReceiveOrder> list = receiveOrderMapper.selectSelfEnReceiveOrderByOpenid(openid);
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			for(int i=0;i<list.size();i++){
				SelfReceiveOrder receiveOrder = list.get(i);
				//System.out.println("getSelfReceiveOrderPage:"+receiveOrder.getFetchg());
				receiveOrder.setShowOrderTime(format.format(receiveOrder.getUpdateTime()));
				receiveOrder.setShowReceiveOrderTime(format.format(receiveOrder.getReceiveOrderTime()));
				if(receiveOrder.isHurry()){
					receiveOrder.setSize((Integer.parseInt(receiveOrder.getSize())+1)+"");
				}
				if(receiveOrder.getGetAddress().equals("1")){
					receiveOrder.setGetAddress("南区宅25栋");
				}else if(receiveOrder.getGetAddress().equals("2")){
					receiveOrder.setGetAddress("南区宅17栋");
				}else if(receiveOrder.getGetAddress().equals("3")){
					receiveOrder.setGetAddress("南区宅35栋顺丰");
				}else if(receiveOrder.getGetAddress().equals("4")){
					receiveOrder.setGetAddress("南区7栋对面书报亭");
				}else if(receiveOrder.getGetAddress().equals("5")){
					receiveOrder.setGetAddress("南区一食堂旁书报亭");
				}else if(receiveOrder.getGetAddress().equals("6")){
					receiveOrder.setGetAddress("北区绿野仙踪后菜鸟驿站");
				}else if(receiveOrder.getGetAddress().equals("7")){
					receiveOrder.setGetAddress("南区宅35栋邮政");
				}else if(receiveOrder.getGetAddress().equals("8")){
					receiveOrder.setGetAddress("南区校门口");
				}else if(receiveOrder.getGetAddress().equals("9")){
					receiveOrder.setGetAddress("南区后门邮政");
				}else if(receiveOrder.getGetAddress().equals("10")){
					receiveOrder.setGetAddress("北区京东派");
				}else if(receiveOrder.getGetAddress().equals("11")){
					receiveOrder.setGetAddress("南区京东派");
				}
			}
			page = new PageInfo<SelfReceiveOrder>(list);
		}catch(Exception e){
			log.info("******查找接单记录失败******");
			e.printStackTrace();
		}
		return page;
	}

	@Override
	public PageInfo<SelfReceiveOrder> getSelfDisReceiveOrderPage(int pageNum, String openid) {
		PageInfo<SelfReceiveOrder> page = null;
		try{
			PageHelper.startPage(pageNum, pageSize);
			List<SelfReceiveOrder> list = receiveOrderMapper.selectSelfDisReceiveOrderByOpenid(openid);
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			for(int i=0;i<list.size();i++){
				SelfReceiveOrder receiveOrder = list.get(i);
				//System.out.println("getSelfReceiveOrderPage:"+receiveOrder.getFetchg());
				receiveOrder.setShowOrderTime(format.format(receiveOrder.getUpdateTime()));
				receiveOrder.setShowReceiveOrderTime(format.format(receiveOrder.getReceiveOrderTime()));
				if(receiveOrder.isHurry()){
					receiveOrder.setSize((Integer.parseInt(receiveOrder.getSize())+1)+"");
				}
				if(receiveOrder.getGetAddress().equals("1")){
					receiveOrder.setGetAddress("南区宅25栋");
				}else if(receiveOrder.getGetAddress().equals("2")){
					receiveOrder.setGetAddress("南区宅17栋");
				}else if(receiveOrder.getGetAddress().equals("3")){
					receiveOrder.setGetAddress("南区宅35栋顺丰");
				}else if(receiveOrder.getGetAddress().equals("4")){
					receiveOrder.setGetAddress("南区7栋对面书报亭");
				}else if(receiveOrder.getGetAddress().equals("5")){
					receiveOrder.setGetAddress("南区一食堂旁书报亭");
				}else if(receiveOrder.getGetAddress().equals("6")){
					receiveOrder.setGetAddress("北区绿野仙踪后菜鸟驿站");
				}else if(receiveOrder.getGetAddress().equals("7")){
					receiveOrder.setGetAddress("南区宅35栋邮政");
				}else if(receiveOrder.getGetAddress().equals("8")){
					receiveOrder.setGetAddress("南区校门口");
				}else if(receiveOrder.getGetAddress().equals("9")){
					receiveOrder.setGetAddress("南区后门邮政");
				}else if(receiveOrder.getGetAddress().equals("10")){
					receiveOrder.setGetAddress("北区京东派");
				}else if(receiveOrder.getGetAddress().equals("11")){
					receiveOrder.setGetAddress("南区京东派");
				}
			}
			page = new PageInfo<SelfReceiveOrder>(list);
		}catch(Exception e){
			log.info("******查找接单记录失败******");
			e.printStackTrace();
		}
		return page;
	}

}
