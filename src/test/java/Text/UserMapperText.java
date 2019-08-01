package Text;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

import com.ideasStudio.website.entity.Cart;
import com.ideasStudio.website.entity.District;
import com.ideasStudio.website.entity.User;
import com.ideasStudio.website.mapper.CartMapper;
import com.ideasStudio.website.mapper.DistrictMapper;
import com.ideasStudio.website.mapper.UserMapper;
import com.ideasStudio.website.vo.CartVo;

public class UserMapperText {

	@Test
	public void UserText() {
		ApplicationContext ac=new ClassPathXmlApplicationContext("spring-dao.xml");
		
		UserMapper um=ac.getBean("userMapper",UserMapper.class);
		/*User newUser=new User();
		String id=UUID.randomUUID().toString().substring(0, 8);
		newUser.setId(id);
		newUser.setPhone("11111111111");
		newUser.setCreateTime(new Date());
		newUser.setStatus(1);
		newUser.setOpenId("aaaaaa");
		um.register(newUser);*/
	}
	@Test
	public void text() {
		System.out.println("aa");
		/*ApplicationContext ac=new ClassPathXmlApplicationContext("spring-redis.xml");
		System.out.println("aa");
		RedisTemplate<Object, Object> template=ac.getBean("redisTemplate",RedisTemplate.class);
		String v=template.opsForValue().get("h").toString();
		System.out.println(v);*/
		/*DistrictMapper dm=ac.getBean("districtMapper",DistrictMapper.class);
		List<District> list=dm.getDistrictList(0);
		System.out.println(list.size());*/
		
		/*Double p=2.0;
		Double p1=1.9;
		BigDecimal b1=new BigDecimal(Double.toString(p));
		BigDecimal b2=new BigDecimal(Double.toString(p1));
		Double r=b1.subtract(b2).doubleValue();
		System.out.println(r);*/
		
	}
}
