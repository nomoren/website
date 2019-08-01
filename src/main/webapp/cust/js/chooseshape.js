function checkwidth(){
	var width = document.body.clientWidth;
	if(width<=800){
		alert("该页面只适合电脑端访问，请切换电脑端访问！");
		window.location.href="/";
		return false;
	}
	return true;
}

window.onload = function(){
	var flag = checkwidth();
	if(flag){				//首先检测宽度 检测宽度通过然后发异步获取图形相关信息
		var url = "../cust/getShape.do";
		$.ajax({
			"url":url,
			"type":"post",
			"dataType":"json",
			success:function(json){
				if (json.status == 200) {
					var list = json.data;
					$("#goodsInfo").empty();
					var html='<div class="l-plp-grid__item l-plp-grid__item_slot-1 js-grid-tile">'
						+'<div class="b-product-tile">'
						+'<canvas class="b-product-tile__aspect-ratio-holder" height="471" width="471"></canvas>'
						+'<div class="b-tile-category-description">'
						+'<div class="b-tile-category-description__inner">'
						+'<h1 class="b-tile-category-description__title-script">亚克力材料</h1>'
						+'<p class="b-tile-category-description__content">具有较好的透明性<br/>外观光滑平整优美<br/>表面硬度优异且容易清洁<br/></p>'
						+'</div></div></div></div>';
					$("#goodsInfo").append(html);
					showList(list);
				}else{
					alert("服务器繁忙，请稍后再试");
				}
			}
		})
	}
}


function showList(list){
	for (var i=0;i<=list.length-1;i++){
		var html='<div class="l-plp-grid__item js-grid-tile" data-colors-to-show="" data-default-sz="24.0" data-cid="us-boys-clothing-t-shirts-and-polos">'
			+'<div class="b-product-tile js-product-tile js-plp-producttile js-product-impression" id="2d7ed13cb42097477a691c3a41" data-itemid="940646_ecru" data-product-impression="{&quot;productID&quot;:&quot;940646&quot;,&quot;productStockLevel&quot;:55,&quot;productName&quot;:&quot;Plain T-Shirt&quot;,&quot;productPrice&quot;:10,&quot;productSalePrice&quot;:10,&quot;productCategory&quot;:&quot;Boys&#39; Clothing&quot;,&quot;productSubCategory&quot;:&quot;T-Shirts &amp; Polos&quot;,&quot;productColour&quot;:&quot;Marine Blue&quot;}">'
			+'<canvas class="b-product-tile__aspect-ratio-holder" height="471" width="471"></canvas>'
			+'<div class="b-quickview-popup">'
			+'<div class="b-quickview-popup__inner js-quickview-placeholder js-qv-holder-940646_ecru"></div>'
			+'<i class="b-quickview-popup__overlay js-quickview-close"></i>'
			+'</div>'
			+'<div class="b-product-tile__inner js-product-tile-wrapper">'
			+'<div class="b-product-tile__top">'
			+'<div class="b-product-image js-product-image b-product-alt-view js-plp-producttile">'
			+'<a class="b-product-image__link js-thumb-link" title="">'
			+'<div class="b-product-alt-view__item b-product-alt-view__item_main js-tile-main">'
			+'<img class="b-product-image__img js-main-product-image b-lazyload_loaded" onclick="choose()" width="100%" height="100%" data-shape="#{id}" data-material="#{material}" title="#{title}" src="#{path}-1.png"/>'
			+'</div>'
			+'<div class="b-product-alt-view__item b-product-alt-view__item_alt js-tile-alt">'
			+'<img class="b-product-image__img js-main-product-image b-lazyload_loaded" onclick="choose()" width="100%" height="100%" data-shape="#{id}" data-material="#{material}" title="#{title}" src="#{path}-2.png"/>'
			+'</div></a></div></div>'
			+'<div class="b-product-tile__bottom b-product-tile__bottom-940646_ecru ">'
			+'<h2 class="b-product-name">'
			+'<a class="b-product-name__link js-name-link" href="product_item.html?id=#{id}" style="font-family: 思源黑体 CN ExtraLight;font-size:14px ">#{title}</a>'
			+'</h2>'
			+'<div class="pd_num"><div class="b-price b-price_plp"><span class="b-price__value" aria-label="Sale Price">'
			+'<span class="" style="font-family: 思源黑体 CN ExtraLight;font-size:14px ">#{area}</span>'
			+'</span></div></div><div class="pd_price"><div class="b-price b-price_plp">'
			+'<span class="b-price__value" aria-label="Sale Price">'
			+'<span class="b-price__digit" style="font-family: 思源黑体 CN ExtraLight;font-size:14px ">￥#{price}</span>'
			+'</span></div></div></div></div></div></div>';
		html = html.replace(/#{id}/g,list[i].shapeId);
		html = html.replace(/#{material}/g,list[i].material);
		html = html.replace(/#{path}/g,list[i].titleImgURL);
		html = html.replace(/#{title}/g,list[i].shapeTitle);
		html = html.replace(/#{price}/g,list[i].price+" 起");
		html = html.replace(/#{area}/g,"整体面积:"+list[i].width+"cm X "+list[i].height+"cm");
		$("#goodsInfo").append(html);
	}
}