package com.ideasStudio.website.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ideasStudio.website.entity.GoodsEntity;
import com.ideasStudio.website.entity.GoodsSize;
import com.ideasStudio.website.vo.GoodsVo;

//商品信息Mapper
public interface GoodsInfoMapper {
	/**
	 * 根据材质和类型搜索商品
	 * @param material 材质
	 * @return 商品集合
	 */
	List<GoodsVo> showGoods(@Param("material")String material,
							@Param("bigtype")Integer bigtype,
							@Param("smalltype")Integer smalltype);

	
	/**
	 * 根据商品ID获取商品详情
	 * @param id 商品ID
	 * @return 一个商品实例
	 */
	GoodsVo getGoods(Integer id);
	
	/**
	 * 根据商品id查询商品单价
	 * @param id
	 * @return
	 */
	Double getGooodsPrice(Integer id);
	
	
	/**
	 * 根据id查询对应GoodsSize
	 * @param sizeId
	 * @return
	 */
	GoodsSize getGoodsSizeMoney(Integer sizeId);
	
	/**
	 * 随机从商品表取出num条数据
	 * @param num
	 * @return
	 */
	List<GoodsVo> getListByRand(Integer num);
	
	/**
	 * 新增一个商品 
	 * @param goods
	 * @return
	 */
	Integer insert(GoodsEntity goods);
	/**
	 * 插入商品的尺寸
	 * @param size
	 * @return
	 */
	Integer insertSize(GoodsSize size);
	//删除width,heigth为null的
	Integer deleteNull();
	/**
	 * 分页获取商品列表
	 * @return
	 */
	List<GoodsVo> getAllGoods(@Param("jump")Integer jump,@Param("num")Integer num);
	/**
	 * 查询商品数量
	 * @return
	 */
	Integer getGoodsCount();
	/**
	 * 改变商品的转状态，上架或者下架
	 * @return
	 */
	Integer changGoodsStatus(@Param("id")Integer id,@Param("status")Integer status);
	/**
	 * 根据商品id删除商品信息
	 * @param id
	 * @return
	 */
	Integer deleteGoods(Integer goodsId);
	/**
	 * 根据商品id删除商品的尺寸和价格信息
	 * @param id
	 * @return
	 */
	Integer deleteGoodsSize(Integer goodsId);
	/**
	 * 根据商品id查询类型和材质
	 * @param goodsId
	 * @return
	 */
	GoodsVo getTypeAndMaterialById(Integer goodsId);
}
