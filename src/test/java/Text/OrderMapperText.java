package Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ideasStudio.website.entity.GoodsEntity;
import com.ideasStudio.website.entity.Order;
import com.ideasStudio.website.mapper.GoodsInfoMapper;
import com.ideasStudio.website.mapper.OrderMapper;
import com.ideasStudio.website.util.CreateOrderNumber;
import com.ideasStudio.website.vo.GoodsVo;
import com.ideasStudio.website.vo.OrderVo;
public class OrderMapperText {

	@Test
	public void text() {
		ApplicationContext ac=new ClassPathXmlApplicationContext("spring-dao.xml");
		OrderMapper om=ac.getBean("orderMapper",OrderMapper.class);
		System.out.println(om.getOrderByOrderNo("201903290438764323983"));
		
		
	}
}
