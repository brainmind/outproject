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
    	<span rel="tab" name="carmodelvin" url="<%=path %>/<%=Constants.ROOT %>/car/sel">车型选择</span>
        <span class="click" rel="tab" name="carmodelvin" url="<%=path %>/<%=Constants.ROOT %>/car/toVin">VIN码</span>
    </div>
    <div class="che_pic">
    	<ul>
            <li>
            	<div class="vinbox">
					<p>请输入VIN码，以精确匹配您的爱车</p>
                	<p><input name="" type="text"></p>
                </div>
                <a href="<%=path %>/<%=Constants.ROOT %>/car/selVin" class="ensure">确定</a>
            </li>
        </ul>
    </div>
</div>
</body>
</html>
