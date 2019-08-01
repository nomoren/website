package com.ideasStudio.website.crontab;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ideasStudio.website.config.BackupsPaths;
import com.ideasStudio.website.entity.CustCart;
import com.ideasStudio.website.mapper.CustMapper;

/**
 * 定时清除数据库中没有完成的订单
 * @author Administrator
 *
 */
@Component
public class TimeClearCustOrder{

	Logger logger = Logger.getLogger(TimeClearCustOrder.class);
	@Autowired
	private CustMapper custmapper;
	
	@Autowired
	private BackupsPaths backups;

	/*private CustMapper getCustMapper(){
		return SpringUtil.getBean(CustMapper.class);
	}*/
	
	@Autowired
	private HttpServletRequest request;
	
	public void execute(){
		//TODO任务列表
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//1.取出当前时间
		Date date = new Date();
		//2.把当前时间设置到7天前
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		//calendar.add(Calendar.WEEK_OF_MONTH, -1);
		//3.数据库查询7天前没有完成的单 status=0 并且
		//4.把查询出来的id删除掉
		String pasttime = sdf.format(calendar.getTime());
		List<CustCart> ids = custmapper.queryPastOrder(pasttime);
		DeletePastOrder(ids,sdf);
	}
	
	@Transactional
	public void DeletePastOrder(List<CustCart> list,SimpleDateFormat sdf){
		for (CustCart cart : list) {
			 Integer row  = custmapper.DeleteCustRecordById(cart.getId());
			 String tomcatpath= request.getServletContext().getRealPath(backups.getTomcatPaths());
             String backuppath = backups.getBackupsPaths();
             File tomcatfile = new File(tomcatpath+"/"+cart.getOrdernum()+".zip");
             File backupfile = new File(backuppath+"/"+cart.getOrdernum()+".zip");
             if(tomcatfile.exists()){		//一并清除文件
            	 tomcatfile.delete();
             }
             if(backupfile.exists()){		//一并清除文件
            	 backupfile.delete();
             }
			if(row!=1){
				throw new RuntimeException("数据库操作异常");
			}
		}
		logger.info("清除数据库过期订单>>>>>数量:"+list.size()+"条,时间:"+sdf.format(new Date()));
	}
}
