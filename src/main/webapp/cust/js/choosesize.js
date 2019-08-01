function getThumbnnail(){
    var xhr= new XMLHttpRequest();
    var ordernumber = $("#ordernumber").val();
    xhr.onreadystatechange=function(){
        if(xhr.readyState==4&&xhr.status==200){
            var image=this.response;
            $("#small_thumbnail")[0].src = window.URL.createObjectURL(image);
            $("#big_thumbnail")[0].src = window.URL.createObjectURL(image);
        }
    }
    xhr.open("get","../cust/getThumbnnail.do?piece=0&ordernumber="+ordernumber,true);
    xhr.responseType="blob";
    xhr.send();    
}

function getSizeUrl(){
    var url = "../cust/getSize.do";
    var data = $("#shape").val();
	$.ajax({
            "url":url,
            "data":"shape="+data,
            "type":"post",
            "dataType":"json",
            success:function(json){
                if(json.status==200){
                	if("1"==json.data){
                		$(".size").remove();
                	}else{
                		 $("#small_thumbnail_size")[0].setAttribute("src",json.data);
                         $("#big_thumbnail_size")[0].setAttribute("src",json.data);
                    }
                }}
        })
}

function getSizeDetails(){
    var url = "../cust/getSizeDetails.do";
    var data = $("#shape").val();
    $.ajax({
        "url":url,
        "data":"shape="+data,
        "type":"post",
        "dataType":"json",
        success:function(json){
            if(json.status==200){
            	var data = json.data;
            	if(data.adjustable==true){
            	    $("#adjustable").val(true);
            		$("#sz1").empty();
            		var ss = data.ssss;
            		for(var i=0;i<ss.length;i++){
                        var size = "<div class='col-xs-4 bg ${width}x${height}' data-type='size' data-perimeter='${perimeter}' data-price='${price}' data-w='${width}' data-h='${height}' data-id='${id}'> <a class='text-muted text-value' data-value='0' data-spec='94' "
                            +"href='javascript:' data-v='${height}x${width}' > <div style='position:relative' class='bm'>"
                            +"<img src='../cust/img/size/size_icon2.png' class='img-responsive' style='margin: 0px auto;'/>"
                            +"<div class='icon-image' style='top:-8%;width:${width%};height:${height%};'></div> </div>"
                            +"<span class='sss'><span><small>宽</small> ${width} x <small>高</small> ${height}</span> <small> 厘米 </small></span></a> </div>";
                        for (var int = 0; int < 5; int++) {
                            size = size.replace("${width}", ss[i].defaultWidth);
                            size = size.replace("${height}", ss[i].defaultHeight);
                        }
                        size = size.replace("${id}", ss[i].id);
                        size = size.replace("${width%}", ((ss[i].defaultWidth/260)*100)+"%");
                        size = size.replace("${height%}", ((ss[i].defaultHeight/260)*100)+"%");
                        size = size.replace("${perimeter}",ss[i].perimeter);
                        size = size.replace("${price}",data.price);
                        $("#sz1").append(size);
                        addListener();
                        $("#sz-1").addClass("active");
                        $("#sz1").addClass("active").addClass("in");
                        bg();
                    }
                }else if(data.adjustable==false){
                    $("#adjustable").val(false);
            		var heights = data.heights;
            		if(heights!=null){
                        var html = "<div class='sz_zdy col-xs-12 sz' id='zdy' data-sf='0'><div class='bg active' data-type='size' ><a class='text-muted' href='javascript:' data-v=''  data-w='' data-h=''>"
                            +"<div class='bm'><img src='../cust/img/size/size_icon2.png' class='img-responsive' style=''/><div class='icon-image' style=' '></div>"
                            +"</div> <span></span><small></small></a></div></div><div class='col-xs-12 top5'><div class='row'><label class='col-xs-2'> <i class='text-muted glyphicon glyphicon-resize-horizontal'></i>宽：</label>"
                            +"<div class='col-xs-9'><input class='single-slider-w-v form-control input-sm' style='float: left;padding:5px 3px;width:60px;height: 26px;' />（厘米）"
                            +"<small class='text-danger'></small></div><div class='col-xs-12 top30'><input type='hidden' class='single-slider-w' style='display: none' />"
                            +"<div class='slider-container theme-green' style='width: 400px;'></div></div></div><div class='row' style='margin-top: 3rem;'>"
                            +"<label class='col-xs-2'><i class='text-muted glyphicon glyphicon-resize-vertical'></i> 高：</label><div class='col-xs-9'>"
                            +"<input class='single-slider-h-v form-control input-sm' style='float: left;padding:5px 3px;width:60px;height: 26px;' />（厘米）<small class='text-danger'></small>"
                            +"</div><div class='col-xs-12 top30'><input type='hidden' class='single-slider-h' style='display: none' /><div class='slider-container theme-green'"
                            +"style='width: 400px;'></div></div></div><script type='text/javascript' src='./js/size.js'></script></div>";
                        $("#sz3").append(html);
            			settingrule(data.widths,data.heights);
                        $("#sz-3").addClass("active");
                        $("#sz3").addClass("active").addClass("in");
                        bg();
            		}else{
            			 var html = "<div class='sz_zdy col-xs-12 sz' id='zdy' data-sf='0'><div class='bg active' data-type='size' ><a class='text-muted' href='javascript:' data-v=''  data-w='' data-h=''>"
                             +"<div class='bm'><img src='../cust/img/size/size_icon2.png' class='img-responsive' style=''/><div class='icon-image' style=' '></div>"
                             +"</div> <span></span><small></small></a></div></div><div class='col-xs-12 top5'><div class='row'><label class='col-xs-3'> <i class='text-muted glyphicon glyphicon-resize-horizontal'></i>${unit}：</label>"
                             +"<div class='col-xs-8'><input class='single-slider-w-v form-control input-sm' style='float: left;padding:5px 3px;width:60px;height: 26px;' />（厘米）"
                             +"<small class='text-danger'></small></div><div class='col-xs-12 top30'><input type='hidden' class='single-slider-w' style='display: none' />"
                             +"<div class='slider-container theme-green' style='width: 400px;'></div></div></div><script type='text/javascript' src='./js/size.js'></script></div>";
            			 if($("#shape").val()==2){
            		        html = html.replace("${unit}","边长");
                        }else if($("#shape").val()==7){
            		        html  = html.replace("${unit}","直径");
                        }
                        $("#sz2").append(html);
                        settingrule(data.widths,null);
                        $("#sz-2").addClass("active");
                        $("#sz2").addClass("active").addClass("in");
                        bg();
            		}
            	}
            }
        }
    })
}

