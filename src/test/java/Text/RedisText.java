package Text;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisText {

	@Test
	public void redisTEXT() {
		
		ApplicationContext ac=new ClassPathXmlApplicationContext("spring-redis.xml");
		RedisTemplate<Object, Object> template=ac.getBean("redisTemplate",RedisTemplate.class);
		
		String v=template.opsForValue().get("b").toString();
		System.out.println(v);
	}
}
