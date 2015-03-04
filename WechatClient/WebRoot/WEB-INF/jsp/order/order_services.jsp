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
		url:"<%=path %>/<%=Constants.ROOT %>/order/service.json?type=${type}&carId=${carId}",
		dataType:"json",
		type:"get",
		success:function(r){
			if($.type(r) == "object"){
				var container = $("div.day_list > ul");
				var totalPrice = 0;
				if(r.commodities && r.commodities.length > 0){
					for(var i=0; i<r.commodities.length; i++){
						var commodity = r.commodities[i];
						var categoryid = commodity["category_id"];
						var commprice = 0.00;
						if(!isNaN(commodity["total_price"])){
							commprice = parseFloat(commodity["total_price"]);
							var serviceFee = r["service_fees"][i];
							if(serviceFee && serviceFee.price){
								commprice += parseFloat(serviceFee.price);
							}
							if(commodity.recommand == "1"){
								totalPrice += commprice;
							}
						}
						var li = null;
						var commodityInfo = "<div class=\"day_pic\"><img src=\""+commodity["pic_url"]+"\" height=\"100%\"></div>"+
						"<div class=\"day_title\" data-id=\""+commodity["id"]+"\" category-id=\""+categoryid+"\" data-number=\""+commodity["number"]+"\">"+
						"<h1>"+commodity["label"]+" SN ("+commodity["number"]+")</h1><h2><span>用量：1</span><span>"+commprice.toFixed(2)+"</span></h2></div>";
						if(commodity.recommand != "1"){
							li = $("li[categoryid="+categoryid+"]");
							if(li[0] && li.is("li")){
								var commodityDL = $("li[categoryid="+categoryid+"] > div.day_more > dl");
								var dd = $(document.createElement("dd"));
								dd.html(commodityInfo);
								commodityDL.append(dd);
								continue;
							}
						}
						li = $(document.createElement("li"));
						li.appendTo(container);
						var servName = commodity["category_label"];
						if(servName != "" && servName.length > 2){
							servName = servName.substring(0,2)+"<br/>"+servName.substring(2, servName.length);
						}
						li.attr("type", "services");
						li.html("<div class=\"xz\" style=\"display:block;\"></div><div class=\"day_name\">"+servName+"</div>"+
								"<div id=\"commodity_cur"+categoryid+"\">"+commodityInfo+"</div><div class=\"day_more\"><dl></dl></div>"+
								"<input type=\"hidden\" name=\"commodities.checked\" value=\"1\"/>"+
								"<input type=\"hidden\" name=\"commodities.id\" value=\""+commodity["id"]+"\"/>"+
								"<input type=\"hidden\" name=\"commodities.label\" value=\""+commodity["label"]+"\"/>"+
								"<input type=\"hidden\" name=\"commodities.number\" value=\""+commodity["number"]+"\"/>"+
								"<input type=\"hidden\" name=\"commodities.price\" value=\""+commprice+"\"/>"+
								"<input type=\"hidden\" name=\"commodities.category_id\" value=\""+commodity["category_id"]+"\"/>");
						li.attr("dataid", commodity["id"]);
						li.attr("typeid", commodity["type"]);
						li.attr("recommend", commodity.recommand);
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
						li.attr("type", "fees");
						li.html("<div class=\"xd\" style=\"display:block;\"></div><div class=\"day_name\" title=\""+serviceFee["title"]+"\">"+serviceFee["title"]+"</div>"+
								"<div class=\"day_pic\"><img src=\"<%=path %>/styles/images/7.jpg\" height=\"100%\"></div>"+
								"<div class=\"day_title\"><h1>"+serviceFee["title"]+"工时费</h1><h2><span></span>&nbsp;<span>"+price+"</span></h2></div>"+
								"<input type=\"hidden\" name=\"service_fees.checked\" value=\"1\"/>"+
								"<input type=\"hidden\" name=\"service_fees.type\" value=\""+serviceFee["type"]+"\"/>"+
								"<input type=\"hidden\" name=\"service_fees.title\" value=\""+serviceFee["title"]+"\"/>"+
								"<input type=\"hidden\" name=\"service_fees.price\" value=\""+price+"\"/>"+
								"<input type=\"hidden\" name=\"service_fees.category_id\" value=\""+serviceFee["category_id"]+"\"/>");
						li.attr("typeid", serviceFee["type"]);
						li.attr("categoryid", serviceFee["category_id"]);
						if(serviceFee["category_id"] != 0){
							li.hide();
						}
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
		var logo = carType.logourl == "" ? "<%=path %>/styles/images/idx_logo.png" : carType.logourl;
		$("div.add_logo > img").attr("src", logo);
	}
});

function applyService(){
	var selnum = $("div.xz:visible");
	var feenum = $("div.xd:visible");
	if((selnum == false || selnum.length == 0) && (feenum == false || feenum.length == 0)){
		WxchatClient.Dialog.show("请选择至少一项服务项目.");
		return;
	}
	document.serviceForm.submit();
}
</script>
</head>
<body>
<form action="<%=path + Constants.ROOT %>/order/to" method="post" name="serviceForm">
<input type="hidden" name="type" value="${type }" />
<input type="hidden" name="car_id" value="${carId }" />
<div class="wapper">
	<div class="add_top">
    	<div class="add_logo"><img src="<%=path %>/styles/images/4.png"></div>
        <div class="add_car add_cars">
        	<h1>奥迪 A3 （进口）<br/>1.4TFSI 手动 </h1>
            <a href="#" class="idx_car_a" backurl="/order/service_1q1_type=${type}">更改车型</a>
        </div>      
	</div>
	<div class="day_t"><span>保养项目</span>&nbsp;&nbsp;总价：<span id="totalprice">0.00</span>元 (含工时费）</div>
    <div class="day_list">
    	<ul>
    	</ul>
        <a href="<%=path %>/<%=Constants.ROOT %>/car/selItem" class="addpro"><img src="<%=path %>/styles/images/2.png">添加新项</a>
    </div>
</div>
<div class="checkbox">
    <div class="check">
        <input name="isSelfService" type="checkbox" value="1" id="isSelfServiceid"><label for="isSelfServiceid">自带配件</label>
    </div>
    <a href="javascript:applyService();" class="res_ser">预约服务</a>
</div>
</form>
<!-- 车库选择界面    #########  begin  ############-->
<div class="tc_bg"></div>
<div class="tcmain">
    <div class="close"></div>
    <h1>我的车型库</h1>
    <ul>
        <li >本田 歌诗图 2.4L 2013年产</li>
    </ul>
    <a href="<%=path %>/<%=Constants.ROOT %>/car/sel?backurl=/order/service_1q1_type=${type}" class="other">其它车型</a>
</div>
<!-- 车库选择界面   #########  end  ############ -->
</body>
</html>