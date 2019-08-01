package com.ideasStudio.website.mapper;

import java.util.List;

import com.ideasStudio.website.entity.Problems;
import com.ideasStudio.website.entity.QA;
import com.ideasStudio.website.vo.ProblemsAndQA;

/**
 * 常见问题mapper操作借口
 * @author 覃远祠
 */
public interface FAQMapper {

	/**
	 * 插入一个问题板块
	 * @return 返回操作受影响的行数
	 * @param problem 插入的问题的板块
	 */
	Integer insertProblem(Problems problem);
	
	/**
	 * 插入一个具体的问题
	 * @return 返回操作受影响的行数
	 * @param qa  插入的具体的问题
	 */
	Integer insertQA(QA qa);
	
	/**
	 * 通过问题板块的id获取问题大板块
	 * @return 返回获取到的问题板块
	 * @param 需要查找问题板块的id
	 */
	Problems getProblemById(Integer id);
	
	/**
	 * 通过问题详情的id获取问题
	 * @return 返回获取到的问题
	 * @param 需要查找问题详情的id
	 */
	QA getQAById(Integer id);
	
	/**
	 * 通过问题板块获取下面的所有的问题
	 * @param parentId 问题板块id
	 * @return 返回详情集合
	 */
	List<QA> getQAlistByParentId(Integer parentId);
	
	/**
	 * 获取所有的问题板块  比如下拉选上需要用到
	 * @return 返回成功获取问题板块的list集合
	 */
	List<Problems> getProblems();
	
	/**
	 * 获取指定模块下有多少个问题
	 * @param parentId 模块ID
	 * @return 返回查询到的数量  为了在删除的时候判断删除数量对不对
	 */
	Integer getQACountByparntId(Integer parentId);
	
	/**
	 * 根据id删除指定问题
	 * @param id 问题的id
	 * @return	返回受影响的行数
	 */
	Integer delQAById(Integer id);
	
	/**
	 * 删除指定模块下的所有的问题
	 * @param id 模块的id
	 * @return 返回受影响的行数
	 */
	Integer delQAByparentId(Integer parentId);
	
	/**
	 * 删除指定模块 
	 * @param id 模块的id
	 * @return 返回受影响的行数
	 */
	Integer delProblemById(Integer id);	
	
	/**
	 * 获取所有的问题板块以及问题详情
	 * @return 
	 */
	List<ProblemsAndQA> getAll();
	
	
}
