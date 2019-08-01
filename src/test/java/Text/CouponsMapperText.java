package Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ideasStudio.website.entity.EmpCode;
import com.ideasStudio.website.entity.EmpCodeRecord;
import com.ideasStudio.website.entity.GoodsEntity;
import com.ideasStudio.website.entity.Order;
import com.ideasStudio.website.mapper.GoodsInfoMapper;
import com.ideasStudio.website.mapper.OrderMapper;
import com.ideasStudio.website.mapper.CouponsMapper;
import com.ideasStudio.website.util.CreateOrderNumber;
import com.ideasStudio.website.vo.EmpCodeVo;
import com.ideasStudio.website.vo.GoodsVo;
import com.ideasStudio.website.vo.OrderVo;
public class CouponsMapperText {

	@Test
	public void text() {
		ApplicationContext ac=new ClassPathXmlApplicationContext("spring-dao.xml");
		CouponsMapper cm=ac.getBean("couponsMapper",CouponsMapper.class);
		EmpCode emp=cm.findEmpCodeByCode("ideas20190101001");
		System.out.println(emp);
		System.out.println(cm.findCouponsCodeByCode("yhm20190102"));
		
		/*EmpCodeRecord record=new EmpCodeRecord();
		record.setCode("20170101001");
		record.setMoney(310.00);
		record.setUsedate(new Date());
		cm.insertNewEmpCodeRecode(record);*/
		
	}
	@Test
	public void text2() {
		ApplicationContext ac=new ClassPathXmlApplicationContext("spring-dao.xml");
		CouponsMapper cm=ac.getBean("couponsMapper",CouponsMapper.class);
		List<EmpCodeVo> list=cm.getOneEmpCodeRecordList(0, 5, "小粉", null);
		System.out.println(list);
		
	}
}
