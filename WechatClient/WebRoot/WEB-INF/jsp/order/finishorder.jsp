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
<body class="bg01">
<div class="wapper">
	<div class="submit_order">
    	<h1 class="my_order">我的订单</h1>
        <p class="my_order_num"><strong>订单编号：</strong>201409876678912</p>
       <div class="add_top more">
      	 <a href="javascript:;">
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
            </a>
	</div>
    	<p class="my_order_num my_order_address"><strong>地址：</strong>北京市海淀区恩济西园中澳写字西二门A208室</p>
   	   <div class="now">
     	<p class="status"><strong>订单状态：</strong></p>
        <div class="status_process">
        	<div class="status_processes">
            	<ul>
                	<li><img src="<%=path %>/styles/images/status.png"><span>已预约</span></li>
                    <li><img src="<%=path %>/styles/images/status.png"><span>配货中</span></li>
                    <li class="changable"><img src="<%=path %>/styles/images/status_off.png"><span>服务完成</span></li>
                </ul>
                <div class="clearfix half"></div> <!--class为half的时候表示正在配货，class为hundred_percent时，表示服务已完成！-->
            </div>
            <div class="assessment"><a href="<%=path %>/<%=Constants.ROOT %>/order/recommend" class="ensure">立即评价</a></div>
        </div>
     </div>
       <div class="clear"></div>
       <div class="order_log">
		<div class="content">
        	<div class="up"><img src="<%=path %>/styles/images/up.png"></div>
            <ul>
            	<li>订单日志</li>
                <li>
                      <dl>
                       	 <dd>2014.12.16 </dd>
                        <dd>13:55 </dd>
                        <dd>已完成此订单</dd>
                      </dl>
                </li>
                <li>
          			<dl>
                        <dd>2014.12.16 </dd>
                        <dd>13:55 </dd>
                        <dd>技师已确认此订单</dd>
                      </dl>
                </li>
                <li>
          			<dl>
                        <dd>2014.12.16 </dd>
                        <dd>13:55 </dd>
                        <dd>提交订单</dd>
                      </dl>
                </li>
            </ul>
        </div>
     </div>
     <a href="javascript:;" class="address click_more">更多历史订单<img src="<%=path %>/styles/images/click_more.png"></a>
        <div class="bg01 more_content">
        	<p class="my_order_num"><strong>订单编号：</strong>201409876678912</p>
            <div class="add_top more">
                <a href="javascript:;">
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
                </a>														
			</div>
            <p class="my_order_num"><strong>订单编号：</strong>201409876678912</p>
            <div class="add_top more">
                 <a href="javascript:;">
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
                    </a>
            </div>
    </div>
    </div>
</div>
</body>
</html>