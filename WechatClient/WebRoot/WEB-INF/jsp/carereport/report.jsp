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
<link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=path %>/styles/css/reset.min.css">
<link rel="stylesheet" type="text/css" href="<%=path %>/styles/css/slider-pic.css">
<script type="text/javascript" src="<%=path %>/common/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="<%=path %>/common/js/jquery.event.drag-1.5.min.js"></script>
<script type="text/javascript" src="<%=path %>/common/js/jquery.touchSlider.js"></script>
<script type="text/javascript" src="<%=path %>/common/js/touchTouch.jquery.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	<%-- var imgs = ["<%=path %>/upload/images/test.jpg", "<%=path %>/styles/images/banner/banner02.jpg", "<%=path %>/styles/images/banner/banner03.jpg"];
	var mainSliderDiv = $(document.createElement("div"));
	var imgContainer = $(document.createElement("div"));
	imgContainer.addClass("slider-main_image");
	mainSliderDiv.append(imgContainer);
	mainSliderDiv.addClass("slider-main_visual");
	$(document.body).append(mainSliderDiv);
	var img1 = $(document.createElement("img"));
	img1.attr("src", imgs[0]);
	img1.addClass("slider-xx");
	imgContainer.append(img1);
	var ul = $(document.createElement("ul"));
	imgContainer.append(ul);
	var alist = "";
	for(var i=0; i<imgs.length; i++){
		ul.append("<li><img src=\""+imgs[i]+"\"/></li>");
		alist += "<a href=\"\">"+(i+1)+"</a>";
	}
	imgContainer.append("<a href=\"javascript:;\" id=\"slider-btn_prev\"></a><a href=\"javascript:;\" id=\"slider-btn_next\"></a>");
	var paging = $(document.createElement("div"));
	paging.addClass("slider-flicking_con");
	var span = $(document.createElement("span"));
	paging.append(span);
	span.html(alist);
	mainSliderDiv.append(paging);
	 --%>
	$(".slider-main_image").touchSlider({
		flexible : true,
		speed : 200,
		btn_prev : $("#slider-btn_prev"),
		btn_next : $("#slider-btn_next"),
		paging : $(".slider-flicking_con a"),
		counter : function (e) {
			$(".slider-flicking_con a").removeClass("slider-on").eq(e.current-1).addClass("slider-on");
		}
	});
	/* $(".main_image").bind("mousedown", function() {
		$dragBln = false;
	});
	$(".main_image").bind("dragstart", function() {
		$dragBln = true;
	});
	$(".main_image a").click(function() {
		if($dragBln) {
			return false;
		}
	}); */
	
	$('#showResourcePic a').touchTouch();
});
</script>
<style type="text/css">
	.thumbnail {position:relative; width:100%; height:61px;}
	.thumbnail a{display:block; width:61px; height:61px; float:left;}
</style>
</head>
<body>
<div id="showResourcePic" class="thumbnail">
<a href="<%=path %>/styles/images/banner/banner01.jpg" style="background-image:url(<%=path %>/styles/images/banner/banner01.jpg)"></a>
<a href="<%=path %>/styles/images/banner/banner02.jpg" style="background-image:url(<%=path %>/styles/images/banner/banner01.jpg)"></a>
<a href="<%=path %>/styles/images/banner/banner03.jpg" style="background-image:url(<%=path %>/styles/images/banner/banner01.jpg)"></a>
<a href="<%=path %>/styles/images/banner/banner04.jpg" style="background-image:url(<%=path %>/styles/images/banner/banner01.jpg)"></a>
</div>

<div class="slider-main_visual">
    <div class="slider-main_image">
        <img src="<%=path %>/styles/images/banner/banner01.jpg" class="slider-xx">
        <ul>					
            <li><img src="<%=path %>/styles/images/banner/banner01.jpg"></li>
            <li><img src="<%=path %>/styles/images/banner/banner02.jpg"></li>
            <li><img src="<%=path %>/styles/images/banner/banner03.jpg"></li>
            <li><img src="<%=path %>/styles/images/banner/banner04.jpg"></li>
            <li><img src="<%=path %>/styles/images/banner/banner05.jpg"></li>
        </ul>
        <a href="javascript:;" id="slider-btn_prev"></a>
        <a href="javascript:;" id="slider-btn_next"></a>
    </div>
    <div class="slider-flicking_con">
         <span>
            <a href="">1</a>
            <a href="">2</a>
            <a href="">3</a>
            <a href="">4</a>
            <a href="">5</a>
         </span>
    </div>
</div>
</body>
</html>