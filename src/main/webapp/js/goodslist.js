//获取url参数
(function ($) {
	$.getUrlParam = function (name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null) return unescape(r[2]); return null;
	}
})(jQuery);
//加载完成，显示默认商品列表（亚克力，风景，单幅画）
$(document).ready(function() {
			var bigtype=$.getUrlParam("bigtype");
			var smalltype=$.getUrlParam("smalltype");
			var data="material=亚克力&bigtype="+bigtype+"&smalltype="+smalltype;
			if(bigtype==null||smalltype==null||bigtype>4||bigtype<0||smalltype>3||smalltype<0){
				data="material=亚克力";
			}
			var url = "../goods/show.do";
				$.ajax({
					"url" : url,
					"data" : data,
					"type" : "POST",
					"datatype" : "json",
					"success" : function(json) {
						if (json.status == 200) {
							var list = json.data;
							showList(list);				
					}else{
						alert("服务器繁忙，请稍后再试");
					}
				}
			});
				
				
		});

function toshow(bigtype,smalltype){
	var url = "../goods/show.do";
	var data="bigtype="+bigtype+"&smalltype="+smalltype;
	$.ajax({
		"url" : url,
		"data" : data,
		"type" : "POST",
		"datatype" : "json",
		"success" : function(json) {
			if (json.status == 200) {
				var list = json.data;
				showList(list);				
		}else{
			alert("服务器繁忙，请稍后再试");
		}
	}
});
}
















		function showList(list){
			$("#goodsInfo").empty();
			var html='<div class="l-plp-grid__item l-plp-grid__item_slot-1 js-grid-tile">'
			+'<div class="b-product-tile">'
			+'<canvas class="b-product-tile__aspect-ratio-holder" height="471" width="244"></canvas>'
			+'<div class="b-tile-category-description">'
			+'<div class="b-tile-category-description__inner">'
			+'<h1 class="b-tile-category-description__title-script">亚克力材料</h1>'
			+'<p class="b-tile-category-description__content">具有较好的透明性<br/>外观光滑平整优美<br/>表面硬度优异且容易清洁<br/></p>'
			+'</div></div></div></div>';
			$("#goodsInfo").append(html);
			for (var i=0;i<=list.length-1;i++){
				var html='<div class="l-plp-grid__item js-grid-tile" data-idx="#{index}" data-colors-to-show="" data-default-sz="24.0" data-cid="us-boys-clothing-t-shirts-and-polos">'
				+'<div class="b-product-tile js-product-tile js-plp-producttile js-product-impression" id="2d7ed13cb42097477a691c3a41" data-itemid="940646_ecru" data-product-impression="{&quot;productID&quot;:&quot;940646&quot;,&quot;productStockLevel&quot;:55,&quot;productName&quot;:&quot;Plain T-Shirt&quot;,&quot;productPrice&quot;:10,&quot;productSalePrice&quot;:10,&quot;productCategory&quot;:&quot;Boys&#39; Clothing&quot;,&quot;productSubCategory&quot;:&quot;T-Shirts &amp; Polos&quot;,&quot;productColour&quot;:&quot;Marine Blue&quot;}">'
				+'<canvas class="b-product-tile__aspect-ratio-holder" height="471" width="244"></canvas>'
				+'<div class="b-quickview-popup">'
				+'<div class="b-quickview-popup__inner js-quickview-placeholder js-qv-holder-940646_ecru"></div>'
				+'<i class="b-quickview-popup__overlay js-quickview-close"></i>'
				+'</div>'
				+'<div class="b-product-tile__inner js-product-tile-wrapper">'
				+'<div class="b-product-tile__top">'
				+'<div class="b-product-image js-product-image b-product-alt-view js-plp-producttile">'
				+'<a class="b-product-image__link js-thumb-link" href="product_item.html?id=#{id}" title="">'
				+'<div class="b-product-alt-view__item b-product-alt-view__item_main js-tile-main">'
				+'<img class="b-product-image__img js-main-product-image b-lazyload_loaded" title="#{title}" src="..#{path}/img0.png">'
				+'</div>'
				+'<div class="b-product-alt-view__item b-product-alt-view__item_alt js-tile-alt">'
				+'<img class="b-product-image__img js-alt-product-image b-lazyload_loaded"  title="#{title}" src="..#{path}/img1.png">'
				+'</div></a></div></div>'
				+'<div class="b-product-tile__bottom b-product-tile__bottom-940646_ecru ">'
				+'<h2 class="b-product-name">'
				+'<a class="b-product-name__link js-name-link" href="product_item.html?id=#{id}" style="font-family: 思源黑体 CN ExtraLight;font-size:14px ">#{title}</a>'
				+'</h2>'
				+'<div class="pd_num"><div class="b-price b-price_plp"><span class="b-price__value" aria-label="Sale Price">'
				+'<span class="" style="font-family: 思源黑体 CN ExtraLight;font-size:14px ">#{number}</span>'
				+'</span></div></div><div class="pd_price"><div class="b-price b-price_plp">'
				+'<span class="b-price__value" aria-label="Sale Price">'
				+'<span class="b-price__digit" style="font-family: 思源黑体 CN ExtraLight;font-size:14px ">￥#{price}</span>'
				+'</span></div></div></div></div></div></div>';
				html = html.replace(/#{index}/g,i+1);
				html = html.replace(/#{id}/g,list[i].id);
				html = html.replace(/#{number}/g,list[i].number);
				html = html.replace(/#{path}/g,list[i].path);
				html = html.replace(/#{title}/g,list[i].title);
				html = html.replace(/#{price}/g,list[i].price);
				$("#goodsInfo").append(html);
			}
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		/*$("#type").click(function(){
			var target=event.target || event.srcElement;
			var text=target.innerText;
			var url = "../uploadInfo/show.do";
			//alert(target.tagName);
			if(target.tagName=="A"){
			$.ajax({
				"url" : url,
				"data" : "material="+text,
				"type" : "POST",
				"datatype" : "json",
				"success" : function(json) {
					if (json.status == 200) {
						var list = json.data;
						$("#goodsInfo").empty();
						showList(list);
										
					}
				}
			});
			}
		});*/