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
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">
$(function(){
	var carType = WxchatClient.currentCarType();
	$("td.car_name").html(carType.brand+" "+carType.sername+" "+carType.car);
	var logo = carType.logourl == "" ? "<%=path %>/styles/images/idx_logo.png" : carType.logourl;
	$("div.add_logo > img").attr("src", logo);
	
	$.ajax({
		url:"<%=path + Constants.ROOT %>/order/getOrder.json?orderId=${orderid }",
		type:"get",
		dataType:"json",
		success:function(r){
			if(r && $.type(r) == "object"){
				r.contact.length<5 ? $("#contact").html(r.contact) :$("#contact").html(r.contact.substring(0,3)+'...');
				$("#mobile").html(r.mobile);
				$("#address").html("<strong>地址：</strong>" + r.address);
				var totalPrice = 0;
				var commdoties = r.commodities;
				if(commdoties && $.type(commdoties) == "array"){
					for(var i=0; i<commdoties.length; i++){
						var commdoty = commdoties[i];
						var commPrice = parseFloat(isNaN(commdoty.total_price)?"0":commdoty.total_price);
						totalPrice += commPrice;
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
				$("#real_price").html((totalPrice-5).toFixed(2));
			}
		}
	});
	
	
	$.ajax({
		url:"<%=path %>/pay/config?url="+window.location.href,
		type:"get",
		dataType:"json",
		success:function(r){
			if(r && r.code && r.code == "200"){
				wx.config({
				    debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
				    appId: r.appid, // 必填，公众号的唯一标识
				    timestamp:r.timestamp, // 必填，生成签名的时间戳
				    nonceStr: r.noncestr, // 必填，生成签名的随机串
				    signature: r.signature,// 必填，签名，见附录1
				    jsApiList: ["chooseWXPay"] //必填，需要使用的JS接口列表，所有JS接口列表见附录2
				});
				
				wx.ready(function(){
				    // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，
				    // 则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
					WxchatClient.Dialog.show("微信JS验证成功！");
				});
				
				wx.error(function(res){
				    // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
					WxchatClient.Dialog.show("微信JS验证失败！"+res);
				});
			}
		}
	});
});

function surePay(){
}
</script>
</head>
<body>
<div class="wapper">
	<div class="submit_order">
    	<h1 class="my_order">我的订单</h1>
        <p class="my_order_num"><strong>订单编号：</strong><span>${order_number }</span></p>
        <div class="add_top">
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
                <td class="fol"><span class="fol">联系人：</span></td>
                <td class="nametel fol" id="contact">王犟</td>
                <td class="fol ml20">手机号：</td>
                <td class="nametel fol" id="mobile">18600755941</td>
              </tr>
            </table>
        </div>      
	</div>
    <p class="my_order_num my_order_address" id="address"><strong>地址：</strong></p>
    <div class="intotal">
    	<table width="100%" border="0">
          <tr>
            <td width="80%">总金额：</td>
            <td width="20%"><span id="total_price">150</span>元</td>
          </tr>
          <tr>
            <td>优惠：</td>
            <td><span>－5</span>元</td>
          </tr>
          <tr>
            <td><strong>需支付：</strong></td>
            <td><span id="real_price">145</span>元</td>
          </tr>
        </table>
    </div>
    <div class="payment">
    	<div class="payments"><strong>支付方式</strong></div>
        <ul>
        	<li><a href="javascript:;"><strong>微信支付</strong></a></li>
            <li><a href="javascript:;"><strong>银行卡支付</strong></a></li>
        </ul>
    </div>
    <a href="javascript:surePay();" rel="<%=path + Constants.ROOT %>/order/history" class="pay">确认支付</a>
    </div>
</div>
</body>
</html>