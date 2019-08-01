var control = $('#inputImage');
control.fileinput({
    language: 'zh', 
    uploadUrl: '../cust/upload.do',
    allowedFileExtensions: ['jpg', 'gif', 'png','tif','psd'],
    uploadAsync: true, 
    showUpload: true, 
    showPreview: false, 
    showCaption: true,
    browseClass: "btn black", 
    dropZoneEnabled: false,
    minImageWidth: 1000, 
    minImageHeight: 1000,
    maxFileSize: 102400,
    maxFileCount: 1,
    enctype: 'multipart/form-data',
    validateInitialCount: true,
    previewFileIcon: "<i class='glyphicon glyphicon-picture'></i>",
    msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
    defaultPreviewContent: '',
}).on('filebatchselected', function (event, file) {     //选中文件事件
    $(this).fileinput("upload");
}).on("fileuploaded", function (event, data, previewId, index) {
    var filename = data.response.message;
    var status = data.response.status;
    if(status==200){
        var $filename = $('#uploadfilename');
        $filename.val(filename);
    }
});

function setreadonly(){
    $(".args input").each(function(){
        this.setAttribute("readonly",true);
    })
}


function chooserect(){
    var obj = event.srcElement||event.target;
    var idealheight = obj.getAttribute('data-idealHeight');
    var idealwidth = obj.getAttribute('data-idealwidth');
    $("#idealHeight").val(idealheight);
    $("#idealwidth").val(idealwidth);
    $("#piece").val($(obj)[0].getAttribute("data-piece"));
}
function cut(){
    var piece = $("#piece");
    uploadCutImageArg($(piece).val());
}
function display_piece(piece,ordernumber){
    var xhr= new XMLHttpRequest();
    xhr.onreadystatechange=function(){
        if(xhr.readyState==4&&xhr.status==200){
            $("#preloader").css("display","none");
            var image=this.response;
            $("#piece-"+piece).css("background","url("+window.URL.createObjectURL(image)+")");
            $("#piece-"+piece).css("background-size","100% 100%");
            $("#piece-"+piece).css("background-color","#f5f5f5");
            $("#piece-"+piece)[0].setAttribute("data-cut","true");
            $("#piece-"+piece).css("border","0px");
            $("#alert-info").css("display","block");
            $("#cutwindow").modal('hide');
        }
    }
    xhr.open("get","../cust/getThumbnnail.do?piece="+piece+"&ordernumber="+ordernumber,true);
    xhr.responseType="blob";
    xhr.send();
    $("#preloader").css("display","block");
}
function next(){
    var nextflag = false;
    $(".rect .tile").each(function(){
        if($(this)[0].getAttribute("data-cut")=="true"){
            nextflag = true;
        }else{
            nextflag = false;
            $(this).css("border","1px dashed red");
            return false;
        }
    })
    if(nextflag){
        var piece = $("#piece").val();
        if(piece!=0){
            takeScreenshot();
        }else{
            $("#form_cust").submit();
        }
    }else{
        alert("请上传剩余图片素材");
        step--;
    }
}
function takeScreenshot() {
    var ordernumber = $("#ordernumber").val();
    var copyDom = $(".rect")[0];
    var dom = $(".rect")[0];
    $('body').append(copyDom);
    html2canvas(copyDom,{
      width: dom.offsetWidth,
      height: dom.offsetHeight,
      allowTaint:true,
      useCORS:true,
    }).then(function(canvas){
      var context = canvas.getContext('2d');
      context.mozImageSmoothingEnabled = false;
      context.webkitImageSmoothingEnabled = false;
      context.msImageSmoothingEnabled = false;
      context.imageSmoothingEnabled = false;
        uploadThumbnnail(canvas.toDataURL(),ordernumber);
    });
}

function uploadThumbnnail(urlcode,ordernumber){
    var url = "../cust/uploadthumbnnail.do";
    var data = "urlcode="+urlcode+"&ordernumber="+ordernumber;
    $.ajax({
        "url":url,
        "data":data,
        "type":"post",
        beforeSend:function(){
            $("#preloader").css("display","block");
        },
        "dataType":"json",
        "success":function(json){
            $("#preloader").css("display","none");
            $("#form_cust").submit();
        }
    })
}
$(".wizzy").wizzy();


window.onload = function(){
    var width = document.body.clientWidth;
    if(width<=800){
		alert("该页面只适合电脑端访问，请切换电脑端访问！");
		window.location.href="/";
	}
    if(width<=995){
        $("#cutwindow .modal-content").css("height","");
    }else if(width>995){
    	$("#cutwindow .modal-content").css("height","50rem");
    }
	
}

setreadonly();

