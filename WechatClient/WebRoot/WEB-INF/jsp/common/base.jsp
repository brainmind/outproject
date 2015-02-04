<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.wechat.client.utils.*" %>
<%
	String path = request.getContextPath();
%>
<link rel="stylesheet" type="text/css" href="<%=path %>/styles/css/reset.min.css">
<link rel="stylesheet" type="text/css" href="<%=path %>/styles/css/style.css">
<script type="text/javascript" src="<%=path %>/common/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="<%=path %>/common/js/jquery.event.drag-1.5.min.js"></script>
<script type="text/javascript" src="<%=path %>/common/js/jquery.touchSlider.js"></script>
<script type="text/javascript" src="<%=path %>/common/js/zepto.min.js"></script>
<script type="text/javascript" src="<%=path %>/common/js/show.js"></script>
<script type="text/javascript" src="<%=path %>/common/cookie/wxchatclient-cookie-1.0.js"></script>
<script type="text/javascript" src="<%=path %>/common/business/wxchatclient-dialog-1.0.js"></script>
<script type="text/javascript" src="<%=path %>/common/business/wxchatclient-pageload-1.0.js"></script>
<script type="text/javascript">
var contextPath = "<%=path %>";
var projectRoot = "<%=Constants.ROOT %>";
</script>