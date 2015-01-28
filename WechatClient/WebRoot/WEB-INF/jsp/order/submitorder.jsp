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
        <div class="day_t">保养配件&nbsp;&nbsp;总价：<span>328.00</span>元 (含工时费）</div>
        <div class="day_list order_list">
    	<ul>
        	<li>
            	<div class="xz"></div>
            	<div class="day_name">更换<br/>机油</div>
                <div class="day_pic"><img src="<%=path %>/styles/images/5.jpg" height="100%"></div>
                <div class="no_arrow">
                	<div class="h_border">
                		<h1>嘉实多磁护合成机油 5W-30 SN (4L)</h1>
                    </div>
                    <h2><span class="border_left">用量：1</span><span>180.00</span></h2>
                </div>           
          	</li>
            <li>
            	<div class="xz"></div>
            	<div class="day_name">更换<br/>机油</div>
                <div class="day_pic"><img src="<%=path %>/styles/images/6.jpg" height="100%"></div>
                <div class="no_arrow">
                	<div class="h_border">
                		<h1>嘉实多磁护合成机油 5W-30 SN (4L)</h1>
                    </div>
                    <h2><span class="border_left">用量：1</span><span>180.00</span></h2>
                </div>    
          	</li>
        </ul>
    </div>
    <a href="<%=path %>/<%=Constants.ROOT %>/order/payonline" class="pay">在线支付（立减5元）</a>
    <a href="<%=path %>/<%=Constants.ROOT %>/order/payonface" class="pay pay_unlock">当面付款</a>
    </div>
</div>
</body>
</html>