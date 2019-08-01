package com.ideasStudio.website.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ideasStudio.website.entity.Border;
import com.ideasStudio.website.entity.CustCart;
import com.ideasStudio.website.entity.Shape;
import com.ideasStudio.website.entity.ShapeSizeStandard;
import com.ideasStudio.website.entity.ShapeUrl;
import com.ideasStudio.website.vo.CartVo;

/**
 * 私人订制持久层操作接口
 * @author Administrator
 *
 */
@Mapper
public interface CustMapper {

	/**
	 * 获取图形列表
	 * @return
	 */
	@Select("SELECT id,shapeId,shapeNo,titleImgURL,shapeName,adjustable,shapeTitle,material,price,width,height FROM shape")
	List<Shape> getShape();
	
	/**
	 * 根据对应的图形，获取到对应的url路径
	 * @param shape	图形的ID
	 * @return 返回查询到的对应的url路径
	 */
	 ShapeUrl getUrl(Integer shape);
	
	/**
	 * 往私人订制购物车中插入一条记录
	 * @param cart  插入的记录
	 * @return 返回插入的id  然后绑定到页面上，下次用户提交补全信息
	 */
	Integer InsertCustCart(CustCart cart);
	
	/**
	 * 往私人订制购物车中插入一条记录
	 * @param cart  插入的记录
	 * @return 返回插入的id  然后绑定到页面上，下次用户提交补全信息
	 */
	CustCart findRecord(String ordernum);
	
	/**
	 * 查找私人订制图片尺寸路径
	 * @param Integer shapeId  图形id
	 * @return 查询到的图形的路径
	 */
	String findsizepic(Integer shapeId);
	
	/**
	 * 根据图形ID图形是否是标准图形
	 * @param shapeId 图形的id
	 * @return 返回0则代表是标准图形，它有标准的宽高，返回1则表示是可调的图形
	 */
	ShapeUrl findAdjustable(Integer shapeId);
	
	/**
	 * 查找标准尺寸
	 * @param shapeId 查找图形ID
	 * @return 返回查找到的图形的尺寸
	 */
	List<ShapeSizeStandard> findStandardSize(@Param("shapeId")Integer shapeId);
	
	/**
	 * 查找单独的标注的尺寸信息
	 * @param shapeId 图形的Id
	 * @return 返回查询到的尺寸信息
	 */
	ShapeSizeStandard findShapeSizeStandardById(Integer shapeId);
	
	/**
	 * 查找非标准尺寸
	 * @param shapeId  查找图形的id
	 * @param field 查询的字段
	 * @return 返回超找到的图形的尺寸
	 */
	List<Integer> findNonStandardSize(@Param("shapeId")Integer shapeId,@Param("field")String field);

	/**
	 * 根据关键字查询边框
	 * @param keyword
	 * @return
	 */
	List<Border> findBorderByKeyWord(@Param("keyword")String keyword,@Param("material") String material);
	
	/**
	 * 查询所有的边框
	 * @return
	 */
	List<Border> findBorderAll(String material);
	
	/**
	 * 根据borderId查询border
	 * @param borderId	border的Id
	 * @return 返回查询到的border
	 */
	Border findBorderById(Integer borderId);
	
	/**
	 * 查询订单的状态
	 * @param recordId
	 * @return
	 */
	Integer selectOrderStatus(Integer recordId);
	
	/**
	 * 查询订单的状态
	 * @param recordId
	 * @return
	 */
	@Select("SELECT status from custcart where ordernum=#{ordernum}")
	Integer selectOrderStatusByOrdernum(String ordernum);
	
	/**
	 * 更新数据库的操作
	 * @param cart	更新的数据
	 * @return	返回操作的结果
	 */
	Integer UpdataInfo(CustCart cart);
	
	/**
	 * 更新这条记录的状态
	 * @param status
	 */
	void UpdateStatus(@Param("status")Integer status,@Param("recordId")Integer recordId);

	/**
	 * 根据用户id查询购物车列表
	 * @param uid
	 * @return
	 */
	List<CartVo> getCartList(String uid);
	
	/**
	 * 根据uid查询购物车总金额,返回购物车总金额
	 * @param uid
	 * @return
	 */
	Double getCartMoney(String uid);
	
	/**
	 * 根据记录ID查询用户该记录商品所对应的单价
	 * @param recirdId
	 * @return
	 */
	CustCart getCartPriceByRecordId(Integer recordId);
	
	/**
	 * 根据记录Id修改购物车中购买数量
	 * @param cart 修改的值
	 * @return 返回修改记录受影响的行数
	 */
	Integer UpdateCartOrNumByRecordId(CustCart cart);	
	
	/**
	 * 根据记录Id删除购物车中加入的商品
	 * @param recordId 记录ID
	 * @return 返回修改记录受影响的行数
	 */
	Integer DeleteCustRecordById(Integer recordId);	
	
	/**
	 * 查询用户的购物车数量
	 * @param uid
	 * @return
	 */
	Integer getCartCount(String uid);

	/**
	 * 往私人订制购物车中插入一条记录
	 * @param cart  插入的记录
	 * @return 返回插入的id  然后绑定到页面上，下次用户提交补全信息
	 */
	Integer addCustCart(CustCart cart);

	/**
	 * 清空用户购物车
	 * @param uid
	 * @return
	 */
	Integer deleteAllCart(String uid);

	
	
	@Select("SELECT id,ordernum from custcart where createtime < #{date} and status = 0")
	List<CustCart> queryPastOrder(String date);

	
	@Select("SELECT * FROM custcart where ordernum = #{orderNum}")
	CustCart queryOrderByOrdernum(String orderNum);
	
	
	@Select("SELECT * FROM shape where shapeId = #{shape}")
	Shape queryTitleByShape(Integer shape);
}
