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
	$(".add_an li").click(function() {
		$(".add_an li").removeClass("pro_click");
		$(this).addClass("pro_click");
	});

	/* 日常保养 */
	initRenderingCommandotyService();
	
	/* 车辆选择 */
	$(".che_vin dd").eq(0).addClass("che_click");
	$(".che_vin dd").click(function() {
		$(".che_vin dd").removeClass("che_click");
		$(this).addClass("che_click");
	});
	/* 评价 */
	$(".assessment_choice a").eq(0).addClass("cur");
	$(".assessment_choice a").click(function() {
		$(".assessment_choice a").removeClass("cur");
		$(this).addClass("cur");
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
		if(WxchatClient.currentCarType() == null){
			window.location.href = contextPath+projectRoot+"/car/sel";
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
				$(".idx_car").html("<h1>"+brand+" "+sername+"<br/>"+car+"</h1><a href=\"#\" class=\"idx_car_a\">更改车型</a>");
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

function initRenderingCommandotyService(){
	$(".day_list li .day_title").click(function() {
		$(this).toggleClass("day_cur");
		$(this).next(".day_more").toggle();
	});
	$(".day_more dd").click(function() {
		$(".day_more dd").removeClass("dd_cur");
		$(this).addClass("dd_cur");
		$(".day_more").hide();
		$(this).parents(".day_more").prev(".day_title").prev(
				".day_pic").html(
				$(this).find(".day_pic").html());
		$(this).parents(".day_more").prev(".day_title").html(
				$(this).find(".day_title").html());
	});
	$(".day_name").click(function() {
		$(this).prev(".xz").show();
	});
	$(".xz").click(function() {
		$(this).hide();
	});
}