package com.demo.service.impl;


import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.mapper.TbUserMapper;
import com.demo.pojo.TbUser;
import com.demo.pojo.TbUserExample;
import com.demo.pojo.TbUserExample.Criteria;
import com.demo.pojo.UserCheck;
import com.demo.service.TbUserService;
import com.demo.util.DloadImgUtil;

@Service
public class TbUserServiceImpl implements TbUserService {

    private static Logger log = LoggerFactory.getLogger(TbUserServiceImpl.class);

    @Autowired
    private TbUserMapper tbUserMapper;

    @Override
    public int insertUser(TbUser user) {
        // TODO Auto-generated method stub
        int i = 0;
        try {
            Date date = new Date();
            user.setCreditScore(100);
            user.setEnable(false);
            user.setCreatetime(new java.sql.Date(date.getTime()));
            user.setUpdatetime(new java.sql.Date(date.getTime()));
            i = tbUserMapper.insert(user);
        } catch (Exception e) {
            log.info("******用户添加失败******\n");
            e.printStackTrace();
        }

        return i;
    }

    @Override
    public int deleteUserById(String id) {
        // TODO Auto-generated method stub
        int i = 0;
        try {
            i = tbUserMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            log.info("******用户删除失败******\n");
            e.printStackTrace();
        }

        return i;
    }

    @Override
    public int updateUserById(TbUser user) {
        // TODO Auto-generated method stub
        int i = 0;
        try {
            Date date = new Date();
            user.setUpdatetime(new java.sql.Date(date.getTime()));
            i = tbUserMapper.updateByPrimaryKeySelective(user);
        } catch (Exception e) {
            log.info("******用户更新失败******\n");
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public TbUser getUserById(String id) {
        // TODO Auto-generated method stub
        TbUser user = null;
        try {
            user = tbUserMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            log.info("******用户查找失败******\n");
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public String doloadImg(String accesstoken, String serverId) {
        String realPath = "/opt/images";
        String filePath = DloadImgUtil.downloadMedia(accesstoken, serverId, realPath);
        return filePath;
    }


    @Override
    public int updateUserEnable(UserCheck userCheck) {
        // TODO Auto-generated method stub
        int i = 0;
        try {
            i = tbUserMapper.updateUserEnable(userCheck);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.toString());
        }
        return i;
    }

    @Override
    public TbUser getUserByPhoneNum(String phonenum) {
        // TODO Auto-generated method stub
        TbUser user = null;
        try {
            user = tbUserMapper.getUserByPhoneNum(phonenum);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<TbUser> getEnableUser() {
        // TODO Auto-generated method stub
        List<TbUser> list = null;
        try {
            TbUserExample example = new TbUserExample();
            example.createCriteria().andEnableEqualTo(true);

            list = tbUserMapper.selectByExample(example);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int changeEnable(String phonenum) {
        // TODO Auto-generated method stub
        int i = 0;
        try {
            TbUser user = tbUserMapper.getUserByPhoneNum(phonenum);
            if (user.getEnable()) {
                user.setCreditScore(100);
                user.setEnable(false);
                i = tbUserMapper.updateByPrimaryKeySelective(user);
            } else {
                user.setEnable(true);
                i = tbUserMapper.updateByPrimaryKeySelective(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public int changeEnalbeIn(String phonenum) {
        // TODO Auto-generated method stub
        int i = 0;
        try {
            TbUser user = tbUserMapper.getUserByPhoneNum(phonenum);
            System.out.println(user.toString());
            if (user.getCreditScore() == 666) {
                user.setCreditScore(100);
                i = tbUserMapper.updateByPrimaryKeySelective(user);
            } else {
                user.setCreditScore(666);
                i = tbUserMapper.updateByPrimaryKeySelective(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }


}
