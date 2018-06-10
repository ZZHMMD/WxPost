package com.demo.mapper;

import com.demo.pojo.TbOrder;
import com.demo.pojo.TbOrderExample;
import com.demo.pojo.TbUser;
import com.demo.pojo.UpdateStatusByOrderId;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbOrderMapper {
    int countByExample(TbOrderExample example);

    int deleteByExample(TbOrderExample example);

    int deleteByPrimaryKey(Integer id);
    int insert(TbOrder record);
    int insertSelective(TbOrder record);
    List<TbOrder> selectByExample(TbOrderExample example);
    TbOrder selectByPrimaryKey(Integer id);
    int updateByExampleSelective(@Param("record") TbOrder record, @Param("example") TbOrderExample example);
    int updateByExample(@Param("record") TbOrder record, @Param("example") TbOrderExample example);
    int updateByPrimaryKeySelective(TbOrder record);
    int updateByPrimaryKey(TbOrder record);
	List<TbOrder> getOrderList();
	List<TbOrder> getSelfOrderList(String openid);
	List<TbOrder>	getHistoryOrderList(String openid);
	int updateStatusByOrderId(UpdateStatusByOrderId pojo);
	TbUser getTheUser(int orderId);
	int forOurSelf(int orderid);
	int updateEnable(int id);
	int updateDisable(int id);
	int getUnFetchCount();
	int getOrderCountByOpenid(String openid);
	int getThirdCountFrom11_11(String openid);
	
	
}