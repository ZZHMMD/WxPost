package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.demo.pojo.Result;
import com.demo.pojo.SelfReceiveOrder;
import com.demo.pojo.TbReceiveOrder;
import com.demo.service.TbReceiveOrderService;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/receiveorder")
public class TbReceiveOrderController {
	
	@Autowired
	private TbReceiveOrderService receiveOrderService;
	
	@RequestMapping("/get/{id}")
	public String getReceiveOrderById(@PathVariable String id,Model model){
		TbReceiveOrder receiveOrder = receiveOrderService.getReceiveOrderById(Integer.parseInt(id));
		Result result = new Result();
		result.setObj(receiveOrder);
		
		if(receiveOrder!=null){
			result.setMsg("ok");
			result.setStatue(200);
		}else{
			result.setMsg("err");
			result.setStatue(500);
		}
		model.addAttribute("result",result);
		return "";
	}
	
	@RequestMapping("/delete/{id}")
	public String deleteReceiveOrderById(@PathVariable String id,Model model){
		int i = receiveOrderService.deleteReceiveOrderById(Integer.parseInt(id));
		Result result = new Result();
		result.setObj(null);
		
		if(i!=0){
			result.setMsg("ok");
			result.setStatue(200);
		}else{
			result.setMsg("err");
			result.setStatue(500);
		}
		model.addAttribute("result",result);
		return "";
	}
	
	@RequestMapping("/insert")
	@ResponseBody
	public String insertReceiveOrder(TbReceiveOrder receiveOrder ){
		int i = receiveOrderService.insertReceiveOrder(receiveOrder);
		Result result = new Result();
		result.setObj(null);
		if(i == 1){
			result.setMsg("ok");
			result.setStatue(200);
		}else if(i == 2){
			result.setMsg("2");
			result.setStatue(500);
		}else if(i == 3){
			result.setMsg("3");
			result.setStatue(500);
		}else if(i == 4){
			result.setMsg("4");
			result.setStatue(500);
		}else if(i == 5){
			result.setMsg("5");
			result.setStatue(500);
		}else{
			result.setMsg("err");
			result.setStatue(500);
		}
		return JSON.toJSONString(result);
	}
	
	@RequestMapping("/update/{id}")
	public String updateReceiveOrderById(TbReceiveOrder receiveOrder ,Model model){
		int i = receiveOrderService.updateReceiveOrderById(receiveOrder);
		Result result = new Result();
		result.setObj(null);
		if(i!=0){
			result.setMsg("ok");
			result.setStatue(200);
		}else{
			result.setMsg("err");
			result.setStatue(500);
		}
		model.addAttribute("result",result);
		return "";
	}
	
	@RequestMapping("/page/{pageNum}")
	public String getSelfReceiveOrderPage(@PathVariable String pageNum, String openid,Model model){
		
		PageInfo<SelfReceiveOrder> page = receiveOrderService.getSelfReceiveOrderPage(Integer.parseInt(pageNum), openid);
		Result result = new Result();
		result.setObj(page);
		if(page!=null){
			result.setMsg("ok");
			result.setStatue(200);
		}else{
			result.setMsg("err");
			result.setStatue(500);
		}
		model.addAttribute("result", result);
		return "billList";
	}
	
	@RequestMapping("/pagejson/{pageNum}")
	@ResponseBody
	public String getSelfReceiveOrderPage(@PathVariable String pageNum,String openid){
		PageInfo<SelfReceiveOrder> page = receiveOrderService.getSelfReceiveOrderPage(Integer.parseInt(pageNum), openid);
		Result result = new Result();
		result.setObj(page);
		if(page!=null){
			result.setMsg("ok");
			result.setStatue(200);
		}else{
			result.setMsg("err");
			result.setStatue(500);
		}
		return JSON.toJSONString(result);
	}
	
	@RequestMapping("/enpage/{pageNum}")
	public String getSelfEnReceiveOrderPage(@PathVariable String pageNum, String openid,Model model){
		PageInfo<SelfReceiveOrder> page = receiveOrderService.getSelfEnReceiveOrderPage(Integer.parseInt(pageNum), openid);
		Result result = new Result();
		result.setObj(page);
		if(page!=null){
			result.setMsg("ok");
			result.setStatue(200);
		}else{
			result.setMsg("err");
			result.setStatue(500);
		}
		model.addAttribute("result", result);
		return "billList";
	}
	
	@RequestMapping("/enpagejson/{pageNum}")
	@ResponseBody
	public String getSelfEnReceiveOrderPage(@PathVariable String pageNum,String openid){
		PageInfo<SelfReceiveOrder> page = receiveOrderService.getSelfEnReceiveOrderPage(Integer.parseInt(pageNum), openid);
		Result result = new Result();
		result.setObj(page);
		if(page!=null){
			result.setMsg("ok");
			result.setStatue(200);
		}else{
			result.setMsg("err");
			result.setStatue(500);
		}
		return JSON.toJSONString(result);
	}
	
	@RequestMapping("/dispage/{pageNum}")
	public String getSelfDisReceiveOrderPage(@PathVariable String pageNum, String openid,Model model) throws Exception{
		
		/*PageInfo<SelfReceiveOrder> page = receiveOrderService.getSelfDisReceiveOrderPage(Integer.parseInt(pageNum), openid);
		Result result = new Result();
		result.setObj(page);
		if(page!=null){
			result.setMsg("ok");
			result.setStatue(200);
		}else{
			result.setMsg("err");
			result.setStatue(500);
		}
		model.addAttribute("result", result);*/
		return "billList";
	}
	@RequestMapping("/dispagejson/{pageNum}")
	@ResponseBody
	public String getSelfDisReceiveOrderPage(@PathVariable String pageNum,String openid){
		PageInfo<SelfReceiveOrder> page = receiveOrderService.getSelfDisReceiveOrderPage(Integer.parseInt(pageNum), openid);
		Result result = new Result();
		result.setObj(page);
		if(page!=null){
			result.setMsg("ok");
			result.setStatue(200);
		}else{
			result.setMsg("err");
			result.setStatue(500);
		}
		return JSON.toJSONString(result);
	}
	
	

	
	
}
