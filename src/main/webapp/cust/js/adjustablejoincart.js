$("#add-to-cart").on("click",function(){
    var id = $("#id").val();
    if(id=="1"){alert("此订单正在加入购物车，请勿重复提交！");return;}
    if(id=="2"){alert("此订单已经加入购物车，请勿重复提交！");return;}
    var flag = $("#piece-0")[0].getAttribute("data-cut");
    if(flag=="false"){          //先判断用户有没有上传图片操作
        alert("请先调整或者上传您的设计图！");
        return false;
    }
    var width = $("#sizeWidth").val();
    var height = $("#sizeHeight").val();
    var data;
    if(!isNaN(width)&&!isNaN(height)){
        $.ajax({
            url:"../cust/judgeLogin.do",
            type:"post",
            dataType:"json",
            success:function(json){
                if(json.data!=null){
                    var uid = json.data;
                    $("#uid").val(uid);
                    data = $("#form_submit").serialize();
                    $.ajax({
                        url:"../cust/joincustcart.do",
                        data:data,
                        before:function(){
                            $("#id").val(1);
                        },
                        type:"POST",
                        dataType: "json",
                        success:function (json) {
                            if(json.status==200){
                                $("#id").val(2);
                                window.location.href="../store/cart.html";
                            }else{
                                console.log(json.message);
                                alert(json.message);
                            }
                        }
                    })

                }else{
                    $("#Login").modal('show');
                }
            }
        })
    }else{
        alert("您还没有选择定制的尺寸！");
    }
})