function addListener(){
    $('.text-value').on("click", function () {
        var width = $(this)[0].getAttribute("data-w");
        var height = $(this)[0].getAttribute("data-h");
        $("#size").empty();
        $("#size").append("<span><small>宽</small> "+width+" x <small>高</small> "+height+"</span> <small> 厘米 </small></span>");
    });
}

function selectborder(){
	var keyword = $("#border").val();
    var material = $("#material").val();
    var url;
    var data;
    if(keyword==1){
    	url="../cust/findBorderAll.do";
    	data = "material="+material;
    }else{
    	url="../cust/findBorderBykeyword.do";
    	data = "keyword="+keyword+"&material="+material;
    }

    $.ajax({
        "url":url,
        "data":data,
        "type":"post",
        "dataType":"json",
        success:function(json){
            if(json.status==200){
            	var borders = json.data;
                if(borders.length>0){
                	$("#selectborder").empty();
                }
            	for(var i=0;i<borders.length;i++){
            		var html="<div class='col-xs-4 bg' data-type='border' data-borderId='${borderId}' data-price='${price}'><a class='text-muted'> <div style='position:relative' class='bm'> <img src='${path}' class='img-responsive' style='margin: 0px auto;'/></div><span>${name}</span></a></div>";
            		html = html.replace("${path}", borders[i].path);
            		html = html.replace("${borderId}", borders[i].borderId);
            		html = html.replace("${price}", borders[i].price);
            		html = html.replace("${name}", borders[i].name+":"+borders[i].keyword);
            		$("#selectborder").append(html);
            	}
                bg();
            }}
    })
}
$("#border").on("change",function(){
	selectborder()
})
selectborder();

function bg(){
	$(".bg").on("click",function(){
		borderprice = $(this).val();
		var adjustable = $("#adjustable").val();
		if(adjustable=="true"){
            if(this.getAttribute("data-type")=='border'){
                $(this).parent().children().removeClass("active");
                $("#borderPrice").val(this.getAttribute("data-price"));
                var borderprice =Math.round(($("#borderPrice").val()*$("#perimeter").val())).toFixed(2);
                var total =(parseFloat($("#paintingPrice").val())+parseFloat(borderprice)).toFixed(2);
                $("#price").text("￥"+total);
                $("#total").val(total);
                $("#borderId").val(this.getAttribute("data-borderid"));
            }else if(this.getAttribute("data-type")=="size"){
                $(this).parent().children().removeClass("active");
                $("#perimeter").val(this.getAttribute("data-perimeter"));
                $("#sizeWidth").val(this.getAttribute("data-w"));
                $("#sizeHeight").val(this.getAttribute("data-h"));
                $("#paintingPrice").val(parseFloat(this.getAttribute("data-price")).toFixed(2));
                $("#price").text("￥"+parseFloat(this.getAttribute("data-price")).toFixed(2));
                $("#total").val(parseFloat(this.getAttribute("data-price")).toFixed(2));
                $("#sizeId").val(this.getAttribute("data-id"));
            }
            $(this).addClass("active");
        }else if(adjustable=="false"){/*$("#price").text("￥"+Math.round(((w*h)*0.0001)*590).toFixed(2))+"00";*/
            if(this.getAttribute("data-type")=="size"){
                $("#price").text("￥"+$("#paintingPrice").val());
                $(this).addClass("active");
            }else if(this.getAttribute("data-type")=='border'){
                $("#borderId").val(this.getAttribute("data-borderid"));
                $(this).parent().children().removeClass("active");
                $("#borderPrice").val(this.getAttribute("data-price"));
                dis();
                $(this).addClass("active");
            }
        }
	})
}

function  dis(){
    var width = $("#sizeWidth").val();
    var height = $("#sizeHeight").val();
    var shape = $("#shape").val();
    var perimeter = null;
    if(shape==7){
        perimeter = Math.ceil((((width/2)*Math.PI)*2)*0.01);
    }else{
        perimeter = Math.ceil(((width*2)+(height*2))*0.01);
    }
    var borderprice =Math.round(($("#borderPrice").val()*perimeter)).toFixed(2);
    var total =(parseFloat($("#paintingPrice").val())+parseFloat(borderprice)).toFixed(2);
    $("#price").text("￥"+total);
    $("#total").val(total);
    $("#perimeter").val(perimeter);
}

