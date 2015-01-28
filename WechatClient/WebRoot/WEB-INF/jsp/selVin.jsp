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
				<div class="che_vin">
                    <h1>VIN码：<span>BC789nnc78ujkln</span></h1>
                    <h1>您的爱车型号是：</h1>
                    <dl>
                        <dd><img src="<%=path %>/styles/images/1.jpg"><p>广汽本田传祺2014款1.6T手自动一体版</p></dd>
                        <dd><img src="<%=path %>/styles/images/1.jpg"><p>广汽本田传祺2014款1.6T手自动一体版</p></dd>
                    </dl>
                    <h2><a href="<%=path %>/<%=Constants.ROOT %>/car/sel">不是我的汽车型号</a></h2>
				</div>
                <a href="<%=path %>/<%=Constants.ROOT %>/order/service" class="ensure">确定</a>
            </li>
        </ul>
    </div>
</div>
</body>
</html>