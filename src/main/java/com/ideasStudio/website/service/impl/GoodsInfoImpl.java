package com.ideasStudio.website.service.impl;


import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * 插入商品实现类
 * @author Administrator
 *
 */
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ideasStudio.website.entity.GoodsEntity;
import com.ideasStudio.website.entity.GoodsSize;
import com.ideasStudio.website.mapper.GoodsInfoMapper;
import com.ideasStudio.website.service.GoodsInfoService;
import com.ideasStudio.website.service.ex.ServiceException;
import com.ideasStudio.website.vo.GoodsVo;
@Service()
public class GoodsInfoImpl implements GoodsInfoService{
	@Autowired
	GoodsInfoMapper goodsMapper;

	//获取商品列表
	@Override
	public List<GoodsVo> showGoodByMaterial(String material,Integer bigtype,Integer smalltype) {
		List<GoodsVo> list=goodsMapper.showGoods(material, bigtype, smalltype);
		return list;
	}
	
	//获取商品详情
	@Override
	public GoodsVo getGoods(Integer id) {
		GoodsVo goods=goodsMapper.getGoods(id);
		return goods;
	}

	//获取商品尺寸
	@Override
	public GoodsSize getGoodsSizeMoney(Integer sizeId) {
		GoodsSize gs=goodsMapper.getGoodsSizeMoney(sizeId);
		return gs;
	}
	//随机获取num条商品
	@Override
	public List<GoodsVo> getGoodsListByRand(Integer num) {
		return goodsMapper.getListByRand(num);
	}
	/**
	 * 上传商品信息
	 */
	@Override
	@Transactional
	public void insert(GoodsEntity goods, Double[] length, Double[] width, Double[] price,
			CommonsMultipartFile[] file,HttpServletRequest request) {
		goods.setStatus(1);
		Integer num=goodsMapper.insert(goods);//添加商品
		if(num!=1) {
			throw new ServiceException("添加失败");
		}
		for(int i=0;i<length.length;i++) {//循环保存商品的尺寸和价格列表
			GoodsSize gs=new GoodsSize();
			gs.setGoodsId(goods.getId());
			gs.setLength(length[i]);
			gs.setWidth(width[i]);
			gs.setPrice(price[i]);
			Integer row=goodsMapper.insertSize(gs);//添加商品的尺寸
			if(row!=1) {
				throw new ServiceException("添加失败");
			}
		}
		goodsMapper.deleteNull();//删除width，heigth为null的
		String goodsDirName=goods.getPath();
		String realPath=request.getServletContext().getRealPath(goodsDirName);
		System.out.println(realPath);
		File uploadDir=new File(realPath);//创建商品图在tomcat上的目录
		if(!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
		for(int i=0;i<file.length;i++) {//共会上传2-4张图，循环保存
			int beginIndex=file[i].getOriginalFilename().lastIndexOf(".");
			String suffix=file[i].getOriginalFilename().substring(beginIndex);
			String fileName="img"+i+suffix;
			File dest=new File(uploadDir, fileName);
			try {
				file[i].transferTo(dest);//生成图片
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	//获取所有商品
	@Override
	public List<GoodsVo> showAllGoods(Integer page) {
		Integer num=5;//每页5条数据
		Integer jump=(page-1)*5;//跳过了多少条
		return goodsMapper.getAllGoods(jump, num);
	}
	//获取商品数量
	@Override
	public Integer getGoodCount() { 
		return goodsMapper.getGoodsCount();
	}
	//改变商品的状态
	@Override
	public void changeGoodsStatus(Integer id, Integer status) {
		Integer row=goodsMapper.changGoodsStatus(id, status);
		if(row!=1) {
			throw new ServiceException("设置失败，请稍后再试");
		}
	}
	//删除商品
	@Override
	@Transactional
	public void deleteGoods(Integer goodsId) {
		Integer row=goodsMapper.deleteGoods(goodsId);
		if(row!=1) {
			throw new ServiceException("删除失败");
		}
		Integer row1=goodsMapper.deleteGoodsSize(goodsId);
		if(row1==0) {
			throw new ServiceException("删除失败");
		}
	}
	//根据id查询type和materials
	@Override
	public GoodsVo getTypeAndMaterialById(Integer goodsId) {
		return goodsMapper.getTypeAndMaterialById(goodsId);
	}


	
	
	
	
	
	
	

}
