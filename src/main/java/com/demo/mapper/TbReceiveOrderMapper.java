package com.demo.mapper;

import com.demo.pojo.SelfReceiveOrder;
import com.demo.pojo.TbReceiveOrder;
import com.demo.pojo.TbReceiveOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbReceiveOrderMapper {
    int countByExample(TbReceiveOrderExample example);

    int deleteByExample(TbReceiveOrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbReceiveOrder record);

    int insertSelective(TbReceiveOrder record);

    List<TbReceiveOrder> selectByExample(TbReceiveOrderExample example);

    TbReceiveOrder selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbReceiveOrder record, @Param("example") TbReceiveOrderExample example);

    int updateByExample(@Param("record") TbReceiveOrder record, @Param("example") TbReceiveOrderExample example);

    int updateByPrimaryKeySelective(TbReceiveOrder record);

    int updateByPrimaryKey(TbReceiveOrder record);
    
    List<SelfReceiveOrder> selectSelfReceiveOrderByOpenid(String openid);
    
    List<SelfReceiveOrder> selectSelfHistoryReceiveOrderByOpenid(String openid);
    
}