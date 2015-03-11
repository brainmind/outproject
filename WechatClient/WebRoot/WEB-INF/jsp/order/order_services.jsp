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
				var commcd = new Array();
				if(r.commodities && r.commodities.length > 0){
					r.commodities.sort(function(a,b){
						return a.category_id>b.category_id?1:-1;
					});
					for(var i=0; i<r.commodities.length; i++){
						var commodity = r.commodities[i];
						var categoryid = commodity["category_id"];
						var commprice = 0.00, sprice = 0.00, ischecked = 0;
						commcd[categoryid] = ischecked;
						if(!isNaN(commodity["total_price"]) && commodity.id != null && commodity.id != ""){
							sprice = parseFloat(commodity["total_price"]);
							ischecked = 1;
							commcd[categoryid] = ischecked;
							var serviceFee = null;
							for(var k=0; k<r["service_fees"].length; k++){
								if(r["service_fees"][k].category_id == categoryid){
									serviceFee = r["service_fees"][k];
								}
							}
							if(serviceFee && serviceFee.price){
								commprice += sprice + parseFloat(serviceFee.price);
							}else{
								commprice = sprice;
							}
						}
						var li = $("li[categoryid="+categoryid+"]");
						var commoditymsg='',dtit = '';
						if(commodity.id==''){
							dtit = '<div class=\"day_title\" style="background:#fff;"';
							commoditymsg ="<h1 style='margin-top:15px;'>"+commodity["label"] +"</h1>";
						}else{
							dtit = '<div class=\"day_title\"';
							commoditymsg ="<h1>"+commodity["label"]+"</h1><h2><span>用量："+commodity["number"]+"</span><span>"+commprice.toFixed(2)+"</span></h2>";
						}
						var commodityInfo = "<div class=\"day_pic\"><img src=\""+commodity["pic_url"]+"\" height=\"100%\"></div>"+
							  dtit +" data-id=\""+commodity["id"]+"\" category-id=\""+categoryid+"\" data-number=\""+commodity["number"]+"\">"+commoditymsg+"</div>";
						if(commodity.recommand != "1" || li[0]){
							if(li[0] && li.is("li")){
								var commodityDL = $("li[categoryid="+categoryid+"] > div.day_more > dl");
								var dd = $(document.createElement("dd"));
								dd.html(commodityInfo);
								commodityDL.append(dd);
								continue;
							}
						}
						totalPrice += sprice;
						li = $(document.createElement("li"));
						li.appendTo(container);
						var servName = commodity["category_label"];
						if(servName != "" && servName.length > 2){
							servName = servName.substring(0,2)+"<br/>"+servName.substring(2, servName.length);
						}
						li.attr("type", "services");
						var commodityxz = '';
						if(commodity.id==''){
							commodityxz = '<div class=\"xz\"></div>';
						}else{
							commodityxz = '<div class=\"xz\" style=\"display:block;\"></div>';
						}
						li.html(commodityxz + "<div class=\"day_name\">"+servName+"</div>"+
								"<div id=\"commodity_cur"+categoryid+"\">"+commodityInfo+"</div><div class=\"day_more\"><dl></dl></div>"+
								"<input type=\"hidden\" name=\"commodities.checked\" value=\""+ischecked+"\"/>"+
								"<input type=\"hidden\" name=\"commodities.id\" value=\""+commodity["id"]+"\"/>"+
								"<input type=\"hidden\" name=\"commodities.label\" value=\""+commodity["label"]+"\"/>"+
								"<input type=\"hidden\" name=\"commodities.number\" value=\""+commodity["number"]+"\"/>"+
								"<input type=\"hidden\" name=\"commodities.price\" value=\""+sprice+"\"/>"+
								"<input type=\"hidden\" name=\"commodities.category_id\" value=\""+commodity["category_id"]+"\"/>");
						li.attr("dataid", commodity["id"]);
						li.attr("typeid", commodity["type"]);
						li.attr("recommend", commodity.recommand);
						li.attr("categoryid", commodity["category_id"]);
					}
				}
				if(r["service_fees"] && r["service_fees"].length > 0){
					r["service_fees"].sort(function(a,b){
						return a.category_id>b.category_id?1:-1;
					});
					var firstLi = null, lastLi = null;
					for(var i=0; i<r["service_fees"].length; i++){
						var serviceFee = r["service_fees"][i];
						var li = $(document.createElement("li"));
						li.appendTo(container);
						var price = 0.00;
						if(!isNaN(serviceFee["price"])){
							price = parseFloat(serviceFee["price"]).toFixed(2);	
							if((serviceFee["category_id"] == 0 || commcd[serviceFee["category_id"]] == "1")){
								totalPrice += parseFloat(serviceFee["price"]);
							}
						}
						li.attr("type", "fees");
						var feeTitle = serviceFee["category_id"]=="0"?"工时费":serviceFee["title"];
						li.html("<div class=\"xd\" style=\"display:block;\"></div><div class=\"day_name\" title=\""+feeTitle+"\">"+feeTitle+"</div>"+
								"<div class=\"day_pic\"><img src=\"<%=path %>/styles/images/7.jpg\" height=\"100%\"></div>"+
								"<div class=\"day_title\" style='background:#fff;'><h1>"+serviceFee["title"]+"</h1><h2><span></span>&nbsp;<span>"+price+"</span></h2></div>"+
								"<input type=\"hidden\" name=\"service_fees.checked\" value=\""+(serviceFee["category_id"] == "0" ? "1" : commcd[serviceFee["category_id"]])+"\"/>"+
								"<input type=\"hidden\" name=\"service_fees.type\" value=\""+serviceFee["type"]+"\"/>"+
								"<input type=\"hidden\" name=\"service_fees.title\" value=\""+serviceFee["title"]+"\"/>"+
								"<input type=\"hidden\" name=\"service_fees.price\" value=\""+price+"\"/>"+
								"<input type=\"hidden\" name=\"service_fees.category_id\" value=\""+serviceFee["category_id"]+"\"/>");
						li.attr("typeid", serviceFee["type"]);
						li.attr("fee-checked", serviceFee["category_id"] == "0" ? "1" : commcd[serviceFee["category_id"]]);
						li.attr("categoryid", serviceFee["category_id"]);
						if(serviceFee["category_id"] != 0){
							li.hide();
						}
						if(i == 0) firstLi = li;
						if(i+1 == r["service_fees"].length) lastLi = li;
					}
					if(firstLi != lastLi){
						lastLi.after(firstLi);
					}
				}
				$("#totalprice").html(totalPrice.toFixed(2));
				initRenderingCommandotyService();
			}
		}
	});
	
	var	backurl = "";
	var carType = WxchatClient.currentCarType();
	if(carType && carType != null && carType != ""){
		var carName = carType.brand;
		var serName = carType.sername;
		var car = carType.car;
		$("div.add_cars > h1").first().html(carName+" "+serName+"<br/>"+car);
		var logo = carType.logourl == "" ? "<%=path %>/styles/images/idx_logo.png" : carType.logourl;
		$("div.add_logo > img").attr("src", logo);
		$("input[type=hidden][name=brand]").val(carName);
		$("input[type=hidden][name=sername]").val(serName);
		$("input[type=hidden][name=car]").val(car);
		$("input[type=hidden][name=logourl]").val(carType.logourl);
	}else{
		window.location.href = contextPath+projectRoot+"/car/sel?backurl="+backurl;
		return false;
	}		
	
	
	var isSelf = $("input[type=checkbox][name=isSelfService]");
	isSelf.on("click",function(){
		var $this = $(this);
		isSelfService($this);
	});
	isSelfService(isSelf);
});

