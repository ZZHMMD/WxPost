package com.demo.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.demo.pojo.Result;
import com.demo.pojo.TbUser;
import com.demo.pojo.UserCheck;
import com.demo.service.TbUserService;

import net.sf.json.util.JSONUtils;

@Controller
public class TbUserController {

	@Autowired
	private TbUserService tbUserService;
	
	@RequestMapping("/getuser/{id}")
	@ResponseBody
    public String getUserById(@PathVariable String id,Model model){
		TbUser user =  tbUserService.getUserById(id);
		Result result = new Result();
		result.setObj(null);
		if(user!=null){
			result.setMsg("ok");
			result.setStatue(200);
		}else{
			result.setMsg("err");
			result.setStatue(500);	
		};	
		return JSON.toJSONString(result);
	}
	
	@RequestMapping("/deleteuser/{id}")
    public String deleteUserById(@PathVariable String id,Model model){
		
		int i = tbUserService.deleteUserById(id);
		Result result = new Result();
		result.setObj(null);
		if(i!=0){
			result.setMsg("ok");
			result.setStatue(200);
		}else{
			result.setMsg("err");
			result.setStatue(500);	
		}
		model.addAttribute("result", result);	
		return "";
	}
	
	@RequestMapping("/insertuser")
    public String deleteUserById(TbUser user,Model model){
		
		int i = tbUserService.insertUser(user);
		Result result = new Result();
		result.setObj(null);
		if(i!=0){
			result.setMsg("ok");
			result.setStatue(200);
		}else{
			result.setMsg("err");
			result.setStatue(500);	
		}
		model.addAttribute("result", result);	
		return "";
	}
	
	@RequestMapping("/updateuser")
    public String updateUserById(TbUser user,Model model){
		
		int i = tbUserService.updateUserById(user);
		Result result = new Result();
		result.setObj(null);
		if(i!=0){
			result.setMsg("ok");
			result.setStatue(200);
		}else{
			result.setMsg("err");
			result.setStatue(500);	
		}
		model.addAttribute("result", result);	
		return "";
	}
	
	@RequestMapping("/panduan")
    public String panduan(String openid,Model model){
		
		TbUser user = tbUserService.getUserById(openid);
		Result result = new Result();
		if(user!=null){
			result.setMsg("exist");
			result.setStatue(200);
		}else{
			result.setMsg("noexist");
			result.setStatue(404);
		}
		result.setObj(user);
		model.addAttribute("result", result);	
		return "/userinfo";
	}
	
	@RequestMapping(value="/choose")
	@ResponseBody
    public void chooseInsertOrUpdate(String flag,HttpServletRequest request,HttpServletResponse response,Model model){
		try{
			response.setContentType("text/html;charset=UTF-8");
			String str = request.getParameter("user");
			TbUser userA =JSON.parseObject(str, TbUser.class);
			TbUser aUser = tbUserService.getUserById(userA.getId()); 
			Result result = new Result();
			result.setObj(null);	
			if(flag.equals("change")){
				String accesstoken = AccessTokenThread.accessToken.getToken();
				String fileName =  tbUserService.doloadImg(accesstoken, userA.getStudentCard());
				userA.setStudentCard(fileName);
			}
			
			if(aUser!=null){
				tbUserService.updateUserById(userA);
				result.setMsg("���³ɹ�");
				result.setStatue(200);
			}else{
				tbUserService.insertUser(userA);
				result.setMsg("ע��ɹ�");
				result.setStatue(500);
			}
			String res =  JSON.toJSONString(result);
			response.getWriter().print(res);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping("/setenable")
	@ResponseBody
	public String updateUserEnable(UserCheck userCheck){
		
		int i = tbUserService.updateUserEnable(userCheck);
		
		Result result = new Result();
		result.setObj(null);
		if(i != 0){
			result.setMsg("�û����ͨ��!");
			result.setStatue(200);
		}else{
			result.setMsg("�û���˲�ͨ��!");
			result.setStatue(500);
		}
		return JSONObject.toJSONString(result);
	}
	
	
}
