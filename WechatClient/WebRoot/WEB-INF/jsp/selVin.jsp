<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.wechat.client.utils.*"%>
<%
	String path = request.getContextPath();
%>
<div class="che_vin">
	<h1>
		VIN码：<span id="vinspanid"></span>
	</h1>
	<h1>您的爱车型号是：</h1>
	<dl id="carlistdlid">
		<dd dataid="4455">
				<img src="<%=path%>/styles/images/1.jpg">
			<p><span>广汽本田传祺2014款</span><span>1.6T手自动一体版</span></p>
		</dd>
	</dl>
<h2>
	<a href="<%=path%>/<%=Constants.ROOT%>/car/sel">不是我的汽车型号</a>
</h2>
