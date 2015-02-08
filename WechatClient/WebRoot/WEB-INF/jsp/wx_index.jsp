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
	$(".main_visual").hover(function(){
			$("#btn_prev,#btn_next").fadeIn();
		},function(){
			$("#btn_prev,#btn_next").fadeOut();
		});
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
	if(carType && carType != null && carType != ""){
		$(".idx_car_o").hide();
		$(".idx_car").show();
		var carName = carType.brand;
		var serName = carType.sername;
		var car = carType.car;
		$("div.idx_car > h1").first().html(carName+" "+serName+"<br/>"+car);
	}
	
	$.ajax({
		url:"<%=path %>/<%=Constants.ROOT %>/order/service.json",
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
	});
});
</script>
</head>
<body>
<div class="wapper">
    <div class="main_visual">
    <div class="main_image">
        <img src="<%=path %>/styles/images/banner01.jpg" class="xx">
        <ul>					
            <li class="img_3"><img src="<%=path %>/styles/images/banner01.jpg"></li>
            <li class="img_4"><img src="<%=path %>/styles/images/banner01.jpg"></li>
            <li class="img_1"><img src="<%=path %>/styles/images/banner01.jpg"></li>
            <li class="img_2"><img src="<%=path %>/styles/images/banner01.jpg"></li>
            <li class="img_5"><img src="<%=path %>/styles/images/banner01.jpg"></li>
        </ul>
        <!--<a href="javascript:;" id="btn_prev"></a>
        <a href="javascript:;" id="btn_next"></a>-->
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
            
        </ul>
    </div>
    <div class="idx_tel">客服热线：400-898-9988</div>
</div>
<div class="tc_bg"></div>
<div class="tcmain">
    <div class="close"></div>
    <h1>我的车型库</h1>
    <ul>
        <li >本田 歌诗图 2.4L 2013年产</li>
        <li>本田 歌诗图 2.4L 2013年产</li>
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

