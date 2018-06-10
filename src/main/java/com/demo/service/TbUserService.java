package com.demo.service;

import java.util.List;

import com.demo.pojo.TbUser;
import com.demo.pojo.UserCheck;

public interface TbUserService {
	
	public int insertUser(TbUser user) ;
	
	public int deleteUserById(String id);
	
	public int updateUserById(TbUser user);
	
	public TbUser getUserById(String id);
	
	public String doloadImg(String accesstoken, String serverId);
	
	public int updateUserEnable(UserCheck userCheck);
	
	 public TbUser getUserByPhoneNum(String phonenum);
	 
	 public List<TbUser> getEnableUser();
	 
	 public int changeEnable(String phonenum);
	 
	 public int changeEnalbeIn(String phonenum);

}
