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
	<div class="neir r_dz">
    	<p><strong>请选择地区</strong></p>
        <div class="add_sel">
        <select name="">
            <option selected>北京市</option>
            <option>上海市</option>
        </select>
        <select name="">
            <option selected>海淀区</option>
            <option>普陀区</option>
        </select>
        </div>
        <div class="add_text"><input name="" type="text" value="详细地址"></div>
        <p>目前仅提供北京地区五环内及回龙观天通苑地区上门服务</p>
        <p><strong>预约服务时间</strong></p>
    </div>
	<div class="neir ser_nr"> 
    	<ul>
        	<li class="bor">
            	<select name="">
                    <option selected>12月&nbsp;28日&nbsp;星期三</option>
                    <option>12月&nbsp;29日&nbsp;星期四</option>
                </select>
                <select name="">
                    <option selected>上午10:00－11:00</option>
                    <option>上午11:00－12:00</option>
                </select>
            </li>
        	<li class="bor">
            	<p class="name">姓名</p>
                <p class="text"><input name="" type="text"></p>
            </li>
            <li class="bor">
            	<p class="name">手机号</p>
                <p class="text"><input name="" type="text"></p>
            </li>
            <li>
            	<p class="time"><span>29</span>秒后再次获取</p>
                <a href="javascript:;" class="yzm">获取验证码</a>
            </li>
            <li>
            	<p class="ball"></p>
                <p class="entry">验证码</p>
            </li>
            <li class="bor"><input name="" type="text"></li>
        </ul>    	
    </div>
    <a href="<%=path %>/<%=Constants.ROOT %>/order/ready" class="ensure">提交预约</a>
</div>
</body>
</html>
