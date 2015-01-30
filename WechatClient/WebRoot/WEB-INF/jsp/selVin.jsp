<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.wechat.client.utils.*"%>
<%
	String path = request.getContextPath();
%>
<ul>
	<li>
		<div class="che_vin">
			<h1>
				VIN码：<span id="vinspanid">BC789nnc78ujkln</span>
			</h1>
			<h1>您的爱车型号是：</h1>
			<dl>
				<dd dataid="4455">
					<img src="<%=path%>/styles/images/1.jpg">
					<p><span>广汽本田传祺2014款</span><span>1.6T手自动一体版</span></p>
				</dd>
				<dd dataid="5689">
					<img src="<%=path%>/styles/images/1.jpg">
					<p><span>广汽本田传祺2014款</span><span>1.6T手自动一体版</span></p>
				</dd>
			</dl>
			<h2>
				<a href="<%=path%>/<%=Constants.ROOT%>/car/sel">不是我的汽车型号</a>
			</h2>
		</div> <a href="<%=path%>/<%=Constants.ROOT%>/order/service" class="ensure">确定</a>
	</li>
</ul>
