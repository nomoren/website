var allPage="";//总页数
var urlParam="";//请求参数
$("#search").click(function(){
	var url="../admin/getEmpCodeUseRecordList.do";
	var data="page=1";
	var param=$("#empData").val(); 
	if(param!=""){
		if(param.substring(0,5)=="ideas") urlParam="&code="+param;
		else  urlParam="&name="+param;
		data=data+urlParam;
		console.log(data);
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
  }
});
//显示列表
function showList(list){
	$(".x-cate").empty();
	for(var i=0;i<list.length;i++){
		var tr='<tr><td>'
			   +'<div class="layui-unselect layui-form-checkbox" lay-skin="primary" data-id="2"><i class="layui-icon">&#xe605;</i></div>'
			   +'</td>'
			   +'<td>#{id}</td>'
			   +'<td>#{empName}</td>'
			   +'<td>#{empCode}</td>'
			   +'<td>#{usedate}</td>'
			   +'<td>#{money}</td>'
			   +'</tr>';
		tr=tr.replace(/#{id}/g,list[i].empId);
		tr=tr.replace(/#{empName}/g,list[i].empName);
		tr=tr.replace(/#{empCode}/g,list[i].empCode);
		if(list[i].usedate==null) tr=tr.replace(/#{usedate}/g,"无记录");
		else tr=tr.replace(/#{usedate}/g,list[i].usedate);
		if(list[i].singleMoney==null) tr=tr.replace(/#{money}/g,"0.0");
		else tr=tr.replace(/#{money}/g,list[i].singleMoney);
		$(".x-cate").append(tr);
	}
}
//获取首页，尾页，上一页，下一页数据
function getList(target){
	var url="../admin/getEmpCodeUseRecordList.do";
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
	if(urlParam!=""){
		data=data+urlParam;
	$.ajax({
		"url":url,
		"data":data,
		"type":"POST",
		"dataType":"JSON",
		"success":function(json){
			var list=json.data.list;
			var num=json.data.num;//总数
			allPage=json.data.allPage;
			$(".x-right").text("总共有数据:"+num+"条");
			showList(list);
		}
	});
  }
}
