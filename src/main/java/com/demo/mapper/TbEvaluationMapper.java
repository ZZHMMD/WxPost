package com.demo.mapper;

import com.demo.pojo.TbEvaluation;
import com.demo.pojo.TbEvaluationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbEvaluationMapper {
    int countByExample(TbEvaluationExample example);

    int deleteByExample(TbEvaluationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbEvaluation record);

    int insertSelective(TbEvaluation record);

    List<TbEvaluation> selectByExample(TbEvaluationExample example);

    TbEvaluation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbEvaluation record, @Param("example") TbEvaluationExample example);

    int updateByExample(@Param("record") TbEvaluation record, @Param("example") TbEvaluationExample example);

    int updateByPrimaryKeySelective(TbEvaluation record);

    int updateByPrimaryKey(TbEvaluation record);
    
    public List<TbEvaluation> selectEvaluationListByOrderId(int orderId);
}