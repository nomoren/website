var allPage="";//总页数
$(document).ready(function(){
	var url="../admin/getOrderByStatus.do";
	var data="page=1&payStatus=0&outStatus=0&takeStatus=0";
	$.ajax({
		"url":url,
		"data":data,
		"type":"POST",
		"dataType":"JSON",
		"success":function(json){
			var list=json.data.list;
			var num=json.data.num;
			allPage=json.data.allPage;
			$(".x-right").text("总共有数据:"+num+"条");
			showOrderList(list);
		}
	});
});
function showOrderList(list){
	$("#orderList").empty();
	for(var i=0;i<list.length;i++){
		var tr= '<tr><td><div class="layui-unselect layui-form-checkbox" lay-skin="primary" data-id="2"><i class="layui-icon">&#xe605;</i></div></td>'
    		+'<td>#{no}</td>'
    		+'<td>#{name}</td>'
    		+'<td>#{money}</td>'
    		+'<td>未支付</td>'
    		+'<td>#{date}</td>'
    		+'<td class="td-manage">'
    		+'<a title="查看"  onclick="x_admin_show(\'查看\',\'userAddress.html?id=#{id}\')" href="javascript:;">'
			+'<i class="layui-icon">&#xe63c;</i></a>'
			+'</td></tr>';
	tr=tr.replace(/#{id}/g,list[i].id);
	tr=tr.replace(/#{no}/g,list[i].orderNo);
	tr=tr.replace(/#{name}/g,list[i].recvName);
	tr=tr.replace(/#{money}/g,list[i].pay);
	tr=tr.replace(/#{date}/g,list[i].orderTime);
	$("#orderList").append(tr);
	}
}
//获取首页，尾页，上一页，下一页数据
function getOrderList(target){
	var url="../admin/getOrderByStatus.do";
	var data="";
	var page=0;//将要跳转页  
	var nowPage=parseInt($(".num").text());//当前页
	if($(target).attr("class")=="first"){//首页
		data="page=1&payStatus=0&outStatus=0&takeStatus=0";
		$(".num").text("1");
		$(".prev").css("display","none");
	}else if($(target).attr("class")=="prev"){//上一页
		if(nowPage==1){
			return;
		}
		page=nowPage-1;
		data="page="+page+"&payStatus=0&outStatus=0&takeStatus=0";
		$(".num").text(page);
	}else if($(target).attr("class")=="next"){//下一页
		if(nowPage==allPage){
			return;
		}
		page=nowPage+1;
		data="page="+page+"&payStatus=0&outStatus=0&takeStatus=0";
		$(".num").text(page);
		$(".first").css("display","inline");
		$(".prev").css("display","inline");
	}else{//尾页
		page=allPage;
		data="page="+page+"&payStatus=0&outStatus=0&takeStatus=0";
		$(".num").text(allPage);
		$(".first").css("display","inline");
		$(".prev").css("display","inline");
	}
	$.ajax({
		"url":url,
		"data":data,
		"type":"POST",
		"dataType":"JSON",
		"success":function(json){
			var list=json.data.list;
			var num=json.data.num;//用户总数
			allPage=json.data.allPage;
			$(".x-right").text("总共有数据:"+num+"条");
			showOrderList(list);
		}
	});
}
//根据订单编号查询订单
$("#search").click(function(){
	var url="../admin/getOrderByOrderNo.do";
	var data=$("#orderNo").val();
	if(data!=""){
		$.ajax({
			"url":url,
			"data":"orderNo="+data,
			"type":"POST",
			"dataType":"JSON",
			"success":function(json){
				var list=json.data;
				if(list[0]==null){//0 0 0
					layer.msg("查无此单！",{icon:2,time:1500});
				}else if(list[0].payStatus==1){
					layer.msg("该单状态为已支付，请到待出货页查询详情！",{icon:2,time:2000});
				}else if(list[0].outStatus==1){
					layer.msg("该单状态为已发货，请到待收货页查询详情！",{icon:2,time:2000});
				}else if(list[0].takeStatus==1){
					layer.msg("该单状态为已完成，请到已完成页查询详情！",{icon:2,time:2000});
				}else{
					showOrderList(list);
				}
			}
		});
	}
});