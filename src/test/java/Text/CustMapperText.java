package Text;

import java.util.Date;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ideasStudio.website.entity.CustCart;
import com.ideasStudio.website.mapper.CustMapper;

public class CustMapperText {

	@Test
	public void insertCart_uid() {
		ApplicationContext ac=new ClassPathXmlApplicationContext("spring-dao.xml");
		CustMapper cust=ac.getBean("custMapper",CustMapper.class);
		CustCart cart = new CustCart();
		cart.setUid("uid1111");
		cart.setShapeid(3);
		cart.setOrdernum(2525+"");
		cart.setPath("D:/tts9/workspace/website-v2/src/main/java/com/ideasStudio/website/interceptor");
		cart.setCreatetime(new Date());
		cart.setCreateuid("uid1111");
		Integer row = cust.InsertCustCart(cart);
		System.out.println("row"+row+"-------id="+cart.getId());
	}
	
	@Test
	public void insertCart_uuid() {
		ApplicationContext ac=new ClassPathXmlApplicationContext("spring-dao.xml");
		CustMapper cust=ac.getBean("custMapper",CustMapper.class);
		CustCart cart = new CustCart();
		cart.setUuid("uuid1111");
		cart.setShapeid(3);
		cart.setOrdernum(2528+"");
		cart.setPath("D:/tts9/workspace/website-v2/src/main/java/com/ideasStudio/website/interceptor");
		cart.setCreatetime(new Date());
		cart.setCreateuuid("uid1111");
		Integer row = cust.InsertCustCart(cart);
		System.out.println("row"+row+"-------id="+cart.getId());
	}
	
	@Test
	public void findRecored() {
		ApplicationContext ac=new ClassPathXmlApplicationContext("spring-dao.xml");
		CustMapper cust=ac.getBean("custMapper",CustMapper.class);
		CustCart id = cust.findRecord(3000+"");
		System.out.println("id="+id);
	}

}
