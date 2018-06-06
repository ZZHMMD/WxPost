package com.demo.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.demo.mapper.TbEvaluationMapper;
import com.demo.pojo.TbEvaluation;
import com.demo.pojo.TbEvaluationExample;
import com.demo.pojo.TbEvaluationExample.Criteria;
import com.demo.service.TbEvaluationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class TbEvaluationServiceImpl implements TbEvaluationService {
	
	@Value("${PAGE_SIZE}")
	public int pageSize;
	
	private static Logger log = LoggerFactory.getLogger(TbEvaluationServiceImpl.class);

	@Autowired
	private TbEvaluationMapper evaluationMapper;

	@Override
	public int insertEvalution(TbEvaluation evaluation) {
		// TODO Auto-generated method stub
		int i = 0;
		try{
			Date date = new Date();
			evaluation.setCreateTime(new java.sql.Date(date.getTime()));
			
			i = evaluationMapper.insert(evaluation);
		}catch(Exception e){
			log.info("******新增评论信息失败******\n"+e);
		}
		return i;
	}

	@Override
	public int deleteEvalutionByOrderId(int orderId) {
		// TODO Auto-generated method stub
		int i = 0;
		try{
			TbEvaluationExample example = new TbEvaluationExample();
			example.createCriteria().andOrderIdEqualTo(orderId);
			
			i = evaluationMapper.deleteByExample(example);
		}catch(Exception e){
			log.info("******删除评论信息失败******\n"+e);
		}
		
		return i;
	}

	@Override
	public TbEvaluation getEvaluationById(int id) {
		// TODO Auto-generated method stub
		TbEvaluation evaluation = null;
		try{
             evaluation  = evaluationMapper.selectByPrimaryKey(id);
		}catch(Exception e){
			log.info("******查找评论信息失败******\n"+e);
		}
		
		return evaluation;
	}

	@Override
	public int updateEvaluationById(TbEvaluation evaluation) {
		// TODO Auto-generated method stub
		int i = 0;
		try{
			i = evaluationMapper.updateByPrimaryKey(evaluation);
		}catch(Exception e){
			log.info("******更新评论信息失败******\n"+e);
		}
		
		return i;
	}

	@Override
	public List<TbEvaluation> getAllEvaluationByOrderId(int orderId) {
		// TODO Auto-generated method stub
		List<TbEvaluation> list = null;
		try{
			list = evaluationMapper.selectEvaluationListByOrderId(orderId);
		}catch(Exception e){
			log.info("******查找评论列表失败******\n"+e);
		}
		
		return list;
	}

	@Override
	public PageInfo<TbEvaluation> getPartEvaluationByOrderId(int orderId,int pageNo) {
		// TODO Auto-generated method stub
		PageInfo<TbEvaluation> page = null;
		try{
			PageHelper.startPage(pageNo, pageSize);
			
			List<TbEvaluation> list = evaluationMapper.selectEvaluationListByOrderId(orderId);
			 page = new PageInfo<TbEvaluation>(list);
			
			
		}catch(Exception e){
			log.info("******获取评论分页信息失败******");
		}
		
		return page;
	}

	@Override
	public int deleteEvaluationById(int id) {
		// TODO Auto-generated method stub
		int i= 0;
		try{
			i= evaluationMapper.deleteByPrimaryKey(id);
		}catch(Exception e){
			log.info("******删除评论信息失败******\n");
			e.printStackTrace();
		}
		
		return i;
	}

	
	
	
	
}
