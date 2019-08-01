package com.ideasStudio.website.service.customization;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ideasStudio.website.config.BackupsPaths;
import com.ideasStudio.website.entity.CustCartArg;

public interface JoinCustCartService {

		/**
		 * 更新数据库中存储的私人订制订单其他数据  在私人定制的第二部分只有一些数据  没有填全 这里方法是第三步骤 不全第三个步骤的数据
		 * @param arg 第三步骤提交的数据
		 * @param session 用户对应的session  需要从中读取uid或者和uuid
		 */
		void UpdataInfo(CustCartArg arg,HttpSession session,HttpServletRequest request);
		
		/**
		 * 直接插入一条私人订制的订单记录  一次性插入全部数据 
		 *  这里因为1号图形需要长宽都可调 所以把第二步骤的内容和第三步骤的内容合成一步 ，这样就可以在三步骤中单独去调节裁减比例了
		 * @param arg   提交的全部数据
		 * @param session 用户对应的session  需要从中读取uid或者uuid
		 * @param request	请求对象 需要读取tomcat下的文件路径 所以用到request
		 */
		void InsertInfo(CustCartArg arg,HttpSession session,HttpServletRequest request);
		
}
