package com.ideasStudio.website.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ideasStudio.website.entity.Problems;
import com.ideasStudio.website.entity.QA;
import com.ideasStudio.website.mapper.FAQMapper;
import com.ideasStudio.website.service.FAQService;
import com.ideasStudio.website.service.ex.DelProblemByIdFailException;
import com.ideasStudio.website.service.ex.DelQAByIdFailException;
import com.ideasStudio.website.service.ex.DelQAByparentIdFailException;
import com.ideasStudio.website.service.ex.GetProblemByIdNotFoundException;
import com.ideasStudio.website.service.ex.GetProblemsIsNullException;
import com.ideasStudio.website.service.ex.GetQAByIdNotFoundException;
import com.ideasStudio.website.service.ex.GetQAlistByParentIdNotFoundException;
import com.ideasStudio.website.service.ex.InsertProblemsFailException;
import com.ideasStudio.website.service.ex.InsertQAFailException;
import com.ideasStudio.website.vo.ProblemsAndQA;
/**
 * 操作问题的业务层借口实现方法
 * @author 覃远祠
 */
@Service("faqservice")
public class FAQServiceImpl implements FAQService{

	@Autowired
	private FAQMapper faqmapper;
	

	/**
	 * 这个需要同时添加问题板块和问题详情，所以是需要添加事务的
	 * @param pro 插入的问题板块
	 * @param qa 插入的问题详情
	 * @throws InsertQAFailException 插入问题详情 时抛出的异常
	 * @throws InsertProblemsFailException 插入问题板块时抛出的异常
	 */
	@Transactional			
	@Override
	public void addProblemAndQA(Problems pro, QA qa) throws InsertQAFailException,InsertProblemsFailException{
		Problems problem = insertProblem(pro);	//首先在数据库中添加问题板块
		insertQA(qa, problem);   //然后在数据库中插入问题详情
	}
	
	/**
	 * @param pro 单独插入的问题板块
	 * @throws InsertProblemsFailException 插入问题板块时抛出的异常
	 */
	@Override
	public void addProblem(Problems pro) throws InsertProblemsFailException{
		insertProblem(pro);
	}

	
	/**
	 * 单独插入问题详情
	 * @param qa 插入的问题详情
	 * @param pro 插入的问题板块 需要获取父级板块
	 * @throws InsertQAFailException 插入问题详情 时抛出的异常
	 */
	@Override
	public void addQA(QA qa,Problems pro) throws InsertQAFailException{
		insertQA(qa, pro);
	}

	/**
	 * 根据problem id找对应的问题，如果找不到则抛出异常
	 * @throws GetProblemByIdNotFoundException 抛出找不到problem异常
	 */
	@Override
	public Problems getProblemById(Integer id) throws GetProblemByIdNotFoundException{
		Problems pro = faqmapper.getProblemById(id);
		if(pro==null){
			throw new GetProblemByIdNotFoundException("找不到对应的问题板块");
		}
		return pro;
	}

	
	/**
	 * 根据QA问题详情id找对应的QA，如果找不到则抛出异常
	 * @throws GetQAByIdNotFoundException 抛出找不到QA异常
	 */
	@Override
	public QA getQAById(Integer id) throws GetQAByIdNotFoundException{
		QA qa = faqmapper.getQAById(id);
		if(qa==null){
			throw new GetQAByIdNotFoundException("找不到对应的问题详情");
		}
		return qa;
	}

	/**
	 * 根据父级板块的id查找下面所有的问题
	 * return List<QA> 返回正常查找到的问题板块下的所有的问题集合
	 */
	@Override
	public List<QA> getQAlistByParentId(Integer parentId) throws GetQAlistByParentIdNotFoundException{
		List<QA> questionsAndAnswers = faqmapper.getQAlistByParentId(parentId);
		if(questionsAndAnswers==null){
			throw new GetQAlistByParentIdNotFoundException("该问题板块下没有问题");
		}
		return questionsAndAnswers;
	}

