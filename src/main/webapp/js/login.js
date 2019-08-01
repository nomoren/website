var on=false;//手机格式开关，符合则为true
$(document).ready(function(){
	var loc = document.location.toString();
	var arrUrl = loc.split("//");
	var start = arrUrl[1].split("/");
	/*console.log(loc);
	console.log(start.length);*/
	if(start.length>=3){
		var loginurl="../user/checkLogin.do";
		var carturl="../goods/getCartCount.do";
	}else{
		var loginurl="./user/checkLogin.do";
		var carturl="./goods/getCartCount.do";
	}
	$.ajax({
		"url":loginurl,
		"type":"POST",
		"dataType":"JSON",
		"success":function(json){
			if(json.data.loginStatus==1){//已登录，显示个人中心
				$("#userCenter,.userc").css('display','block');
				$("#alogin,.loginpc").css('display','none');
				$.ajax({
					"url":carturl,
					"type":"POST",
					"dataType":"JSON",
					"success":function(json){
						if(json.status==200){
							var num=json.data;
							$("#js-mini-cart-total-qty").text(num);
						}
					}
					
				});
			}
		}
	});
});
function phoneReg(target){
	var phoneReg=/^[1][3,4,5,7,8][0-9]{9}$/;
	var mobile=$(target).val();
	if(mobile.length==0)
	{
		$(target).css("border","1px solid red");
		$("#phoneNoOk").css('display','inline');
		on=false;
		return;
	}    
	if(mobile.length!=11||!phoneReg.test(mobile))
	{
		$(target).css("border","1px solid red");
		$("#phoneNoOk").css('display','inline');
		on=false;
		return;
	}
	$("#phoneOk").css('display','inline');
	on=true;
}
$("#loginMobile").focus(function(){
	var target=event.target || event.srcElement;
	$(target).css("border","1px solid #C0C0C0");
	var span=$(target).nextAll("span");
	$($(span)[0]).css('display','none');
	$($(span)[1]).css('display','none');
});
/* 获取验证码*/
function getCode(target){
	var phone=$("#loginMobile").val();
	if(phone==""||!on){
		$("#loginMobile").css("border","1px solid red");
		$("#phoneNoOk").css('display','inline');
		return;
	}
	var loc = document.location.toString();
	var arrUrl = loc.split("//");
	var start = arrUrl[1].split("/");
	if(start.length>=3){
		var url="../user/getCode.do";
	}else{
		var url="./user/getCode.do";
	}
	$.ajax({
		"url":url,
		"data":"phone="+phone,
		"type":"POST",
		"dataType":"JSON",
		"success":function(json){
			if(json.status==200){
				$(".code-img").empty();
				$(".code-img").append($("<span>已发送</span>"));
			}else{
				alert(json.message);
			}
		}
	});
}
/*登录*/
$("#loginNewBtn").click(function(){
	var phone=$("#loginMobile").val();
	var code=$("#loginCode").val();
	if(phone==""||code==""||!on){
		alert("请完善信息");
		return;
	}
	var loc = document.location.toString();
	var arrUrl = loc.split("//");
	var start = arrUrl[1].split("/");
	if(start.length>=3){
		var url="../user/login.do";
	}else{
		var url="./user/login.do";
	}
	$.ajax({
		"url":url,
		"data":"phone="+phone+"&code="+code,
		"type":"POST",
		"dataType":"JSON",
		"success":function(json){

			if(json.status==200){
				/*alert("登陆成功");*/
				$('#Login').modal('hide')
				$("#userCenter,.userc").css('display','block');
				$("#alogin,.loginpc").css('display','none');
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
		}
	});
});
$("#wxLogin").click(function(){
	var loc = document.location.toString();
	var arrUrl = loc.split("//");
	var start = arrUrl[1].split("/");
	if(navigator.platform.indexOf('Win32')!=-1){ 
		if(start.length>=3){
			location.href="../user/wxLogin.do";
		}else{
			location.href="./user/wxLogin.do";
		}
     }else{
    	 if(start.length>=3){
 			location.href="../user/mWxLogin.do";
 		}else{
 			location.href="./user/mWxLogin.do";
 		}
     } 
});
//回车登录
$(document).keydown(function (event) {
	if(event.keyCode==13){
		$("#loginNewBtn").click();
	}
});

$("#alogin").click(function(){
	var win_h=$(window).height();
	var h=(win_h-344)/2;
	$(".modal-dialog").css("margin-top",h+"px");
});
