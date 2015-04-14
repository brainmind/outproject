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
<link rel="stylesheet" type="text/css" href="<%=path %>/styles/css/style.css">
<link rel="stylesheet" type="text/css" href="<%=path %>/styles/css/slider-pic.css">
<script type="text/javascript" src="<%=path %>/common/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="<%=path %>/common/js/jquery.event.drag-1.5.min.js"></script>
<script type="text/javascript" src="<%=path %>/common/js/jquery.touchSlider.js"></script>
<script type="text/javascript">
$(document).ready(function () {
	$.ajax({
		url:"<%=path + Constants.ROOT %>/car/my.json",
		type:"get",
		dataType:"json",
		success:function(r){
			if(r && r.cars){
				var carsUl = $("#carslist");
				var cars = r.cars;
				for(var i=0; i<cars.length; i++){
					var imgUrl = cars[i]["brand_logo"];
					var label = cars[i]["label"];
					carsUl.append("<li id=\"cardetailid"+i+"\"><table width=\"100%\" border=\"0\" class=\"import_warp\">"+
		                      "<tr><td class=\"pics\"><img src=\""+imgUrl+"\"/></td>"+
		                      "<td class=\"fonts\">"+label+"</td></tr>"+
		                      "</table></li>");
					$("#cardetailid"+i).data("records", cars[i]["records"]);
				}
				renderCarList();
			}
		}
	});
});

function renderCarList(){
	$(".slider-main_image").touchSlider({
		flexible : true,
		speed : 200,
		btn_prev : $("#slider-btn_prev"),
		btn_next : $("#slider-btn_next"),
		paging : $(".slider-flicking_con a"),
		counter : function (e) {
			var li = $("#cardetailid" + (e.current-1));
			var records = li.data("records");
			var recordUl = $("div.car_style > ul");
			if(records && records.length > 0){
				for(var i=0; i<records.length; i++){
					var id = records[i]["record_id"];
					var createTime = records[i]["create_time"];
					var info = records[i]["record_info"];
					recordUl.append("<li data-id=\""+id+"\"><dl><dd>"+createTime+"</dd><dd>"+info+"</dd></dl></li>");
				}
				recordUl.children("li").on("click", function(){
					var id = $(this).attr("data-id");
					window.location.href = "<%=path + Constants.ROOT %>/car/report?recordId="+id;
					return false;
				});
			}else{
				recordUl.empty();
			}
		}
	});
}
</script>
</head>
<body class="bg01">
<div class="wapper bg01">
	<div class="submit_order">
        <h1 class="my_order">我的车型库</h1>
        <div class="slider-main_visual">
	        <div class="slider-main_image bgfff">
	            <img src="<%=path %>/styles/images/bg_pos.jpg" class="slider-xx">
	            <ul class="import" id="carslist">					
	            </ul>
	            <a href="javascript:;" id="slider-btn_prev"></a>
	            <a href="javascript:;" id="slider-btn_next"></a>
	        </div>
        </div>
         <div class="car_style">
         	<ul>
            </ul>
         </div>
    </div>
</div>

</body>
</html>