function isSelfService(isSelf){
	if(isSelf.attr("checked") == "checked"){
		$("div.day_list > ul > li[type=services]").hide();
		$("div.day_list > ul > li > input[type=hidden][name='commodities.checked']").val(0);
		var price = 0.00, totalPrice = 0.00;
		$("div.day_list > ul > li > input[type=hidden][name='service_fees.price']").each(function(){
			price += parseFloat($(this).val());
		});
		var tp = $("#totalprice");
		totalPrice = price;
		tp.html(totalPrice.toFixed(2));
		$("div.day_list > ul > li[type=fees]").show();
		$("div.day_list > ul > li > input[type=hidden][name='service_fees.checked']").val(1);
	}else{
		$("div.day_list > ul > li[type=services]").show();
		var price = 0.00, totalPrice = 0.00;
		var toDoorFee = $("div.day_list > ul > li[type=fees][categoryid=0] > input[type=hidden][name='service_fees.price']").val();
		$("div.day_list > ul > li[type=services]").each(function(){
			var sli = $(this);
			var category_id = sli.attr("categoryid");
			var commodities_checked = $("input[type=hidden][name='commodities.checked']", sli);
			var isChecked = $("div.xz", sli).is(":hidden");
			if(isChecked){
				commodities_checked.val(0);
				$("div.day_list > ul > li[type=fees][categoryid="+category_id+"] > input[type=hidden][name='service_fees.checked']").val(0);
			}else{
				var serviceFee = $("div.day_list > ul > li[type=fees][categoryid="+category_id+"] > input[type=hidden][name='service_fees.price']").val();
				var commodiFee = $("input[type=hidden][name='commodities.price']", sli).val();
				commodities_checked.val(1);
				price += parseFloat(serviceFee) + parseFloat(commodiFee);
			}
		});
		var tp = $("#totalprice");
		totalPrice = parseFloat(toDoorFee) + price;
		tp.html(totalPrice.toFixed(2));
		$("div.day_list > ul > li[type=fees][categoryid!=0]").hide();
	}
}

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
<input type="hidden" name="brand" value="" />
<input type="hidden" name="sername" value="" />
<input type="hidden" name="car" value="" />
<input type="hidden" name="logourl" value="" />
<div class="wapper">
	<div class="add_top">
    	<div class="add_logo"><img src="<%=path %>/styles/images/idx_logo.png"></div>
        <div class="add_car add_cars">
        	<h1>&nbsp;</h1>
            <a href="#" class="idx_car_a" backurl="/order/service_1q1_type=${type}">更改车型</a>
        </div>      
	</div>
	<div class="day_t"><span>保养项目</span>&nbsp;&nbsp;总价：<span id="totalprice">0.00</span>元 (含工时费）</div>
    <div class="day_list">
    	<ul>
    	</ul>
        <a href="<%=path + Constants.ROOT %>/car/selItem?carId=${carId }&type=${type}" class="addpro"><img src="<%=path %>/styles/images/2.png">添加新项</a>
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
    <ul></ul>
    <a href="<%=path %>/<%=Constants.ROOT %>/car/sel?backurl=/order/service_1q1_type=${type}" class="other">其它车型</a>
</div>
<!-- 车库选择界面   #########  end  ############ -->
</body>
</html>