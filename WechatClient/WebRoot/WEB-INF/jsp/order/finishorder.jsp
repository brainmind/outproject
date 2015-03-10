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
	$.ajax({
		url:"<%=path + Constants.ROOT %>/order/myOrders.json",
		type:"get",
		dataType:"json",
		success:function(r){
			if(r && r.orders && $.type(r.orders) == "array"){
				var moreDiv = $("div.more_content");
				for(var i=0; i<r.orders.length; i++){
					var order = r.orders[i];
					if(i==0){
						viewOrderDetail(order.orderid, order);
					}
					var orderNumber = $(document.createElement("p"));
					orderNumber.addClass("my_order_num");
					orderNumber.html("<strong>订单编号：</strong>" + order.order_number);
					moreDiv.append(orderNumber);
					var contentDiv = $(document.createElement("div"));
					contentDiv.addClass("add_top more");
					var contentLink = $(document.createElement("a"));
					contentLink.attr("id", order.orderid);
					contentLink.data("order", order);
					contentLink.on("click", function(){
						var $this = $(this);
						var order_id = $this.attr("id");
						var pre_order_id = $("input[type=hidden][name=orderId]").val();
						if(pre_order_id != order_id){
							var orderData = $this.data("order");
							showStatAndLog(orderData, true, $this);
						}
						return false;
					});
					contentDiv.append(contentLink);
					moreDiv.append(contentDiv);
					contentLink.html("<div class=\"add_logo order_img\"><img src=\""+order.brand_logo+"\"></div>");
					var content = $(document.createElement("div"));
					content.addClass("order_font");
					contentLink.append(content);
					var carlabel = order.car_label;
					if(order.car_label.length > 14){
						carlabel = order.car_label.substring(0, 14)+"...";
					}
					var orderContact = order.contact;
					if(order.contact.length > 4){
						orderContact = order.contact.substring(0, 4)+"...";
					}
					content.html("<table width=\"100%\" border=\"0\"><tr>"+
                    "<td colspan=\"4\" class=\"car_name\">"+carlabel+"</td></tr>"+
                  	"<tr><td colspan=\"4\" class=\"full_care\">大保养服务</td></tr>"+
                  	"<tr><td colspan=\"4\" height=\"8\"></td></tr>"+
                  	"<tr><td>联系人：</td><td class=\"nametel\">"+orderContact+"</td><td>手机号：</td>"+
                  	"<td class=\"nametel\">"+order.mobile+"</td></tr></table>");
				}
			}
		}
	});
});

function viewOrderDetail(id, order){
	var carlabel = order.car_label;
	if(carlabel.length > 14){
		carlabel = carlabel.substring(0, 14)+"...";
	}
	var orderContact = order.contact;
	if(order.contact.length > 4){
		orderContact = order.contact.substring(0, 4)+"...";
	}
	$("td.car_name").first().html(carlabel);
	$("#contact").html(orderContact);
	$("#mobile").html(order.mobile);
	$("input[type=hidden][name=orderId]").val(id);
	$("input[type=hidden][name='order_number']").val(order.order_number);
	$("#order_number").html("<strong>订单编号：</strong>" + order.order_number);
	$("div.order_img > img").attr("src", order.brand_logo);
	showStatAndLog(order, false);
}

