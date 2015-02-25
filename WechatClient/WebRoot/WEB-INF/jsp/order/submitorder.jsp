<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="yz" uri="/WEB-INF/tld/yz.tld" %>
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
	$("td.car_name").html(carType.brand+" "+carType.sername+"<br/>"+carType.car);
	var logo = carType.logourl == "" ? "<%=path %>/styles/images/idx_logo.png" : carType.logourl;
	$("div.add_logo > img").attr("src", logo);
	
	$.ajax({
		url:"<%=path + Constants.ROOT %>/order/getOrder.json?orderId=${orderId }",
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
				var commdoties = r.commodities;
				var container = $("div.day_list > ul");
				var totalPrice = 0;
				if(commdoties && $.type(commdoties) == "array"){
					for(var i=0; i<commdoties.length; i++){
						var commdoty = commdoties[i];
						var li = $(document.createElement("li"));
						container.append(li);
						var commPrice = parseFloat(isNaN(commdoty.total_price)?"0":commdoty.total_price);
						totalPrice += commPrice;
						li.html("<div class=\"xd\"></div>"+
				            	"<div class=\"day_name\">"+commdoty.category_label+"</div>"+
				                "<div class=\"day_pic\"><img src=\""+commdoty.pic_url+"\" height=\"100%\"></div>"+
				                "<div class=\"no_arrow\">"+
				                "	<div class=\"h_border\">"+
				                "		<h1>"+commdoty.label+" SN (4L)</h1>"+
				                "    </div>"+
				                "    <h2><span class=\"border_left\">用量："+commdoty.number+"</span><span>"+commPrice.toFixed(2)+"</span></h2>"+
				                "</div>");
					}
				}
				var serviceFees = r.service_fees;
				if(serviceFees && $.type(serviceFees) == "array"){
					for(var i=0; i<serviceFees.length; i++){
						var fee = serviceFees[i];
						var feePrice = parseFloat(isNaN(fee.price)?"0":fee.price);
						totalPrice += feePrice;
					}
				}
				$("#total_price").html(totalPrice.toFixed(2));
			}
		}
	});
});

function payonline(){
	var form = document.payForm;
	$(form).attr("action", "<%=path + Constants.ROOT %>/order/payonline");
	form.submit();
}

function payonface(){
	var form = document.payForm;
	$(form).attr("action", "<%=path + Constants.ROOT %>/order/payonface");
	form.submit();
}
</script>
</head>
<body>
<div class="wapper">
	<form name="payForm" method="get">
		<input type="hidden" name="orderid" value="${orderId }"/>
		<input type="hidden" name="order_number" value=""/>
	</form>
	<div class="submit_order">
    	<h1 class="my_order">我的订单</h1>
        <p class="my_order_num" id="order_number"><strong>订单编号：</strong></p>
        <div class="add_top">
    	<div class="add_logo order_img"><img src="<%=path %>/styles/images/10.jpg"></div>
        <div class="order_font">
        	<table width="100%" border="0">
              <tr>
                <td colspan="4" class="car_name">广汽本田 传祺 1.8L旗舰版</td>
              </tr>
              <tr>
                <td colspan="4" class="full_care">${serviceName }</td>
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
	</div>
    <p class="my_order_num my_order_address" id="address"><strong>地址：</strong></p>
    <div class="day_t">保养配件&nbsp;&nbsp;总价：<span id="total_price">0.00</span>元 (含工时费）</div>
        <div class="day_list order_list">
	    	<ul>
	        </ul>
    	</div>
    <a href="javascript:payonline();" class="pay">在线支付（立减5元）</a>
    <a href="javascript:payonface()" class="pay pay_unlock">当面付款</a>
    </div>
</div>
</body>
</html>