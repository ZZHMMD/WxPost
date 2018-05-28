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
				   TbOrder order = orderMapper.selectByPrimaryKey(receiveOrder.getOrderId());
				   order.setStatus(2);
				   int flag =  orderMapper.updateByPrimaryKey(order);
				   Date date = new Date();
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
			   //�û�ûע��
			   i=5;   
		   }else{
			   i=3;
		   }
		   
	   }catch(Exception e){
		   log.info("******���ӽӵ���Ϣʧ��******");
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
			log.info("******ɾ���ӵ���Ϣʧ��******\n"+e);
		}
		return i;
	}

	@Override
	public int updateReceiveOrderById(TbReceiveOrder receiveOrder) {
		// TODO Auto-generated method stub
		int i=0;
		try{
			i =  receiveOrderMapper.updateByPrimaryKey(receiveOrder);
		}catch(Exception e){
			log.info("******���½ӵ���Ϣʧ��******\n"+e);
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
			log.info("******���ҽӵ���Ϣʧ��******\n"+e);
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
			log.info("******������ʷ�ӵ�����ʧ��******");
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
					//�˺�δͨ����֤
					return 2;
				}else if(user.getCreditScore() <=80){
					//�˺��������ֹ���
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
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			for(int i=0;i<list.size();i++){
				SelfReceiveOrder receiveOrder = list.get(i);
				receiveOrder.setShowOrderTime(format.format(receiveOrder.getUpdateTime()));
				receiveOrder.setShowReceiveOrderTime(format.format(receiveOrder.getReceiveOrderTime()));
				if(receiveOrder.isHurry()){
					receiveOrder.setSize((Integer.parseInt(receiveOrder.getSize())+1)+"");
				}
				if(receiveOrder.getGetAddress().equals("1")){
					receiveOrder.setGetAddress("����լ25��");
				}else if(receiveOrder.getGetAddress().equals("2")){
					receiveOrder.setGetAddress("����լ17��");
				}else if(receiveOrder.getGetAddress().equals("3")){
					receiveOrder.setGetAddress("����լ35��");
				}else if(receiveOrder.getGetAddress().equals("4")){
					receiveOrder.setGetAddress("����7�������鱨ͤ");
				}else if(receiveOrder.getGetAddress().equals("5")){
					receiveOrder.setGetAddress("����һʳ�����鱨ͤ");
				}else if(receiveOrder.getGetAddress().equals("6")){
					receiveOrder.setGetAddress("������Ұ���ٺ������վ");
				}else if(receiveOrder.getGetAddress().equals("7")){
					receiveOrder.setGetAddress("������С��������");
				}else if(receiveOrder.getGetAddress().equals("8")){
					receiveOrder.setGetAddress("����У�ſ�");
				}else if(receiveOrder.getGetAddress().equals("9")){
					receiveOrder.setGetAddress("������������");
				}
			}
			
			page = new PageInfo<SelfReceiveOrder>(list);
			
		}catch(Exception e){
			log.info("******���ҽӵ���¼ʧ��******");
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
					DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					for(int i=0;i<list.size();i++){
						SelfReceiveOrder receiveOrder = list.get(i);
						receiveOrder.setShowOrderTime(format.format(receiveOrder.getUpdateTime()));
						receiveOrder.setShowReceiveOrderTime(format.format(receiveOrder.getReceiveOrderTime()));
						if(receiveOrder.isHurry()){
							receiveOrder.setSize((Integer.parseInt(receiveOrder.getSize())+1)+"");
						}
						if(receiveOrder.getGetAddress().equals("1")){
							receiveOrder.setGetAddress("����լ25��");
						}else if(receiveOrder.getGetAddress().equals("2")){
							receiveOrder.setGetAddress("����լ17��");
						}else if(receiveOrder.getGetAddress().equals("3")){
							receiveOrder.setGetAddress("����լ35��");
						}else if(receiveOrder.getGetAddress().equals("4")){
							receiveOrder.setGetAddress("����7�������鱨ͤ");
						}else if(receiveOrder.getGetAddress().equals("5")){
							receiveOrder.setGetAddress("����һʳ�����鱨ͤ");
						}else if(receiveOrder.getGetAddress().equals("6")){
							receiveOrder.setGetAddress("������Ұ���ٺ������վ");
						}else if(receiveOrder.getGetAddress().equals("7")){
							receiveOrder.setGetAddress("������С��������");
						}else if(receiveOrder.getGetAddress().equals("8")){
							receiveOrder.setGetAddress("����У�ſ�");
						}else if(receiveOrder.getGetAddress().equals("9")){
							receiveOrder.setGetAddress("������������");
						}
					}
					page = new PageInfo<SelfReceiveOrder>(list);
				}catch(Exception e){
					log.info("******���ҽӵ���¼ʧ��******");
					e.printStackTrace();
				}
				return page;
	}

}
