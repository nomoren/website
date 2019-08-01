$(document).ready(function(){
	$.ajax({
		"url":"../user/checkLogin.do",
		"type":"POST",
		"dataType":"JSON",
		"success":function(json){
			var map=json.data;
			if(map.loginStatus==1){//已登录，显示个人中心标签,登录隐藏
				if(map.name!=null){//用户名不为空，就显示
					$(".b-account-overview__welcoming").text("欢迎您！"+map.name);
				}
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
				$("#toLogin").trigger("click");//弹出登录框
			}
		},
	});
});

//退出当前账号
$(".out").click(function(){
	var result = confirm("确认退出当前账号吗？");
	if(result==true){
		var url="../user/outLogin.do";
		$.ajax({
			"url":url,
			"type":"POST",
			"dataType":"JSON",
			"success":function(){
				$("#userCenter").css('display','none'); 
				$("#alogin").css('display','block'); 
				$("#js-mini-cart-total-qty").text(0);
				$(".historyOrder").empty();
				$("#useraddress").empty();
				$("#userInfo")[0].reset();
			},
			"error":function(){
				$("#toLogin").trigger("click");//弹出登录框
			}
		});
	}
});


var pOn=false;//手机格式开关，符合则为true
var bOn=false;//生日格式开关，符合则为true
var eOn=false;//邮箱格式开关，符合则为true
//验证手机号格式
function uphoneReg(target){
	var phoneReg=/^[1][3,4,5,7,8][0-9]{9}$/;
	var mobile=$(target).val();
	var span=$(target).nextAll("span");
	if(mobile.length==0)
	{
		$($(span)[1]).css('display','inline');
		$(target).css("border","1px solid red");
		pOn=false;
		return;
	}    
	if(mobile.length!=11||!phoneReg.test(mobile))
	{
		$($(span)[1]).css('display','inline');
		$(target).css("border","1px solid red");
		pOn=false;
		return;
	}
	$($(span)[0]).css('display','inline');
	pOn=true;
}
//验证生日格式
function birReg(target){
	var birReg=/^(\d{4})-(\d{2})-(\d{2})$/;
	var birthday=$("#birthday").val();
	var span=$(target).nextAll("span");
	if(birthday==""||!birReg.test(birthday)){
		$($(span)[1]).css('display','inline');
		$(target).css("border","1px solid red");
		bOn=false;
		return;
	}
	$($(span)[0]).css('display','inline');
	bOn=true;
}
//验证邮箱格式
function emailReg(target){
	var emailReg=/^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
	var email=$("#email").val();
	var span=$(target).nextAll("span");
	if(birthday==""||!emailReg.test(email)){
		$($(span)[1]).css('display','inline');
		$(target).css("border","1px solid red");
		eOn=false;
		return;
	}
	$($(span)[0]).css('display','inline');
	eOn=true;
}

//保存用户信息
function saveUserInfo(){
/*	console.log(pOn)
	console.log(bOn)
	console.log(eOn)*/
	if(pOn+bOn!=2){
		alert("信息有误或不完整，请完善信息")
		return;
	}
	var url="../user/changeUserInfo.do";
	var data=$("#userInfo").serialize();
	/*console.log("data====="+data);*/
	$.ajax({
		"url":url,
		"data":data,
		"type":"POST",
		"dataType":"JSON",
		"success":function(json){
			if(json.status==200){
				alert("保存成功");
			}else if(json.status==510){//号码被注册
				alert(json.message);
				$("#phoneNoExit").css('display','none');
				$("#phoneIsExit").css('display','inline');
				$("#phone").css("border","1px solid red");
			}else{
				alert(json.message);
			}
		},
		"error":function(){
			/*alert("登录过期，请重新登录");*/
			$("#toLogin").trigger("click");
		}
	});
}
//控制个人信息表样式
$(".b-text-input").focus(function(){
	var target=event.target || event.srcElement;
	$(target).css("border","1px solid #C0C0C0");
	var span=$(target).nextAll("span");
	$($(span)[0]).css('display','none');
	$($(span)[1]).css('display','none');
});

