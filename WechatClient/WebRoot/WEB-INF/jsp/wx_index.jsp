<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.wechat.client.utils.*" %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML>
<html>
<head>
<!--date:20140121-->
<title>小马上门汽车保养服务</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" Content="no-cach" />
<meta name="robots" content="all" />
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<link rel="shortcut icon" href="<%=path %>/styles/images/favicon.ico" type="image/x-icon" />
<jsp:include page="common/base.jsp" />
<script type="text/javascript">
$(document).ready(function () {
		$("#btn_prev,#btn_next").fadeOut();
		var idx = $(".idx_nav li").length;
		for(var nav=0;nav<idx;nav++){
			if(nav%3==0){
				$(".idx_nav li").eq(nav-1).css({"border-right":"none"});
			}
		}
	/* $(".main_visual").hover(function(){
			$("#btn_prev,#btn_next").fadeIn();
		},function(){
			$("#btn_prev,#btn_next").fadeOut();
		}); */
	$dragBln = false;
	$(".main_image").touchSlider({
		flexible : true,
		speed : 200,
		btn_prev : $("#btn_prev"),
		btn_next : $("#btn_next"),
		paging : $(".flicking_con a"),
		counter : function (e) {
			$(".flicking_con a").removeClass("on").eq(e.current-1).addClass("on");
		}
	});
	$(".main_image").bind("mousedown", function() {
		$dragBln = false;
	});
	$(".main_image").bind("dragstart", function() {
		$dragBln = true;
	});
	$(".main_image a").click(function() {
		if($dragBln) {
			return false;
		}
	});
	timer = setInterval(function() { $("#btn_next").click();}, 5000);
	$(".main_visual").hover(function() {
		clearInterval(timer);
	}, function() {
		timer = setInterval(function() { $("#btn_next").click();}, 5000);
	});
	$(".main_image").bind("touchstart", function() {
		clearInterval(timer);
	}).bind("touchend", function() {
		timer = setInterval(function() { $("#btn_next").click();}, 5000);
	});

	var carType = WxchatClient.currentCarType();
	if(carType == null){
		carType = WxchatClient.getDefaultCarType();
	}
	if(carType && carType != null && carType != ""){
		//显示当前车型
		$(".idx_car_o").hide();
		$(".idx_car").show();
		var carName = carType.brand;
		var serName = carType.sername;
		var car = carType.car;
		$("div.idx_car > h1").first().html(carName+" "+serName+"<br/>"+car);
		
		//加载服务项目
		/* $.ajax({
			url:"<%=path + Constants.ROOT %>/order/service.json",
			dataType:"json",
			type:"get",
			success:function(r){
				var serviceul = $("div.idx_nav > ul");
				if($.type(r) == "object"){
					if(r["service_fees"] && r["service_fees"].length > 0){
						var serviceNum = r["service_fees"].length;
						var fixNum = 0;
						if(serviceNum > 6){
							fixNum = 5;
						}else{
							fixNum = serviceNum;
						}
						for(var i=0; i<fixNum; i++){
							var serviceFee = r["service_fees"][i];
							var li = $(document.createElement("li"));
							li.html("<a href=\"<%=path + Constants.ROOT %>/order/service?carId="+carType.id+"&type="+serviceFee.category_id+"\"><img src=\"<%=path %>/styles/images/idx_nav"+(i+1)+".png\">"+
									serviceFee.title+"</a>");
							li.appendTo(serviceul);
						}
						if(fixNum == 5){
							var li = $(document.createElement("li"));
							li.html("<a href=\"<%=path + Constants.ROOT %>/car/selItem\"><img src=\"<%=path %>/styles/images/idx_nav6.png\">更多分项</a>");
							serviceul.append(li);
						}
					}
				}
			}
		}); */
		
		$("a[rel=services]").each(function(){
			var $this = $(this);
			var url = $this.attr("href");
			$this.removeAttr("href");
			$this.parent().on("click", function(){
				window.location.href=url+"&car_id="+carType.id;
			});
		});
	}
});
</script>
</head>
<body>
<div class="wapper">
    <div class="main_visual">
    <div class="main_image">
        <img src="<%=path %>/styles/images/banner/banner01.jpg" class="xx">
        <ul>					
            <li class="img_3"><img src="<%=path %>/styles/images/banner/banner01.jpg"></li>
            <li class="img_4"><img src="<%=path %>/styles/images/banner/banner02.jpg"></li>
            <li class="img_1"><img src="<%=path %>/styles/images/banner/banner03.jpg"></li>
            <li class="img_2"><img src="<%=path %>/styles/images/banner/banner04.jpg"></li>
            <li class="img_5"><img src="<%=path %>/styles/images/banner/banner05.jpg"></li>
        </ul>
        <a href="javascript:;" id="btn_prev"></a>
        <a href="javascript:;" id="btn_next"></a>
    </div>
    <div class="flicking_con">
            <div class="flicking_inner">
            <a href="">1</a>
            <a href="">2</a>
            <a href="">3</a>
            <a href="">4</a>
            <a href="">5</a>
         </div>
    </div>
    </div>
    <div class="idx_top">
    	<div class="idx_logo"></div>
        <div class="idx_car_o"><a href="#"></a></div>
        <div class="idx_car">
        	<h1>奥迪 A3 （进口）<br/>1.4TFSI 手动 </h1>
            <a href="#" class="idx_car_a">更改车型</a>
        </div>        
    </div>
    <div class="idx_nav">
        <ul>
            <li><a href="<%=path + Constants.ROOT %>/order/service?type=1" rel="services"><img src="<%=path %>/styles/images/idx_nav1.png">日常保养</a></li>
            <li><a href="<%=path + Constants.ROOT %>/order/service?type=2" rel="services"><img src="<%=path %>/styles/images/idx_nav2.png">更换电瓶</a></li>
            <li><a href="<%=path + Constants.ROOT %>/order/service?type=3" rel="services"><img src="<%=path %>/styles/images/idx_nav3.png">更换刹车片</a></li>
            <li><a href="<%=path + Constants.ROOT %>/order/service?type=4" rel="services" title="加装PM2.5空调滤膜"><img src="<%=path %>/styles/images/idx_nav4.png">加装空调滤膜</a></li>
            <li><%-- <a href="<%=path + Constants.ROOT %>/order/service?type=5" rel="services"><img src="<%=path %>/styles/images/idx_nav5.png">更换轮胎</a> --%></li>
            <li></li>
        </ul>
    </div>
    <div class="idx_tel">客服热线：400-898-9988</div>
</div>
<div class="tc_bg"></div>
<div class="tcmain">
    <div class="close"></div>
    <h1>我的车型库</h1>
    <ul>
    </ul>
    <a href="<%=path %>/<%=Constants.ROOT %>/car/sel" class="other">其它车型</a>
</div>
<!-- loading -->
<div class="loading">
    <div class="loadbox">
        <div class="loadtext">loading...</div>
        <div class="circle1"></div>
        <div class="circle2"></div>
    </div>
</div>
<!-- /loading -->
</body>
</html>

