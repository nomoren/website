package com.ideasStudio.website.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ideasStudio.website.entity.ResponseResult;
import com.ideasStudio.website.service.ex.DelProblemByIdFailException;
import com.ideasStudio.website.service.ex.DelQAByIdFailException;
import com.ideasStudio.website.service.ex.DelQAByparentIdFailException;
import com.ideasStudio.website.service.ex.GetProblemByIdNotFoundException;
import com.ideasStudio.website.service.ex.GetProblemsIsNullException;
import com.ideasStudio.website.service.ex.GetQAByIdNotFoundException;
import com.ideasStudio.website.service.ex.GetQAlistByParentIdNotFoundException;
import com.ideasStudio.website.service.ex.InsertLeaveMessageFailException;
import com.ideasStudio.website.service.ex.InsertProblemsFailException;
import com.ideasStudio.website.service.ex.InsertQAFailException;
import com.ideasStudio.website.service.ex.MessageTypeSwitchException;
import com.ideasStudio.website.service.ex.PhoneIsExistException;
import com.ideasStudio.website.service.ex.UpdateLeaveMessageStatusException;
import com.ideasStudio.website.util.ex.SendEmailFailException;

/**
 * 所有的Controller层的父类，service层抛出的异常的处理类
 * @author 覃远祠
 *
 */
public class BaseController {

	/**
	 * 所有业务层产生的异常的处理方法，会生成对应额异常处理状态吗和异常信息
	 * 然后通过发送给客户端
	 * @return  返回异常处理结果对应的状态码和封装异常信息的结果集
	 */
	@ResponseBody
	@ExceptionHandler(Exception.class)
	public ResponseResult<Void> HanderException(Exception e){
		/**
		 * 留言类型的异常我定义在6XX放状态吗之间
		 * 通用异常状态吗定义在4XX之间
		 */
		if(e instanceof InsertLeaveMessageFailException){
			return new ResponseResult<Void>(601,e);		//插入留言异常
		}else if(e instanceof UpdateLeaveMessageStatusException){
			return new ResponseResult<Void>(602,e);    //更新留言发送邮件状态异常
		}else if(e instanceof MessageTypeSwitchException){
			return new ResponseResult<Void>(603,e);    //选择留言类型异常
		}else if(e instanceof SendEmailFailException){
			return new ResponseResult<Void>(604,e);    //发送邮件异常
		}else if(e instanceof InsertQAFailException){
			return new ResponseResult<Void>(501,e);    //插入问题详情异常
		}else if(e instanceof InsertProblemsFailException){
			return new ResponseResult<Void>(502,e);    //插入问题板块异常
		}else if(e instanceof GetQAlistByParentIdNotFoundException){
			return new ResponseResult<Void>(503,e);    //根据问题板块获取问题详情发生异常
		}else if(e instanceof GetQAByIdNotFoundException){
			return new ResponseResult<Void>(504,e);    //根据问题详情id找不到问题异常
		}else if(e instanceof GetProblemsIsNullException){
			return new ResponseResult<Void>(505,e);    //没有问题板块是访问抛出的异常
		}else if(e instanceof GetProblemByIdNotFoundException){
			return new ResponseResult<Void>(506,e);    //根据问题板块id找不到问题板块异常
		}else if(e instanceof DelQAByparentIdFailException){
			return new ResponseResult<Void>(507,e);    //根据问题板块id删除下面的问题详情发生异常
		}else if(e instanceof DelQAByIdFailException){
			return new ResponseResult<Void>(508,e);    //删除问题详情根据id发生异常
		}else if(e instanceof DelProblemByIdFailException){
			return new ResponseResult<Void>(509,e);    //删除问题板块异常
		}else if(e instanceof PhoneIsExistException) {
			return new ResponseResult<Void>(510,e);		//用户修改信息时号码已存在
		}else{//"发生错误！请稍后重试，详情请联系系统管理员！"
			return new ResponseResult<Void>(400,e);		//通用性异常，抛出400状态码
		}
	}
}
