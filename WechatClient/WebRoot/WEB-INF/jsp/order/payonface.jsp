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
    <div class="bound success">
           <table width="100%" border="0">
              <tr>
                <td class="order_number">订单号：20142198900765430</td>
              </tr>
              <tr>
                <td><div class="success_note">您已成功提交 广本凌派 1.8L 上门保养服务。</div></td>
              </tr>
              <tr>
                <td class="tips01">我们会及时与您联系服务事宜，请您的手机保持畅通。</td>
              </tr>
              <tr>
                <td class="contact">客服热线：400－898－9988</td>
              </tr>
              <tr><td height="30"></td></tr>
            </table>
 			<a href="<%=path %>/<%=Constants.ROOT %>/order/service" class="ensure other">其它服务</a>
    </div>  
</div>
</body>
</html>