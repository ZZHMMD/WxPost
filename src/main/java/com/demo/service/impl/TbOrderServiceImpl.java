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
import com.demo.pojo.TbOrderExample;
import com.demo.pojo.TbReceiveOrder;
import com.demo.pojo.TbReceiveOrderExample;
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
		try{
			order =  tbOrderMapper.selectByPrimaryKey(id);
		}catch(Exception e){
			log.info("******���Ҷ���ʧ��******\n"+e);
		}
		return order;
	}

	@Override
	public int deleteOrderById(int id) {
		// TODO Auto-generated method stub
		int i =0;
		try{
			i = tbOrderMapper.deleteByPrimaryKey(id);
		}catch(Exception e){
			log.info("******����ɾ��ʧ��******");
			e.printStackTrace();
		}	
		return i;
	}

	@Override
	public int updateOrderById(TbOrder order) {
		// TODO Auto-generated method stub
		int i= 0;
		try{
			TbOrderExample example = new TbOrderExample();
			Date date = new Date();
			order.setUpdateTime(new Date(date.getTime()));
			i = tbOrderMapper.updateByPrimaryKeySelective(order);
		}catch(Exception e){
			log.info("******��������ʧ��******");
			e.printStackTrace();
		}	
		return i;
	}

	@Override
	public int insertOrder(TbOrder order) {
		// TODO Auto-generated method stub
		int i= 0;
		try{
			
			// 0����ûע�� 1�������� 2����������� 
			int flag =  checkUser(order.getOpenid());
			if(flag == 1){
				Date date = new Date();
				order.setCreateTime(new java.sql.Date(date.getTime()));
				order.setUpdateTime(new java.sql.Date(date.getTime()));
				//���ö�����״̬1��ʾ���� �Ѿ�������δ���ӵ� 2��ʾ���ӵ� 3��ʾ�����Ѿ��ʹ� 4������ʱ
				order.setStatus(1);
				i = tbOrderMapper.insert(order);
			}else if(flag ==2){
				i=2;
			}else{
				//�û���û��ע��
				i=3;
			}
			
		}catch(Exception e){
			log.info("******�������ʧ��******");
			e.printStackTrace();
		}	
		return i;
	}

	@Override
	public PageInfo<TbOrder> getOrderPageList(int pageNum) {
		// TODO Auto-generated method stub
		PageInfo<TbOrder> page = null;
		try{
			PageHelper.startPage(pageNum, pageSize);
			List<TbOrder> list = tbOrderMapper.getOrderList();
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			for(int i=0;i<list.size();i++){
				
				TbOrder order = list.get(i);
				if(order.getHurry()){
					order.setSize((Integer.parseInt(order.getSize())+1)+"");
				}
				order.setShowTime(format.format(order.getUpdateTime()));
				order.setReceiveNum("");
				if(order.getGetAddress().equals("1")){
					order.setGetAddress("����լ25��");
				}else if(order.getGetAddress().equals("2")){
					order.setGetAddress("����լ17��");
				}else if(order.getGetAddress().equals("3")){
					order.setGetAddress("����լ35��");
				}else if(order.getGetAddress().equals("4")){
					order.setGetAddress("����7�������鱨ͤ");
				}else if(order.getGetAddress().equals("5")){
					order.setGetAddress("����һʳ�����鱨ͤ");
				}else if(order.getGetAddress().equals("6")){
					order.setGetAddress("������Ұ���ٺ������վ");
				}else if(order.getGetAddress().equals("7")){
					order.setGetAddress("������С��������");
				}else if(order.getGetAddress().equals("8")){
					order.setGetAddress("����У�ſ�");
				}else if(order.getGetAddress().equals("9")){
					order.setGetAddress("������������");
				}
			}
			page = new PageInfo<TbOrder>(list);
		}catch(Exception e){
			log.info("******��ȡ������ҳʧ��******");
		}
		
		return page;
	}

	@Override
	public int checkUser(String id) {
		// TODO Auto-generated method stub
		TbUserExample example = new TbUserExample();
		Criteria criteria =  example.createCriteria();
		criteria.andIdEqualTo(id);
		List<TbUser> list = tbUserMapper.selectByExample(example);
		TbUser user = null;
		if(list.size()>0){
		 user = list.get(0);
		 if(user.getCreditScore() <=80){
				//�˺��������ֹ���
				return 2;
			}
		}else{
			return 0;
		}
		return 1;
	}

	@Override
	public PageInfo<TbOrder> getSelfOrderPageList(int pageNum, String openid) {
		// TODO Auto-generated method stub
				PageInfo<TbOrder> page = null;
				try{
					PageHelper.startPage(pageNum, pageSize);
					List<TbOrder> list = tbOrderMapper.getSelfOrderList(openid);
					DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					for(int i=0;i<list.size();i++){
						TbOrder order = list.get(i);
						if(order.getHurry()){
							order.setSize((Integer.parseInt(order.getSize())+1)+"");
						}
						if(order.getStatus() ==2){
							TbUser user = tbOrderMapper.getTheUser(order.getId());
							order.setUsername(user.getName());
							order.setPhoneNum(user.getPhoneNum());
						}
						order.setShowTime(format.format(order.getUpdateTime()));
						if(order.getGetAddress().equals("1")){
							order.setGetAddress("����լ25��");
						}else if(order.getGetAddress().equals("2")){
							order.setGetAddress("����լ17��");
						}else if(order.getGetAddress().equals("3")){
							order.setGetAddress("����լ35��");
						}else if(order.getGetAddress().equals("4")){
							order.setGetAddress("����7�������鱨ͤ");
						}else if(order.getGetAddress().equals("5")){
							order.setGetAddress("����һʳ�����鱨ͤ");
						}else if(order.getGetAddress().equals("6")){
							order.setGetAddress("������Ұ���ٺ������վ");
						}else if(order.getGetAddress().equals("7")){
							order.setGetAddress("������С��������");
						}else if(order.getGetAddress().equals("8")){
							order.setGetAddress("����У�ſ�");
						}else if(order.getGetAddress().equals("9")){
							order.setGetAddress("������������");
						}
					}
					page = new PageInfo<TbOrder>(list);
				}catch(Exception e){
					log.info("******��ȡ���˶�����ҳʧ��******");
					e.printStackTrace();
				}
				return page;
	}

	@Override
	public int receiveThing(String id) {
		// TODO Auto-generated method stub
		int i = 0;
		try{
			TbOrder order = tbOrderMapper.selectByPrimaryKey(Integer.parseInt(id));
			 if(order.getStatus() == 2){
				 UpdateStatusByOrderId  pojo = new UpdateStatusByOrderId(3,(short)2,Integer.parseInt(id));
				 Date date = new Date();
				 pojo.setUpdatetime(new java.sql.Date(date.getTime()));
				 i = tbOrderMapper.updateStatusByOrderId(pojo);
			 }
		}catch(Exception e){
			log.info("******���ջ������״̬ʧ��******");
			e.printStackTrace();
		}
		return i;
	}

	@Override
	public PageInfo<TbOrder> getHistoryOrderPageList(int pageNum, String openid) {
		PageInfo<TbOrder> page = null;
		try{
			PageHelper.startPage(pageNum, pageSize);
			List<TbOrder> list = tbOrderMapper.getHistoryOrderList(openid);
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			for(int i=0;i<list.size();i++){
				TbOrder order = list.get(i);
				if(order.getHurry()){
					order.setSize((Integer.parseInt(order.getSize())+1)+"");
				}
				order.setShowTime(format.format(order.getUpdateTime()));
				if(order.getGetAddress().equals("1")){
					order.setGetAddress("����լ25��");
				}else if(order.getGetAddress().equals("2")){
					order.setGetAddress("����լ17��");
				}else if(order.getGetAddress().equals("3")){
					order.setGetAddress("����լ35��");
				}else if(order.getGetAddress().equals("4")){
					order.setGetAddress("����7�������鱨ͤ");
				}else if(order.getGetAddress().equals("5")){
					order.setGetAddress("����һʳ�����鱨ͤ");
				}else if(order.getGetAddress().equals("6")){
					order.setGetAddress("������Ұ���ٺ������վ");
				}else if(order.getGetAddress().equals("7")){
					order.setGetAddress("������С��������");
				}else if(order.getGetAddress().equals("8")){
					order.setGetAddress("����У�ſ�");
				}else if(order.getGetAddress().equals("9")){
					order.setGetAddress("������������");
				}
			}
			page = new PageInfo<TbOrder>(list);
		}catch(Exception e){
			log.info("******��ȡ���˶�����ҳʧ��******");
			e.printStackTrace();
		}
		return page;
	}

}
