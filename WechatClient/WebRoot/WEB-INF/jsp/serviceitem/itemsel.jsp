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
$(function(){
	var carType = WxchatClient.currentCarType();
	if(carType && carType != null && carType != ""){
		var carName = carType.brand;
		var serName = carType.sername;
		var car = carType.car;
		var logourl = carType.logourl;
		$("div.add_logo > img").attr("src", logourl);
		$("div.add_car > h1").first().html(carName+" "+serName+"<br/>"+car);
	}
	
	$.ajax({
		url:"<%=path %>/<%=Constants.ROOT %>/order/service.json",
		dataType:"json",
		type:"get",
		success:function(r){
			var serviceul = $("ul.add_an");
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
						li.attr("typeid", serviceFee.type);
						li.attr("category_id", serviceFee.category_id);
						li.appendTo(serviceul);
					}
					renderServices();
				}
			}
		}
	});
});

function addService(){
	var type="";
	$("ul.add_an > li.pro_click").each(function(){
		var $li = $(this);
		type += (type == ""?"":",") + $li.attr("category_id");
	});
	var carId = WxchatClient.currentCarType().id;
	window.location.href = "<%=path + Constants.ROOT %>/order/service?type="+type+"&carId"+carId;
}
</script>
</head>
<body>
<div class="wapper">
	<div class="add_top">
    	<div class="add_logo"><img src="<%=path %>/styles/images/4.png"></div>
        <div class="add_car">
        	<h1>奥迪 A3 （进口）<br/>1.4TFSI 手动 </h1>
        </div>      
	</div>
	<div class="add_tj">保养项目－添加新项</div>
    <ul class="add_an">
    </ul>
    <div class="add_btn">
    	<a href="javascript:addService();">确定</a>
        <a href="javascript:window.history.back();">取消</a>
    </div>
</div>
</body>
</html>