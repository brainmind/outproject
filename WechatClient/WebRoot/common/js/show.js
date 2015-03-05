window.onload = function() {
	$(".loading").hide();
	/* 订单管理 */
	if ($('div').hasClass('hundred_percent')) {
		$(".changable").find("img").attr("src",
				contextPath + "/styles/images/status.png");
		$(".changable").find("span").css("color", "#86d104");
	}
	;
	// 设定首页焦点图尺寸
	var height = $(window).height();
	var width = $(window).width();
	/* 焦点图 */
	var xx_height = $(".xx").height();
	$(".main_visual").height(xx_height);

	$(window).resize(function() {
		var height = $(window).height();
		var width = $(window).width();
		/* 焦点图 */
		var xx_height = $(".xx").height();
		$(".main_visual").height(xx_height);
	});

	/* 服务预约 */
	$(".addlist").eq(0).addClass("add_click");
	$(".addlist").click(function() {
		$(".addlist").removeClass("add_click");
		$(this).addClass("add_click");
	});

	/* 日常保养 添加新项 */
	renderServices();

	/* 日常保养 */
	initRenderingCommandotyService();
	
	/* 车辆选择 */
	renderSelectByVIN();
	
	/* 评价 */
	$(".assessment_choice a").each(function(i){
		if(i == 0){
			$(this).addClass("cur");
			var opId = $(this).attr("id");
			var fieldId = $(this).attr("field-id");
			$("#value"+fieldId).val(opId);
		}
		$(this).click(function() {
			$(".assessment_choice a").removeClass("cur");
			$(this).addClass("cur");
			var opId = $(this).attr("id");
			var fieldId = $(this).attr("field-id");
			$("#value"+fieldId).val(opId);
		});
	});
	/* 支付成功 */
	$(".payment li a").eq(0).addClass("cur");
	$(".payment li a").click(function() {
		$(".payment li a").removeClass("cur");
		$(this).addClass("cur");
	});
	/* 首页弹出层 */
	var tc_h = $(".tcmain").height();
	$(".tcmain").css({
		"top" : (height - tc_h) / 2
	});
	if (width > 640) {
		$(".tc_bg").css({
			"left" : "50%",
			"margin-left" : "-320px"
		});
	} else {
		$(".tc_bg").css({
			"left" : "0",
			"margin-left" : "0"
		});
	}
	$(".idx_car_o,.idx_car_a").click(function() {
		var backurl = $(this).attr("backurl");
		if(backurl == null){
			backurl = "";
		}
		if(WxchatClient.currentCarType() == null){
			window.location.href = contextPath+projectRoot+"/car/sel?backurl="+backurl;
			return false;
		}
		$(".tc_bg").fadeIn();
		var myCarTypeSel = $(".tcmain");
		if (width > 640) {
			myCarTypeSel.animate({
				"left" : "50%",
				"margin-left" : "-288px"
			});
		} else {
			myCarTypeSel.animate({
				"left" : "5%",
				"margin-left" : "0"
			});
		}
		var myCarTypeList = WxchatClient.Cookie.getCookieVal(WxchatClient.Cookie_CarType_Key);
		var ul = $("ul", myCarTypeSel);
		if(myCarTypeList){
			if($.type(myCarTypeList)=="array" && myCarTypeList.length > 0){
				for(var i=0; i<myCarTypeList.length; i++){
					var carType = myCarTypeList[i];
					var li = ul.html();
					ul.html(li+"<li dataid=\""+carType.id+"\" brand=\""+carType.brand+"\" " +
							"serie=\""+carType.sername+"\" car=\""+carType.car+"\" " +
							"logourl=\""+carType.logourl+"\" isdefault="+carType.isdefault+">"+carType.brand+" "+carType.sername+""+carType.car+"</li>");
				}
			}else{
				var brand = myCarTypeList.brand;
				var sername = myCarTypeList.sername;
				var car = myCarTypeList.car;
				var id = myCarTypeList.id;
				var logourl = myCarTypeList.logourl;
				var isdefault = myCarTypeList.isdefault;
				ul.html("<li dataid=\""+id+"\" brand=\""+brand+"\" serie=\""+sername+"\" car=\""+car+"\" " +
						"logourl=\""+logourl+"\" isdefault="+isdefault+">"+brand+" "+sername+" "+car+"</li>");
			}
			ul.children("li").on("click", function(){
				var $li = $(this);
				var isdefault = $li.attr("isdefault");
				if(isdefault || isdefault == "true"){
					$(".tc_bg").hide();
					$(".tcmain").animate({
						"left" : "-100%"
					});
					return false;
				}
				var id = $li.attr("dataid");
				var brand = $li.attr("brand");
				var sername = $li.attr("serie");
				var car = $li.attr("car");
				var logourl = $li.attr("logourl");
				var cookiecartype = "{id:\""+id+"\",brand:\""+brand+"\",logourl:\""+logourl+"\"," +
				"car:\""+car+"\",sername:\""+sername+"\",isdefault:"+isdefault+"}";
				WxchatClient.setCurrentCarType(cookiecartype);
				$(".idx_car").html("<h1>"+brand+" "+sername+"<br/>"+car+"</h1><a href=\"#\" class=\"idx_car_a\" backurl=\""+backurl+"\">更改车型</a>");
			});
		}
	});
	$(".tc_bg,.close,.tcmain li,.other").click(function() {
		$(".tc_bg").hide();
		$(".tcmain").animate({
			"left" : "-100%"
		});
		if(WxchatClient.currentCarType() != null){
			$(".idx_car_o").hide();
			$(".idx_car").show();
		}
	});
	/** 订单管理* */
	$(".click_more").click(function() {
		var more = $(".more_content");
		if (more.is(":hidden")) {
			more.show();
			$(this).find("img").attr("src",
					contextPath + "/styles/images/click_more_off.png");
		} else {
			more.hide();
			$(this).find("img").attr("src",
					contextPath + "/styles/images/click_more.png");
		}
	});
	// 获取表格的第一列
};

