/*$("document").ready(function(){
	//页面一加载完成，就显示省列表
	showDistrictList($("#province"),"---请选择省---",0);
});*/
//显示列表
function showDistrictList(menu,defaultText,parentId){
	menu.empty();
	var op='<option value="0">'+defaultText+'</option>';
	menu.append(op);
	var url="../district/getList.do";
	var data="parentId="+parentId;
	$.ajax({
		"url":url,
		"data":data,
		"type":"POST",
		"dataType":"JSON",
		"success":function(json){
			if(json.status==200){
				var list=json.data;
				for(var i=0;i<list.length;i++){
					var op='<option value="'+list[i].id+'">'+list[i].name+'</option>';
					menu.append(op);
				}
			}
		}
	});
}
//显示省的城市列表
function showCityList(){
	showDistrictList($("#city"),"---请选择市---",$("#province").val());
}
//显示城市的区列表
function showAreaList(){
	showDistrictList($("#area"),"---请选择区---",$("#city").val());
}
$("#province").change(function(){
	$("#city").empty();
	var op='<option value="0">---请选择市---</option>';
	$("#city").append(op);
	
	$("#area").empty();
	var op='<option value="0">---请选择区---</option>';
	$("#area").append(op);
	if($("#province").val()!=0){
		showCityList();
	}
});
$("#city").change(function(){
	$("#area").empty();
	var op='<option value="0">---请选择区---</option>';
	$("#area").append(op);
	if($("#city").val()!=0){
		showAreaList();
	}
});



