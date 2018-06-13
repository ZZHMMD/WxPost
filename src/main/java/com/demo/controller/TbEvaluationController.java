package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo.pojo.Result;
import com.demo.pojo.TbEvaluation;
import com.demo.service.TbEvaluationService;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/evaluation")
public class TbEvaluationController {

    @Autowired
    private TbEvaluationService evaluationService;

    @RequestMapping("/get/{id}")
    public String getEvaluationById(@PathVariable String id, Model model) {
        TbEvaluation evaluation = evaluationService.getEvaluationById(Integer.parseInt(id));
        Result result = new Result();
        result.setObj(evaluation);
        if (evaluation != null) {
            result.setMsg("ok");
            result.setStatue(200);
        } else {
            result.setMsg("err");
            result.setStatue(500);
        }
        model.addAttribute("result", result);
        return "";
    }

    @RequestMapping("/insert")
    public String insertEvaluatiom(TbEvaluation evaluation, Model model) {
        int i = evaluationService.insertEvalution(evaluation);
        Result result = new Result();
        result.setObj(null);
        if (i != 0) {
            result.setMsg("ok");
            result.setStatue(200);
        } else {
            result.setMsg("err");
            result.setStatue(500);
        }
        model.addAttribute("result", result);
        return "";
    }

    @RequestMapping("/delete/{orderid}")
    public String deleteEvaluationById(@PathVariable String id, Model model) {
        int i = evaluationService.deleteEvalutionByOrderId(Integer.parseInt(id));
        Result result = new Result();
        result.setObj(null);
        if (i != 0) {
            result.setMsg("ok");
            result.setStatue(200);
        } else {
            result.setMsg("err");
            result.setStatue(500);
        }
        model.addAttribute("result", result);
        return "";
    }

    @RequestMapping("/update")
    public String updateEvaluation(TbEvaluation evaluation, Model model) {
        int i = evaluationService.updateEvaluationById(evaluation);
        Result result = new Result();
        result.setObj(null);
        if (i != 0) {
            result.setMsg("ok");
            result.setStatue(200);
        } else {
            result.setMsg("err");
            result.setStatue(500);
        }
        model.addAttribute("result", result);
        return "";
    }

    @RequestMapping("/page/{orderid}/{pageNo}")
    public String getEvaluationPage(@PathVariable String orderid, @PathVariable String pageNo, Model model) {
        PageInfo<TbEvaluation> page = evaluationService.getPartEvaluationByOrderId(Integer.parseInt(orderid), Integer.parseInt(pageNo));
        Result result = new Result();
        result.setObj(page);
        if (page != null) {
            result.setMsg("ok");
            result.setStatue(200);
        } else {
            result.setMsg("err");
            result.setStatue(500);
        }
        model.addAttribute("result", result);
        return "test";
    }


}
