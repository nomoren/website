//获取url参数
(function ($) {
	$.getUrlParam = function (name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null) return unescape(r[2]); return null;
	}
})(jQuery);

//页面加载完成，显示订单详情
$(document).ready(function(){
	var orderId=$.getUrlParam("orderId");
	var url="../order/getOrderInfo.do";
	var data="orderId="+orderId;
	$.ajax({
		"url":url,
		"data":data,
		"type":"POST",
		"dataType":"JSON",
		"success":function(json){
			if(json.status==200){
				var order=json.data.orderVo;
				var money=json.data.money;
				$("#name").text(order.recvName);
				$("#province").text(order.recvDistrict);
				$("#address").text(order.recvAddress);
				$("#phone").text(order.recvPhone);
				$("#xiaoJi").text("￥"+money);
			var list=order.list;
			$("#cartNum").text(list.length+"件");
			$("#orderList").empty();
			for(var i=0;i<=list.length;i++){
				var td='<tr class="b-products-table__row b-products-table__row_product js-cart-item "><td class="b-products-table__cell b-products-table__cell_image">'
					   +'<a class="b-product-image__link js-thumb-link" href="#" title="#{title}">' 
					   +'<picture class="js-amplience-img"> '
					   +'<img class=" " src="..#{path}" width="100%">'
					   +'</picture>'
					   +'</a></td><td class="b-products-table__cell b-products-table__cell_details"><div class="product-list-item" style="padding-left: 20px;"><div class="b-product-name b-space-sm js-product-name">'
					   +'<a class="b-product-name__link" href="#" title="#{title}">#{title}</a>'
					   +'</div><div class="b-space-sm"><span class="label b-hide">价格:</span><div class="b-price"><span class="b-price__value">' 
					   +'<span class="b-price__caption">价格：</span>'
					   +'<span class="b-price__digit">￥#{price}</span>'
					   +'</span></div></div><div class="b-space-sm ">'
					   +'<span class="b-price__caption">商品编号: </span> '
					   +'<span class="b-price__digit">#{no}</span>'
					   +'</div> <div class="b-product-variations b-product-variations_cart"><ul class="b-product-variations__list">'
					   +'<li class="b-product-variations__item js-size">'
					   +'<span class="b-product-variations__title">尺寸: </span><div class="b-select b-select_inline b-select_borderless">'
					   +'<span class="b-price__digit">#{size}</span>'
					   +'</div></li></ul></div></div></td><td class="b-products-table__cell b-products-table__cell_quantity"><div class="b-stepper b-stepper_cart">'
					   +'<input class="b-stepper__input b-text-input js-qty-input" type="text" value="#{number}" readonly="readonly">'
					   +'</div> </td><td class="b-products-table__cell b-products-table__cell_total"><span class="b-hide b-show_sm_ib b-product-variations__title b-product-variations__title_total">小计:</span>'
					   +'<span class="b-price"> <span class="b-price__digit">￥#{allMoney}</span>'
					   +'</span></td></tr>';
					td=td.replace(/#{path}/g,list[i].goodsImage);
					td=td.replace(/#{title}/g,list[i].goodsTitle);
					td=td.replace(/#{price}/g,list[i].goodsPrice);
					td=td.replace(/#{no}/g,list[i].goodsNo);
					td=td.replace(/#{size}/g,list[i].goodsLength+"cm*"+list[i].goodsWidth+"cm");
					td=td.replace(/#{number}/g,list[i].goodsNum);
					td=td.replace(/#{allMoney}/g,list[i].allMoney);
					$("#orderList").append(td);
				}
			}else{
				console.log(json.message);
				alert(json.message);
			}
		},
		"error":function(){
			alert("您的登录信息已过期，请重新登录");
			location.href="../store/productlist.html";
		}
	});
});