//查询规定时间范围内用户的所有订单
$("#dwfrm_profile_customer_title").change(function(){
	var target=event.target || event.srcElement;
	var range=$(target).val();//时间范围
	var url="../order/getOrderByDate.do";
	var data="range="+range;
	if(range!=0){
	$.ajax({
		"url":url,
		"data":data,
		"type":"POST",
		"dataType":"JSON",
		"success":function(json){
			var list=json.data;
			if(list.length==0){
				alert("无任何记录");
				return;
			}
			$(".historyOrder").empty();
			for(var i=0;i<list.length;i++){//显示订单
			var div='<div class="panel panel-default"><div class="panel-heading">'
					+'<h4 class="panel-title">#{text}</h4>'
					+'</div><div class="panel-body">'
					+'<table class="orders-table" width="100%">'
					+'<thead><tr><th width="15%">商品</th><th width="10%">单价</th>'
					+'<th width="10%">数量</th><th width="10%">小计</th><th width="10%">支付状态</th>'
					+'<th width="10%">发货状态</th>'
					+'<th width="10%">操作</th></tr>'
					+'</thead><tbody class="orders-body#{id}">'
					+'</tbody></table></div></div>';
			  div=div.replace(/#{text}/g,"订单号:"+list[i].orderNo+",下单时间:"+list[i].orderTime+",收货人:"+list[i].recvName);
			  div=div.replace(/#{id}/g,i);
			  $(".historyOrder").append(div);
			  var orderList=list[i].list;
			  $(".orders-body"+i).empty();
			  for(var j=0;j<orderList.length;j++){//显示订单中的商品
				  var tr='<tr>'
					  	+'<td>#{title}</td>'
					  	+'<td>¥<span>#{price}</span></td>'
					  	+'<td>#{num}件</td>'
					  	+'<td>¥<span>#{allMoney}</span></td>'
					  	+'<td id="ispay#{id}">未支付</td>'
					  	+'<td id="isout#{id}">未发货</td>'
					  	+'<td><a id="topay#{id}" href="checkout-pay.html?orderId=#{oid}">去支付</a>'
					  	+'</td>'
					  	+'</tr>';
					  tr=tr.replace(/#{title}/g,orderList[j].goodsTitle+"  "+orderList[j].goodsLength+"cm*"+orderList[j].goodsWidth+"cm");
					  tr=tr.replace(/#{price}/g,orderList[j].goodsPrice);
					  tr=tr.replace(/#{num}/g,orderList[j].goodsNum);
					  tr=tr.replace(/#{allMoney}/g,orderList[j].allMoney);
					  tr=tr.replace(/#{id}/g,orderList[j].id);
					  tr=tr.replace(/#{oid}/g,list[i].oid);
					  $(".orders-body"+i).append(tr);
					  if(list[i].payStatus==1){
						  $("#ispay"+orderList[j].id).text("已支付");
						  $("#topay"+orderList[j].id).css("display","none");
					  }
					  if(list[i].outStatus==1) $("#isout"+orderList[j].id).text("已发货");
			  }
			}
		},
		"error":function(){
			/*alert("登录过期，请重新登录");*/
			$("#toLogin").trigger("click");
		}
	});
  }
});

//显示用户的收货地址列表
$("#show_address").click(function(){
	showAddressList();
});
//显示收货地址列表
function showAddressList(){
	var url="../address/getAddressNum.do";
	$.ajax({
		"url":url,
		"type":"POST",
		"dataType":"JSON",
		"success":function(json){
			var map=json.data;
			var num=map.num;
			if(num==0){
				$("#useraddress").empty();
				return;
			}
			var list=map.list;
			$("#useraddress").empty();
			for(var i=0;i<list.length;i++){
				var tr='<tr>'
					   +'<td>#{name}</td>'
					   +'<td>#{address}</td>'
					   +'<td>#{phone}</td>'
					   +'<td><a class="btn btn-xs btn-info" href="javascript:modifiedById(#{id})"><span class="fa fa-edit"></span> 修改</a></td>'
					   +'<td><a class="btn btn-xs add-del btn-info" href="javascript:deleteById(#{id})"><span class="fa fa-trash-o"></span> 删除</a></td>'
					   +'<td><a class="btn btn-xs add-def btn-info" href="javascript:setDefaultById(#{id})"><span class="fa fa-trash-o"></span> 设为默认</a></td>'
					   +'</tr>';
				tr=tr.replace(/#{id}/g,list[i].id);
				tr=tr.replace(/#{name}/g,list[i].recvName);
				tr=tr.replace(/#{address}/g,list[i].recvDistrict+list[i].recvAddress);
				var phone=list[i].recvPhone;
				phone =  phone.replace(/^(\d{3})\d{4}(\d+)/,"$1****$2")
				tr=tr.replace(/#{phone}/g,phone);
				$("#useraddress").append(tr);
			}
				$(".add-def").eq(0).css("display","none");
		},
		"error":function(){
			alert("登录过期，请重新登录");
			$("#toLogin").trigger("click");
		}
	});
} 
//删除地址
function deleteById(id){
	var url="../address/deleteAddress.do";
	var data="addressId="+id;
	var result = confirm("确认删除此地址吗？");
	if(result==true){
		$.ajax({
			"url":url,
			"data":data,
			"type":"POST",
			"dataType":"JSON",
			"success":function(json){
				if(json.status==200){
					showAddressList();
				}else{
					alert(json.message);
				}
			},
			"error":function(){
				alert("登录过期，请重新登录");
				$("#toLogin").trigger("click");
			}
		});
	}
}

//设置默认收货地址
function setDefaultById(id){
	var url="../address/setDefault.do";
	var data="addressId="+id;
	$.ajax({
		"url":url,
		"data":data,
		"type":"POST",
		"dataType":"JSON",
		"success":function(json){
			if(json.status==200){
				showAddressList();
			}else{
				alert(json.message);
			}
		},
		"error":function(){
			alert("登录过期，请重新登录");
			$("#toLogin").trigger("click");
		}
	});
}
var data=null;//要修改的收货地址信息
var mClick=false;//判断修改按钮有没有被点击，点击了就执行修改
//修改收货地址
$("#modifiedAddress").click(function(){
	if(!on||$("#recvName").val()==""||$("#province").val()=="0"||$("#city").val()=="0"||$("#area").val()=="0"||$("#recAddress").val()==""){
		alert("收获信息有误！请完善收货信息");
		return;
	}
	data=$("#address-form").serialize();
	mClick=true;
});
//新增收货地址
$("#saveAddress").click(function(){
	if(!on||$("#recvName").val()==""||$("#province").val()=="0"||$("#city").val()=="0"||$("#area").val()=="0"||$("#recAddress").val()==""){
		alert("收获信息有误！请完善收货信息");
		return;
	}
	var newdata=$("#address-form").serialize();
	var url="../address/insertAddress.do";
	$.ajax({
		"url":url,
		"data":newdata,
		"type":"POST",
		"dataType":"JSON",
		"success":function(json){
			if(json.status==200){
				alert("保存成功");
				showAddressList();
				 $('#myModal').modal('hide');
			}else{
				alert(json.message);
			}
		},
		"error":function(){
			alert("登录过期，请重新登录");
			$("#toLogin").trigger("click");
		}
	});
});

//修改收货信息
function modifiedById(id){//传入地址的id
	$("#showDistrict").trigger("click");
	$("#saveAddress").css("display","none");
	$("#modifiedAddress").css("display","block");
	setInterval(function(){//点击修改地址后，每秒检测收货信息框的修改按钮有没有被点击
		if(mClick==true){//点击了，执行修改
			var url="../address/updateAddress.do";
			var mdata=data+"&id="+id;
			$.ajax({
				"url":url,
				"data":mdata,
				"type":"POST",
				"dataType":"JSON",
				"success":function(json){
					alert("修改成功");
					showAddressList();
					 $('#myModal').modal('hide');
				},
				"error":function(){
					alert("登录过期，请重新登录");
					$("#toLogin").trigger("click");
				}
			});
			mClick=false;
		}
		
	},1000);

}
//一弹出收货信息框，就显示省列表
$("#showDistrict").click(function(){
	$('#address-form')[0].reset();  
	$("#saveAddress").css("display","block");
	$("#modifiedAddress").css("display","none");
	showDistrictList($("#province"),"---请选择省---",0);
})
