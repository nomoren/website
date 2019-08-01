var step = 1;
(function($){
    bsStep();
    $.fn.wizzy = function(options) {

        let settings = $.extend({
            stepNumbers: false,
            progressType: 'fill',
        }, options);

        return this.each(function(){
            let elem = $(this);
            let nav = elem.find('.wz-header nav');
            let navigator = elem.find('.wz-navigator');
            let content = elem.find('.wz-inner');

            let btnNext = '<a href="#" class="btn btn-primary right btn-lg" data-action="next" style="background:black;">下一步 <i class="fas fa-angle-right"></i></a>';
            let btnBack = '<a href="#" class="btn btn-warning left btn-lg" data-action="back"><i class="fas fa-angle-left"></i> 上一步</a>';
            let btnFinish = '<a href="#" class="btn btn-success right btn-lg" id="finish" data-action="finish">完&nbsp;成 <i class="fas fa-check"></i></a>';
            let btnaddcart = '<a href="#" class="btn btn-success right btn-lg" id="addcart" data-action="addcart">加入购物车<i class="fas fa-check"></i></a>';
            let step_links = elem.find('ul li a').toArray();
            let step_count = step_links.length;
            let step_status = new Array(step_count);
            let step_content = elem.find('.wz-step').toArray();
            let link_width = $(step_links[0]).width();


            function init(){
                for(i = 1 ; i < step_count ; i++){
                    step_status[i] = 0;
                }
                step_status[0] = 1;
                updateTemplate();
                render();
            }

            function moveProgress(step){
                if(settings.progressType == 'fill'){
                    let progressWidth = link_width * (step + 1);
                    nav.find('.progress').css({'width':progressWidth + 'px'});
                }
                if(settings.progressType == 'slide'){
                    nav.find('.progress').css({'width':link_width + 'px'});
                    let distance = link_width * (step);
                    nav.find('.progress').css({'left':distance + 'px'});
                }

            }

            function updateTemplate(){
                nav.append('<div class="progress"></div>');
                moveProgress(step);
                step_links.forEach(element => {
                    $(element).wrapInner('<span></span>');
                });
            }

            function loader(show){
                let loader ='<div class="loading"></div>';
                if(show === true){
                    content.fadeOut(400,function(){
                        elem.addClass('progress');
                        setTimeout(function(){
                            elem.append(loader);
                        }, 500);
                    });
                }
                else{
                    elem.find('.loading').remove();
                    elem.removeClass('progress');
                    setTimeout(function(){
                        content.fadeIn(400);
                    }, 400);
                }
            }

            function react(action){
                if(step >= 0 && step < step_count){
                    if(action === 'next'||action ==='finish'){
                        step_status[step++] = 1;
                        if(step_status[step] === 0){
                            step_status[step] = 1;
                        }
                        if(step=='2'){
                            next();
                        }else{
                            /* bsStep(step+1);
                             render(step);*/
                        }

                    }
                    else if(action == 'back'){
                        step--;
                        window.location="./../cust/selectshape.html";
                        /*bsStep(step+1);
                        render(step);*/
                    }
                    else if(action == 'addcart'){
                        bsStep(4+1);
                        $("#addcart").prop('disabled', true);
                        loader(true);
                        setTimeout(function(){
                            loader(false);
                            alert("恭喜您，定制完成，已加入购物车，请前往购物车结算");
                        }, 3000);

                    }
                }

            }

            function render(){
                navigator.html('');

                if(step === 0){
                    navigator.append(btnNext);
                }
                else if(step === step_count-1){
                    navigator.append(btnaddcart);
                }
                else{
                    navigator.append(btnBack + btnNext);
                }

                elem.find('nav a').removeClass('active completed');
                for(i = 0 ; i < step ; i++){
                    $(step_links[i]).addClass('completed');
                }
                $(step_links[i]).addClass('active');

                elem.find('.wz-body .wz-step').removeClass('active');
                $(step_content[step]).addClass('active');

                moveProgress(step);
            }

            $(elem).on('click','.wz-navigator .btn',function(e){
                e.preventDefault();
                let action = $(this).data('action');
                react(action);
            });
            init();
            bsStep(2);
        });
    }
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

}(jQuery));

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
function display_piece(ordernumber){
    var xhr= new XMLHttpRequest();
    xhr.onreadystatechange=function(){
        if(xhr.readyState==4&&xhr.status==200){
            $("#preloader").css("display","none");
            var image=this.response;
            $("#piece-0").css("background","url("+window.URL.createObjectURL(image)+")");
            $("#piece-0").css("background-size","100% 100%");
            $("#piece-0").css("background-color","#f5f5f5");
            $("#piece-0")[0].setAttribute("data-cut","true");
            $("#piece-0").css("border","0px");
            $("#alert-info").css("display","block");
            $("#cutwindow").modal('hide');
        }
    }
    xhr.open("get","../cust/getThumbnnail.do?piece=0&ordernumber="+ordernumber,true);
    xhr.responseType="blob";
    xhr.send();
    $("#preloader").css("display","block");
}
$(".wizzy").wizzy();

function uploadCutImageArg(piece){
    var $dataX = $('#dataX'),
        $dataY = $('#dataY'),
        $dataHeight = $('#dataHeight'),
        $dataWidth = $('#dataWidth'),
        $filename = $('#uploadfilename'),
        $shape = $("#shape");
    var dataX = $dataX.val();
    var dataY = $dataY.val();
    var dataHeight = $dataHeight.val();
    var dataWidth = $dataWidth.val();
    var filename = $filename.val();
    var shape = $shape.val();
    var url = "../cust/uploadCutImgArg.do";
    var data  = "filename="+filename+"&dataX="+dataX+"&dataY="+dataY+"&dataHeight="+dataHeight+"&dataWidth="+dataWidth+"&piece=0&shape="+shape;
    if(!isNaN(dataX)&&!isNaN(dataY)&&!isNaN(dataHeight)&&!isNaN(dataWidth)&&filename!=''&&!isNaN(piece)){
        $.ajax({
            "url":url,
            "data":data,
            "type":"post",
            beforeSend:function(){
                $("#preloader").css("display","block");
            },
            "dataType":"json",
            success:function(json){
                $("#preloader").css("display","none");
                if(json.status==200){
                    var data = json.data;
                    $("#ordernumber").val(data.ordernumber);
                    display_piece(data.ordernumber);
                }else{
                    alert(json.message);
                    alert("切图失败，请稍后重试!");
                    if(piece==0){
                        step--;
                    }
                }
            }
        })

    }else if(filename==''){
        alert("请先上传您的设计图片!");
        if(piece==0){
            step--;
        }
    }else if(isNaN(piece)){
        alert("请选择定制区域！");
        if(piece==0){
            step--;
        }
    }else{
        alert("参数错误！");
        if(piece==0){
            step--;
        }
    }

}


setreadonly();
function setRectSize(width,height){
    var obj = $("#piece-0")[0];
    var idealHeight = Math.round(height / 0.126984126984127);
    var idealWidth = Math.round(width / 0.126984126984127);
    obj.setAttribute("data-idealheight",idealHeight);
    obj.setAttribute("data-idealWidth",idealWidth);
    obj.setAttribute("data-cut",false);
	$(".rect").css("width",(width/6)+"Vw");
	$(".rect").css("height",(height/6)+"Vw");
    
}
