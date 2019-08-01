var allPage="";//总页数
$(document).ready(function(){
	var url="../admin/getAllGoods.do";
	var data="page=1";
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
			showGoodsList(list);
		}
	});
});
//显示列表
function showGoodsList(list){
	$(".x-cate").empty();
	for(var i=0;i<list.length;i++){
		var tr='<tr><td>'
			   +'<div class="layui-unselect layui-form-checkbox" lay-skin="primary" data-id="2"><i class="layui-icon">&#xe605;</i></div>'
			   +'</td>'
			   +'<td>#{id}</td>'
			   +'<td><img src="..#{path}/img0.png" style="width: 60px;height: 60px"> </td>'
			   +'<td>#{title}</td>'
			   +'<td>#{No}</td>'
			   +'<td>#{price}</td>'
			   +'<td>#{material}</td>'
			   +'<td class="td-status#{id}">#{status}</td>'
			   +'<td class="td-manage">'
			   +'<button class="layui-btn layui-btn-normal layui-btn-mini" id="up#{id}" onclick="up(#{id})" style="display: inline-block;margin-left: 10px">上架此商品</button>'
			   +'<button class="layui-btn layui-btn-warm layui-btn-mini" id="down#{id}" onclick="down(#{id})" style="display: inline-block;">下架此商品</button>'
			   +'<button class="layui-btn layui-btn-danger layui-btn-mini" onclick="deleteGoods(this,#{id})" style="display: inline-block;">删除</button>'
			  /* +'<button class="layui-btn layui-btn-normal layui-btn-mini" onclick="updateGoods(this,#{id})" style="display: inline-block;">修改</button>'*/
			   +'</td></tr>';
		tr=tr.replace(/#{id}/g,list[i].id);
		tr=tr.replace(/#{title}/g,list[i].title);
		tr=tr.replace(/#{path}/g,list[i].path);
		tr=tr.replace(/#{No}/g,list[i].number);
		tr=tr.replace(/#{price}/g,"￥"+list[i].price);
		tr=tr.replace(/#{material}/g,list[i].material);
		if(list[i].status==1){
			tr=tr.replace(/#{status}/g,"上架中");
			$("#up"+list[i].id).css('display','none');
		}else{
			tr=tr.replace(/#{status}/g,"已下架");
			$("#down"+list[i].id).css('display','none'); 
		}
		$(".x-cate").append(tr);
		if(list[i].status==1)$("#up"+list[i].id).css('display','none');
		else $("#down"+list[i].id).css('display','none'); 
	}
}
//获取首页，尾页，上一页，下一页数据
function getUserList(target){
	var url="../admin/getAllGoods.do";
	var data="";
	var page=0;//将要跳转页  
	var nowPage=parseInt($(".num").text());//当前页
	if($(target).attr("class")=="first"){//首页
		data="page=1";
		$(".num").text("1");
		$(".prev").css("display","none");
	}else if($(target).attr("class")=="prev"){//上一页
		if(nowPage==1){
			return;
		}
		page=nowPage-1;
		data="page="+page;
		$(".num").text(page);
	}else if($(target).attr("class")=="next"){//下一页
		if(nowPage==allPage){
			return;
		}
		page=nowPage+1;
		data="page="+page;
		$(".num").text(page);
		$(".first").css("display","inline");
		$(".prev").css("display","inline");
	}else{//尾页
		page=allPage;
		data="page="+page;
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
			showGoodsList(list);
		}
	});
}
//上架
function up(id){
	var url="../admin/changeGoodsStatus.do";
	var data="id="+id+"&status=1";
	layer.confirm('确认要上架吗？',function(index){
		$.ajax({
			"url":url,
			"data":data,
			"type":"POST",
			"dataType":"JSON",
			"success":function(json){
				if(json.status==200){
					$(".td-status"+id).text("上架中");
					$("#up"+id).css('display','none'); 
					$("#down"+id).css('display','inline-block');
					layer.msg('已上架!',{icon:1,time:1000});
				}else{
					alert(json.message);
				}
			}
		});
	});
}
//下架
function down(id){
	var url="../admin/changeGoodsStatus.do";
	var data="id="+id+"&status=0";
	layer.confirm('确认要下架吗？',function(index){
		$.ajax({
			"url":url,
			"data":data,
			"type":"POST",
			"dataType":"JSON",
			"success":function(json){
				if(json.status==200){
					$(".td-status"+id).text("已下架");
					$("#down"+id).css('display','none'); 
					$("#up"+id).css('display','inline-block');
					layer.msg('已下架!',{icon:1,time:1000});
				}else{
					alert(json.message);
				}
			}
		});
	});
}
//删除
function deleteGoods(obj,id){
	layer.confirm('确认删除吗？',function(index){
		var url="../admin/deleteGoods.do";
		var data="goodsId="+id;
		$.ajax({
			"url":url,
			"data":data,
			"type":"POST",
			"dataType":"JSON",
			"success":function(json){
				if(json.status==200){
					$(obj).parents("tr").remove();
					layer.msg('已删除!',{icon:1,time:1000});
				}else{
					layer.msg(json.message,{icon:2,time:1000});
				}
			}
		});
	});
}

