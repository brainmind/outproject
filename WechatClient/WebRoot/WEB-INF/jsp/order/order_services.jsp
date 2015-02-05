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
<jsp:include page="../common/base.jsp" />
<script type="text/javascript">
$(document).ready(function () {
	$.ajax({
		url:"<%=path %>/<%=Constants.ROOT %>/order/service.json",
		dataType:"json",
		type:"get",
		success:function(r){
			if($.type(r) == "object"){
				var container = $("div.day_list > ul");
				var totalPrice = 0;
				if(r.commodities && r.commodities.length > 0){
					for(var i=0; i<r.commodities.length; i++){
						var commodity = r.commodities[i];
						var li = $(document.createElement("li"));
						li.appendTo(container);
						var price = 0.00;
						if(!isNaN(commodity["total_price"])){
							price = parseFloat(commodity["total_price"]).toFixed(2);
							totalPrice += parseFloat(commodity["total_price"]);
						}
						var servName = commodity["category_label"];
						if(servName != "" && servName.length > 2){
							servName = servName.substring(0,2)+"<br/>"+servName.substring(2, servName.length);
						}
						li.html("<div class=\"xz\" style=\"display:block;\"></div><div class=\"day_name\">"+servName+"</div>"+
								"<div class=\"day_pic\"><img src=\""+commodity["pic_url"]+"\" height=\"100%\"></div>"+
								"<div class=\"day_title\"><h1>"+commodity["label"]+" SN ("+commodity["number"]+")</h1><h2><span>用量：1</span><span>"+price+"</span></h2></div>");
						li.attr("dataid", commodity["id"]);
						li.attr("typeid", commodity["type"]);
						li.attr("categoryid", commodity["category_id"]);
					}
				}
				if(r["service_fees"] && r["service_fees"].length > 0){
					for(var i=0; i<r["service_fees"].length; i++){
						var serviceFee = r["service_fees"][i];
						var li = $(document.createElement("li"));
						li.appendTo(container);
						var price = 0.00;
						if(!isNaN(serviceFee["price"])){
							price = parseFloat(serviceFee["price"]).toFixed(2);
							totalPrice += parseFloat(serviceFee["price"]);
						}
						li.html("<div class=\"xz\" style=\"display:block;\"></div><div class=\"day_name\" title=\""+serviceFee["title"]+"\">工时费</div>"+
								"<div class=\"day_pic\"><img src=\"<%=path %>/styles/images/7.jpg\" height=\"100%\"></div>"+
								"<div class=\"day_title\"><h1>小马上门服务</h1><h2><span></span>&nbsp;<span>"+price+"</span></h2></div>");
						li.attr("typeid", serviceFee["type"]);
						li.attr("categoryid", serviceFee["category_id"]);
					}
				}
				$("#totalprice").html(totalPrice.toFixed(2));
				initRenderingCommandotyService();
			}
		}
	});
	
	var carType = WxchatClient.currentCarType();
	if(carType && carType != null && carType != ""){
		var carName = carType.brand;
		var serName = carType.sername;
		var car = carType.car;
		$("div.add_cars > h1").first().html(carName+" "+serName+"<br/>"+car);
	}
});
</script>
</head>
<body>
<div class="wapper">
	<div class="add_top">
    	<div class="add_logo"><img src="<%=path %>/styles/images/4.png"></div>
        <div class="add_car add_cars">
        	<h1>奥迪 A3 （进口）<br/>1.4TFSI 手动 </h1>
            <a href="#">更改车型</a>
        </div>      
	</div>
	<div class="day_t"><span>保养项目</span>&nbsp;&nbsp;总价：<span id="totalprice">328.00</span>元 (含工时费）</div>
    <div class="day_list">
    	<ul></ul>
        <a href="<%=path %>/<%=Constants.ROOT %>/car/selItem" class="addpro"><img src="<%=path %>/styles/images/2.png">添加新项</a>
    </div>
</div>
<div class="checkbox">
    <div class="check">
        <input name="isSelfService" type="checkbox" value="" id="isSelfServiceid"><label for="isSelfServiceid">自带配件</label>
    </div>
    <a href="<%=path %>/<%=Constants.ROOT %>/order/to" class="res_ser">预约服务</a>
</div>
</body>
</html>