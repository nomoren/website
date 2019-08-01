var allPage="";//总页数
$(document).ready(function(){
	var url="../admin/getEmpCodeList.do";
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
			showList(list);
		}
	});
});
//显示商品列表
function showList(list){
	$(".x-cate").empty();
	for(var i=0;i<list.length;i++){
		var tr='<tr><td>'
			   +'<div class="layui-unselect layui-form-checkbox" lay-skin="primary" data-id="2"><i class="layui-icon">&#xe605;</i></div>'
			   +'</td>'
			   +'<td>#{id}</td>'
			   +'<td>#{empName}</td>'
			   +'<td>#{empCode}</td>'
			   +'<td>#{count}</td>'
			   +'<td>#{money}</td>'
			   +'<td class="td-manage">'
			   +'<button id="delete#{id}" class="layui-btn layui-btn-danger layui-btn-mini" onclick="deleteEmpcode(this,\'#{empCode}\')" style="display: inline-block;">删除</button>'
			   +'</td></tr>';
		tr=tr.replace(/#{id}/g,list[i].empId);
		tr=tr.replace(/#{empName}/g,list[i].empName);
		tr=tr.replace(/#{empCode}/g,list[i].empCode);
		tr=tr.replace(/#{count}/g,list[i].useCount);
		if(list[i].money==null) tr=tr.replace(/#{money}/g,"0.0");
		else tr=tr.replace(/#{money}/g,list[i].money);
		
		$(".x-cate").append(tr);
	}
}
//获取首页，尾页，上一页，下一页数据
function getList(target){
	var url="../admin/getEmpCodeList.do";
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
			showList(list);
		}
	});
}

//删除
function deleteEmpcode(obj,code){
	layer.confirm('确认删除吗？',function(index){
		var url="../admin/deleteEmpCode.do";
		var data="code="+code;
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

