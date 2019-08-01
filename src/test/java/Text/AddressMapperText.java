package Text;

import com.ideasStudio.website.entity.Address;
import com.ideasStudio.website.mapper.AddressMapper;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AddressMapperText {

	@Test
	public void UserText() {
		System.out.println("aaa");
		
	}
	@Test
	public void text() {
		ApplicationContext ac=new ClassPathXmlApplicationContext("spring-dao.xml");
		AddressMapper am=ac.getBean("addressMapper",AddressMapper.class);
		Integer num=am.getAddressNum("70f75f4c");
		System.out.println("aaaaaaaaa"+num);
		/*Double p=2.0;
		Double p1=1.9;
		BigDecimal b1=new BigDecimal(Double.toString(p));
		BigDecimal b2=new BigDecimal(Double.toString(p1));
		Double r=b1.subtract(b2).doubleValue();
		System.out.println(r);*/
		
	}
}
