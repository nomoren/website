var cartMoney=0;
$(document).ready(function(){
	var url="../cart/list.do";
	var map;
	$("#cartList").empty();
	//显示用户购物车列表
	$.ajax({
		"url":url,
		"type":"POST",
		"dataType":"JSON",
		"success":function(json){
			map=json.data;
			var list=map.list;
			if(list.length==0){
				alert("您还没有添加任何商品");
				return;
			}
			$("#cartNum").text(list.length+"件");
			$("#js-mini-cart-total-qty").text(list.length);
			for(var i=0;i<list.length;i++){
				var tr='<tr class="b-products-table__row b-products-table__row_product js-cart-item " data-uuid="43105194f2f1fb9fe124230ccb" data-pid="5052751322127" id="goods#{cartid}">'
				+'<td class="b-products-table__cell b-products-table__cell_image">'
				+'<a class=" js-thumb-link" href="#{goodshref}" title="Athleisure Elodie Cross Over Vest">'
				+'<picture class="js-amplience-img" >'
				+'<img class=" " src="..#{goodsPath}#{suffix}"></picture>'
				+'</a></td>'
				+'<td class="b-products-table__cell b-products-table__cell_details">'
				+'<div class="product-list-item">'
				+'<div class="b-product-name b-space-sm js-product-name"> '
				+'<a class="b-product-name__link" href="#{goodshref}" title="">#{goodsTitle}</a>'
				+'</div>'
				+'<div class="b-space-sm"><span class="label b-hide">价格:</span><div class="b-price"><span class="b-price__value"> <span class="b-price__caption">价格：</span> '
				+'<span id="goodsPrice#{cartid}" class="b-price__digit">#{goodsPrice}</span>'
				+'</span></div></div><div class="b-space-sm ">'
				+'<span class="b-price__caption">商品编号: </span> '
				+'<span class="b-price__digit">#{goodsNo}</span>'
				+'</div>'
				+'<div class="b-product-variations b-product-variations_cart"><ul class="b-product-variations__list">'
				+'<li class="b-product-variations__item js-size">'
				+'<span class="b-product-variations__title">尺寸: </span>'
				+'<div class="b-select b-select_inline b-select_borderless">'
				+'<span class="b-price__digit">#{goodsSize}</span>'
				+'</div></li></ul></div></div></td>'
				+'<td class="b-products-table__cell b-products-table__cell_quantity"><div class="b-stepper b-stepper_cart">'
				+'<span class="b-button b-button_borderless b-stepper__button b-stepper__button_decrement  js-qty-increase" role="button" tabindex="0" onclick="addOrSubOne(#{cartid},$(this).text(),#{cust})">–</span>'
				+'<input id="goodsNumber#{cartid}" class="b-stepper__input b-text-input js-qty-input" type="text" id="dwfrm_cart_shipments_i0_items_i0_quantity" maxlength="2" value="#{goodsNumber}" data-max="38" data-min="1" data-step="1" readonly="readonly">' 
				+'<span class="b-button b-button_borderless b-stepper__button b-stepper__button_decrement  js-qty-increase" role="button" tabindex="0" onclick="addOrSubOne(#{cartid},$(this).text(),#{cust})">+</span>'
				+'</div></td><td class="b-products-table__cell b-products-table__cell_total">'
				+'<span class="b-hide b-show_sm_ib b-product-variations__title b-product-variations__title_total">小计:</span><span class="b-price">'
				+'<span id="allMoney#{cartid}" class="b-price__digit">￥#{allMoney}</span>'
				+'</span></td><td class="b-products-table__cell b-products-table__cell_actions"><div class="item-user-actions">'
				+'<button id="remove#{cartid}" type="button" class="btn btn-link" onclick="remove(#{cartid},#{cust})">删除</button>'
				+'</div></td>'
				+'</tr>';
				tr = tr.replace(/#{cartid}/g,list[i].cartId);
				tr = tr.replace(/#{cust}/g,list[i].cust);
				tr = tr.replace(/#{cust}/g,list[i].cust);
				tr = tr.replace(/#{goodsPath}/g,list[i].goodsPath);
				tr = tr.replace(/#{goodsTitle}/g,list[i].goodsTitle);
				tr = tr.replace(/#{goodsPrice}/g,list[i].goodsPrice);
				tr = tr.replace(/#{suffix}/g,list[i].cust==1?"":"/img0.png")
				tr = tr.replace(/#{goodsNo}/g,list[i].goodsNo);
				tr = tr.replace(/#{goodsNumber}/g,list[i].goodsNum);
				tr = tr.replace(/#{goodsSize}/g,"长:"+list[i].goodsLength+"CM;宽:"+list[i].goodsWidth+"CM");
				tr = tr.replace(/#{allMoney}/g,list[i].money);
				if(list[i].cust!=1){
					tr=tr.replace(/#{goodshref}/g,"./product_item.html?id="+list[i].goodsId);
				}else{
					tr=tr.replace(/#{goodshref}/g,"#");
				}
				
				$("#cartList").append(tr);
			}
			cartMoney=map.money;
			$("#xiaoJi").text("￥"+cartMoney);
			$("#zongJi").text("￥"+cartMoney);
		}
	});
	//显示倾力推荐
	$.ajax({
		"url":"../goods/getGoodsListByRand.do",
		"data":"num=4",
		"type":"POST",
		"dataType":"JSON",
		"success":function(json){
			if(json.status==200){
				var list=json.data;
				for(var i=0;i<list.length;i++){
					$("#a"+i).attr("href","product_item.html?id="+list[i].id);
					$("#img"+i).attr("title",list[i].title);
					$("#img"+i).attr("src",".."+list[i].path+"/img0.png");
					$("#img"+i+"_"+i).attr("src",".."+list[i].path+"/img1.png");
					$("#title"+i).text(list[i].title);
					$("#title"+i).attr("href","product_item.html?id="+list[i].id);
					$("#price"+i).text("￥"+list[i].price);
				}
			}else{
				$("#listRand").empty();
			}
		},
		"error":function(){
			$("#toLogin").trigger("click");
		}
		
	});
});

//购物车商品数量加一
function addOrSubOne(cartId,type,cust){
	var num=$("#goodsNumber"+cartId).val();//商品数量
	var url="../cart/addSubOne.do";
	var map;
	if(type=="+"){
		type="add";
	}else{
		type="sub";
		if(num==1){//商品数量为0，不执行请求
			remove(cartId,cust);
			return;
		}
	}
	var data="cartId="+cartId+"&type="+type+"&cust="+cust;
	$.ajax({
		"url":url,
		"data":data,
		"type":"POST",
		"dataType":"JSON",
		"success":function(json){
			if(json.status==200){
				map=json.data;
				var goodsMoney=map.goodsMoney;
				cartMoney=map.allMoney;
				if(type=="add"){
					num++;
				}else{
					num--;
				}
				$("#goodsNumber"+cartId).val(num);
				$("#allMoney"+cartId).text("￥"+goodsMoney);//单项商品总金额
				$("#xiaoJi").text("￥"+cartMoney);
				$("#zongJi").text("￥"+cartMoney);
				$("#iscoupons").text("-");
			}else{
				alert(json.message);
			}
		},
		"error":function(){
			$("#toLogin").trigger("click");//没有登录，弹出登录框
			location.reload();
		}
	});
}

//删除一条购物车数据
function remove(id,cust){
	var url="../cart/deleteCart.do";
	var data="cartId="+id+"&cust="+cust;
	var result = confirm("确认删除此商品吗？");
	if(result==true){
	$.ajax({
		"url":url,
		"data":data,
		"type":"POST",
		"dataType":"JSON",
		"success":function(json){
			if(json.status==200){
				$("#goods"+id).remove();
				cartMoney=json.data;
				$("#xiaoJi").text("￥"+cartMoney);
				$("#zongJi").text("￥"+cartMoney);
				$("#iscoupons").text("-");
				$.ajax({
					"url":"../goods/getCartCount.do",
					"type":"POST",
					"dataType":"JSON",
					"success":function(json){
						if(json.status==200){
							var num=json.data;
							$("#cartNum").text(num+"件");
							$("#js-mini-cart-total-qty").text(num);
						}
					}
				});
			}else{
				alert(json.message);
			}
		},
		"error":function(){
			$("#toLogin").trigger("click");
			location.reload();
		}
	});
 }
}
//去支付
$("#gotoPlay").click(function(){
	if($("#cartNum").text()=="0件"){
		
		alert("您还没有购买任何商品");
		return;
	}
	location.href="checkout.html";
});
//使用优惠码
function useCoupon(){
	if(cartMoney<499.0){
		alert("订单金额小于499，无法使用优惠券");
		return;
	}
	var couponCode=$("#couponCode").val();
	if(couponCode!=""){
		var reg=/^[a-z]*95$/i;
		if(reg.test(couponCode)){//使用员工码
			$.ajax({
				"url":"../coupons/findEmpCode.do",
				"data":"empCode="+couponCode,
				"type":"POST",
				"dataType":"JSON",
				"success":function(json){
					if(json.status==200){
						var payMoney=json.data;
						$("#iscoupons").text("打九五折");
						$("#zongJi").text("￥"+payMoney);
					}else{
						alert(json.message);
					}
				},
				"error":function(){
					$("#toLogin").trigger("click");
					location.reload();
				}
				
			});
		}else if(couponCode.substring(0,5)=="ideas"){//使用优惠券
			$.ajax({
				"url":"../coupons/findCouponsCode.do",
				"data":"couponsCode="+couponCode,
				"type":"POST",
				"dataType":"JSON",
				"success":function(json){
					if(json.status==200){
						var payMoney=json.data;
						$("#iscoupons").text("-￥50.0");
						$("#zongJi").text("￥"+payMoney);
					}else{
						alert(json.message);
					}
				},
				"error":function(){
					$("#toLogin").trigger("click");
					location.reload();
				}
			});
		}else{
			alert("请输入正确的优惠码")
		}
	}
}












