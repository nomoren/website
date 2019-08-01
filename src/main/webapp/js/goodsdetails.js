//获取url参数
(function ($) {
	$.getUrlParam = function (name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null) return unescape(r[2]); return null;
	}
})(jQuery);

var goodsId;//商品的id
var sizeId;//商品尺寸的id，尺寸id变，价格跟着变
//页面加载完成，就显示商品详情
$(document).ready(function(){
	goodsId=$.getUrlParam("id");
	if(goodsId==null||goodsId==""||isNaN(goodsId)){//参数有误，返回列表页
		 location.href="productlist.html";
		return;
	}
	var url="../goods/details.do";
	var data="id="+goodsId;
	$.ajax({
		"url":url,
		"data":data,
		"type":"POST",
		"dataType":"JSON",
		"success":function(json){
			var goods=json.data;
			if(goods==null){//无此商品，返回列表页
				location.href="product_list.html";
				return;
			}
			//console.log("................"+goods)
			$("#small01,#big01").attr("src",".."+goods.path+"/img0.png");
			$("#small02,#big02").attr("src",".."+goods.path+"/img1.png");
			$("#small03,#big03").attr("src","../images/productuse/details/img2.png");
			$("#small04,#big04").attr("src","../images/productuse/details/img3.png");
			$("#title").text(goods.title);
			$("#number").text("商品编号:"+goods.number);
			$("#material").text("材料："+goods.material);
			var list=goods.sizeList;
			$("#goodsSize").text(list[0].length+'cm*'+list[0].width+'cm')
			$("#sizePrice").text("￥"+list[0].price)
			sizeId=list[0].id;
			$("#sizelist").empty();
			for(var i=0;i<=list.length-1;i++){
				var li='<li class="b-variation__item js-variation-swatch selectable" id="size">'
					+'<a id="size#{sizeId}" class="b-variation__link swatchanchor selectable size" onClick="change(#{sizeId})" href="javascript:void(0)" data-lgimg="">' 
					+'<span class="b-variation__value" >#{size}</span>'
					+'</a>'
					+'</li>';
				li=li.replace(/#{sizeId}/g,list[i].id);
				li=li.replace(/#{size}/g,list[i].length+"CM*"+list[i].width+"CM");
				$("#sizelist").append(li);
			}
		}
	});
	
});
//加入购物车
$("#add-to-cart").click(function(){
	var url="../cart/add.do";
	var data="goodsId="+goodsId+"&goodsNum="+1+"&sizeId="+sizeId;
	$.ajax({
		"url":url,
		"data":data,
		"type":"POST",
		"dataType":"JSON",
		"success":function(json){
			if(json.status==200){
				alert("加入购物车成功");
				$.ajax({
					"url":"../goods/getCartCount.do",
					"type":"POST",
					"dataType":"JSON",
					"success":function(json){
						if(json.status==200){
							var num=json.data;
							$("#js-mini-cart-total-qty").text(num);
						}
					}
					
				});
			}else{
				alert(json.message);
			}
		},
		"error":function(xhr){
			$("#toLogin").trigger("click");//未登录或登陆过期，弹出登陆框
		}
	});
});
//商品尺寸变，根据尺寸查询对应的价格
function change(id){
	sizeId=id;
	var url="../goods/getSizeMoney.do";
	var data="sizeId="+id;
	$.ajax({
		"url":url,
		"data":data,
		"type":"POST",
		"dataType":"JSON",
		"success":function(json){
			if(json.status==200){
				var gs=json.data;
				$("#sizePrice").text("￥"+gs.price)
				$("#goodsSize").text(gs.length+'cm*'+gs.width+'cm')
			}
		}
	});
}
















































/* 获取验证码
function getCode(target){
	var codeInput=$(target).prev("input");
	var code=$(codeInput).val();
	var phone;
	if($(codeInput).attr("id")=="loginPassword"){
		phone=$("#loginPhone").val()
	}else{
		phone=$("#regPhone").val()
	}
	if(phone==""||!on){
		alert("请输入正确的手机号码");
		return;
	}
	var url="../user/getCode.do";
//	alert(phone+"::"+code);
	$.ajax({
		"url":url,
		"data":"phone="+phone+"&code="+code,
		"type":"POST",
		"dataType":"JSON",
		"success":function(json){
			if(json.status==200){
				alert("验证码已发送");
			}
		}
	});
}
登录
$("#loginModalYesId").click(function(){
	var url="../user/login.do";
	var phone=$("#loginPhone").val();
	var code=$("#loginPassword").val();
//	alert(phone+".."+code);
	if(phone==""||code==""){
		alert("请完善信息");
		return;
	}
	$.ajax({
		"url":url,
		"data":"phone="+phone+"&code="+code,
		"type":"POST",
		"dataType":"JSON",
		"success":function(json){
			if(json.status==200){
				alert("登陆成功");
			}else if(json.status==510){
				alert(json.message);
			}else if(json.status==512){
				alert(json.message);
			}else if(json.status==513){
				alert(json.message);
			}
		}
	});
});
注册
$("#register").click(function(){
	var url="../user/reg.do";
	var phone=$("#regPhone").val();
	var code=$("#regPassword").val()
//	alert(phone+"::"+code);
	if(phone==""||code==""){
		alert("请完善信息");
		return;
	}
	$.ajax({
		"url":url,
		"data":"phone="+phone+"&code="+code,
		"type":"POST",
		"dataType":"JSON",
		"success":function(json){
			if(json.status==200){
				alert("注册成功");
			}else if(json.status==511){
				alert(json.message);
			}else if(json.status==512){
				alert(json.message);
			}else if(json.status==513){
				alert(json.message);
			}
		}
	});
});*/