package com.demo.mapper;

import com.demo.pojo.TbUser;
import com.demo.pojo.TbUserExample;
import com.demo.pojo.UserCheck;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbUserMapper {
    int countByExample(TbUserExample example);

    int deleteByExample(TbUserExample example);

    int deleteByPrimaryKey(String id);

    int insert(TbUser record);

    int insertSelective(TbUser record);

    List<TbUser> selectByExample(TbUserExample example);

    TbUser selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TbUser record, @Param("example") TbUserExample example);

    int updateByExample(@Param("record") TbUser record, @Param("example") TbUserExample example);

    int updateByPrimaryKeySelective(TbUser record);

    int updateByPrimaryKey(TbUser record);
    
    int updateUserEnable(UserCheck userCheck);
    
    String getPhoneNumByOrderid(int id);
    
    TbUser getUserByPhoneNum(String phonenum);
}