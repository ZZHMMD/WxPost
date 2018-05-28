package com.demo.controller;


import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.demo.pojo.Result;
import com.demo.pojo.TbOrder;
import com.demo.service.TbOrderService;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/order")
public class TbOrderController {

	@Autowired
	private TbOrderService tbOrderService;
	
	@RequestMapping("/get/{id}")
	public String getOrderById(@PathVariable String id,Model model){
		TbOrder order = tbOrderService.getOrderById(Integer.parseInt(id));
		Result result = new Result(); 
		result.setObj(order);
		if(order!=null){
			result.setMsg("ok");
			result.setStatue(200);
		}else{
			result.setMsg("err");
			result.setStatue(500);
		}
		model.addAttribute("result",result );
		return "orderDetail";
	}
	
	@RequestMapping("/receivething")
	@ResponseBody
	public String receiveThing(String id){
		int i =  tbOrderService.receiveThing(id);
		Result result = new Result();
		result.setObj(null);
		if(i!=0){
			result.setMsg("ok");
			result.setStatue(200);
		}else{
			result.setMsg("err");
			result.setStatue(500);
		}
		return JSON.toJSONString(result);
	}
	
	@RequestMapping("/get")
	@ResponseBody
	public String getOrderJsonById(String id,Model model){
		TbOrder order = tbOrderService.getOrderById(Integer.parseInt(id));
		Result result = new Result(); 
		result.setObj(order);
		if(order!=null){
			result.setMsg("ok");
			result.setStatue(200);
		}else{
			result.setMsg("err");
			result.setStatue(500);
		}
		return JSON.toJSONString(result);
	}
	
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public String deleteOrderById(@PathVariable String id,Model model){
		int  i = tbOrderService.deleteOrderById(Integer.parseInt(id));
		Result result = new Result(); 
		result.setObj(null);
		if(i!=0){
			result.setMsg("ok");
			result.setStatue(200);
		}else{
			result.setMsg("err");
			result.setStatue(500);
		}
		return JSON.toJSONString(result);
	}
	
	@RequestMapping("/insert")
	@ResponseBody
	public String insertOrderById( TbOrder order,Model model){
		int i = tbOrderService.insertOrder(order);
		Result result = new Result(); 
		result.setObj(null);
		if(i == 1){
			result.setMsg("ok");
			result.setStatue(200);
		}else if(i == 2){
			result.setMsg("2");
			result.setStatue(500);
		}else if(i==3){
			result.setMsg("3");
			result.setStatue(500);
		}else{
			result.setMsg("err");
			result.setStatue(500);
		}
		return JSON.toJSONString(result);
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public String updateOrderById(TbOrder order){
		int i = tbOrderService.updateOrderById(order);
		Result result = new Result(); 
		result.setObj(null);
		if(i!=0){
			result.setMsg("ok");
			result.setStatue(200);
		}else{
			result.setMsg("err");
			result.setStatue(500);
		}
		return JSON.toJSONString(result);
	}
	
	@RequestMapping("/page/{pageNum}")
	public String getOrderPageList(@PathVariable String pageNum,Model model){
		PageInfo<TbOrder>page = tbOrderService.getOrderPageList(Integer.parseInt(pageNum));
		Result result = new Result();
		result.setObj(page);
		if(page!=null){
			result.setMsg("ok");
			result.setStatue(200);
		}else{
			result.setMsg("err");
			result.setStatue(500);
		}
		model.addAttribute("result",result );
		return "getOrder";
	}
	
	@RequestMapping("/pagejson/{pageNum}")
	@ResponseBody
	public String getOrderPageJsonList(@PathVariable String pageNum,Model model){
		PageInfo<TbOrder>page = tbOrderService.getOrderPageList(Integer.parseInt(pageNum));
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
	
	@RequestMapping("/selfpage/{pageNum}")
	public String getSelfOrderPageList(String openid,@PathVariable String pageNum,Model model){
		PageInfo<TbOrder>page = tbOrderService.getSelfOrderPageList(Integer.parseInt(pageNum), openid);
		Result result = new Result();
		result.setObj(page);
		if(page!=null){
			result.setMsg("ok");
			result.setStatue(200);
		}else{
			result.setMsg("err");
			result.setStatue(500);
		}
		model.addAttribute("result",result );
		return "selfOrderList";
	}
	
	@RequestMapping(value="/selfpagejson/{pageNum}",method = RequestMethod.GET)
	@ResponseBody
	public String getSelfOrderPageListJson(String openid,@PathVariable String pageNum,HttpServletResponse response,Model model){
		PageInfo<TbOrder>page = tbOrderService.getSelfOrderPageList(Integer.parseInt(pageNum), openid);
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
