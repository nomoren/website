package com.ideasStudio.website.service;

import java.util.List;

import com.ideasStudio.website.entity.Problems;
import com.ideasStudio.website.entity.QA;
import com.ideasStudio.website.service.ex.GetProblemByIdNotFoundException;
import com.ideasStudio.website.service.ex.GetProblemsIsNullException;
import com.ideasStudio.website.service.ex.GetQAByIdNotFoundException;
import com.ideasStudio.website.service.ex.GetQAlistByParentIdNotFoundException;
import com.ideasStudio.website.service.ex.InsertProblemsFailException;
import com.ideasStudio.website.service.ex.InsertQAFailException;
import com.ideasStudio.website.vo.ProblemsAndQA;

/**
 * 操作问题接口
 * @author 覃远祠
 */
public interface FAQService {
	
	/**
	 * 在数据库中添加一个问题
	 * @param pro 问题大板块
	 * @param qa  问题详情
	 * @return 
	 * @throws InsertQAFailException 插入问题详情 时抛出的异常
	 * @throws InsertProblemsFailException 插入问题板块时抛出的异常
	 */
	void addProblemAndQA(Problems pro,QA qa);
	
	/**
	 * 添加一个问题板块
	 * @param pro 添加的问题板块
	 * @throws InsertProblemsFailException 插入问题板块时抛出的异常
	 */
	void addProblem(Problems pro);
	
	/**
	 * 在已有问题板块的基础场单独添加一个问题详情
	 * @param qa 插入的问题详情
	 * @throws InsertQAFailException 插入问题详情 时抛出的异常
	 */
	void addQA(QA qa,Problems pro);
	
	/**
	 * 通过问题板块的id获取问题大板块
	 * @return 返回获取到的问题板块
	 * @throws GetProblemByIdNotFoundException 根据problemid找不到对应问题板块是抛出的异常
 	 */
	Problems getProblemById(Integer id) throws GetProblemByIdNotFoundException;
	
	/**
	 * 通过问题详情的id获取问题
	 * @return 返回获取到的问题
	 * @param id 需要获取的问题详情的id
	 * @throws GetQAByIdNotFoundException 根据问题详情的id找不到问题详情时抛出的异常
	 */
	QA getQAById(Integer id)throws GetQAByIdNotFoundException;
	
	/**
	 * 通过问题板块获取下面的所有的问题
	 * @param parentId 问题板块id
	 * @return 返回详情集合
	 * @throws GetQAlistByParentIdNotFoundException 抛出的找不到问题详情的异常
	 */
	List<QA> getQAlistByParentId(Integer parentId)throws GetQAlistByParentIdNotFoundException;
	
	
	/**
	 * 获取所有的问题板块  比如下拉选上需要用到
	 * @return 返回成功获取问题板块的list集合
	 * @throws GetProblemsIsNullException 找不到问题板块时抛出的异常
	 */
	List<Problems> getProblems()throws GetProblemsIsNullException;

	/**
	 * 删除问题
	 * @param id 根据问题的id删除
	 */
	void delQA(Integer id);
	
	/**
	 * 删除问题板块，同时根据问题板块的id删除下面的所有的问题详情的ID
	 * @param id 问题板块的id
	 */
	void delProblemAndQA(Integer id);
	
	/**
	 * 获取到所有的问题模块以及问题详情
	 */
	List<ProblemsAndQA> getAll();
}
