package com.ideasStudio.website.controller;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ideasStudio.website.entity.GoodsEntity;
import com.ideasStudio.website.entity.GoodsSize;
import com.ideasStudio.website.entity.ResponseResult;
import com.ideasStudio.website.service.CartService;
import com.ideasStudio.website.service.GoodsInfoService;
import com.ideasStudio.website.service.customization.CustService;
import com.ideasStudio.website.vo.GoodsVo;

/**
 * 处理商品信息的controller
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/goods")
public class GoodsInfoController extends BaseController{
	@Autowired
	private GoodsInfoService  goodsService;
	@Autowired
	private CartService cartService;
	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;
	@Autowired
	private CustService custservice;
	@PostConstruct
	public void dobefore() {
		RedisSerializer serializer=new StringRedisSerializer();
		redisTemplate.setKeySerializer(serializer);
	}
	/**
	 * 根据材质和类型查询商品列表 
	 * @param material 材质
	 * @return 符合要求的商品列表
	 */
	
	@SuppressWarnings("unchecked")
	@ResponseBody                            
	@RequestMapping(value="/show.do",method=RequestMethod.POST)
	public ResponseResult<List<GoodsVo>> showAll(@RequestParam(required=false,defaultValue="亚克力") String material,
												@RequestParam(required=false,defaultValue="1")Integer bigtype,
												@RequestParam(required=false,defaultValue="0")Integer smalltype){
		
		List<GoodsVo> list=(List<GoodsVo>) redisTemplate.opsForValue().get(material+bigtype+smalltype);
		if(list==null) {
			synchronized (this) {
				list=(List<GoodsVo>) redisTemplate.opsForValue().get(material+bigtype+smalltype);
				if(list==null) {
				System.out.println("去数据库拿了");
				list=goodsService.showGoodByMaterial(material, bigtype, smalltype);
				redisTemplate.opsForValue().set(material+bigtype+smalltype, list);
				}
			}
		}else {
			System.out.println("去缓存拿了");
		}
		ResponseResult<List<GoodsVo>> rr=new ResponseResult<>();
		rr.setData(list);
		return rr;
	}
	/**
	 * 根据id查询商品
	 * @param id 商品id
	 * @return 商品
	 */
	@ResponseBody
	@RequestMapping(value="/details.do")
	public ResponseResult<GoodsVo> getGoods(Integer id){
		GoodsVo goods=goodsService.getGoods(id);
		Collections.sort(goods.getSizeList());
		ResponseResult<GoodsVo> rr=new ResponseResult<>();
		rr.setData(goods);
		return rr;
	}
	/**
	 * 查询用户的购物车数量
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getCartCount.do",method=RequestMethod.POST)
	public ResponseResult<Integer> getCartCount(HttpSession session){
		String uid=session.getAttribute("uid").toString();
		ResponseResult<Integer> rr=new ResponseResult<>();
		if(uid!=null) {
			Integer num=cartService.getCartCount(uid) + custservice.getCartCount(uid);
			rr.setData(num);
		}else {//还没有登录的情况
			rr.setData(0);
		}
		return rr;
	}
	/**
	 * 根据根据尺寸id查询对应的价格
	 * @param sizeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="getSizeMoney.do",method=RequestMethod.POST)
	public ResponseResult<GoodsSize> getGoodsSizeMoney(Integer sizeId){
		GoodsSize gs=goodsService.getGoodsSizeMoney(sizeId);
		ResponseResult<GoodsSize> rr=new ResponseResult<>();
		rr.setData(gs);
		return rr;
	}
	/** 
	 * 随机获取num条商品数据
	 * @param num
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getGoodsListByRand.do",method=RequestMethod.POST)
	public ResponseResult<List<GoodsVo>> getListByRand(Integer num){
		List<GoodsVo> list=goodsService.getGoodsListByRand(num);
		ResponseResult<List<GoodsVo>> rr=new ResponseResult<>();
		rr.setData(list);
		return rr;
	}
	 
	/**
	 * 返回插入图片信息页面
	 * @return jsp名称
	 */
	@RequestMapping("/show.do")
	public String showUploadHtml() {
		return "uploadInfo";
	}
	/**
	 * 新增一个商品
	 * @param request
	 * @param goods 商品
	 * @param length 长
	 * @param width 宽
	 * @param price 价格
	 * @param file 图片
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/uploadGoods.do",method=RequestMethod.POST)
	public ResponseResult<Void> uploadImge(
			HttpServletRequest request,
			GoodsEntity goods,
			@RequestParam("length")Double[] length,
			@RequestParam("width")Double[] width,
			@RequestParam("price")Double[] price,
			@RequestParam("file")CommonsMultipartFile[] file
			){
		String path="/images/productuse/"+goods.getNumber();
		goods.setPath(path);
		goodsService.insert(goods, length, width, price, file,request);
		GoodsVo goodsVo=goodsService.getTypeAndMaterialById(goods.getId());
		if(goodsVo!=null) {
			String material=goodsVo.getMaterial();
			Integer bigtype=goodsVo.getBigtype();
			Integer smalltype=goodsVo.getSmalltype();
			redisTemplate.delete(material+bigtype+smalltype);
			redisTemplate.delete(material+bigtype+0);
		}
		return new ResponseResult<>(200, "恭喜，上传商品信息成功,返回商品列表页可查看商品信息");
	}
	/**
	 * 重命名上传的文件
	 * @return
	 */
	private String getFileName() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new Date());
	}
}
