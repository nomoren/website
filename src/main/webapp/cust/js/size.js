function settingrule(widths,heights){
	$('.single-slider-w-v , .single-slider-w').val(widths[widths.length-1]);
	$('.single-slider-h-v , .single-slider-h').val(heights==null?widths[widths.length-1]:heights[heights.length-1]);
	$("#sizeWidth").val(widths[1]);
	$("#sizeHeight").val(heights==null?widths[1]:heights[1]);
	if(heights!=null){
		var sz_zdy_h,h_from=heights[0],h_to=heights[heights.length-1],h_scale=heights;
	}
	var sz_zdy_w,w_from=widths[0],w_to=widths[widths.length-1],w_scale=widths;
	var h_w = $('.xz').width();
	if (h_w < 45) {
		h_w = 430;
	} else {
		h_w = h_w - 45;
	}
	click_size_zdy();
	$.ajax({
		type: "GET",
		url: "../js/jquery.range.min.js",
		dataType: "script",
		global: false,
		"throws": true,
		cache: true,
		success: function () {
			sz_zdy_w = $('.single-slider-w').jRange({
				from: w_from,
				to: w_to,
				step: 1,//一次滑动多少
				scale: w_scale,
				format: '%s',
				width: h_w,
				showLabels: true,
				showScale: true
			});
			if(heights!=null){
				sz_zdy_h = $('.single-slider-h').jRange({
					from: h_from,
					to: h_to,
					step: 1,
					scale: h_scale,
					format: '%s',
					width: h_w,
					showLabels: true,
					showScale: true
				});
			}

			$('.single-slider-w-v').on("change mousedown touchstart", function () {
				var v = $(this).val(),
					$j = $(this).attr('data-j');
				if (v > h_to) {
					v = h_to;
					$(this).val(v).next(".text-danger").text('数值最大不能超过' + h_to);
				} else if (v < 10) {
					v = 10;
					$(this).val(v).next(".text-danger").text('数值最小不能少于10');
				} else {
					$(this).next(".text-danger").text('');
				}
				click_size_zdy();
				if ($j == 'sz_zdy_w') sz_zdy_w.setValue(v);
				click_size_active();
			});

			$('.single-slider-w').next('.slider-container').on('change mouseup.slider touchend.slider touchcancel.slider', function () {
				var v, cat,
					c = $(this).prev('input').attr('class'),
					s = '.' + c + '-v';
				var check = function () {
					if (c == 'single-slider-w') {
						cat = sz_zdy_w;
					}
					v = cat.getValue();
					if (v <= 10) {
						v = 10;
						cat.setValue(v);
						$(s).val(v).next(".text-danger").text('数值最小不能少于10');
					} else {
						$(s).next(".text-danger").text('');
					}
					$(s).val(v);
					if ($(s).val() == v) {
						clearInterval(set);
						click_size_zdy();
					}
					click_size_active();
				};
				var set = setInterval(check, 400);
			});

			if(heights!=null){
				$('.single-slider-h-v').on("change mousedown touchstart", function () {
					var v = $(this).val(),
						$j = $(this).attr('data-j');
					if (v > h_to) {
						v = h_to;
						$(this).val(v).next(".text-danger").text('数值最大不能超过' + h_to);
					} else if (v < 10) {
						v = 10;
						$(this).val(v).next(".text-danger").text('数值最小不能少于10');
					} else {
						$(this).next(".text-danger").text('');
					}
					click_size_zdy();
					if ($j == 'sz_zdy_h') sz_zdy_h.setValue(v);
					click_size_active();
				});
				$('.single-slider-h').next('.slider-container').on('change mouseup.slider touchend.slider touchcancel.slider', function () {
					var v, cat,
						c = $(this).prev('input').attr('class'),
						s = '.' + c + '-v';
					var check = function () {
						if (c == 'single-slider-h') {
							cat = sz_zdy_h;
						}
						v = cat.getValue();
						if (v <= 10) {
							v = 10;
							cat.setValue(v);
							$(s).val(v).next(".text-danger").text('数值最小不能少于10');
						} else {
							$(s).next(".text-danger").text('');
						}
						$(s).val(v);
						if ($(s).val() == v) {
							clearInterval(set);
							click_size_zdy();
						}
						click_size_active();
					};
					var set = setInterval(check, 400);
				});
			}
			$('.sz a').on('click', function () {
				click_size_active();
			});
		}
	})
	function click_size_active() {
		$('#zdy').attr('data-sf', 1);
	}

	function click_size_zdy() {
		var w = $('.single-slider-w-v').val(),
		h = heights==null?$('.single-slider-w-v').val():$('.single-slider-h-v').val(),
		th = $('#zdy a'),
		span = '宽 ' + w + ' x 高 ' + h,
		dv_w = 100, r = 25;
		$("#sizeWidth").val(w);
		$("#sizeHeight").val(h);
		var paintingPrice = Math.round(((w*h)*0.0001)*590).toFixed(2);
		$("#paintingPrice").val(paintingPrice);
		dis();
		getsize(w,h);
		th.attr({
		'data-v': w + 'x' + h,
		'data-w': w,
		'data-h': h
	});
	var b = 3.5, w2 = (w / b).toFixed(0), h2 = (h / b).toFixed(0), t = 0, x = 1;

	if (h2 > w2) {
		if (h2 > 40) { t = (h2 * .2); x = 1.4; }
		if (h2 > 50) { t = 0; }
		if (h2 > 60) { x = 1; }
	} else if (h2 < w2) {
		if (w2 > 40) { t = (h2 * .2); x = 1.4; }
		if (w2 > 50) { t = 0; }
		if (w2 > 60) { x = 1; }
	}
	if (h2 > 40) { dv_w = 90; }
	if (h2 > 50) { dv_w = 85; }
	if (h2 > 60) { dv_w = 80; }
	if (h2 > 70) { dv_w = 75; }
	if (h2 > 80) { dv_w = 70; }
	if (w2 > 10) { r = 35; }
	if (w2 > 20) { r = 40; }
	if (w2 > 30) { r = 45; }
	if (w2 > 40) { r = 50; }
	if (w2 > 50) { r = 55; }
	if (w2 > 60) { r = 60; }
	if (w2 > 70) { r = 65; }
	if (w2 > 80) { r = 70; }
	w2 = (w2 * x).toFixed(0);
	h2 = (h2 * x).toFixed(0);
		if($("#shape").val()==7){
			th.html('<div style="position:relative;height:143px;width:143px;margin: 0px auto;overflow: visible;" class="bm"><img src="./img/size/size_icon1.png" class="img-responsive" style="position: absolute;bottom:0;right:' + r + '%;width:' + dv_w + '%;height:' + dv_w + '%;" /><div class="icon-image" style="top:' + t + '%;width:' + w2 + '%;height:' + h2 + '%;border-radius:50%;"></div></div><span>' + span + '</span><small>厘米</small>');
		}else{
			th.html('<div style="position:relative;height:143px;width:143px;margin: 0px auto;overflow: visible;" class="bm"><img src="./img/size/size_icon1.png" class="img-responsive" style="position: absolute;bottom:0;right:' + r + '%;width:' + dv_w + '%;height:' + dv_w + '%;" /><div class="icon-image" style="top:' + t + '%;width:' + w2 + '%;height:' + h2 + '%;"></div></div><span>' + span + '</span><small>厘米</small>');
		}
	}
}