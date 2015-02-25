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
						viewOrderDetail(order.orderid);
					}
					var orderNumber = $(document.createElement("p"));
					orderNumber.addClass("my_order_num");
					orderNumber.html("<strong>订单编号：</strong>" + order.order_number);
					moreDiv.append(orderNumber);
					var contentDiv = $(document.createElement("div"));
					contentDiv.addClass("add_top more");
					var contentLink = $(document.createElement("a"));
					contentLink.attr("href", "javascript:viewOrderDetail('"+order.orderid+"');");
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
					content.html("<table width=\"100%\" border=\"0\"><tr>"+
                    "<td colspan=\"4\" class=\"car_name\">"+carlabel+"</td></tr>"+
                  	"<tr><td colspan=\"4\" class=\"full_care\">大保养服务</td></tr>"+
                  	"<tr><td colspan=\"4\" height=\"10\"></td></tr>"+
                  	"<tr><td>联系人：</td><td class=\"nametel\">"+order.contact+"</td><td>手机号：</td>"+
                  	"<td class=\"nametel\">"+order.mobile+"</td></tr></table>");
				}
			}
		}
	});
});

function viewOrderDetail(id){
	$.ajax({
		url:"<%=path + Constants.ROOT %>/order/getOrder.json?orderId="+id,
		type:"get",
		dataType:"json",
		success:function(r){
			if(r && $.type(r) == "object"){
				var order = r.baseBean;
				$("#contact").html(r.contact);
				$("#mobile").html(r.mobile);
				$("#address").html("<strong>地址：</strong>" + r.address);
				$("input[type=hidden][name='order_number']").val(r.orderNumber);
				$("#order_number").html("<strong>订单编号：</strong>" + r.orderNumber);
				var stat = order.stat?0:parseInt(order.stat);
				$("div.status_processes > ul > li").each(function(i){
					if(stat >= i){
						$(this).addClass("changable");
					}
				});
				$("div.status_processes > div").removeClass(function(){
					if(isNaN(stat) || stat == 0){
						$(this).removeClass("half");
					}else if(stat == 1){
						$(this).toggleClass("half");
					}else if(stat == 2){
						$(this).toggleClass("hundred_percent");
					}
				});
			}
		}
	});
}
</script>
</head>
<body class="bg01">
<div class="wapper">
	<div class="submit_order">
    	<h1 class="my_order">我的订单</h1>
        <p class="my_order_num" id="order_number"><strong>订单编号：</strong></p>
       <div class="add_top more">
      	 <a href="javascript:;">
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
            <div class="assessment"><a href="<%=path %>/<%=Constants.ROOT %>/order/recommend" class="ensure">立即评价</a></div>
        </div>
     </div>
       <div class="clear"></div>
       <div class="order_log">
		<div class="content">
        	<div class="up"><img src="<%=path %>/styles/images/up.png"></div>
            <ul>
            	<li>订单日志</li>
                <li>
                      <dl>
                       	 <dd>2014.12.16 </dd>
                        <dd>13:55 </dd>
                        <dd>已完成此订单</dd>
                      </dl>
                </li>
                <li>
          			<dl>
                        <dd>2014.12.16 </dd>
                        <dd>13:55 </dd>
                        <dd>技师已确认此订单</dd>
                      </dl>
                </li>
                <li>
          			<dl>
                        <dd>2014.12.16 </dd>
                        <dd>13:55 </dd>
                        <dd>提交订单</dd>
                      </dl>
                </li>
            </ul>
        </div>
     </div>
     <a href="javascript:;" class="address click_more">更多历史订单<img src="<%=path %>/styles/images/click_more.png"></a>
        <div class="bg01 more_content">
    	</div>
    </div>
</div>
</body>
</html>