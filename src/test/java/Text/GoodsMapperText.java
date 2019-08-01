package Text;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ideasStudio.website.entity.GoodsEntity;
import com.ideasStudio.website.mapper.GoodsInfoMapper;
import com.ideasStudio.website.vo.GoodsVo;

public class GoodsMapperText {

	@Test
	public void text() {
		ApplicationContext ac=new ClassPathXmlApplicationContext("spring-dao.xml");
		GoodsInfoMapper gs=ac.getBean("goodsInfoMapper",GoodsInfoMapper.class);
		List<GoodsVo> list=gs.getListByRand(5);
		System.out.println(list);
	}
}
