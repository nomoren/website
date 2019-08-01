package com.ideasStudio.website.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 后台商品管理
 * @author Administrator
 *
 */
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ideasStudio.website.controller.BaseController;
import com.ideasStudio.website.entity.ResponseResult;
import com.ideasStudio.website.service.GoodsInfoService;
import com.ideasStudio.website.service.ex.ServiceException;
import com.ideasStudio.website.vo.GoodsVo;
@Controller
@RequestMapping("/admin")
public class GoodsMangerController extends BaseController{
	@Autowired
	private GoodsInfoService goodsService;
	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;
	@PostConstruct
	public void dobefore() {
		RedisSerializer serializer=new StringRedisSerializer();
		redisTemplate.setKeySerializer(serializer);
	}
	/**
	 * 分页获取商品
	 * @return
	 */
	@RequestMapping(value="/getAllGoods.do",method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<Map<Object, Object>> getAllGoods(Integer page){
		Map<Object, Object> map=new HashMap<>();
		Integer number=goodsService.getGoodCount();//商品总数
		Integer allPage=1;//总页，当数据不够分页，默认一页
		if(number>5) {
			allPage=number%5==0?number/5:number/5+1;//总共可以分多少页
		}
		if(page>allPage) {
			throw new ServiceException("没有数据了");
		}
		map.put("num", number);//总数
		map.put("allPage", allPage);//共几页
		List<GoodsVo> list=goodsService.showAllGoods(page);
		map.put("list", list);
		ResponseResult<Map<Object, Object>> rr=new ResponseResult<>();
		rr.setData(map);
		return rr;
	}
	/**
	 * 改变商品的状态，上架或下架
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping(value="/changeGoodsStatus.do",method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<Void> changeGoodsStatus(Integer id,Integer status){
		goodsService.changeGoodsStatus(id, status);
		GoodsVo goodsVo=goodsService.getTypeAndMaterialById(id);
		if(goodsVo!=null) {
			String material=goodsVo.getMaterial();
			Integer bigtype=goodsVo.getBigtype();
			Integer smalltype=goodsVo.getSmalltype();
			redisTemplate.delete(material+bigtype+smalltype);//上架或下架后，删除redis缓存
			redisTemplate.delete(material+bigtype+0);
		}
		return new ResponseResult<>();
	}
	/**
	 * 删除商品信息
	 * @param goodsId
	 * @return
	 */
	@RequestMapping(value="/deleteGoods.do",method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<Void> deleteGoods(Integer goodsId){
		goodsService.deleteGoods(goodsId);
		return new ResponseResult<>();
	}
}
