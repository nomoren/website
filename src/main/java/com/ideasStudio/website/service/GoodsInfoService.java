package com.ideasStudio.website.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ideasStudio.website.entity.GoodsEntity;
import com.ideasStudio.website.entity.GoodsSize;
import com.ideasStudio.website.vo.GoodsVo;

/**
 * 插入商品借口
 * @author Administrator
 *
 */
public interface GoodsInfoService {
	/**
	 * 根据材质查询商品信息
	 * @param materwal
	 * @return
	 */
	List<GoodsVo> showGoodByMaterial(String materwal,Integer bigtype,Integer smalltype);
	/**
	 * 分页查询商品
	 * page 页数
	 * @return
	 */
	List<GoodsVo> showAllGoods(Integer page);
	/**
	 * 根据商品id查询商品详情
	 * @param id 商品id
	 * @return 一个商品实例
	 */
	GoodsVo getGoods(Integer id);
	
	/**
	 * 根据尺寸id查询GoodsSize
	 * @param sizeId
	 * @return
	 */
	GoodsSize getGoodsSizeMoney(Integer sizeId);
	
	/**
	 * 随机获取num个商品列表
	 * @param num
	 * @return
	 */
	List<GoodsVo> getGoodsListByRand(Integer num);
	
	/**
	 * 插入一个商品
	 * @param goods 商品信息
	 * @param length 长
	 * @param width 宽
	 * @param price 价格
	 * @param file 图片
	 */
	void insert(GoodsEntity goods,Double[] length,
			Double[] width,Double[] price,CommonsMultipartFile[] file,HttpServletRequest request);
	/**
	 * 获取所有商品
	 * @return
	 */
	Integer getGoodCount();
	/**
	 * 上架商品或下架
	 * @param id 商品id
	 * @param status 0，1
	 */
	void changeGoodsStatus(Integer id,Integer status);
	/**
	 * @param goodsId
	 */
	void deleteGoods(Integer goodsId);
	/**
	 * 根据id查询type和material
	 * @param goodsId
	 * @return
	 */
	GoodsVo getTypeAndMaterialById(Integer goodsId);
}
