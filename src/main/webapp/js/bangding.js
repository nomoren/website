
var on=false;//手机格式开关，符合则为true
		function phoneReg(target){
			var phoneReg=/^[1][3,4,5,7,8][0-9]{9}$/;
			var mobile=$(target).val();
			if(mobile.length==0)
			{
				alert('请输入手机号码！');
				on=false;
				return;
			}    
			if(mobile.length!=11||!phoneReg.test(mobile))
			{
				alert('请输入有效的手机号码！');
				on=false;
				return;
			}
			on=true;
		}
		/* 获取验证码*/
		function getCode(target){
			var phone=$("#loginMobile").val();
			if(phone==""||!on){
				alert("请输入正确的手机号码");
				return;
			}
			var url="../user/getCode.do";
			$.ajax({
				"url":url,
				"data":"phone="+phone,
				"type":"POST",
				"dataType":"JSON",
				"success":function(json){
					if(json.status==200){
						$("#code").remove();
						$("#codeinfo").append($('<span style="position: relative;left: 265px;bottom: 25px">验证码已发送</span>'));
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
			var unionid=$("#unionid").val();
			if(phone==""||code==""||!on){
				alert("请完善信息");
				return;
			}
			var url="../user/login.do";
			$.ajax({
				"url":url,
				"data":"phone="+phone+"&code="+code+"&unionid="+unionid,
				"type":"POST",
				"dataType":"JSON",
				"success":function(json){
					if(json.status==200){
						location.href="../store/productlist.html";
					}else{
						alert(json.message);
					}
				}
			});
		});