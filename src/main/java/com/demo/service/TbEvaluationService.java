package com.demo.service;

import java.util.List;

import com.demo.pojo.TbEvaluation;
import com.github.pagehelper.PageInfo;

public interface TbEvaluationService {

    public int insertEvalution(TbEvaluation evaluation);

    public int deleteEvaluationById(int id);

    public int deleteEvalutionByOrderId(int orderId);

    public TbEvaluation getEvaluationById(int orderId);

    public int updateEvaluationById(TbEvaluation evaluation);

    public List<TbEvaluation> getAllEvaluationByOrderId(int orderId);

    public PageInfo<TbEvaluation> getPartEvaluationByOrderId(int orderId, int pageNo);

}