function showStatAndLog(order,isShow, p){
	var saldiv = $(document);
	if(isShow){
		saldiv = $("#statusandlog_flag");
		if(saldiv[0]){
		}else{
			saldiv = $("#statusandlogs").clone();
			saldiv.attr("id", "statusandlog_flag");
		}
		p.parent().after(saldiv);
		$("html,body").animate({scrollTop: p.offset().top}, 500);
		$("a.ensure", saldiv).attr("href", "javascript:void(0);");
		$("a.ensure", saldiv).on("click", function(){
			recommend(order.orderid);
			return false;
		});
	}
	$("#address", saldiv).html("<strong>地址：</strong>" + order.address);
	var stat = order.state?parseInt(order.state):1;
	$("div.status_processes > ul > li", saldiv).each(function(i){
		if(stat==100 && i==1){
			$(this).addClass("changable");
		}
		if(stat==300 && i==2){
			$(this).addClass("changable");
		}
	});
	$("div.status_processes > div", saldiv).removeClass(function(){
		if(isNaN(stat) || stat == 1){
			$(this).removeClass("half");
		}else if(stat == 100){
			$(this).toggleClass("half");
		}else if(stat == 300){
			$(this).toggleClass("hundred_percent");
		}
	});
	var logs = order.logs;
	var logUl = $("div.content > ul", saldiv);
	logUl.html("<li>订单日志</li>");
	if(logs && logs.length > 0){
		for(var i=0; i<logs.length; i++){
			var log = logs[i];
			var li = $(document.createElement("li"));
			logUl.append(li);
			var oDate = DUtil.format(new Date(log.create_time), "yyyy-MM-dd");
			var oTime = DUtil.format(new Date(log.create_time), "hh:mm");
			li.html("<dl><dd>"+oDate+"</dd><dd>"+oTime+"</dd><dd>"+log.operate_detail+"</dd></dl>");
		}
	}
}

function getOrderDetail(){
	var order_id = $("input[type=hidden][name=orderId]").val();
	window.location.href="<%=path + Constants.ROOT%>/order/ready?orderId="+order_id;
}

function recommend(id){
	var order_id = "";
	if(id && id != null && id != ""){
		order_id = id;
	}else{
		order_id = $("input[type=hidden][name=orderId]").val();
	}
	window.location.href="<%=path + Constants.ROOT %>/order/recommend?orderId="+order_id;
}
</script>
</head>
<body class="bg01">
<div class="wapper">
	<div class="submit_order" id="myorderdetail">
    	<h1 class="my_order">我的订单</h1>
    	<input type="hidden" name="orderId" value=""/>
        <p class="my_order_num" id="order_number"><strong>订单编号：</strong></p>
       <div class="add_top more">
      	 <a href="javascript:getOrderDetail();">
            <div class="add_logo order_img"><img src="<%=path %>/styles/images/10.jpg"></div>
            <div class="order_font">
                <table width="100%" border="0">
                  <tr>
                    <td colspan="4" class="car_name"></td>
                  </tr>
                  <tr>
                    <td colspan="4" class="full_care">大保养服务</td>
                  </tr>
                  <tr>
                    <td colspan="4" height="10"></td>
                  </tr>
                  <tr>
                    <td>联系人：</td>
                    <td class="nametel" id="contact"></td>
                    <td>手机号：</td>
                    <td class="nametel" id="mobile"></td>
                  </tr>
                </table>
            </div>      
            </a>
	</div>
	<div id="statusandlogs">
    	<p class="my_order_num my_order_address" id="address"><strong>地址：</strong></p>
   	   <div class="now">
	     	<p class="status"><strong>订单状态：</strong></p>
	        <div class="status_process">
	        	<div class="status_processes">
	            	<ul>
	                	<li><img src="<%=path %>/styles/images/status.png"><span>已预约</span></li>
	                    <li class="changable"><img src="<%=path %>/styles/images/status_off.png"><span>配货中</span></li>
	                    <li class="changable"><img src="<%=path %>/styles/images/status_off.png"><span>服务完成</span></li>
	                </ul>
	                <div class="clearfix half"></div> <!--class为half的时候表示正在配货，class为hundred_percent时，表示服务已完成！-->
	            </div>
	            <div class="assessment"><a href="javascript:recommend();" class="ensure">立即评价</a></div>
	        </div>
	     </div>
       <div class="clear"></div>
       <div class="order_log">
		<div class="content">
        	<div class="up"><img src="<%=path %>/styles/images/up.png"></div>
            <ul>
            	<li>订单日志</li>
            </ul>
        </div>
		</div>
     </div>
     <a href="javascript:void(0);" class="address click_more">更多历史订单<img src="<%=path %>/styles/images/click_more.png"></a>
        <div class="bg01 more_content" style="background:#ffffff;">
    	</div>
    </div>
</div>
</body>
</html>