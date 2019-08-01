var allPage="";//总页数
$(document).ready(function(){
	var url="../admin/getUserList.do";
	var data="page=1&status=1";
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
			showUserList(list);
		}
	});
});
//显示用户列表
function showUserList(list){
	$("#userInfo").empty();
	for(var i=0;i<list.length;i++){
		var tr='<tr>'
			+'<td>'
			+'<div class="layui-unselect layui-form-checkbox" lay-skin="primary" data-id="2"><i class="layui-icon">&#xe605;</i></div>'
			+'</td>'
			+'<td>#{id}</td>'
			+'<td>#{name}</td>'
			+'<td>#{sex}</td>'
			+'<td>#{phone}</td>'
			+'<td>#{email}</td>'
			+'<td>#{date}</td>'
			+'<td class="td-status">'
			+'<span class="layui-btn layui-btn-normal layui-btn-mini">已启用</span></td><td class="td-manage">'
			+'<a onclick="member_stop(this,\'#{id}\')" href="javascript:;"  title="启用">'
			+'<i class="layui-icon">&#xe601;</i></a>'
			+'<a title="删除" onclick="member_del(this,\'#{id}\')" href="javascript:;">'
			+'<i class="layui-icon">&#xe640;</i>'
			+'</a></td></tr>';
		tr=tr.replace(/#{id}/g,list[i].id);
		tr=tr.replace(/#{name}/g,list[i].name);
		if(list[i].gender==0){
			tr=tr.replace(/#{sex}/g,"女");
		}else if(list[i].gender==1){
			tr=tr.replace(/#{sex}/g,"男");
		}else{
			tr=tr.replace(/#{sex}/g,"");
		}
		tr=tr.replace(/#{phone}/g,list[i].phone);
		tr=tr.replace(/#{email}/g,list[i].email);
		tr=tr.replace(/#{date}/g,list[i].createTime);
		$("#userInfo").append(tr);
	}
}

//获取首页，尾页，上一页，下一页数据
function getUserList(target){
	var url="../admin/getUserList.do";
	var data="";
	var page=0;//将要跳转页  
	var nowPage=parseInt($(".num").text());//当前页
	if($(target).attr("class")=="first"){//首页
		data="page=1&status=1";
		$(".num").text("1");
		$(".prev").css("display","none");
	}else if($(target).attr("class")=="prev"){//上一页
		if(nowPage==1){
			return;
		}
		page=nowPage-1;
		data="page="+page+"&status=1";
		$(".num").text(page);
	}else if($(target).attr("class")=="next"){//下一页
		if(nowPage==allPage){
			return;
		}
		page=nowPage+1;
		data="page="+page+"&status=1";
		$(".num").text(page);
		$(".first").css("display","inline");
		$(".prev").css("display","inline");
	}else{//尾页
		page=allPage;
		data="page="+page+"&status=1";
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
			showUserList(list);
		}
	});
}
/*用户-停用和启用*/
function member_stop(obj,id){
	  var str="";
	  if($(obj).attr('title')=='启用'){
		  str="确认要停用吗？"
	  }else{
		  str="确认要启用吗？"
	  }
    layer.confirm(str,function(index){
    	
        if($(obj).attr('title')=='启用'){
          $.ajax({
        	  "url":"../admin/changeUserStatus.do",
        	  "data":"status=0&id="+id,
        	  "type":"POST",
        	  "dataType":"JSON",
        	  "success":function(){
        		  $(obj).attr('title','停用')
                  $(obj).find('i').html('&#xe62f;');
                  $(obj).parents("tr").find(".td-status").find('span').addClass('layui-btn-disabled').html('已停用');
                  layer.msg('已停用!',{icon: 5,time:1000});
        	  }
          });
        }else{
        	  $.ajax({
            	  "url":"../admin/changeUserStatus.do",
            	  "data":"status=1&id="+id,
            	  "type":"POST",
            	  "dataType":"JSON",
            	  "success":function(){
            		  $(obj).attr('title','启用')
                      $(obj).find('i').html('&#xe601;');
                      $(obj).parents("tr").find(".td-status").find('span').removeClass('layui-btn-disabled').html('已启用');
                      layer.msg('已启用!',{icon: 6,time:1000});
            	  }
              });
         
        }
        
    });
}

/*用户-删除*/
function member_del(obj,id){
    layer.confirm('确认要删除吗？',function(index){
        $.ajax({
        	"url":"../admin/deleteUser.do",
        	"data":"id="+id,
        	"type":"POST",
        	"dataType":"JSON",
        	"success":function(json){
        		if(json.status==200){
        			$(obj).parents("tr").remove();
        		    layer.msg('已删除!',{icon:1,time:1000});
        		}else{
        			layer.msg(json.message,{icon:2,time:1500});
        		}
        	}
        });
       
    });
}