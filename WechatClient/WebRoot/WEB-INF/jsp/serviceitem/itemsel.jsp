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
	<div class="add_top">
    	<div class="add_logo"><img src="<%=path %>/styles/images/4.png"></div>
        <div class="add_car">
        	<h1>奥迪 A3 （进口）<br/>1.4TFSI 手动 </h1>
        </div>      
	</div>
	<div class="add_tj">保养项目－添加新项</div>
    <ul class="add_an">
    	<li><img src="<%=path %>/styles/images/idx_nav3.png">更换刹车片</li>
        <li><img src="<%=path %>/styles/images/idx_nav4.png">加装滤芯</li>
        <li><img src="<%=path %>/styles/images/idx_nav2.png">更换电瓶</li>
        <li><img src="<%=path %>/styles/images/idx_nav5.png">更换轮胎</li>
    </ul>
    <div class="add_btn">
    	<a href="<%=path %>/<%=Constants.ROOT %>/order/service">确定</a>
        <a href="javascript:;">取消</a>
    </div>
</div>
</body>
</html>