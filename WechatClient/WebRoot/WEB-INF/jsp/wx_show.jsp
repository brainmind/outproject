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
<link rel="shortcut icon" href="<%=path %>/styles/images/favicon.ico" type="image/x-icon" />
<style>
	.wapper_show{position:relative; max-width:640px; margin:0 auto; min-height:100%; background:#fff;}
	.wapper_show img{width:100%;height:100%;}
	.wapper_more { max-width:640px; height:30px; text-align:center; font-size:16px; line-height:30px;}
	.wapper_more  a{text-decoration:none; color:#ff5500; font-weight:700;}
</style>
</head>
<body>
<div class="wapper_show">
		<img src="<%=path %>/styles/images/wx_show.jpg"  alt="小马上门" />  
</div>
<div class="wapper_more"><a href="http://eqxiu.com/s/WXar09?eqrcode=1">更多介绍</a></div>
</body>
</html>

