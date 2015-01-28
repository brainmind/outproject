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
</head>
<body>
<div class="wapper">
	<div class="submit_order">
    	<h1 class="my_order">我的订单</h1>
        <p class="my_order_num"><strong>订单编号：</strong>201409876678912</p>
        <div class="add_top">
    	<div class="add_logo order_img"><img src="<%=path %>/styles/images/10.jpg"></div>
        <div class="order_font">
        	<table width="100%" border="0">
              <tr>
                <td colspan="4" class="car_name">广汽本田 传祺 1.8L旗舰版</td>
              </tr>
              <tr>
                <td colspan="4" class="full_care">大保养服务</td>
              </tr>
              <tr>
                <td colspan="4" height="10"></td>
              </tr>
              <tr>
                <td>联系人：</td>
                <td class="nametel">王犟</td>
                <td>手机号：</td>
                <td class="nametel">18600755941</td>
              </tr>
            </table>
        </div>      
	</div>
    <p class="my_order_num my_order_address"><strong>地址：</strong>北京市海淀区恩济西园中澳写字西二门A208室</p>
    <div class="intotal">
    	<table width="100%" border="0">
          <tr>
            <td width="80%">总金额：</td>
            <td width="20%"><span>150</span>元</td>
          </tr>
          <tr>
            <td>优惠：</td>
            <td><span>－5</span>元</td>
          </tr>
          <tr>
            <td><strong>需支付：</strong></td>
            <td><span>145</span>元</td>
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
    <a href="<%=path %>/<%=Constants.ROOT %>/order/finishorder" class="pay">确认支付</a>
    </div>
</div>
</body>
</html>