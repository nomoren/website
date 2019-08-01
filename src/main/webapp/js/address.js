var couponsCode="";//有无使用优惠券
//页面加载完成后显示支付金额
$(document).ready(function(){
	var url="../cart/getCartMoney.do";
	$.ajax({
		"url":url,
		"type":"POST",
		"dataType":"JSON",
		"success":function(json){
			var payMoney=json.data.payMoney;
			if(json.data.couponsCode!=null){
				couponsCode=json.data.couponsCode;
			}
			if(json.status==200){
				$("#cartMoney").text("￥"+payMoney);
			}else{
				alert("服务器连接异常，请稍后刷新");
			}
		}
	});
	
	$.ajax({
		"url":"../address/getAddressNum.do",
		"type":"POST",
		"dataType":"JSON",
		"success":function(json){
			var num=json.data.num;
			if(num==0){//该用户还没添加过收货地址，显示收货信息表单
				$("#dwfrm_singleshipping_shippingAddress").css('display','block');
				$("#addNewAddress").css('display','none');
				$("#addressSelect").css('display','none'); 
				showDistrictList($("#province"),"---请选择省---",0);
			}else{//已有收货地址，直接显示
				var list=json.data.list;//用户的收货地址列表
				addressId=list[0].id;
				$("#addressList").empty();
				for(var i=0;i<list.length;i++){
					var op="<option value="+list[i].id+">#{addressText}</option>";
					op=op.replace(/#{addressText}/g,list[i].recvName+"    "+
							list[i].recvPhone+"    "+list[i].recvDistrict+"   "+
							list[i].recvAddress+"    "+list[i].recvZip);
					$("#addressList").append(op);
				}
			}
		}
	});
	
});
var addressId="";//用户的收货地址id
//添加收获地址并使用该地址
$("#addDefault").click(function(){
	if(!on||$("#recvName").val()==""||$("#province").val()=="0"||$("#city").val()=="0"||$("#area").val()=="0"||$("#recAddress").val()==""){
		alert("收获信息有误！请完善收货信息");
		return;
	}
	var url="../address/addAndSetDefault.do"
	var data=$("#dwfrm_singleshipping_shippingAddress").serialize();
	$.ajax({
		"url":url,
		"data":data,
		"type":"POST",
		"dataType":"JSON",
		"success":function(json){
			if(json.status==200){
				$("#addressId").val(json.data);
				addressId=json.data;
				alert("已保存");
			}else{
				alert("保存失败，请稍后再试");
			}
		},
		"error":function(){
			alert("您的登录信息已过期，请重新登录");
			location.href="../store/product_list.html";
		}
	});
});
$("#addressList").change(function(){
	 addressId=$(this).val();
});
$("#giftCard").blur(function(){
	if($(this).val().length>200){
		alert("字数不能超过200")
		$(this).css("border","1px solid red");
	}
});
//创建订单
$("#createOrder").click(function(){
	if($("#giftCard").val().length>200){
		return;
	}
	var url="../order/createOrder.do";
	var data="addressId="+addressId+"&giftCard="+$("#giftCard").val()+"&useCoupons="+couponsCode;
	$.ajax({
		"url":url,
		"data":data,
		"type":"POST",
		"dataType":"JSON",
		"success":function(json){
			if(json.status==200){
				var orderId=json.data;
				location.href="checkout-pay.html?orderId="+orderId;
			}else{
				alert(json.message)
			}
		},
		"error":function(){
			alert("您的登录信息已过期，请重新登录");
			location.href="../store/productlist.html";
		}
	});
	
});
