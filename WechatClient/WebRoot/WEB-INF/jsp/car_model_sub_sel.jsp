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
<jsp:include page="common/base.jsp" />
</head>
<body>
<div class="wapper">
    <div class="che_btn">
    	<span class="click" rel="tab" name="carmodelvin" url="<%=path %>/<%=Constants.ROOT %>/car/sel">车型选择</span>
        <span rel="tab" name="carmodelvin" url="<%=path %>/<%=Constants.ROOT %>/car/toVin">VIN码</span>
    </div>
    <div class="che_pic">
    	<ul>
        	<li>
            	<dl class="che_cx">
                	<dd><a href="<%=path %>/<%=Constants.ROOT %>/order/service">凌派</a></dd>
                    <dd><a href="<%=path %>/<%=Constants.ROOT %>/order/service">凌派</a></dd>
                    <dd><a href="<%=path %>/<%=Constants.ROOT %>/order/service">凌派</a></dd>
                    <dd><a href="<%=path %>/<%=Constants.ROOT %>/order/service">凌派</a></dd>
                    <dd><a href="<%=path %>/<%=Constants.ROOT %>/order/service">凌派</a></dd>
                </dl>
            </li>
        </ul>
    </div>
</div>
</body>
</html>