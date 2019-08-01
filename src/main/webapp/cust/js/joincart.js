$("#add-to-cart").on("click",function(){
    var uid = $("#uid").val()
    var width = $("#sizeWidth").val();
    var height = $("#sizeHeight").val();
    var data;
    if(!isNaN(width)&&!isNaN(height)&&width!=0&&height!=0){
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
                            url:"../cust/updateinfo.do",
                            data:data,
                            type:"POST",
                            dataType: "json",
                            success:function (json) {
                                if(json.status==200){
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