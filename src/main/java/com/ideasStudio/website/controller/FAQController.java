package com.ideasStudio.website.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ideasStudio.website.entity.Problems;
import com.ideasStudio.website.entity.QA;
import com.ideasStudio.website.entity.ResponseResult;
import com.ideasStudio.website.service.FAQService;
import com.ideasStudio.website.vo.ProblemsAndQA;

/**
 * 处理常见问题的Controller
 * @author 覃远祠
 */
@Controller
@RequestMapping("/faq")
public class FAQController extends BaseController{

	@Autowired
	private FAQService faqservice;
	
	/**
	 * 获得常见问题管理页面的方法
	 * @param modelAndView spring容器传入的modelandview
	 * @return 返回添加视图的modelandview	
	 */
	@RequestMapping("/getFaq-backstage.do")
	public ModelAndView getFAQBackStagePage(ModelAndView modelAndView){
		modelAndView.setViewName("faq-backstage");
		return modelAndView;
	}
	
	/**
	 * 获得常见问题页面的方法
	 * @param modelAndView spring容器传入的modelandview
	 * @return 返回添加视图的modelandview	
	 */
	@RequestMapping("/faqPage.do")
	public ModelAndView getFAQPage(ModelAndView modelAndView,HttpServletRequest request){
		request.setAttribute("all", faqservice.getAll());
		modelAndView.setViewName("faq");
		return modelAndView;
	}
	
	
	/**
	 * 一次性添加一个问题模块和一个详细问题
	 * @param problem 问题模块
	 * @param question 问题详情问题
	 * @param answer 问题详情答案
	 * @return 正确添加时返回200状态码 否则抛出异常交友BaseController处理
	 */
	@RequestMapping("/addProblemAndQA.do")
	@ResponseBody
	public ResponseResult<Void> addProblemAndQA(
			@RequestParam("problem")String problem,
			@RequestParam("question")String question,
			@RequestParam("answer")String answer){
		Problems pro = new Problems();
		pro.setProblem(problem);
		QA qa = new QA();
		qa.setQuestion(question);
		qa.setAnswer(answer);
		faqservice.addProblemAndQA(pro, qa);
		return new ResponseResult<Void>();
	}
	
	/**
	 * 单独添加一个问题模块
	 * @param problem 添加的问题模块
	 * @return 正确添加时返回200状态码 否则抛出异常交友BaseController处理
	 */
	@RequestMapping("/addProblem.do")
	@ResponseBody
	public ResponseResult<Void> addProblem(
			@RequestParam("problem")String problem){
		Problems pro = new Problems();
		pro.setProblem(problem);
		faqservice.addProblem(pro);
		return new ResponseResult<Void>();
	}
	
	/**
	 * 单独添加一个问题详情
	 * @param problemId  问题模块的ID
	 * @param question 问题内容
	 * @param answer 问题答案
	 * @return 成功插入一个问题详情返回200状态码 则抛出异常交友BaseController处理
	 */
	@RequestMapping("/addQA.do")
	@ResponseBody
	public ResponseResult<Void> addQA(
			@RequestParam("problemId")Integer problemId,
			@RequestParam("question")String question,
			@RequestParam("answer")String answer){
		Problems pro = new Problems();
		pro.setId(problemId);
		QA qa = new QA();
		qa.setQuestion(question);
		qa.setAnswer(answer);
		faqservice.addQA(qa,pro);
		return new ResponseResult<Void>();
	}
	
	/**
	 * 获取所有的问题板块
	 * @return 返回获取到的所有的问题板块，如果获取不到则会抛出异常
	 */
	@RequestMapping("/getProblems.do")
	@ResponseBody
	public ResponseResult<List<Problems>> getProblems(){
		List<Problems> problems = faqservice.getProblems();
		ResponseResult<List<Problems>> rr = new ResponseResult<List<Problems>>();
		rr.setData(problems);
		return rr;
	}
	
	/**
	 * 通过大的问题板块获取下面的所有的问题详情
	 * @param problemid 问题板块的id
	 * @return 返回获取到的所有的问题详情集合 如果获取不到则抛出异常
	 */
	@RequestMapping("/getQAs.do")
	@ResponseBody
	public ResponseResult<List<QA>> getQAByParentId(@RequestParam("problemid")Integer problemid){
		List<QA> qas = faqservice.getQAlistByParentId(problemid);
		ResponseResult<List<QA>> rr = new ResponseResult<List<QA>>();
		rr.setData(qas);
		return rr;
	}
	
	/**
	 * 删除指定的问题详细
	 * @param id 删除的指定问题的id
	 * @return 成功删除返回200状态码，否则抛出异常
	 */
	@RequestMapping("/delQA.do")
	@ResponseBody
	public ResponseResult<Void> delQA(@RequestParam("id")Integer id){
		faqservice.delQA(id);
		return new ResponseResult<Void>();
	}
	
	/**
	 * 删除问题板块以及下面所有的问题详情
	 * @param id 删除的问题板块的id
	 * @return 成功删除返回200状态码，否则抛出异常
	 */
	@RequestMapping("/delProblemAndQA.do")
	@ResponseBody
	public ResponseResult<Void> delProblemsAndQA(@RequestParam("id")Integer id){
		faqservice.delProblemAndQA(id);
		return new ResponseResult<Void>();
	}
	
	/**
	 * 获取的所有的问题板块以及问题详情
	 * @return 返回获取到的问题板块以及问题详情
	 */
	@RequestMapping("/getAll.do")
	@ResponseBody
	public ResponseResult<List<ProblemsAndQA>> getAll(){
		ResponseResult<List<ProblemsAndQA>> rr = new ResponseResult<List<ProblemsAndQA>>();
		rr.setData(faqservice.getAll());
		return rr;
	}
	
}
