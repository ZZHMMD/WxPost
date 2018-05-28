package com.demo.service;

import com.demo.pojo.TbOrder;
import com.github.pagehelper.PageInfo;

public interface TbOrderService {
	
	public TbOrder getOrderById(int id);
	
	public int deleteOrderById(int id);
	
	public int updateOrderById(TbOrder order);
	
	public int insertOrder(TbOrder order);
	/**
	 * ���order�ķ�ҳ��Ϣ
	 * @return
	 */
	public PageInfo<TbOrder> getOrderPageList(int pageNum); 
	
	public PageInfo<TbOrder> getSelfOrderPageList(int pageNum, String openid);
	
	public PageInfo<TbOrder> getHistoryOrderPageList(int pageNum, String openid);
	
	public int checkUser(String id);
	
	public int receiveThing(String id);

}
