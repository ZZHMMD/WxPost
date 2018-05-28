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
import com.demo.pojo.TbOrder;
import com.demo.service.TbOrderService;
import com.demo.service.TbReceiveOrderService;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/history")
public class HistoryOrderController {
	
	@Autowired
	private TbOrderService orderService;
	
	@Autowired
	private TbReceiveOrderService receiveOrderService;
	
	@RequestMapping("/order/{pageNum}")
	public String getHistoryOrder(@PathVariable int pageNum,String openid,Model model){
		Result result = new Result();
		PageInfo<TbOrder> page = orderService.getHistoryOrderPageList(pageNum, openid);
		result.setObj(page);
		if(page!=null){
			result.setMsg("ok");
			result.setStatue(200);
		}else{
			result.setMsg("err");
			result.setStatue(500);
		}
		model.addAttribute("result", result);
		return "historyOrderList";
	}
	
	@RequestMapping("/orderjson/{pageNum}")
	@ResponseBody
	public String getHistoryOrderJson(@PathVariable int pageNum,String openid,Model model){
		Result result = new Result();
		PageInfo<TbOrder> page = orderService.getHistoryOrderPageList(pageNum, openid);
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
	
	@RequestMapping("/receiveorder/{pageNum}")
	public String getHistotryReceiveOrder(@PathVariable int pageNum,String openid,Model model){
		Result result = new Result();
		PageInfo<SelfReceiveOrder> page = receiveOrderService.getSelfHistoryReceiveOrderPage(pageNum, openid);
		result.setObj(page);
		if(page!=null){
			result.setMsg("ok");
			result.setStatue(200);
		}else{
			result.setMsg("err");
			result.setStatue(500);
		}
		model.addAttribute("result", result);
		return "historyReceiveOrder";
	}
	
	@RequestMapping("/receiveorderjson/{pageNum}")
	@ResponseBody
	public String getHistotryReceiveOrderJson(@PathVariable int pageNum,String openid,Model model){
		Result result = new Result();
		PageInfo<SelfReceiveOrder> page = receiveOrderService.getSelfHistoryReceiveOrderPage(pageNum, openid);
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