function updateTotalPrice(li){
	var totalPrice = 0;
	$("div.day_list > ul > li > div > div.day_title").each(function(){
		var $this = $(this);
		if($(this).parents("li").find(".xz").is(":hidden")){		
		}else{
			var strPrice = $("h2 > span:last", $this).text();
			if(strPrice && strPrice != ""){
				totalPrice += parseFloat(strPrice);
			}
		}
	});
	var strPrice = $("div.day_list > ul > li[categoryid=0] > div.day_title > h2 > span:last").text();
	totalPrice += parseFloat(strPrice);
	$("#totalprice").html(totalPrice.toFixed(2));
	//更新隐藏域
	var commdoty = $("div.day_title", li).first();
	var id = commdoty.attr("data-id");
	var price = $("h2 > span:last", commdoty).text();
	if(price == null){
		price = "0";
	}
	var categroyid = commdoty.attr("category-id");
	var fee = $("li[type=fees][categoryid="+categroyid+"] > input[type=hidden][name='service_fees.price']", li.parent()).val();
	var serviceprice = parseFloat(price)-parseFloat(fee);
	var label = $("h1", commdoty).html();
	var categoryid = commdoty.attr("category-id");
	var number = commdoty.attr("data-number");
	$("input[type=hidden][name='commodities.id']", li).val(id);
	$("input[type=hidden][name='commodities.label']", li).val(label);
	$("input[type=hidden][name='commodities.number']", li).val(number);
	$("input[type=hidden][name='commodities.price']", li).val(serviceprice);
	$("input[type=hidden][name='commodities.category_id']", li).val(categoryid);
}

function initRenderingCommandotyService(){
	$("div.day_list > ul > li > div > div.day_title").click(function() {
		if(!$(this).hasClass("day_cur")){
			$("div.day_more").hide();
			$("div.day_title").removeClass("day_cur");
		}
		if($(this).parent().next(".day_more").find("dd").length){
			$(this).toggleClass("day_cur");
			$(this).parent().next(".day_more").toggle();
		}
	});
	$(".day_more dd").unbind("click");
	$(".day_more dd").click(function() {
		var dd = $(this);
		$(".day_more dd").removeClass("dd_cur");
		dd.addClass("dd_cur");
		$(".day_more").hide();
		var curDiv = dd.parent().parent().prev();
		var curCommodify = curDiv.html();
		curDiv.html(dd.html());
		dd.html(curCommodify);
		$("div.day_title", curDiv).on("click", function(){
			if(!$(this).hasClass("day_cur")){
				$("div.day_more").hide();
				$("div.day_title").removeClass("day_cur");
			}
			$(this).toggleClass("day_cur");
			$(this).parent().next(".day_more").toggle();
		});
		updateTotalPrice(dd.parent().parent().parent());
	});
	
	$(".day_name").click(function() {
		$(this).prev().show();
		var li = $(this).parent();
		var categoryid = li.attr("categoryid");
		$("input[type=hidden][name='commodities.checked']", li).val(1);
		var li = $("li[type=fees][categoryid="+categoryid+"]");
		var serviceFee = 0;
		if(li[0]){
			li.children("input[name='service_fees.checked']").val(1);
			serviceFee = parseFloat($("input[name='service_fees.price']", li).val());
		}
		var span = $("input[type=hidden][name='commodities.price']" , $(this).parent());
		var fee = span.val();
		if(fee != null && fee != ""){
			var totalFee = parseFloat($("#totalprice").text());
			totalFee += parseFloat(fee)+serviceFee;
			$("#totalprice").html(totalFee.toFixed(2));
		}
	});
	$(".xz").click(function(){
		$(this).hide();
		var li = $(this).parent();
		var categoryid = li.attr("categoryid");
		$("input[type=hidden][name='commodities.checked']", li).val(0);
		var li = $("li[type=fees][categoryid="+categoryid+"]");
		var serviceFee = 0;
		if(li[0]){
			li.children("input[name='service_fees.checked']").val(0);
			var feePrice = $("input[name='service_fees.price']", li).val();
			serviceFee = parseFloat(feePrice);
		}
		var span = $("input[type=hidden][name='commodities.price']" , $(this).parent());
		var fee = span.val();
		if(fee != null && fee != ""){
			var totalFee = parseFloat($("#totalprice").text());
			totalFee -= (parseFloat(fee) + serviceFee);
			$("#totalprice").html(totalFee.toFixed(2));
		}
	});
}

function renderSelectByVIN(){
	$(".che_vin dd").eq(0).addClass("che_click");
	$(".che_vin dd").click(function() {
		$(".che_vin dd").removeClass("che_click");
		$(this).addClass("che_click");
	});
}

function renderServices(){
	$(".add_an li").click(function() {
		$(this).toggleClass("pro_click");
	});
}