	/**
	 * 查找所有的问题板块，如果找不到则抛出异常
	 * @throws GetProblemsIsNullException  找不到时抛出的异常
	 */
	@Override
	public List<Problems> getProblems() throws GetProblemsIsNullException{
		List<Problems> problems = faqmapper.getProblems();
		if(problems==null){
			throw new GetProblemsIsNullException("还没有问题板块，请添加");
		}
		return problems;
	}
	
	/**
	 * @throws DelQAByIdFailException 删除问题详情时失败
	 */
	@Override
	public void delQA(Integer id) throws DelQAByIdFailException{
		delQAById(id);
	}
	
	
	/**
	 * 这个因为要同时删除问题板块以及要删除下面所有的问题详情
	 * 所以需要使用到事务
	 */
	@Transactional
	@Override
	public void delProblemAndQA(Integer id){
		delQAByparentId(id);	
		delProblemById(id);
	}
	
	/**
	 * 获得所有的问题模块以及问题详情
	 */
	@Override
	public List<ProblemsAndQA> getAll() {
		return faqmapper.getAll();
	}
	
	/**
	 * 插入一个大问题板块
	 * @param pro  插入的问题
	 * @throws InsertProblemsFailException 插入问题板块时抛出的异常
	 * @return 返回成功插入的问题板块
	 */
	private Problems insertProblem(Problems pro) throws InsertProblemsFailException{
		Integer rows=faqmapper.insertProblem(pro);
		if(rows!=1){
			throw new InsertProblemsFailException("插入问题板块失败!");
		}
		return pro;  
	}
	
	
	/**
	 * 插入问题详情
	 * @param qa 插入的问题详情
	 * @param pro 插入的问题板块 （需要将问题板块的id给到问题详情字段中）
	 * @throws InsertQAFailException 插入问题详情抛出的异常
	 * @return 返回成功插入的问题
	 */
	private QA insertQA(QA qa,Problems pro) throws InsertQAFailException{
		qa.setParentId(pro.getId());			//把问题详情的父级id放入自己的字段中
		Integer rows = faqmapper.insertQA(qa);
		if(rows!=1){
			throw new InsertQAFailException("插入问题详情时失败！");
		}
		return qa;
	}
	
	/**
	 * 获取指定模块下有多少个问题
	 * @param parentId 模块ID
	 * @return 返回查询到的数量  为了在删除的时候判断删除数量对不对
	 */
	private Integer getQACountByparntId(Integer parentId){
		return faqmapper.getQACountByparntId(parentId);
	}
	
	/**
	 * 根据id删除指定问题
	 * @param id 问题的id
	 * @return	返回受影响的行数
	 */
	private Integer delQAById(Integer id) throws DelQAByIdFailException{
		Integer rows  = faqmapper.delQAById(id);
		if(rows!=1){
			throw new DelQAByIdFailException("根据问题id删除问题时失败");
		}
		return rows;
	}
	
	/**
	 * 删除指定模块下的所有的问题
	 * @param id 模块的id
	 * @return 返回受影响的行数
	 */
	private Integer delQAByparentId(Integer parentId) throws DelQAByparentIdFailException{
		Integer selrows = faqmapper.getQACountByparntId(parentId);
		Integer rows = faqmapper.delQAByparentId(parentId);
		if(rows!=selrows){
			throw new DelQAByparentIdFailException("根据父级问题板块id删除问题时失败");
		}
		return rows;
	}
	
	/**
	 * 删除指定模块 
	 * @param id 模块的id
	 * @return 返回受影响的行数
	 */
	private Integer delProblemById(Integer id) throws DelProblemByIdFailException{
		Integer rows = faqmapper.delProblemById(id);
		if(rows!=1){
			throw new DelProblemByIdFailException("删除问题板块时失败");
		}
		return rows;
	}


}
