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
	var logo = carType.logourl == "" ? "<%=path %>/styles/images/null.png" : carType.logourl;
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
});

function surePay(){
	if (typeof WeixinJSBridge != "undefined"){
		var realPrice = parseFloat($("#real_price").text())*100;
		var goodBody = $("#goodsbody").html();
		$.ajax({
			url:"<%=path %>/pay/prepay",
			type:"post",
			data:"out_trade_no=${order_number }&total_fee="+realPrice+"&good_body="+goodBody,
			dataType:"json",
			success:function(r){
				if(r && r.code && r.code == "200"){
					WeixinJSBridge.invoke("getBrandWCPayRequest", {
				           "appId" : r.appid,     //公众号名称，由商户传入     
				           "timeStamp": r.timestamp,  //时间戳，自1970年以来的秒数     
				           "nonceStr" : r.noncestr,   //随机串     
				           "package" : r.packages,     
				           "signType" : "SHA1",         //微信签名方式:     
				           "paySign" : r.sign //微信签名 
				       },
				       function(res){     
				           if(res.err_msg == "get_brand_wcpay_request:ok") {
				        	   //使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。
				        	   WxchatClient.Dialog.show("订单支付成功！");
				        	   registPayInfo(realPrice, 0, "");
				           }else if(res.err_msg == "get_brand_wcpay_request:cancel"){
				        	   WxchatClient.Dialog.show("订单支付失败！");
				        	   registPayInfo(realPrice, 1, "用户取消");
				           }else if(res.err_msg == "get_brand_wcpay_request:fail"){
				        	   WxchatClient.Dialog.show("订单支付失败！");
				        	   registPayInfo(realPrice, 1, "支付失败");
				           }
				       }
				   );
				}else{
					WxchatClient.Dialog.show("订单支付失败！");
				}
			}
		});
	}else{
		alert("请使用微信浏览器打开.");
	}
}

function registPayInfo(amount, state, reason){
	$.ajax({
	   url:"<%=path + Constants.ROOT %>/order/payinfo",
	   data:"orderId=${orderid }&bank_sequence=${order_number }&amount="+amount+"&state="+state+"&reason="+reason,
	   type:"get",
	   dataType:"json",
	   success:function(r){
		   WxchatClient.Dialog.show("服务确认已支付！");
	   }
   });
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
                <td colspan="4" class="full_care" id="goodsbody">大保养服务</td>
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